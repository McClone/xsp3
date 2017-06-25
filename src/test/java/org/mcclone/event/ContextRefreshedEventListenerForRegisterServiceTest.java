package org.mcclone.event;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.CountDownLatch;

/**
 * Created by mcclone on 17-6-24.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring.xml")
public class ContextRefreshedEventListenerForRegisterServiceTest {

    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    @Autowired
    private CuratorFramework curatorFramework;

    @Test
    public void onApplicationEvent() throws Exception {
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