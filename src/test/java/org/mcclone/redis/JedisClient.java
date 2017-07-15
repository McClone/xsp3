package org.mcclone.redis;

import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

/**
 * Created by mcclone on 17-4-2.
 */
public class JedisClient {

    private Jedis jedis;

    @Before
    public void setUp() throws Exception {
        jedis = new Jedis("localhost", 6379);
    }

    @Test
    public void set() throws Exception {
        jedis.set("foo", "bar");
        String value = jedis.get("foo");
        System.out.println(value);
    }
}
