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
                if (Watcher.Event.EventType.NodeDeleted == event.getType()) {
                    System.out.println("NodeDeleted:" + event.getPath());
//                    countDownLatch.countDown();
                }
                if (Watcher.Event.EventType.NodeCreated == event.getType()) {
                    System.out.println("NodeCreated" + event);
                }
                if (Watcher.Event.EventType.NodeDeleted == event.getType()) {
                    System.out.println("NodeDeleted" + event);
                }
                if (Watcher.Event.EventType.NodeChildrenChanged == event.getType()) {
                    System.out.println("NodeChildrenChanged" + event);
                }
                System.out.println(event);
            });

            System.out.println("zk success.");

            zk.create("/test", "123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
            Stat exists = zk.exists("/test", true);
            if (exists != null) {
                zk.delete("/test", exists.getVersion());
            }

            zk.create("/s", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            zk.create("/s/demo", "d".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
            System.out.printf(new String(zk.getData("/test", true, new Stat())));
        } catch (IOException | InterruptedException | KeeperException e) {
            e.printStackTrace();
        }
    }


}
