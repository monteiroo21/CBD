package pt.tmg.cbd.lab2.ex3;

import org.bson.Document;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Indexes;

public class Ex3b {
    public static void main(String[] args) {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");

        MongoDatabase database = mongoClient.getDatabase("cbd");
        MongoCollection<Document> collection = database.getCollection("restaurants");

        collection.dropIndexes();

        long startTime = System.currentTimeMillis();

        System.out.println("\n---------------- SEARCH LOCALIDADE --------------------\n");
        collection.find(new Document("localidade", "Queens")).forEach(str -> System.out.println(str.toJson()));

        System.out.println("\n---------------- SEARCH GASTRONOMIA --------------------\n");
        collection.find(new Document("gastronomia", "Portuguese")).forEach(str -> System.out.println(str.toJson()));

        System.out.println("\n---------------- SEARCH NOME --------------------\n");
        collection.find(new Document("nome", "Subway")).forEach(str -> System.out.println(str.toJson()));

        long endTime = System.currentTimeMillis();

        System.out.println("\nTime: " + (endTime - startTime));
        System.out.println();

        collection.createIndex(Indexes.ascending("localidade"));
        collection.createIndex(Indexes.ascending("gastronomia"));
        collection.createIndex(Indexes.text("nome"));


        startTime = System.currentTimeMillis();

        System.out.println("\n---------------- SEARCH LOCALIDADE --------------------\n");
        collection.find(new Document("localidade", "Queens")).forEach(str -> System.out.println(str.toJson()));

        System.out.println("\n---------------- SEARCH GASTRONOMIA --------------------\n");
        collection.find(new Document("gastronomia", "Portuguese")).forEach(str -> System.out.println(str.toJson()));

        System.out.println("\n---------------- SEARCH NOME --------------------\n");
        collection.find(new Document("nome", "Subway")).forEach(str -> System.out.println(str.toJson()));

        endTime = System.currentTimeMillis();

        System.out.println("\nTime: " + (endTime - startTime));
        System.out.println();
        mongoClient.close();
    }
}

