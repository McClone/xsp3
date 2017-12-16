package org.mcclone.mongodb;

import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @author Administrator
 */
public interface MongoManage {

    void createShardCollection(String collectionName, List<ShardInfo> shardInfoList) throws Exception;

    void createShardCollection(String collectionName, List<ShardInfo> shardInfoList, String keyField, KeyStrategy keyStrategy) throws Exception;

    List<ShardCollectionInfo> getShardCollections();

    MongoCollection<Document> getConnect(ShardInfo shardInfo);
}
