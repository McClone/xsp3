package org.mcclone;

import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.CountDownLatch;

/**
 * @author McClone
 */
public class SpringRunner {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"spring.xml"});
        CountDownLatch countDownLatch = new CountDownLatch(1);
        ZooKeeper zooKeeper = context.getBean("zooKeeper", ZooKeeper.class);
        try {
            zooKeeper.register(event -> {
                if (Watcher.Event.EventType.NodeCreated == event.getType()) {
                    System.out.println("NodeCreated" + event);
                }
                if (Watcher.Event.EventType.NodeDeleted == event.getType()) {
                    System.out.println("NodeDeleted" + event);
                }
                if (Watcher.Event.EventType.None == event.getType()) {
                    System.out.println("None" + event);
                }
            });
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
