package pt.tmg.cbd.lab2.ex3.d;

import org.bson.Document;
import org.bson.conversions.Bson;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.Projections;

public class Ex3c {
    public static void main(String[] args) {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");

        MongoDatabase database = mongoClient.getDatabase("cbd");
        MongoCollection<Document> collection = database.getCollection("restaurants");

        // 7. Encontre os restaurantes que obtiveram uma ou mais pontuações (score) entre [80 e 100].

        System.out.println("\n------------------- Ex 7 ------------------\n");

        // db.restaurants.find( { 'grades': { $elemMatch: { 'score': { $gt: 80, $lt: 100 } } } } )

        Bson filter = Filters.elemMatch("grades", Filters.and(Filters.gt("score", 80), Filters.lt("score", 100)));

        MongoCursor<Document> cursor = collection.find(filter).iterator();

        try {
            while (cursor.hasNext()) {
                System.out.println(cursor.next().toJson());
                System.out.println();
            }
        } finally {
            cursor.close();
        }


        // 13. Liste o nome, a localidade, o score e gastronomia dos restaurantes que alcançaram sempre pontuações inferiores ou igual a 3. 

        System.out.println("\n------------------- Ex 13 ------------------\n");

        // db.restaurants.find( { 'grades.score': { $not: { $gt: 3 } } } , { _id: 0, nome: 1, localidade: 1, 'grades.score': 1, gastronomia: 1 } )

        // 14. Liste o nome e as avaliações dos restaurantes que obtiveram uma avaliação com um grade "A", um score 10 na data "2014-08-11T00: 00: 00Z" (ISODATE).

        System.out.println("\n------------------- Ex 14 ------------------\n");

        // 17. Liste nome, gastronomia e localidade de todos os restaurantes ordenando por ordem crescente da gastronomia e, em segundo, por ordem decrescente de localidade.

        System.out.println("\n------------------- Ex 17 ------------------\n");

        // 21. Apresente o número total de avaliações (numGrades) em cada dia da semana.
        
        System.out.println("\n------------------- Ex 21 ------------------\n");

        mongoClient.close();
    }
}


