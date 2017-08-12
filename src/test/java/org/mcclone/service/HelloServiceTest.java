package org.mcclone.service;

import org.apache.commons.lang.time.DateFormatUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mcclone.aop.SayHelloBeforeAdvice;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by mcclone on 17-8-4.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring.xml")
public class HelloServiceTest {

    @Resource(name = "helloService")
    private HelloService helloService;

    @Autowired
    private SayHelloBeforeAdvice beforeAdvice;

    @Test
    public void sayHello() throws Exception {
        helloService.sayHello(Thread.currentThread().getName());
    }

    @Test
    public void testProxyFactory() throws Exception {
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setInterfaces(HelloService.class);
        proxyFactory.setTarget(helloService);
        proxyFactory.addAdvice(beforeAdvice);
        HelloService proxy = (HelloService) proxyFactory.getProxy();
        proxy.sayHello(DateFormatUtils.ISO_DATE_FORMAT.format(new Date()));
    }

    @Test
    public void getName() throws Exception {
        System.out.println(helloService.getName("1"));
        System.out.println(helloService.getName("2"));
        System.out.println(helloService.getName("1"));
    }

}