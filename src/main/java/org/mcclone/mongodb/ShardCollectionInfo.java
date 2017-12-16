package org.mcclone.mongodb;

import java.util.List;

/**
 * Created by Administrator on 2017/12/16.
 */
public class ShardCollectionInfo {

    private String shardCollection;
    private List<ShardInfo> shardInfoList;
    private String keyField;
    private KeyStrategy keyStrategy;

    public String getShardCollection() {
        return shardCollection;
    }

    public void setShardCollection(String shardCollection) {
        this.shardCollection = shardCollection;
    }

    public List<ShardInfo> getShardInfoList() {
        return shardInfoList;
    }

    public void setShardInfoList(List<ShardInfo> shardInfoList) {
        this.shardInfoList = shardInfoList;
    }

    public String getKeyField() {
        return keyField;
    }

    public void setKeyField(String keyField) {
        this.keyField = keyField;
    }

    public KeyStrategy getKeyStrategy() {
        return keyStrategy;
    }

    public void setKeyStrategy(KeyStrategy keyStrategy) {
        this.keyStrategy = keyStrategy;
    }

    @Override
    public String toString() {
        return "ShardCollectionInfo{" +
                "shardCollection='" + shardCollection + '\'' +
                ", shardInfoList=" + shardInfoList +
                ", keyField='" + keyField + '\'' +
                ", keyStrategy=" + keyStrategy +
                '}';
    }
}
