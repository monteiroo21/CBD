package pt.ua.cbd.lab3.ex3;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;

import java.util.Set;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        try (CqlSession session = CqlSession.builder().build()) {
            ResultSet rs = session.execute("select release_version from system.local");
            Row row = rs.one();
            System.out.println(row.getString("release_version"));

            System.out.println("\n----------------------------------");
            System.out.println("Insertion and Search\n");

            UUID userId = UUID.randomUUID();
            session.execute(
                "INSERT INTO cbd_videos.user (id, username, email, registration_timestamp) " +
                "VALUES (?, 'João Monteiro', 'joaomonteiro@gmail.com', toTimestamp(now()))",
                userId
            );

            ResultSet userQuery = session.execute("SELECT id, username, email FROM cbd_videos.user WHERE username = 'João Monteiro'");
            Row userRow = userQuery.one();
            if (userRow != null) {
                System.out.println("Before update: " +
                    "Username = " + userRow.getString("username") +
                    ", Email = " + userRow.getString("email"));
            }

            System.out.println("----------------------------------\n");
            System.out.println("Update and Search\n");

            session.execute(
                "UPDATE cbd_videos.user SET email = 'jmonteiro@gmail.com' WHERE username = 'João Monteiro' AND id = ?",
                userId
            );

            ResultSet updatedUserQuery = session.execute("SELECT id, username, email FROM cbd_videos.user WHERE username = 'João Monteiro'");
            Row userRowUpdated = updatedUserQuery.one();
            if (userRowUpdated != null) {
                System.out.println("After update: " +
                    "Username = " + userRowUpdated.getString("username") +
                    ", Email = " + userRowUpdated.getString("email"));
            }

            ResultSet query1 = session.execute("SELECT JSON * FROM cbd_videos.events WHERE user = 'paulo_m' AND type = 'pause'");
            System.out.println("\n4.c. Todos os eventos de determinado utilizador to tipo \"pause\":");
            for (Row row1 : query1) {
                System.out.println(row1.getString(0));
            }

            System.out.println("\n7. Todos os seguidores (followers) de determinado video:");
            ResultSet query2 = session.execute("SELECT JSON followers FROM cbd_videos.followers WHERE video = 'Top 10 filmes de ação'");
            for (Row row2 : query2) {
                System.out.println(row2.getString(0));
            }

            ResultSet query3 = session.execute("SELECT JSON tag, COUNT(name) AS \"videos\" FROM cbd_videos.video_by_tag GROUP BY tag");
            System.out.println("\n11. Lista com as Tags existentes e o numero de videos catalogados com cada uma delas:");
            for (Row row3 : query3) {
                System.out.println(row3.getString(0));
            }

            String query4 = "SELECT JSON user, SUM(views) AS \"views\" FROM cbd_videos.views_by_user GROUP BY user";
            ResultSet rs4 = session.execute(query4);
            System.out.println("\n14. Número de visualizações de um utilizador por video:");
            for (Row row4 : rs4) {
                System.out.println(row4.getString(0));
            }
        }
    }
}