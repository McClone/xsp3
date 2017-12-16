package org.mcclone.mongodb;

/**
 * Created by Administrator on 2017/12/16.
 */
public class ShardCollectionInfo {

    private String shardCollection;
    private String keyField;
    private String keyStrategy;
    private String host;
    private Integer port;
    private String userName;
    private String password;
    private String databaseName;
    private String collectionName;
    private long startKey;
    private long endKey;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public long getStartKey() {
        return startKey;
    }

    public void setStartKey(long startKey) {
        this.startKey = startKey;
    }

    public long getEndKey() {
        return endKey;
    }

    public void setEndKey(long endKey) {
        this.endKey = endKey;
    }

    public String getShardCollection() {
        return shardCollection;
    }

    public void setShardCollection(String shardCollection) {
        this.shardCollection = shardCollection;
    }

    public String getKeyField() {
        return keyField;
    }

    public void setKeyField(String keyField) {
        this.keyField = keyField;
    }

    public String getKeyStrategy() {
        return keyStrategy;
    }

    public void setKeyStrategy(String keyStrategy) {
        this.keyStrategy = keyStrategy;
    }

   
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ShardCollectionInfo that = (ShardCollectionInfo) o;

        if (!shardCollection.equals(that.shardCollection)) return false;
        if (!host.equals(that.host)) return false;
        if (!port.equals(that.port)) return false;
        if (!databaseName.equals(that.databaseName)) return false;
        return collectionName.equals(that.collectionName);
    }

   
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + shardCollection.hashCode();
        result = 31 * result + host.hashCode();
        result = 31 * result + port.hashCode();
        result = 31 * result + databaseName.hashCode();
        result = 31 * result + collectionName.hashCode();
        return result;
    }

   
    public String toString() {
        return "ShardCollectionInfo{" +
                "shardCollection='" + shardCollection + '\'' +
                ", keyField='" + keyField + '\'' +
                ", keyStrategy='" + keyStrategy + '\'' +
                ", host='" + host + '\'' +
                ", port=" + port +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", databaseName='" + databaseName + '\'' +
                ", collectionName='" + collectionName + '\'' +
                ", startKey=" + startKey +
                ", endKey=" + endKey +
                '}';
    }
}
