package org.mcclone.mongodb;

import com.mongodb.BasicDBObject;
import com.mongodb.client.DistinctIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.ListIndexesIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.*;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.apache.hadoop.conf.Configuration;
import org.bson.Document;
import org.bson.conversions.Bson;
import redis.clients.util.Hashing;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Administrator
 */
public class ShardMongoClient {

    private MongoManage mongoManage;
    private Map<String, ShardCollectionInfo> shardCollectionInfoMap = new HashMap<>();

    private ShardMongoClient() {
        Configuration configuration = new Configuration();
        configuration.addResource("mongo-shard.xml");
        mongoManage = new MongoManageImpl(configuration);
        List<ShardCollectionInfo> shardCollectionInfos = mongoManage.getShardCollections();
        for (ShardCollectionInfo shardCollectionInfo : shardCollectionInfos) {
            shardCollectionInfoMap.put(shardCollectionInfo.getShardCollection(), shardCollectionInfo);
        }
    }

    public static ShardMongoClient create() {
        return new ShardMongoClient();
    }

    public MongoManage mongoManage() {
        return mongoManage;
    }

    public ShardMongoCollection shardMongoCollection(String collectionName) {
        return new ShardMongoCollection(collectionName);
    }

    public class ShardMongoCollection {

        private TreeMap<Long, ShardInfo> rangeIndex = new TreeMap<>();
        private String keyField;
        private List<ShardInfo> shardInfos;

        private ShardMongoCollection(String collectionName) {
            if (shardCollectionInfoMap.containsKey(collectionName)) {
                ShardCollectionInfo shardCollectionInfo = shardCollectionInfoMap.get(collectionName);
                shardInfos = shardCollectionInfo.getShardInfoList();
                for (ShardInfo shardInfo : shardInfos) {
                    rangeIndex.put(shardInfo.getStartKey(), shardInfo);
                }
                keyField = shardCollectionInfo.getKeyField();
            } else {
                throw new UnsupportedOperationException(collectionName);
            }
        }

        protected MongoCollection<Document> getShardConnect(Map<String, Object> document) {
            String keyFieldValue = (String) document.get(keyField);
            long key = Hashing.MD5.hash(keyFieldValue);
            Long startKey = rangeIndex.tailMap(key).firstKey();
            return mongoManage.getConnect(rangeIndex.get(startKey));
        }

        public long count() {
            return count(new BasicDBObject());
        }

        public long count(BasicDBObject filter) {
            return count(filter, new CountOptions());
        }

        public long count(BasicDBObject filter, CountOptions options) {
            long count = 0;
            for (ShardInfo shardInfo : shardInfos) {
                count += mongoManage.getConnect(shardInfo).count(filter, options);
            }
            return count;
        }

        public <TResult> DistinctIterable<TResult> distinct(String fieldName, Class<TResult> tResultClass) {
            return null;
        }

        public <TResult> DistinctIterable<TResult> distinct(String fieldName, Bson filter, Class<TResult> tResultClass) {
            return null;
        }

        public FindIterable<Document> find() {
            return null;
        }

        public <TResult> FindIterable<TResult> find(Class<TResult> tResultClass) {
            return null;
        }

        public FindIterable<Document> find(Bson filter) {
            return null;
        }

        public <TResult> FindIterable<TResult> find(Bson filter, Class<TResult> tResultClass) {
            return null;
        }

        public void insertOne(Document document) {
            getShardConnect(document).insertOne(document);
        }

        public void insertOne(Document document, InsertOneOptions options) {

        }

        public void insertMany(List<? extends Document> documents) {

        }

        public void insertMany(List<? extends Document> documents, InsertManyOptions options) {

        }

        public DeleteResult deleteOne(Bson filter) {
            return null;
        }

        public DeleteResult deleteOne(Bson filter, DeleteOptions options) {
            return null;
        }

        public DeleteResult deleteMany(Bson filter) {
            return null;
        }

        public DeleteResult deleteMany(Bson filter, DeleteOptions options) {
            return null;
        }

        public UpdateResult replaceOne(Bson filter, Document replacement) {
            return null;
        }

        public UpdateResult replaceOne(Bson filter, Document replacement, UpdateOptions updateOptions) {
            return null;
        }

        public UpdateResult updateOne(Bson filter, Bson update) {
            return null;
        }

        public UpdateResult updateOne(Bson filter, Bson update, UpdateOptions updateOptions) {
            return null;
        }

        public UpdateResult updateMany(Bson filter, Bson update) {
            return null;
        }

        public UpdateResult updateMany(Bson filter, Bson update, UpdateOptions updateOptions) {
            return null;
        }

        public Document findOneAndDelete(Bson filter) {
            return null;
        }

        public Document findOneAndDelete(Bson filter, FindOneAndDeleteOptions options) {
            return null;
        }

        public Document findOneAndReplace(Bson filter, Document replacement) {
            return null;
        }

        public Document findOneAndReplace(Bson filter, Document replacement, FindOneAndReplaceOptions options) {
            return null;
        }

        public Document findOneAndUpdate(Bson filter, Bson update) {
            return null;
        }

        public Document findOneAndUpdate(Bson filter, Bson update, FindOneAndUpdateOptions options) {
            return null;
        }

        public String createIndex(Bson keys) {
            return null;
        }

        public String createIndex(Bson keys, IndexOptions indexOptions) {
            return null;
        }

        public List<String> createIndexes(List<IndexModel> indexes) {
            return null;
        }

        public ListIndexesIterable<Document> listIndexes() {
            return null;
        }

        public <TResult> ListIndexesIterable<TResult> listIndexes(Class<TResult> tResultClass) {
            return null;
        }

        public void dropIndex(String indexName) {

        }

        public void dropIndex(Bson keys) {

        }

        public void dropIndexes() {

        }
    }


}
