package pt.tmg.cbd.lab2.ex3.d;

import org.bson.Document;
import org.bson.conversions.Bson;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.Projections;

public class Ex3b {
    public static void main(String[] args) {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");

        MongoDatabase database = mongoClient.getDatabase("cbd");
        MongoCollection<Document> collection = database.getCollection("restaurants");

        collection.dropIndexes();

        long startTime = System.currentTimeMillis();

        System.out.println("\n---------------- SEARCH LOCALIDADE --------------------\n");
        Bson projectionFields = Projections.fields(
        Projections.include("nome", "localidade"),
        Projections.excludeId());

        MongoCursor<Document> cursor = collection.find(new Document("localidade", "Queens"))
            .projection(projectionFields).iterator();

        try {
            while (cursor.hasNext()) {
                System.out.println(cursor.next().toJson());
            }
        } finally {
            cursor.close();
        }

        System.out.println("\n---------------- SEARCH GASTRONOMIA --------------------\n");
        projectionFields = Projections.fields(
        Projections.include("nome", "gastronomia"),
        Projections.excludeId());

        cursor = collection.find(new Document("gastronomia", "American"))
            .projection(projectionFields).iterator();

        try {
            while (cursor.hasNext()) {
                System.out.println(cursor.next().toJson());
            }
        } finally {
            cursor.close();
        }

        System.out.println("\n---------------- SEARCH NOME --------------------\n");
        projectionFields = Projections.fields(
        Projections.include("nome"),
        Projections.excludeId());

        cursor = collection.find(new Document("nome", "Subway"))
            .projection(projectionFields).iterator();

        try {
            while (cursor.hasNext()) {
                System.out.println(cursor.next().toJson());
            }
        } finally {
            cursor.close();
        }

        long endTime = System.currentTimeMillis();

        System.out.println("\nTime: " + (endTime - startTime));
        System.out.println();

        collection.createIndex(Indexes.ascending("localidade"));
        collection.createIndex(Indexes.ascending("gastronomia"));
        collection.createIndex(Indexes.text("nome"));

        startTime = System.currentTimeMillis();

        System.out.println("\n---------------- SEARCH LOCALIDADE --------------------\n");
        projectionFields = Projections.fields(
        Projections.include("nome", "localidade"),
        Projections.excludeId());

        cursor = collection.find(new Document("localidade", "Queens"))
            .projection(projectionFields).iterator();

        try {
            while (cursor.hasNext()) {
                System.out.println(cursor.next().toJson());
            }
        } finally {
            cursor.close();
        }

        System.out.println("\n---------------- SEARCH GASTRONOMIA --------------------\n");
        projectionFields = Projections.fields(
        Projections.include("nome", "gastronomia"),
        Projections.excludeId());

        cursor = collection.find(new Document("gastronomia", "American"))
            .projection(projectionFields).iterator();

        try {
            while (cursor.hasNext()) {
                System.out.println(cursor.next().toJson());
            }
        } finally {
            cursor.close();
        }

        System.out.println("\n---------------- SEARCH NOME --------------------\n");
        projectionFields = Projections.fields(
        Projections.include("nome"),
        Projections.excludeId());

        cursor = collection.find(new Document("nome", "Subway"))
            .projection(projectionFields).iterator();

        try {
            while (cursor.hasNext()) {
                System.out.println(cursor.next().toJson());
            }
        } finally {
            cursor.close();
        }

        endTime = System.currentTimeMillis();

        System.out.println("\nTime: " + (endTime - startTime));
        System.out.println();
        mongoClient.close();
    }
}

