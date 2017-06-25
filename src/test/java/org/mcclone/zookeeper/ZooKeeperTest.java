package org.mcclone.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Created by mcclone on 17-6-25.
 */
public class ZooKeeperTest {

    private ZooKeeper zooKeeper;

    @Before
    public void setUp() throws Exception {
        zooKeeper = new ZooKeeper("localhost:2181", 5000, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                if (Event.KeeperState.SyncConnected == event.getState()) {
                    System.out.println("zk Connected.");
                }
            }
        });
    }

    @Test
    public void create() throws Exception {
        String path = zooKeeper.create("/test", "123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        assertNotNull(path);
        System.out.println(path);
    }

    @Test
    public void exists() throws Exception {
        Stat exists = zooKeeper.exists("/service", true);
        assertNotNull(exists);
        System.out.println(exists);
    }

    @Test
    public void getData() throws Exception {
        byte[] result = zooKeeper.getData("/service", true, new Stat());
        System.out.println(new String(result));
    }

    @Test
    public void delete() throws Exception {
        Stat exists = zooKeeper.exists("/service", true);
        if (exists != null) {
            zooKeeper.delete("/service", exists.getVersion());
        }
    }
}
