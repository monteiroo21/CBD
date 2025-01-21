package pt.tmg.cbd.lab2.ex3;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class Ex3d {
    private final MongoCollection<Document> collection;

    public Ex3d(MongoCollection<Document> mongoCollection) {
        this.collection = mongoCollection;
    }

    public int countLocalidades() {
        Bson group = new Document("$group", new Document("_id", "$localidade"));
        Bson sum = new Document("$count", "numLocalidades");
        return collection.aggregate(Arrays.asList(group, sum)).first().getInteger("numLocalidades");
    }

    public Map<String, Integer> countRestByLocalidade() {
        Map<String, Integer> result = new HashMap<>();

        Bson group = new Document("$group", new Document("_id", "$localidade")
                                                        .append("totalRestaurants", new Document("$sum", 1)));

        Bson project = new Document("$project", new Document("localidade", "$_id")
                                                        .append("totalRestaurants", 1)
                                                        .append("_id", 0));
        
        collection.aggregate(Arrays.asList(group, project)).forEach(item -> result.put(item.getString("localidade"), item.getInteger("totalRestaurants")));

        return result;
    }

    public List<String> getRestWithNameCloserTo(String name) {
        List<String> result = new ArrayList<>();

        Document regexQuery = new Document();
        regexQuery.append("$regex", ".*" + Pattern.quote(name) + ".*");
        Bson filter = new Document("nome", regexQuery);
        Bson projection = new Document("_id", 0).append("nome", 1);
        collection.find(filter).projection(projection).forEach(item -> result.add(String.valueOf(item.get("nome"))));

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
