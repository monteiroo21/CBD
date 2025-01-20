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
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;

public class Ex3c {
    public static void main(String[] args) {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");

        MongoDatabase database = mongoClient.getDatabase("cbd");
        MongoCollection<Document> collection = database.getCollection("restaurants");

        // 7. Encontre os restaurantes que obtiveram uma ou mais pontuações (score) entre [80 e 100].

        System.out.println("\n------------------- Ex 7 ------------------\n");

        // db.restaurants.find( { 'grades': { $elemMatch: { 'score': { $gt: 80, $lt: 100 } } } } )

        Bson filter = Filters.elemMatch("grades", Filters.and(Filters.gt("score", 80), Filters.lt("score", 100)));

        collection.find(filter).forEach(str -> System.out.println(str.toJson()));


        // 13. Liste o nome, a localidade, o score e gastronomia dos restaurantes que alcançaram sempre pontuações inferiores ou igual a 3. 

        System.out.println("\n------------------- Ex 13 ------------------\n");

        // db.restaurants.find( { 'grades.score': { $not: { $gt: 3 } } } , { _id: 0, nome: 1, localidade: 1, 'grades.score': 1, gastronomia: 1 } )

        Bson projectionFields = Projections.fields(
        Projections.include("nome", "localidade", "grades.score", "gastronomia"),
        Projections.excludeId());

        filter = Filters.not(Filters.elemMatch("grades", Filters.gt("score", 3)));

        collection.find(filter).projection(projectionFields).forEach(str -> System.out.println(str.toJson()));

        // 14. Liste o nome e as avaliações dos restaurantes que obtiveram uma avaliação com um grade "A", um score 10 na data "2014-08-11T00: 00: 00Z" (ISODATE).

        System.out.println("\n------------------- Ex 14 ------------------\n");

        // db.restaurants.find( { 'grades': { $elemMatch: { score: 10, grade: 'A', date: ISODate("2014-08-11T00:00:00Z") } } } , { _id: 0, nome: 1, grades: 1 } )

        projectionFields = Projections.fields(
            Projections.include("nome", "grades"),
            Projections.excludeId());
    
        filter = Filters.elemMatch("grades", Filters.and(Filters.eq("score", 10), Filters.eq("grade", "A"), Filters.eq("date", Date.from(Instant.parse("2014-08-11T00:00:00Z")))));
        
        collection.find(filter).projection(projectionFields).forEach(str -> System.out.println(str.toJson()));

        // 17. Liste nome, gastronomia e localidade de todos os restaurantes ordenando por ordem crescente da gastronomia e, em segundo, por ordem decrescente de localidade.

        System.out.println("\n------------------- Ex 17 ------------------\n");

        // db.restaurants.find( { } , { _id: 0, nome: 1, gastronomia: 1, localidade: 1 } ).sort({ gastronomia: 1, localidade: -1 })

        projectionFields = Projections.fields(
            Projections.include("nome", "gastronomia", "localidade"),
            Projections.excludeId());

        Bson sortFields = Sorts.orderBy(
            Sorts.ascending("gastronomia"),
            Sorts.descending("localidade"));


        collection.find().sort(sortFields).projection(projectionFields).forEach(str -> System.out.println(str.toJson()));

        // 21. Apresente o número total de avaliações (numGrades) em cada dia da semana.
        
        System.out.println("\n------------------- Ex 21 ------------------\n");

        // db.restaurants.aggregate([ { $unwind: '$grades' }, { $project: { dayOfWeek: { $dayOfWeek: '$grades.date' } } }, { $group: { _id: '$dayOfWeek', numGrades: { $sum: 1 } } }, { $project: { _id: 0, dayOfWeek: '$_id', numGrades: 1 } }, { $sort: { dayOfWeek: 1 } }] )

        projectionFields = Projections.fields(
            Projections.computed("dayOfWeek", new Document("$dayOfWeek", "$grades.date")));

        Bson projectionsFields2 = Projections.fields(
            Projections.include("numGrades"),
            Projections.computed("dayOfWeek", "$_id"),
            Projections.excludeId());

        collection.aggregate(Arrays.asList(
            Aggregates.unwind("$grades"),
            Aggregates.project(projectionFields),
            Aggregates.group("$dayOfWeek", Accumulators.sum("numGrades", 1)), 
            Aggregates.project(projectionsFields2),
            Aggregates.sort(Sorts.ascending("dayOfWeek")))).forEach(str -> System.out.println(str.toJson()));

        mongoClient.close();
    }
}


