package pt.tmg.cbd.lab2.ex4;

import java.util.Scanner;
import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class Ex4a {
    private static MongoCollection<Document> collection;
    private static final int PRODUCTS = 5;
    private static final int TIMESLOT = 30000;

    public static void addRequest(String username, String product) {
        Long currentTime = System.currentTimeMillis();
        Long cutoffTime = currentTime - TIMESLOT;
        collection.deleteMany(new Document("username", username).append("timestamp", new Document("$lt", cutoffTime)));

        Integer count = (int) collection.countDocuments(new Document("username", username).append("timestamp", new Document("$gte", cutoffTime)));

        if (count >= PRODUCTS) {
            System.out.println("ERROR: User " + username + " has exceeded the maximum number of requests in the current timeslot.");
            Document earliestTime = collection.find(new Document("username", username)).sort(new Document("timestamp", 1)).first();
            double timeLeft = (earliestTime.getLong("timestamp") + TIMESLOT - currentTime) / 1000;
            System.out.println("You can make the next request in " + timeLeft + " seconds.");
        } else {
            Document request = new Document("username", username)
                    .append("product", product)
                    .append("timestamp", currentTime);
            collection.insertOne(request);

            System.out.println("Product '" + product + "' added for user '" + username + "'.");
        }
    }

    public static void main( String[] args ) {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("cbd");
        collection = database.getCollection("ex4");
        collection.drop();

        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("Enter the username ('Enter' to quit): ");
            String username = sc.nextLine();
            if (username.isEmpty()) {
                break;
            }
            System.out.print("Enter the product ('Enter' to quit): ");
            String product = sc.nextLine();
            if (product.isEmpty()) {
                break;
            }

            addRequest(username, product);
        }
        sc.close();
        mongoClient.close();
    }
}
