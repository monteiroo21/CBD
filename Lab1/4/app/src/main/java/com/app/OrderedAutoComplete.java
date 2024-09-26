package com.app;
import redis.clients.jedis.Jedis; 
import java.util.Scanner;
import java.io.File;

public class OrderedAutoComplete {
    public static String USERS = "user"; // Key set for users' name
    public static void main( String[] args ) throws Exception {
        Jedis jedis = new Jedis();

        File file = new File("/home/joao/Desktop/3ºANO/1ºSEMESTRE/CBD/Lab1/4/nomes-pt-2021.csv");
        Scanner reader = new Scanner(file);

        while(reader.hasNextLine()) {
            String[] data = reader.nextLine().split(";");
            jedis.zadd(USERS, Double.parseDouble(data[1]), data[0]);
        }

        Scanner sc = new Scanner(System.in);
        String name;
        while (true) {
            System.out.print("Search for ('Enter' for quit): ");
            name = sc.nextLine().toLowerCase();
            if (name.equals("")) {
                break;
            }

            jedis.zrangeByLex(USERS, "[" + name, "(" + name + Character.MAX_VALUE).forEach(System.out::println);
        }

        reader.close();
        sc.close();
        jedis.close();
    }
}
