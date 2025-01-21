package pt.tmg.cbd.lab1.ex5;

import redis.clients.jedis.Jedis;

import java.util.Scanner;

public class ServiceSystemB {
    private static final int QUANTITY = 30;
    private static final int TIMESLOT = 30000;
    private static Jedis jedis;

    private static void addQuantity(String username, Integer quantity) {
        Long time = System.currentTimeMillis();
        Long cutoffTime = time - TIMESLOT;
        jedis.zremrangeByScore(username, 0, cutoffTime);

        Integer total = jedis.zrangeByScore(username, cutoffTime, time).stream()
                .map(entry -> Integer.parseInt(entry.split(":")[0]))
                .mapToInt(Integer::intValue)
                .sum();

        if ((total + quantity) > QUANTITY) {
            System.out.println("ERROR: User " + username.substring(4) + " has exceeded the maximum number of requests in the current timeslot.");
            double earliestTime = jedis.zrangeWithScores(username, 0, 0).stream()
                    .mapToDouble(tuple -> tuple.getScore())
                    .min()
                    .orElse((double) time);
            double timeLeft = (earliestTime + TIMESLOT - time) / 1000;
            System.out.println("You can make the next request in " + timeLeft + " seconds.");
        } else {
            String unique = quantity + ":" + time;
            jedis.zadd(username, time, unique);
            System.out.println(quantity + " products added for user '" + username.substring(4) + "'.");
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

            System.out.print("Enter the quantity ('Enter' to quit): ");
            String resp = sc.nextLine();
            if (resp.isEmpty()) {
                break;
            }
            Integer quantity = Integer.parseInt(resp);

            username = "USER" + username;
            addQuantity(username, quantity);
        }
        sc.close();
        jedis.close();
    }
}
