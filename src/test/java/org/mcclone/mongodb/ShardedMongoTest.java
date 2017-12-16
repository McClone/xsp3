package org.mcclone.mongodb;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.mongodb.BasicDBObjectBuilder;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
public class ShardedMongoTest {

    public static void main(String[] args) throws Exception {
        ShardMongoClient client = ShardMongoClient.create();
//        List<ShardInfo> shardInfos = new ArrayList<>();
//        ShardInfo shardInfo1 = new ShardInfo();
//        shardInfo1.setCollectionName("test1");
//        shardInfo1.setDatabaseName("demo");
//        shardInfo1.setHost("localhost");
//        shardInfo1.setPort(27017);
//        ShardInfo shardInfo2 = new ShardInfo();
//        shardInfo2.setCollectionName("test2");
//        shardInfo2.setDatabaseName("demo");
//        shardInfo2.setHost("localhost");
//        shardInfo2.setPort(27017);
//        shardInfos.add(shardInfo1);
//        shardInfos.add(shardInfo2);
//        client.mongoManage().createShardCollection("test", shardInfos);
        System.out.println(client.mongoManage().getShardCollections());
//        client.shardMongoCollection("test").insertOne(new Document(BasicDBObjectBuilder.start().add("_id", "12312").add("name", "admin").get().toMap()));
//        System.out.println(client.shardMongoCollection("test").find());

    }
}
