package org.mcclone.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by mcclone on 17-6-25.
 */
public class CuratorFrameworkTest {

    private CuratorFramework curatorFramework;

    @Before
    public void setUp() throws Exception {
        curatorFramework = CuratorFrameworkFactory
                .builder()
                .connectString("localhost:2181")
                .sessionTimeoutMs(5000)
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .build();
        curatorFramework.start();
    }

    @Test
    public void testInterProcessMutex() throws Exception {
        InterProcessMutex lock = new InterProcessMutex(curatorFramework, "/lock");
        CountDownLatch countDownLatch = new CountDownLatch(10);
        final AtomicInteger atomicInteger = new AtomicInteger(0);
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    lock.acquire();
                    System.out.println("data:" + atomicInteger.incrementAndGet());
                    countDownLatch.countDown();
                    lock.release();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
        countDownLatch.await();
        System.out.println(atomicInteger);
    }

    @Test
    public void create() throws Exception {
        curatorFramework.create()
                .creatingParentsIfNeeded()
                .withMode(CreateMode.EPHEMERAL)
                .forPath("/test/1", "123".getBytes());
    }

    @Test
    public void getData() throws Exception {
        System.out.println(new String(curatorFramework.getData().forPath("/zookeeper")));
    }

}
