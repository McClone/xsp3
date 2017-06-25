package org.mcclone.zookeeper;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;

/**
 * Created by mcclone on 17-6-24.
 */
public class ZkFactoryBean implements FactoryBean<ZooKeeper>, Watcher {

    private static final Logger logger = LoggerFactory.getLogger(ZkFactoryBean.class);

    private String connectString;
    private int sessionTimeout;
    private Watcher watcher;

    @Override
    public ZooKeeper getObject() throws Exception {
        ZooKeeper zooKeeper;
        if (watcher == null) {
            zooKeeper = new ZooKeeper(connectString, sessionTimeout, this);
        } else {
            zooKeeper = new ZooKeeper(connectString, sessionTimeout, watcher);
        }
        return zooKeeper;
    }

    @Override
    public Class<?> getObjectType() {
        return ZooKeeper.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void process(WatchedEvent event) {
        if (Event.KeeperState.SyncConnected == event.getState()) {
            logger.info("zk Connected.");
        }
    }

    public String getConnectString() {
        return connectString;
    }

    public void setConnectString(String connectString) {
        this.connectString = connectString;
    }

    public int getSessionTimeout() {
        return sessionTimeout;
    }

    public void setSessionTimeout(int sessionTimeout) {
        this.sessionTimeout = sessionTimeout;
    }

    public Watcher getWatcher() {
        return watcher;
    }

    public void setWatcher(Watcher watcher) {
        this.watcher = watcher;
    }
}
