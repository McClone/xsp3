package org.mcclone.event;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

/**
 * Created by mcclone on 17-6-24.
 */
public class ContextRefreshedEventListenerForRegisterServiceTest {

    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    @Test
    public void onApplicationEvent() throws Exception {
        CuratorFramework curatorFramework = CuratorFrameworkFactory
                .builder()
                .connectString("localhost:2181")
                .sessionTimeoutMs(5000)
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .build();

        curatorFramework.start();
        PathChildrenCache pathChildrenCache = new PathChildrenCache(curatorFramework, "/service", true);
        pathChildrenCache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);
        pathChildrenCache.getListenable().addListener((client, event) -> {
            if (event.getType() == PathChildrenCacheEvent.Type.CHILD_ADDED) {
                System.out.println("CHILD_ADDED:" + event.getData().getPath());
            }
            if (event.getType() == PathChildrenCacheEvent.Type.CHILD_REMOVED) {
                System.out.println("CHILD_REMOVED:" + event.getData().getPath());
            }
        });
        countDownLatch.await();
    }

}