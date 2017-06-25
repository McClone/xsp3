package org.mcclone.zookeeper;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.FactoryBean;

/**
 * Created by mcclone on 17-6-25.
 */
public class CuratorFrameworkFactoryBean implements FactoryBean<CuratorFramework> {

    private String connectString = "localhost:2181";
    private RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);

    @Override
    public CuratorFramework getObject() throws Exception {
        return CuratorFrameworkFactory.newClient(connectString, retryPolicy);
    }

    @Override
    public Class<?> getObjectType() {
        return CuratorFramework.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
