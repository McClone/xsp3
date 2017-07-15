package org.mcclone.redis;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mcclone on 17-4-2.
 */
public class ShardedJedisClient {

    public static void main(String[] args) {
        List<JedisShardInfo> jedisShardInfos = new ArrayList<>();
        JedisShardInfo jedisShardInfo_1 = new JedisShardInfo("localhost", 6379);
        JedisShardInfo jedisShardInfo_2 = new JedisShardInfo("localhost", 6380);
        jedisShardInfos.add(jedisShardInfo_1);
        jedisShardInfos.add(jedisShardInfo_2);
        ShardedJedisPool pool = new ShardedJedisPool(new JedisPoolConfig(), jedisShardInfos);
        ShardedJedis shardedJedis = pool.getResource();
        shardedJedis.set("shardedKey", "shardedKey");
        shardedJedis.set("shardedKey1", "shardedKey1");
        shardedJedis.set("shardedKey2", "shardedKey2");
        shardedJedis.set("shardedKey3", "shardedKey3");
    }
}
