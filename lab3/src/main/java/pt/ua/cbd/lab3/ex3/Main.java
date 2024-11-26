package pt.ua.cbd.lab3.ex3;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;

import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        try (CqlSession session = CqlSession.builder().build()) {
            ResultSet rs = session.execute("select release_version from system.local");
            Row row = rs.one();
            System.out.println(row.getString("release_version"));

            System.out.println("\n----------------------------------");
            System.out.println("Insertion and Search\n");

            // Insert user
            UUID userId = UUID.randomUUID(); // Generate a random UUID
            session.execute(
                "INSERT INTO cbd_videos.user (id, username, email, registration_timestamp) " +
                "VALUES (?, 'João Monteiro', 'joaomonteiro@gmail.com', toTimestamp(now()))",
                userId
            );

            // Query user
            ResultSet userQuery = session.execute("SELECT id, username, email FROM cbd_videos.user WHERE username = 'João Monteiro'");
            Row userRow = userQuery.one();
            if (userRow != null) {
                System.out.println("Before update: " + userRow);
            }

            System.out.println("----------------------------------\n");
            System.out.println("Update and Search\n");

            // Update user
            session.execute(
                "UPDATE cbd_videos.user SET email = 'jmonteiro@gmail.com' WHERE username = 'João Monteiro' AND id = ?",
                userId
            );

            // Query user after update
            ResultSet updatedUserQuery = session.execute("SELECT id, username, email FROM cbd_videos.user WHERE username = 'João Monteiro'");
            Row userRowUpdated = updatedUserQuery.one();
            if (userRowUpdated != null) {
                System.out.println("After update: " + userRowUpdated);
            }
        }
    }
}