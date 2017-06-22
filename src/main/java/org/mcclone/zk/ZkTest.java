package org.mcclone.zk;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Created by mcclone on 17-6-11.
 */
public class ZkTest {

    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    public static void main(String[] args) {

        try {
            ZooKeeper zk = new ZooKeeper("localhost:2181", 5000, event -> {
                if (Watcher.Event.KeeperState.SyncConnected == event.getState()) {
                    countDownLatch.countDown();
                }
            });
            countDownLatch.await();
            System.out.println("zk success.");
            zk.create("/test", "123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
            System.out.printf(new String(zk.getData("/test", true, new Stat())));
        } catch (IOException | InterruptedException | KeeperException e) {
            e.printStackTrace();
        }
    }


}
