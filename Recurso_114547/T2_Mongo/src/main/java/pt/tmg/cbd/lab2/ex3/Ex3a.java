package pt.tmg.cbd.lab2.ex3;

import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class Ex3a {
    public static void main(String[] args) {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");

        MongoDatabase database = mongoClient.getDatabase("cbd");
        MongoCollection<Document> collection = database.getCollection("restaurants");

        collection.insertOne(new Document()
            .append("_id", new ObjectId())
            .append("address", new Document()
                        .append("building", "1000")
                        .append("coord", Arrays.asList(-23.0000011, 32.0000011))
                        .append("rua", "Broadway")
                        .append("zipcode", "30201"))
            .append("localidade", "Chicago")
            .append("gastronomia", "Delicatessen")
            .append("grades", Arrays.asList(new Document()
                                            .append("date", Date.from(Instant.parse("2013-01-04T00:00:00.000Z")))
                                            .append("grade", "A")
                                            .append("score", 10),
                                            new Document()
                                            .append("date", Date.from(Instant.parse("2014-01-21T00:00:00.000Z")))
                                            .append("grade", "B")
                                            .append("score", 12)))
            .append("nome", "New Restaurant")
            .append("restaurant_id", "40012300"));

        System.out.println("\n############################################\n");
        collection.find(new Document("nome", "New Restaurant")).forEach(doc -> System.out.println(doc.toJson()));
        System.out.println("\n");


        System.out.println("\n############################################\n");

        Bson updateQuery = new Document("$set", new Document("nome", "New Restaurant 1"));
        Bson update = new Document("nome", "New Restaurant");

        collection.updateOne(update, updateQuery);

        collection.find(new Document("nome", "New Restaurant 1")).forEach(doc -> System.out.println(doc.toJson()));
        System.out.println("\n");


        System.out.println("\n############################################\n");

        Bson searchFilter = new Document("localidade", "Chicago");
        collection.find(searchFilter).forEach(doc -> System.out.println(doc.toJson()));
        System.out.println("\n");

        Bson deleteFilter = new Document("nome", "New Restaurant 1");
        collection.deleteMany(deleteFilter);

        mongoClient.close();
    }
}
