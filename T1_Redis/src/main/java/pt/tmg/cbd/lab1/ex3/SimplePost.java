package pt.tmg.cbd.lab1.ex3;
import redis.clients.jedis.Jedis; 
  
public class SimplePost { 
    public static String USERS_KEY = "users"; // Key set for users' name
	public static String USERS_LIST = "users_list"; // List for users' name
	public static String USERS_MAP =  "users_map"; // List for users' hash map
  
    public static void main(String[] args) { 
        Jedis jedis = new Jedis(); 
        jedis.del(USERS_KEY); 
        jedis.del(USERS_LIST);
        jedis.del(USERS_MAP);

        // some users 
        String[] users = { "Ana", "Pedro", "Maria", "Luis" }; 
 
        jedis.del(USERS_KEY); 
        for (String user : users)
            jedis.sadd(USERS_KEY, user); 

        jedis.smembers(USERS_KEY).forEach(System.out::println); 
        System.out.println();

        for (String user : users)
			jedis.lpush(USERS_LIST, user);
        
        jedis.lrange(USERS_LIST, 0, -1).forEach(System.out::println);
        System.out.println();

        for (int i = 0; i < users.length; i++)
			jedis.hset(USERS_MAP, String.valueOf(i+1), users[i]); 

        jedis.hgetAll(USERS_MAP).forEach((key, value) -> System.out.println(key + ": " + value));

        jedis.close(); 
    } 
} 



