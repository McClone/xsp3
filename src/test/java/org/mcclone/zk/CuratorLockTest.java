package org.mcclone.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by mcclone on 17-6-24.
 */
public class CuratorLockTest {

    private final static CuratorFramework curatorFramework = CuratorFrameworkFactory
            .builder()
            .connectString("localhost:2181")
            .sessionTimeoutMs(5000)
            .retryPolicy(new ExponentialBackoffRetry(1000, 3))
            .build();


    public static void main(String[] args) throws Exception {
        curatorFramework.start();
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
}
