package pt.ua.cbd.lab3.ex3;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;

public class Main {
    public static void main(String[] args) {
        try (CqlSession session = CqlSession.builder().build()) {                                    // (1)
            ResultSet rs = session.execute("select release_version from system.local");              // (2)
            Row row = rs.one();
            System.out.println(row.getString("release_version"));                                    // (3)

            System.out.println("----------------------------------\n");
            System.out.println("Insertion\n");

            session.execute("INSER INTO cbd_videos.user(id, username, email, registration_timestamp) VALUES (uuid(), 'João Monteiro', 'joaomonteiro@gmail.com', toTimestamp(now()))");
            
        }
    }
}