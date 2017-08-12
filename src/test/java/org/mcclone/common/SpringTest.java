package org.mcclone.common;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jms.core.JmsTemplate;
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

    @Autowired(required = false)
    private JmsTemplate jmsTemplate;

    @Resource
    private TaskExecutor executor;

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
        for (int i = 0; i < 10; i++) {
            executor.execute(() -> jmsTemplate.send(session -> session.createTextMessage("hello,Jms." + new Date())));
        }
    }

    @Test
    public void testExecutor() throws Exception {
        executor.execute(() -> System.out.println(new Date()));
    }

    @Test
    public void testJmsListen() throws Exception {
        Thread.sleep(5000);
    }

}
