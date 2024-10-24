package pt.tmg.cbd.lab2.ex3.d;

import java.util.Scanner;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;

public class Ex4a {
    private static final int products = 5;
    private static final int timeslot = 30;

    public static void addRequest(String username, String product, Long timestamp, MongoCollection<Document> collection) {
        Bson filter = Filters.eq("user", username);
        Long productCount = collection.countDocuments(filter); // update this piece of code if it no longer works
        if (productCount == 0) {
            collection.insertOne(new Document()
                .append("_id", new ObjectId())
                .append("user", username)
                .append("requests", new Document()
                        .append("product", product)
                        .append("timestamp", timestamp)));
            System.out.println("Product '" + product + "' added for user '" + username + "'.");
        } else if (productCount >= products) {
            Long oldTimeStamp = getFirstTime(collection, filter);
            System.out.println("ERROR: User " + username + " has exceeded the maximum number of requests in the current timeslot.");
            System.out.println("You can make the next request in " + ((getTime() - oldTimeStamp) / 1000) + " seconds.");
        } else {
            // I want to add a new product and the timestamp into the collection
            System.out.println("Product '" + product + "' added for user '" + username + "'.");
        }
        if (((getTime() - getFirstTime(collection, filter)) / 1000) >= timeslot) {
            // Remove first product and given timestamp
        }

    }

    public static Long getFirstTime(MongoCollection<Document> collection, Bson filter) {
        Bson sort = Sorts.ascending("timestamp");
        Document firstDocument = collection.find(filter).sort(sort).first();
        return Long.parseLong(String.valueOf(firstDocument.get("timestamp")));
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
