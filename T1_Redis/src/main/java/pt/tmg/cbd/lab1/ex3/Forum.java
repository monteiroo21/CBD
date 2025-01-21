package pt.tmg.cbd.lab1.ex3;
import redis.clients.jedis.Jedis; 
 
public class Forum { 
    public static void main(String[] args) { 
        // Ensure you have redis-server running 
        Jedis jedis = new Jedis(); 
        System.out.println(jedis.ping()); 
        System.out.println(jedis.info()); 
        
        jedis.set("key1", "value1");
        jedis.set("key2", "value2");
        jedis.set("key3", "value3");

        jedis.hset("hash1", "field1", "value1");
        jedis.hset("hash1", "field2", "value2");

        jedis.lpush("list1", "item1");
        jedis.lpush("list1", "item2");
        jedis.lpush("list1", "item3");

        jedis.sadd("set1", "member1");
        jedis.sadd("set1", "member2");
        jedis.sadd("set1", "member3");

        jedis.zadd("sortedset", 1, "member1");
        jedis.zadd("sortedset", 3, "member2");
        jedis.zadd("sortedset", 2, "member3");

        System.out.println(jedis.get("key1"));
        System.out.println(jedis.hgetAll("hash1"));
        System.out.println(jedis.lrange("list1", 0, -1));
        System.out.println(jedis.smembers("set1"));
        System.out.println(jedis.zrange("sortedset", 0, -1));
        jedis.close(); 
    } 
} 
