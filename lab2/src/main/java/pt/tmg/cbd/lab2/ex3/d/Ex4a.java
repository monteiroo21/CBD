package pt.tmg.cbd.lab2.ex3.d;

import java.util.Scanner;

public class Ex4a {
    /* 
    private static final int products = 5;
    private static final int timeslot = 30;

    public static String TIMESTAMPS = "timestamps";

    public static Jedis jedis;

    public static void addRequest(String username, String product, Long timestamp) {
        if (jedis.zcard(username) >= products) {
            System.out.println("ERROR: User " + username.substring(4) + " has exceeded the maximum number of requests in the current timeslot.");
            System.out.println("You can make the next request in " + jedis.ttl(username) + " seconds.");

        } else {
            jedis.zadd(username, timestamp, product);
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
            Long timestamp = Long.parseLong(getTime());
            addRequest(username, product, timestamp);
        }
        sc.close();
        jedis.close();
    }
    */
}
