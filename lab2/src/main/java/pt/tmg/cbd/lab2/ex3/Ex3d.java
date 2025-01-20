package pt.tmg.cbd.lab2.ex3;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;

import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Ex3d {
    private final MongoCollection<Document> collection;

    public Ex3d(MongoCollection<Document> mongoCollection) {
        this.collection = mongoCollection;
    }

    public int countLocalidades() {
        AggregateIterable<Document> result = collection.aggregate(Arrays.asList(
            new Document("$group", new Document("_id", "$localidade")), 
            new Document("$count", "numLocalidades")));

        return result.first().getInteger("numLocalidades");
    }

    public Map<String, Integer> countRestByLocalidade() {
        Map<String, Integer> result = new HashMap<>();
        
        collection.aggregate(Arrays.asList(
                                        Aggregates.group("$localidade", Accumulators.sum("totalRestaurants", 1)),
                                        Aggregates.project(Projections.fields(
                                            Projections.computed("localidade", "$_id"),
                                            Projections.include("totalRestaurants"),
                                            Projections.excludeId()
                                        )))).forEach(item -> result.put(item.getString("localidade"), item.getInteger("totalRestaurants")));

        return result;
    }

    public List<String> getRestWithNameCloserTo(String name) {
        List<String> result = new ArrayList<>();

        Bson filter = Filters.regex("nome", name);
        collection.find(filter).projection(Projections.fields(
                                    Projections.include("nome"), 
                                    Projections.excludeId())
                                    ).forEach(item -> result.add(String.valueOf(item.get("nome"))));

        return result;
    }

    public static void main(String[] args) {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");

        MongoDatabase database = mongoClient.getDatabase("cbd");
        Ex3d ex = new Ex3d(database.getCollection("restaurants"));

        System.out.println("Número de localidades distintas: " + ex.countLocalidades());

        System.out.println("\nNúmero de restaurantes por localidade: ");
        Map<String, Integer> res = ex.countRestByLocalidade();
        for (Map.Entry<String, Integer> entry : res.entrySet()) {
            System.out.printf("-> %s - %s\n", entry.getKey(), entry.getValue());
        }
        
        System.out.println("\nNúmero de restaurantes contendo 'Park' no nome: ");
        List<String> res2 = ex.getRestWithNameCloserTo("Park");
        for (String name : res2) {
            System.out.println("-> " + name);
        }

        mongoClient.close();
    }
}
