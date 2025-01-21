package pt.tmg.cbd.lab2.ex3;

import java.util.Arrays;
import java.util.Date;
import java.time.Instant;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class Ex3c {
    public static void main(String[] args) {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");

        MongoDatabase database = mongoClient.getDatabase("cbd");
        MongoCollection<Document> collection = database.getCollection("restaurants");

        // 7. Encontre os restaurantes que obtiveram uma ou mais pontuações (score) entre [80 e 100].

        System.out.println("\n------------------- Ex 7 ------------------\n");

        // db.restaurants.find( { 'grades': { $elemMatch: { 'score': { $gte: 80, $lte: 100 } } } } )

        // Bson filter1 = Filters.elemMatch("grades", Filters.and(Filters.gt("score", 80), Filters.lt("score", 100)));

        Bson filter1 = new Document("grades", new Document("$elemMatch", new Document("score", new Document("$gte", 80).append("$lte", 100))));

        collection.find(filter1).forEach(str -> System.out.println(str.toJson()));


        // 13. Liste o nome, a localidade, o score e gastronomia dos restaurantes que alcançaram sempre pontuações inferiores ou igual a 3. 

        System.out.println("\n------------------- Ex 13 ------------------\n");

        // db.restaurants.find( { 'grades.score': { $not: { $gt: 3 } } } , { _id: 0, nome: 1, localidade: 1, 'grades.score': 1, gastronomia: 1 } )

        Bson projection1 = new Document("_id", 0)
                                    .append("nome", 1)
                                    .append("localidade", 1)
                                    .append("grades.score", 1)
                                    .append("gastronomia", 1);

        // Bson filter2 = Filters.not(Filters.elemMatch("grades", Filters.gt("score", 3)));

        Bson filter2 = new Document("grades.score", new Document("$not", new Document("$gt", 3)));

        collection.find(filter2).projection(projection1).forEach(str -> System.out.println(str.toJson()));

        // 14. Liste o nome e as avaliações dos restaurantes que obtiveram uma avaliação com um grade "A", um score 10 na data "2014-08-11T00: 00: 00Z" (ISODATE).

        System.out.println("\n------------------- Ex 14 ------------------\n");

        // db.restaurants.find( { 'grades': { $elemMatch: { score: 10, grade: 'A', date: ISODate("2014-08-11T00:00:00Z") } } } , { _id: 0, nome: 1, grades: 1 } )

        Bson projection2 = new Document()
                            .append("_id", 0)
                            .append("nome", 1)
                            .append("grades", 1);
    
        // Bson filter3 = Filters.elemMatch("grades", Filters.and(Filters.eq("score", 10), Filters.eq("grade", "A"), Filters.eq("date", Date.from(Instant.parse("2014-08-11T00:00:00Z")))));
        Bson filter3 = new Document("grades", new Document("$elemMatch", new Document("score", 10).append("grade", "A").append("date", Date.from(Instant.parse("2014-08-11T00:00:00Z")))));
        
        collection.find(filter3).projection(projection2).forEach(str -> System.out.println(str.toJson()));

        // 17. Liste nome, gastronomia e localidade de todos os restaurantes ordenando por ordem crescente da gastronomia e, em segundo, por ordem decrescente de localidade.

        System.out.println("\n------------------- Ex 17 ------------------\n");

        // db.restaurants.find( { } , { _id: 0, nome: 1, gastronomia: 1, localidade: 1 } ).sort({ gastronomia: 1, localidade: -1 })

        Bson projection3 = new Document()
                            .append("_id", 0)
                            .append("nome", 1)
                            .append("gastronomia", 1)
                            .append("localidade", 1);

        Bson sortFields = new Document()
                            .append("gastronomia", 1)
                            .append("localidade", -1);


        collection.find().projection(projection3).sort(sortFields).forEach(str -> System.out.println(str.toJson()));

        // 21. Apresente o número total de avaliações (numGrades) em cada dia da semana.
        
        System.out.println("\n------------------- Ex 21 ------------------\n");

        // db.restaurants.aggregate([ { $unwind: "$grades" }, { $group: { _id: { dayOfWeek: { $dayOfWeek: "$grades.date" } }, numGrades: { $sum: 1 } }}, { $project: { _id: 0, dayOfWeek: "$_id.dayOfWeek", numGrades: 1 }} ])

        Bson unwindGrades = new Document("$unwind", "$grades");

        Bson group = new Document("$group", new Document("_id", new Document("dayOfWeek", new Document("$dayOfWeek", "$grades.date")))
                            .append("numGrades", new Document("$sum", 1)));

        Bson projection4 = new Document("$project", new Document("_id", 0)
                                    .append("dayOfWeek", "$_id.dayOfWeek")
                                    .append("numGrades", 1));

        collection.aggregate(Arrays.asList(unwindGrades, group, projection4))
                  .forEach(doc -> System.out.println(doc.toJson()));


        mongoClient.close();
    }
}


