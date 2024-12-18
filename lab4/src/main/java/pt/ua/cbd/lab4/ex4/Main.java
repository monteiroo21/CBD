package pt.ua.cbd.lab4.ex4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Session;
import org.neo4j.driver.Driver;

public class Main {
    private void finalData(String file) throws IOException {
        File f = new File(file);
        BufferedReader br = new BufferedReader(new FileReader(f));
        String line;
        while ((line = br.readLine()) != null) {
            String[] data = line.split(";");
            String race_name = data[0];
            String date = data[1];
            Integer year = Integer.parseInt(data[2]);
            String circuit = data[3];
            String surname = data[4];
            String name = data[5];
            String constructor = data[6];
            Integer grid = Integer.parseInt(data[7]);
            Integer position = Integer.parseInt(data[8]);
            String status = data[9];

        }    
    }

    public static void main(String[] args) {
        String address = "bolt://localhost:7687";
        String user = "neo4j";
        String password = "12345678";
        Driver driver = GraphDatabase.driver(address, AuthTokens.basic(user, password));
        Session session = driver.session();


    }
}