package org.mcclone.redis;

import redis.clients.jedis.Jedis;

/**
 * Created by mcclone on 17-4-2.
 */
public class JedisClient {

    public static void main(String[] args) {
        Jedis jedis = new Jedis("localhost", 6379);
        jedis.set("foo", "bar");
        String value = jedis.get("foo");
        System.out.println(value);
    }
}
