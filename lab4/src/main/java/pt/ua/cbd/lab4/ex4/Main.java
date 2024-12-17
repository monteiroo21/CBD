package pt.ua.cbd.lab4.ex4;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Session;
import org.neo4j.driver.Driver;

public class Main {
    public static void main(String[] args) {
        String address = "bolt://localhost:7687";
        String user = "neo4j";
        String password = "12345678";
        Driver driver = GraphDatabase.driver(address, AuthTokens.basic(user, password));
        Session session = driver.session();
    }
}