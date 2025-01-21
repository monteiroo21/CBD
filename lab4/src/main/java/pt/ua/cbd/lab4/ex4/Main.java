package pt.ua.cbd.lab4.ex4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Session;
import org.neo4j.driver.Values;
import org.neo4j.driver.Driver;

public class Main {
    private final Driver driver;

    public Main(String uri, String user, String password) {
        this.driver = GraphDatabase.driver(uri, AuthTokens.basic(user, password));
    }

    public void close() {
        driver.close();
    }

    private void loadData(String file) throws IOException {
        File f = new File(file);
        BufferedReader br = new BufferedReader(new FileReader(f));
        String line;
        while ((line = br.readLine()) != null) {
            if (line.startsWith("race_name")) {
                continue;
            }

            String[] data = line.split(";");
            String race_name = data[0].replace("\"", "");
            String date = data[1].replace("\"", "");
            Integer year = Integer.parseInt(data[2]);
            String circuit = data[3].replace("\"", "");
            String surname = data[4].replace("\"", "");
            String name = data[5].replace("\"", "");
            String constructor = data[6].replace("\"", "");
            Integer grid = Integer.parseInt(data[7]);
            String position = data[8].equals("\\N") ? "Not Classified" : data[8];
            String status = data[9].replace("\"", "");

            try (Session session = driver.session()) {
                session.executeWrite(tx -> {
                    tx.run(
                        "MERGE (race:Race {name: $race_name, date: $date, year: $year}) " +
                        "MERGE (driver:Driver {surname: $surname, name: $name}) " +
                        "MERGE (constructor:Constructor {name: $constructor}) " +
                        "MERGE (circuit:Circuit {name: $circuit}) " +
                        "MERGE (result:Result {grid: $grid, position: $position, status: $status}) " +
                        "MERGE (driver)-[:RACED_IN]->(race) " +
                        "MERGE (driver)-[:DRIVES_FOR]->(constructor) " +
                        "MERGE (race)-[:RESULTED_IN]->(result)" +
                        "MERGE (race)-[:HELD_IN]->(circuit)",
                        Values.parameters(
           "race_name", race_name,
                            "date", date,
                            "year", year,
                            "circuit", circuit,
                            "surname", surname,
                            "name", name,
                            "constructor", constructor,
                            "grid", grid,
                            "position", position,
                            "status", status
                        )
                    );
                    return null;
                });
            }
        }
        br.close();
    }

    private void executeQueries() {
        try (Session session = driver.session()) {
            System.out.println("# Lista de todos os pilotos");
            session.run("MATCH (d:Driver) RETURN d.name, d.surname").list()
                .forEach(record -> System.out.println(record.asMap()));

            System.out.println("# Número de corridas por ano");
            session.run("MATCH (r:Race) RETURN r.year AS Year, COUNT(r) AS RaceCount ORDER BY Year;").list()
                .forEach(record -> System.out.println(record.asMap()));

            System.out.println("# Número de pilotos que correu por cada equipa");
            session.run("MATCH (d:Driver)-[:DRIVES_FOR]->(c:Constructor) RETURN c.name, count(d)").list()
                .forEach(record -> System.out.println(record.asMap()));

            System.out.println("# Pilotos que participaram numa corrida num dado ano");
            session.run("MATCH (d:Driver)-[:RACED_IN]->(r:Race) WHERE r.name = 'British Grand Prix' AND r.year = 2019 RETURN d.name, d.surname").list()
                .forEach(record -> System.out.println(record.asMap()));

            System.out.println("# Vitórias de um piloto");
            session.run("MATCH (d:Driver)-[:RACED_IN]->(r:Race)-[:RESULTED_IN]->(res:Result) WHERE d.name = 'Ayrton' AND d.surname = 'Senna' AND res.position = '1' RETURN r.name AS RaceName, r.year as RaceYear, res.grid AS StartingGrid, res.position AS FinalPosition, res.status AS Status;").list()
                .forEach(record -> System.out.println(record.asMap()));

            System.out.println("# 10 Pilotos com mais corridas");
            session.run("MATCH (d:Driver)-[:RACED_IN]->(r:Race) RETURN d.name, d.surname, count(r) AS numRaces ORDER BY numRaces DESC LIMIT 10").list()
                .forEach(record -> System.out.println(record.asMap()));

            System.out.println("# 5 Circuitos com mais corridas");
            session.run("MATCH (c:Circuit)<-[:HELD_IN]-(r:Race) RETURN c.name, count(r) AS numRaces ORDER BY numRaces DESC LIMIT 5").list()
                .forEach(record -> System.out.println(record.asMap()));

            System.out.println("# 5 corridas com menos pilotos a terminar");
            session.run("MATCH (r:Race)-[:RESULTED_IN]->(res:Result) WHERE res.position <> 'Not Classified' RETURN r.name, r.year, count(res) AS numFinishers ORDER BY numFinishers ASC LIMIT 5").list()
                .forEach(record -> System.out.println(record.asMap()));

            System.out.println("# Distância mínima entre Fangio e Ricciardo");
            session.run("MATCH path = shortestPath((d1:Driver)-[*]-(d2:Driver)) WHERE d1.surname = 'Fangio' AND d2.surname = 'Ricciardo' RETURN length(path) AS distance").list()
                .forEach(record -> System.out.println(record.asMap()));

            System.out.println("# Pilotos com mais vitórias em 2015");
            session.run("MATCH (d1:Driver)-[:RACED_IN]->(r:Race)<-[:RACED_IN]-(d2:Driver) " + 
                                "WHERE d1.name < d2.name" + 
                                "RETURN d1.name + ' ' + d1.surname AS Driver1, d2.name + ' ' + d2.surname AS Driver2, COUNT(r) AS SharedRaces" +
                                "ORDER BY SharedRaces DESC" +
                                "LIMIT 10").list().forEach(record -> System.out.println(record.asMap()));
        }
    }

    public static void main(String[] args) throws IOException {
        String uri = "bolt://localhost:7687";
        String user = "admin";
        String password = "admin";
        String filePath = "resources/Results.csv";

        Main main = new Main(uri, user, password);
        try {
            main.loadData(filePath);
            System.out.println("Data loaded successfully!");
            main.executeQueries();
        } finally {
            main.close();
        }
    }
}