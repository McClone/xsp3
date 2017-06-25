package org.mcclone.zookeeper;

import org.apache.curator.utils.ZKPaths;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by mcclone on 17-6-25.
 */
public class ZKPathsTest {

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
    public void testMkdirs() throws Exception {
        ZKPaths.mkdirs(zooKeeper, "/test/01");
    }
}
