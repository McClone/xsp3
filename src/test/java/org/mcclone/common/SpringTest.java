package org.mcclone.common;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.MethodInvoker;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author mcclone
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring.xml")
public class SpringTest {

    @Resource
    private JmsTemplate jmsTemplate;

    @Test
    public void testMethodInvoker() throws Exception {
        MethodInvoker methodInvoker = new MethodInvoker();
        String str = "12345";
        methodInvoker.setTargetObject(str);
        methodInvoker.setTargetMethod("concat");
        methodInvoker.setArguments(new String[]{"1"});
        methodInvoker.prepare();
        System.out.println(methodInvoker.invoke());
    }

    @Test
    public void testJmsTemplate() throws Exception {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(10);
        threadPoolTaskExecutor.initialize();
        for (int i = 0; i < 10; i++) {
            threadPoolTaskExecutor.execute(() -> jmsTemplate.send(session -> session.createTextMessage("hello,Jms." + new Date())));
        }
    }

    @Test
    public void testJmsListen() throws Exception {
        Thread.sleep(5000);
    }

}
