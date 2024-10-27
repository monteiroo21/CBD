package pt.tmg.cbd.lab2.ex3.d;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.Updates;

public class Ex4a {
    private static final int products = 5;
    private static final int timeslot = 30;

    public static void addRequest(String username, String product, Long timestamp, MongoCollection<Document> collection) {
        Bson filter = Filters.eq("user", username);
        Document userDoc = collection.find(filter).first();
        Boolean canAdd = true;
        if (userDoc == null) {
            collection.insertOne(new Document("_id", new ObjectId())
                                        .append("user", username)
                                        .append("requests", Arrays.asList(new Document("product", product)
                                                                                .append("timestamp", timestamp))));
            System.out.println("Product '" + product + "' added for user '" + username + "'.");
        } else {
            if (canAdd) {
                collection.updateOne(filter, Updates.push("requests", new Document("product", product)
                        .append("timestamp", timestamp)));
                System.out.println("Product '" + product + "' added for user '" + username + "'.");
            }
            if (((getTime() - getFirstTime(collection, filter)) / 1000) >= timeslot) {
                collection.updateOne(filter, Updates.pullByFilter(Filters.eq("requests.timestamp", getFirstTime(collection, filter))));
                canAdd = true;
            }
            List<Document> requests = userDoc.getList("requests", Document.class);
            System.out.println(requests.size());
            if (!canAdd || requests.size() >= products) {
                canAdd = false;
                Long oldTimeStamp = getFirstTime(collection, filter);
                System.out.println("ERROR: User " + username + " has exceeded the maximum number of requests in the current timeslot.");
                System.out.println("You can make the next request in " + (timeslot-((getTime() - oldTimeStamp) / 1000)) + " seconds.");
            } 
        }
    }

    public static Long getFirstTime(MongoCollection<Document> collection, Bson filter) {
        Document userDoc = collection.find(filter).first();
    
        if (userDoc == null) {
            System.err.println("User document not found for the given filter.");
            return null; // Or throw an exception based on your application's logic
        }
        
        // Extract the 'requests' array from the user document
        List<Document> requests = userDoc.getList("requests", Document.class);
        
        if (requests == null || requests.isEmpty()) {
            System.err.println("No requests found for the user.");
            return null; // Or handle as per your logic
        }
        
        // Sort the 'requests' array by 'timestamp' in ascending order
        requests.sort(Comparator.comparingLong(doc -> {
            Long ts = doc.getLong("timestamp");
            if (ts == null) {
                System.err.println("Encountered a request without a 'timestamp'.");
                return Long.MAX_VALUE; // Push invalid timestamps to the end
            }
            return ts;
        }));
        
        // Retrieve the first request's timestamp
        Document firstRequest = requests.get(0);
        Long firstTimestamp = firstRequest.getLong("timestamp");
        
        if (firstTimestamp == null) {
            System.err.println("The first request does not contain a 'timestamp'.");
            return null; // Or handle as needed
        }
        
        return firstTimestamp;
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
