package pt.tmg.cbd.lab2.ex3.d;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.Updates;

public class Ex4a {
    private static final int products = 5;
    private static final int timeslot = 30;

    public static void addRequest(String username, String product, Long timestamp, MongoCollection<Document> collection) {
        Bson filter = Filters.eq("user", username);
        Document userDoc = collection.find(filter).first();
        long currentTime = getTime(); // Ensure getTime() returns System.currentTimeMillis()
        long timeslotMillis = timeslot * 1000; 
        if (userDoc == null) {
            collection.insertOne(new Document("_id", new ObjectId())
                                        .append("user", username)
                                        .append("requests", Arrays.asList(new Document("product", product)
                                                                                .append("timestamp", timestamp))));
            System.out.println("Product '" + product + "' added for user '" + username + "'.");
        } else {
            // Calculate the threshold timestamp; requests older than this will be removed
            long threshold = currentTime - timeslotMillis;
            
            // Remove requests that are older than the timeslot
            Bson pullFilter = Filters.lt("timestamp", threshold);
            collection.updateOne(filter, Updates.pull("requests", pullFilter));
            
            // Fetch the updated user document
            userDoc = collection.find(filter).first();
            List<Document> requests = userDoc.getList("requests", Document.class);
            int currentRequestCount = (requests != null) ? requests.size() : 0;
            
            if (currentRequestCount < products) {
                // User has not exceeded the maximum number of requests; add the new request
                collection.updateOne(filter, Updates.push("requests", new Document("product", product)
                                            .append("timestamp", timestamp)));
                System.out.println("Product '" + product + "' added for user '" + username + "'.");
            } else {
                // User has reached the maximum number of requests; calculate wait time
                // Find the oldest request timestamp
                Long oldestTimestamp = getFirstTime(collection, filter);
                if (oldestTimestamp != null) {
                    long waitTimeMillis = (oldestTimestamp + timeslotMillis) - currentTime;
                    long waitTimeSeconds = (waitTimeMillis > 0) ? waitTimeMillis / 1000 : 0;
                    System.out.println("ERROR: User " + username + " has exceeded the maximum number of requests in the current timeslot.");
                    System.out.println("You can make the next request in " + waitTimeSeconds + " seconds.");
                } else {
                    // This case should not occur if the schema is consistent, but handle it gracefully
                    System.out.println("ERROR: Unable to determine the next available request time for user '" + username + "'.");
                }
            }
        }
    }

    public static Long getFirstTime(MongoCollection<Document> collection, Bson filter) {
        List<Bson> pipeline = Arrays.asList(
            Aggregates.match(filter),
            Aggregates.unwind("$requests"),
            Aggregates.sort(Sorts.ascending("requests.timestamp")),
            Aggregates.limit(1),
            Aggregates.project(Projections.fields(Projections.excludeId(), Projections.include("requests.timestamp")))
        );

        AggregateIterable<Document> result = collection.aggregate(pipeline);
        Document firstDocument = result.first();

        if (firstDocument != null) {
            Document requests = (Document) firstDocument.get("requests");
            if (requests != null) {
                return requests.getLong("timestamp");
            }
        }

        return null;
    }

    public static Long getTime() {
        return System.currentTimeMillis();
    }

    public static void main( String[] args ) {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");

        MongoDatabase database = mongoClient.getDatabase("cbd");
        MongoCollection<Document> collection = database.getCollection("ex4a");
        Scanner sc = new Scanner(System.in);
        String username, product;
        while (true) {
            System.out.print("Enter the username ('Enter' to quit): ");
            username = sc.nextLine();
            if (username.equals("")) {
                break;
            }
            System.out.print("Enter the product ('Enter' to quit): ");
            product = sc.nextLine();
            if (product.equals("")) {
                break;
            }
            Long timestamp = getTime();
            addRequest(username, product, timestamp, collection);
        }
        sc.close();
        mongoClient.close();
    }
}
