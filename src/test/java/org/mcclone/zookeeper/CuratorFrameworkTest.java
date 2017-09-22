package org.mcclone.zookeeper;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.atomic.DistributedAtomicInteger;
import org.apache.curator.framework.recipes.barriers.DistributedDoubleBarrier;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.apache.curator.framework.recipes.leader.LeaderLatchListener;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.framework.recipes.queue.DistributedQueue;
import org.apache.curator.framework.recipes.queue.QueueBuilder;
import org.apache.curator.framework.recipes.queue.QueueConsumer;
import org.apache.curator.framework.recipes.queue.QueueSerializer;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.ZKPaths;
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

    @Test
    public void testDistributedAtomicInteger() throws Exception {
        //自动创建的是持久节点
        DistributedAtomicInteger atomicInteger = new DistributedAtomicInteger(curatorFramework, "/atomicInteger", new ExponentialBackoffRetry(1000, 3));
        atomicInteger.initialize(1);
        System.out.println(atomicInteger.increment().postValue());
    }

    @Test
    public void testPathChildrenCache() throws Exception {
        PathChildrenCache pathChildrenCache = new PathChildrenCache(curatorFramework, "/test", true);
        //已存在的也会发送事件 POST_INITIALIZED_EVENT
        pathChildrenCache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);
        pathChildrenCache.getListenable().addListener(new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
                switch (event.getType()) {
                    case CHILD_ADDED:
                        System.out.println(event.getType() + "," + event.getData().getPath());
                        break;
                    case CHILD_REMOVED:
                        System.out.println(event.getType() + "," + event.getData().getPath());
                        break;
                    case CHILD_UPDATED:
                        System.out.println(event.getType() + "," + event.getData().getPath());
                        break;
                }
            }
        });
        ZKPaths.mkdirs(curatorFramework.getZookeeperClient().getZooKeeper(), "/test/07");
        Thread.sleep(Integer.MAX_VALUE);
    }

    @Test
    public void testLeaderSelector() throws Exception {

        CountDownLatch countDownLatch = new CountDownLatch(1);
        LeaderSelector selector = new LeaderSelector(curatorFramework, "/master", new LeaderSelectorListenerAdapter() {

            @Override
            public void takeLeadership(CuratorFramework client) throws Exception {
                System.out.println("I am Leader Now");
                System.out.println("release");
                countDownLatch.countDown();
            }
        });

        selector.autoRequeue();
        selector.start();
        countDownLatch.await();
        selector.close();
    }

    @Test
    public void testLeaderLatch() throws Exception {
        LeaderLatch leaderLatch = new LeaderLatch(curatorFramework, "/master-latch", "", LeaderLatch.CloseMode.NOTIFY_LEADER);
        leaderLatch.addListener(new LeaderLatchListener() {
            @Override
            public void isLeader() {
                System.out.println("I am Leader Now");
            }

            @Override
            public void notLeader() {
                System.out.println("release");
            }
        });
        leaderLatch.start();
        leaderLatch.await();
    }

    @Test
    public void testDistributedDoubleBarrier() throws Exception {
        for (int i = 0; i < 5; i++) {
            int finalI = i;
            new Thread(() -> {
                try {
                    DistributedDoubleBarrier distributedDoubleBarrier = new DistributedDoubleBarrier(curatorFramework, "/barrierPath3", 5);
                    System.out.println(finalI);
                    Thread.sleep(Long.parseLong(RandomStringUtils.randomNumeric(3)));
                    distributedDoubleBarrier.enter();
                    System.out.println(System.currentTimeMillis() + ":" + finalI);
                    distributedDoubleBarrier.leave();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
        Thread.sleep(Integer.MAX_VALUE);
    }

    @Test
    public void testQueueBuilder() throws Exception {
        DistributedQueue<String> distributedQueue = QueueBuilder.builder(curatorFramework, new QueueConsumer<String>() {
            @Override
            public void consumeMessage(String message) throws Exception {
                System.out.println("consumeMessage:" + message);
            }

            @Override
            public void stateChanged(CuratorFramework client, ConnectionState newState) {

            }
        }, new QueueSerializer<String>() {
            @Override
            public byte[] serialize(String item) {
                return item.getBytes();
            }

            @Override
            public String deserialize(byte[] bytes) {
                return new String(bytes);
            }
        }, "/queue").buildQueue();
        distributedQueue.start();
        Thread.sleep(Integer.MAX_VALUE);
    }

    @Test
    public void testDistributedQueue() throws Exception {
        DistributedQueue<String> distributedQueue = QueueBuilder.builder(curatorFramework, null, new QueueSerializer<String>() {
            @Override
            public byte[] serialize(String item) {
                return item.getBytes();
            }

            @Override
            public String deserialize(byte[] bytes) {
                return new String(bytes);
            }
        }, "/queue").buildQueue();
        distributedQueue.start();
        for (int i = 0; i < 2; i++) {
            distributedQueue.put(String.valueOf(i));
        }
        distributedQueue.close();
    }
}
