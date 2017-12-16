package org.mcclone.mongodb;

import com.google.common.base.Joiner;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.hadoop.conf.Configuration;
import org.bson.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public void createShardCollection(String shardCollection, List<ShardInfo> shardInfoList) throws Exception {
        createShardCollection(shardCollection, shardInfoList, "_id", KeyStrategy.HASH);
    }

    @Override
    public void createShardCollection(String shardCollection, List<ShardInfo> shardInfoList, String keyField, KeyStrategy keyStrategy) throws Exception {
        switch (keyStrategy) {
            case HASH:
                List<Ranges.Range> ranges = Ranges.build(shardInfoList.size()).getRanges();
                List<Document> data = new ArrayList<>();
                for (int i = 0; i < shardInfoList.size(); i++) {
                    ShardInfo shardInfo = shardInfoList.get(i);
                    Ranges.Range range = ranges.get(i);
                    Document document = new Document();
                    document.put("collectionName", shardInfo.getCollectionName());
                    document.put("databaseName", shardInfo.getDatabaseName());
                    document.put("userName", shardInfo.getUserName());
                    document.put("password", shardInfo.getPassword());
                    document.put("host", shardInfo.getHost());
                    document.put("port", shardInfo.getPort());
                    document.put("startKey", range.getStartKey());
                    document.put("endKey", range.getEndKey());
                    document.put("keyField", keyField);
                    document.put("keyStrategy", keyStrategy.name());
                    document.put("shardCollection", shardCollection);
                    String id = DigestUtils.md5Hex(Joiner.on("|")
                            .join(shardCollection, shardInfo.getDatabaseName(),
                                    shardInfo.getCollectionName(), shardInfo.getHost(), shardInfo.getPort()));
                    document.put("_id", id);
                    data.add(document);
                }
                collection.insertMany(data);
                break;
        }

    }

    @Override
    public List<ShardCollectionInfo> getShardCollections() {
        List<ShardCollectionInfo> shardCollectionInfos = new ArrayList<>();
        for (Document document : collection.find()) {
            ShardCollectionInfo shardCollectionInfo = new ShardCollectionInfo();
            shardCollectionInfo.setKeyStrategy(document.getString("keyStrategy"));
            shardCollectionInfo.setKeyField(document.getString("keyField"));
            shardCollectionInfo.setShardCollection(document.getString("shardCollection"));

            shardCollectionInfo.setStartKey(document.getLong("startKey"));
            shardCollectionInfo.setEndKey(document.getLong("endKey"));
            shardCollectionInfo.setHost(document.getString("host"));
            shardCollectionInfo.setPort(document.getInteger("port"));
            shardCollectionInfo.setDatabaseName(document.getString("databaseName"));
            shardCollectionInfo.setCollectionName(document.getString("collectionName"));
            shardCollectionInfo.setUserName(document.getString("userName"));
            shardCollectionInfo.setPassword(document.getString("password"));
            shardCollectionInfos.add(shardCollectionInfo);
        }
        return shardCollectionInfos;
    }

    @Override
    public MongoCollection<Document> getConnect(ShardCollectionInfo shardCollectionInfo) {
        if (mongoCollectionMap.containsKey(shardCollectionInfo.hashCode())) {
            return mongoCollectionMap.get(shardCollectionInfo.hashCode());
        } else {
            ServerAddress serverAddress = new ServerAddress(shardCollectionInfo.getHost(), shardCollectionInfo.getPort());
            MongoClient client = new MongoClient(serverAddress);
            MongoCollection<Document> mongoCollection = client.getDatabase(shardCollectionInfo.getDatabaseName()).getCollection(shardCollectionInfo.getCollectionName());
            mongoCollectionMap.put(shardCollectionInfo.hashCode(), mongoCollection);
            return mongoCollection;
        }

    }

}
