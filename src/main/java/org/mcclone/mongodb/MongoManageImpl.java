package org.mcclone.mongodb;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.hadoop.conf.Configuration;
import org.bson.Document;

import java.util.*;

/**
 * @author Administrator
 */
class MongoManageImpl implements MongoManage {

    private MongoCollection<Document> collection;

    private Map<Integer, MongoCollection<Document>> mongoCollectionMap = new HashMap<>();

    MongoManageImpl(Configuration configuration) {
        ServerAddress serverAddress = new ServerAddress(configuration.get("mongo.config.host", "localhost"), configuration.getInt("mongo.config.port", 27017));
        MongoClient client = new MongoClient(serverAddress);
        this.collection = client.getDatabase(configuration.get("mongo.config.database", "config")).getCollection(configuration.get("mongo.config.collection", "shardConfig"));
    }

    @Override
    public void createShardCollection(String collectionName, List<ShardInfo> shardInfoList) throws Exception {
        createShardCollection(collectionName, shardInfoList, "_id", KeyStrategy.HASH);
    }

    @Override
    public void createShardCollection(String collectionName, List<ShardInfo> shardInfoList, String keyField, KeyStrategy keyStrategy) throws Exception {
        Set<ShardInfo> shardInfoSet = new HashSet<>(shardInfoList);
        if (shardInfoSet.size() <= 1) {
            throw new IllegalStateException("不支持单分片");
        }
        switch (keyStrategy) {
            case HASH:
                List<Ranges.Range> ranges = Ranges.build(shardInfoList.size()).getRanges();
                List<Document> shardInfos = new ArrayList<>();
                for (int i = 0; i < shardInfoList.size(); i++) {
                    ShardInfo shardInfo = shardInfoList.get(i);
                    Ranges.Range range = ranges.get(i);
                    shardInfo.setStartKey(range.getStartKey());
                    shardInfo.setEndKey(range.getEndKey());
                    Document document = new Document();
                    document.put("collectionName", shardInfo.getCollectionName());
                    document.put("databaseName", shardInfo.getDatabaseName());
                    document.put("userName", shardInfo.getUserName());
                    document.put("password", shardInfo.getPassword());
                    document.put("host", shardInfo.getHost());
                    document.put("port", shardInfo.getPort());
                    document.put("startKey", range.getStartKey());
                    document.put("endKey", range.getEndKey());
                    shardInfos.add(document);
                }
                Document document = new Document();
                document.put("keyField", keyField);
                document.put("keyStrategy", keyStrategy.name());
                document.put("shardCollection", collectionName);
                document.put("shardInfoList", shardInfos);
                document.put("_id", DigestUtils.md5Hex(collectionName));
                collection.insertOne(document);
                break;
        }

    }

    @Override
    public List<ShardCollectionInfo> getShardCollections() {
        List<ShardCollectionInfo> shardCollectionInfos = new ArrayList<>();
        for (Document document : collection.find()) {
            ShardCollectionInfo shardCollectionInfo = new ShardCollectionInfo();
            shardCollectionInfo.setKeyStrategy(KeyStrategy.valueOf(document.getString("keyStrategy")));
            shardCollectionInfo.setKeyField(document.getString("keyField"));
            shardCollectionInfo.setShardCollection(document.getString("shardCollection"));
            List<ShardInfo> shardInfos = new ArrayList<>();
            List<Document> shardInfoDocs = (List<Document>) document.get("shardInfoList");
            for (Document shardInfoDoc : shardInfoDocs) {
                ShardInfo shardInfo = new ShardInfo();
                shardInfo.setStartKey(shardInfoDoc.getLong("startKey"));
                shardInfo.setEndKey(shardInfoDoc.getLong("endKey"));
                shardInfo.setHost(shardInfoDoc.getString("host"));
                shardInfo.setPort(shardInfoDoc.getInteger("port"));
                shardInfo.setDatabaseName(shardInfoDoc.getString("databaseName"));
                shardInfo.setCollectionName(shardInfoDoc.getString("collectionName"));
                shardInfo.setUserName(shardInfoDoc.getString("userName"));
                shardInfo.setPassword(shardInfoDoc.getString("password"));
                shardInfos.add(shardInfo);
            }
            shardCollectionInfo.setShardInfoList(shardInfos);
            shardCollectionInfos.add(shardCollectionInfo);
        }
        return shardCollectionInfos;
    }

    @Override
    public MongoCollection<Document> getConnect(ShardInfo shardInfo) {
        if (mongoCollectionMap.containsKey(shardInfo.hashCode())) {
            return mongoCollectionMap.get(shardInfo.hashCode());
        } else {
            ServerAddress serverAddress = new ServerAddress(shardInfo.getHost(), shardInfo.getPort());
            MongoClient client = new MongoClient(serverAddress);
            MongoCollection<Document> mongoCollection = client.getDatabase(shardInfo.getDatabaseName()).getCollection(shardInfo.getCollectionName());
            mongoCollectionMap.put(shardInfo.hashCode(), mongoCollection);
            return mongoCollection;
        }

    }

}
