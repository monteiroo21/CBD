package pt.tmg.cbd.lab2.ex3.d;

import java.util.Scanner;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class Ex4a {
    private static final int products = 5;
    private static final int timeslot = 30;

    public static void addRequest(String username, String product, Long timestamp, MongoCollection<Document> collection) {
        if (jedis.zcard(username) >= products) {
            System.out.println("ERROR: User " + username + " has exceeded the maximum number of requests in the current timeslot.");
            System.out.println("You can make the next request in " + jedis.ttl(username) + " seconds.");
        } else {
            collection.insertOne(new Document()
                .append("_id", new ObjectId())
                .append("user", username)
                .append("product", product)
                .append("timestamp", timestamp));
            System.out.println("Product '" + product + "' added for user '" + username + "'.");
        }
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
