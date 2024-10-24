package pt.tmg.cbd.lab2.ex3.d;

import java.util.Scanner;
import java.util.Arrays;
import java.util.List;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
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
        if (userDoc == null) {
            collection.insertOne(new Document()
                .append("_id", new ObjectId())
                .append("user", username)
                .append("requests", Arrays.asList(new Document()
                        .append("product", product)
                        .append("timestamp", timestamp))));
            System.out.println("Product '" + product + "' added for user '" + username + "'.");
        } else {
            List<Document> requests = userDoc.getList("requests", Document.class);
            if (requests.size() >= products) {
                Long oldTimeStamp = getFirstTime(collection, filter);
                System.out.println("ERROR: User " + username + " has exceeded the maximum number of requests in the current timeslot.");
                System.out.println("You can make the next request in " + ((getTime() - oldTimeStamp) / 1000) + " seconds.");
            } else {
                collection.updateOne(filter, Updates.push("requests", new Document()
                    .append("product", product)
                    .append("timestamp", timestamp)));
                System.out.println("Product '" + product + "' added for user '" + username + "'.");
            }
            if (((getTime() - getFirstTime(collection, filter)) / 1000) >= timeslot) {
                collection.updateOne(filter, Updates.pullByFilter(Filters.eq("requests.timestamp", getFirstTime(collection, filter))));
            }
        }

    }

    public static Long getFirstTime(MongoCollection<Document> collection, Bson filter) {
        Bson sort = Sorts.ascending("timestamp");
        Document firstDocument = collection.find(filter).sort(sort).first();
        return Long.parseLong(String.valueOf(firstDocument.get("requests.timestamp")));
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
