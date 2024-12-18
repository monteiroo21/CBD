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
            String race_name = data[0];
            String date = data[1];
            Integer year = Integer.parseInt(data[2]);
            String circuit = data[3];
            String surname = data[4];
            String name = data[5];
            String constructor = data[6];
            Integer grid = Integer.parseInt(data[7]);
            String position = data[8];
            String status = data[9];

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
                            "position", position.equals("\\N") ? null : Integer.parseInt(position),
                            "status", status
                        )
                    );
                    return null;
                });
            }
        }
        br.close();
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
        } finally {
            main.close();
        }
    }
}