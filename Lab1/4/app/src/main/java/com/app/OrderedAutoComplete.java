package com.app;
import redis.clients.jedis.Jedis; 
import java.util.Scanner;
import java.io.File;
import java.util.List;

public class OrderedAutoComplete {
    public static String USERS_SCORE = "userByScore"; // Key set for users' name
    public static void main( String[] args ) throws Exception {
        Jedis jedis = new Jedis();

        File file = new File("/home/joao/Desktop/3ºANO/1ºSEMESTRE/CBD/Lab1/4/nomes-pt-2021.csv");
        Scanner reader = new Scanner(file);

        while(reader.hasNextLine()) {
            String[] data = reader.nextLine().split(";");
            jedis.zadd(USERS_SCORE, Double.parseDouble(data[1]), data[0].toLowerCase());
        }

        Scanner sc = new Scanner(System.in);
        String name;
        while (true) {
            System.out.print("Search for ('Enter' for quit): ");
            name = sc.nextLine().toLowerCase();
            if (name.equals("")) {
                break;
            }

            List<String> names = jedis.zrevrangeByScore(USERS_SCORE, "+inf", "-inf");
            for (String nameDict : names) {
                if (nameDict.startsWith(name)) {
                    System.out.println(nameDict);
                }
            }
        }

        reader.close();
        sc.close();
        jedis.close();
    }
}
