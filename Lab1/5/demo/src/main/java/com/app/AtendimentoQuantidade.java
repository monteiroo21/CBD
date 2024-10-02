package com.app;
import java.util.List;
import java.util.Scanner;

import redis.clients.jedis.Jedis; 

public class AtendimentoQuantidade {
    private static final int product_units = 10;
    private static final int timeslot = 30;

    public static String REQUESTS = "requests";
    public static String TIMESTAMPS = "timestamps";

    public static Jedis jedis;

    public static void addRequest(String username, String product, int quantity, Long timestamp) {
        int num_units = 0;
        for (String prod : jedis.hkeys(username)) {
            num_units += Integer.parseInt(jedis.hget(username, prod));
        }
        if (num_units + quantity >= product_units) {
            System.out.println("ERROR: User " + username.substring(4) + " has exceeded the maximum number of requests in the current timeslot.");
            System.out.println("You can make the next request in " + jedis.ttl(username) + " seconds.");
            
        } else {
            jedis.hincrBy(username, product, quantity);
            jedis.expire(username, timeslot);
            jedis.hset(TIMESTAMPS, username, String.valueOf(timestamp)); 
            System.out.println("Product '" + product.substring(7) + "' added for user '" + username.substring(4) + "'.");
        }
    }

    public static String getTime() {
        return jedis.time().get(0);
    }

    public static void main( String[] args ) {
        jedis = new Jedis();
        Scanner sc = new Scanner(System.in);
        String username, product;
        Integer quantity;
        while (true) {
            System.out.print("Enter the username ('Enter' to quit): ");
            username = sc.nextLine();
            username = "USER" + username;
            if (username.substring(4).equals("")) {
                break;
            }
            System.out.print("Enter the product ('Enter' to quit): ");
            product = sc.nextLine();
            product = "PRODUCT" + product;
            if (product.substring(7).equals("")) {
                break;
            }
            System.out.println("Enter the quantity ('Enter' to quit): ");
            String resp = sc.nextLine();
            if (resp.equals("")) {
                break;
            }
            quantity = Integer.parseInt(resp);
            Long timestamp = Long.parseLong(getTime());
            addRequest(username, product, quantity, timestamp);
        }
        sc.close();
        jedis.close();
    }
}
