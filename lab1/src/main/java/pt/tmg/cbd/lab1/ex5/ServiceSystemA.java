package pt.tmg.cbd.lab1.ex5;

import redis.clients.jedis.Jedis;

import java.util.Scanner;

public class ServiceSystemA {
    private static final int PRODUCTS = 5;
    private static final int TIMESLOT = 30000;
    private static Jedis jedis;

    private static void addProduct(String username, String product) {
        Integer time = (int) System.currentTimeMillis();
        Integer cutoffTime = time - TIMESLOT;
        jedis.zremrangeByScore(username, 0, cutoffTime);
        if (jedis.zcard(username) >= PRODUCTS) {
            System.out.println("ERROR: User " + username.substring(4) + " has exceeded the maximum number of requests in the current timeslot.");
            Integer timeLeft = (int) (jedis.zrangeWithScores(username, 0, 0).stream()
                            .mapToDouble(tuple -> tuple.getScore())
                            .min()
                            .orElse((double) time) + TIMESLOT - time) / 1000;
            System.out.println("You can make the next request in " + timeLeft + " seconds.");
        } else {
            jedis.zadd(username, time, product);
            System.out.println("Product '" + product.substring(7) + "' added for user '" + username.substring(4) + "'.");
        }
    }

    public static void main( String[] args ) throws Exception {
        jedis = new Jedis();
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("Enter the username ('Enter' to quit): ");
            String username = sc.nextLine();
            if (username.isEmpty()) {
                break;
            }

            System.out.print("Enter the product ('Enter' to quit): ");
            String product = sc.nextLine();
            if (product.isEmpty()) {
                break;
            }

            username = "USER" + username;
            product = "PRODUCT" + product;
            addProduct(username, product);
        }
        sc.close();
        jedis.close();
    }
}
