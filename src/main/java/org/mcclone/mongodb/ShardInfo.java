package org.mcclone.mongodb;

/**
 * Created by Administrator on 2017/12/16.
 */
public class ShardInfo {

    private String host;
    private Integer port;
    private String userName;
    private String password;
    private String databaseName;
    private String collectionName;
    private long startKey;
    private long endKey;

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

    public boolean match(long key) {
        return key >= startKey && key <= endKey;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShardInfo shardInfo = (ShardInfo) o;

        if (!host.equals(shardInfo.host)) return false;
        if (!port.equals(shardInfo.port)) return false;
        if (userName != null ? !userName.equals(shardInfo.userName) : shardInfo.userName != null) return false;
        if (password != null ? !password.equals(shardInfo.password) : shardInfo.password != null) return false;
        if (!databaseName.equals(shardInfo.databaseName)) return false;
        return collectionName.equals(shardInfo.collectionName);
    }

    @Override
    public int hashCode() {
        int result = host.hashCode();
        result = 31 * result + port.hashCode();
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + databaseName.hashCode();
        result = 31 * result + collectionName.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ShardInfo{" +
                "host='" + host + '\'' +
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
