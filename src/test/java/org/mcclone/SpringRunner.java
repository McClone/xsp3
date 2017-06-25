package org.mcclone;

import org.apache.curator.utils.ZKPaths;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author McClone
 */
public class SpringRunner {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(new String[]{"spring.xml"});
        ZooKeeper zooKeeper = applicationContext.getBean(ZooKeeper.class);
        try {
            ZKPaths.mkdirs(zooKeeper, "/test/01");
        } catch (InterruptedException | KeeperException e) {
            e.printStackTrace();
        }

    }
}
