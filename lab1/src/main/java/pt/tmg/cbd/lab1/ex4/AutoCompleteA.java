package pt.tmg.cbd.lab1.ex4;

import redis.clients.jedis.Jedis; 
import java.util.Scanner;
import java.io.File;

public class AutoCompleteA {
    public static String USERS = "users"; // Key set for users' name
    public static void main( String[] args ) throws Exception {
        Jedis jedis = new Jedis();

        File file = new File("src/main/resources/names.txt");
        if (!file.exists()) {
            System.out.println("File not found");
            jedis.close();
            return;
        }

        Scanner reader = new Scanner(file);
        while(reader.hasNextLine()) {
            jedis.zadd(USERS, 0, reader.nextLine());
        }
        reader.close();

        Scanner sc = new Scanner(System.in);
        String name;
        while (true) {
            System.out.print("Search for ('Enter' for quit): ");
            name = sc.nextLine().toLowerCase();
            if (name.isEmpty()) {
                break;
            }

            jedis.zrangeByLex(USERS, "[" + name, "(" + name + Character.MAX_VALUE).stream().forEach(System.out::println);
        }

        sc.close();
        jedis.close();
    }
}
