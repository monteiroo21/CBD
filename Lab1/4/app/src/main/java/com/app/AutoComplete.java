package com.app;
import redis.clients.jedis.Jedis; 
import java.util.Scanner;
import java.io.File;

public class AutoComplete {
    public static String USERS = "user"; // Key set for users' name
    public static void main( String[] args ) throws Exception {
        Jedis jedis = new Jedis();

        File file = new File("/home/joao/Desktop/3ºANO/1ºSEMESTRE/CBD/Lab1/4/names.txt");
        Scanner reader = new Scanner(file);

        while(reader.hasNextLine()) {
            jedis.zadd(USERS, 0, reader.nextLine());
        }

        Scanner sc = new Scanner(System.in);
        String name;
        while (true) {
            System.out.print("Search for ('Enter' for quit): ");
            name = sc.nextLine().toLowerCase();
            if (name == "") {
                break;
            }

            jedis.zrangeByLex(USERS, "[" + name, "(" + name + Character.MAX_VALUE).forEach(System.out::println);
        }

        reader.close();
        sc.close();
        jedis.close();
    }
}
