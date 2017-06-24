package org.mcclone.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

import java.util.concurrent.CountDownLatch;

/**
 * Created by mcclone on 17-6-24.
 */
public class CuratorTest {

    public static void main(String[] args) {
        CuratorFramework curatorFramework = CuratorFrameworkFactory
                .builder()
                .connectString("localhost:2181")
                .sessionTimeoutMs(5000)
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .build();

        curatorFramework.start();
        try {
            curatorFramework.create()
                    .creatingParentsIfNeeded()
                    .withMode(CreateMode.EPHEMERAL)
                    .forPath("/test/1", "123".getBytes());

            System.out.println(new String(curatorFramework.getData().forPath("/test/1")));

            CountDownLatch countDownLatch = new CountDownLatch(1);
//            curatorFramework.getData().inBackground((client, event) -> {
//                System.out.println(new String(event.getData()));
//                countDownLatch.countDown();
//            }).forPath("/test/1");
//            countDownLatch.await();

            countDownLatch.await();
            curatorFramework.setData().forPath("/test/1", "1".getBytes());
            Thread.sleep(1000);
            curatorFramework.delete().forPath("/test/1");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
