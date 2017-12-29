package org.mcclone.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;

import java.util.List;

/**
 * @author Administrator
 */
public class ServiceTracker implements PathChildrenCacheListener {
    private CuratorFramework curatorFramework;
    private String serverZNode;

    public ServiceTracker(CuratorFramework curatorFramework, String serverZNode) {
        this.curatorFramework = curatorFramework;
        this.serverZNode = serverZNode;
    }

    public void init() throws Exception {
        PathChildrenCache pathChildrenCache = new PathChildrenCache(curatorFramework, serverZNode, true);
        pathChildrenCache.getListenable().addListener(this);
        pathChildrenCache.start();
        List<ChildData> childDataList = pathChildrenCache.getCurrentData();

    }

    @Override
    public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
        switch (event.getType()) {
            case CHILD_ADDED:
                break;
            case CHILD_REMOVED:
                break;
        }
    }
}
