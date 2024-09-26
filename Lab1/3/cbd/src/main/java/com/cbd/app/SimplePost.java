package com.cbd.app;
import redis.clients.jedis.Jedis; 
import java.util.List;
import java.util.HashMap;
  
public class SimplePost { 
    public static String USERS_KEY = "users"; // Key set for users' name
	public static String USERS_LIST = "users_list"; // List for users' name
	public static String USERS_MAP =  "users_map"; // List for users' hash map
  
    public static void main(String[] args) { 
        Jedis jedis = new Jedis(); 
        // some users 
        String[] users = { "Ana", "Pedro", "Maria", "Luis" }; 
 
        // jedis.del(USERS_KEY); // remove if exists to avoid wrong type 
        for (String user : users)  
            jedis.sadd(USERS_KEY, user); 
			jedis.lpush(USERS_LIST, user);
			jedis.hset(USERS_MAP, user, user); 


        jedis.smembers(USERS_KEY).forEach(System.out::println); 
        
        jedis.lrange(USERS_LIST, 0, -1).forEach(System.out::println);

        jedis.hgetAll(USERS_MAP).forEach((key, value) -> System.out.println(key + ": " + value));

        jedis.close(); 
    } 
} 



