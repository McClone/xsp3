package org.mcclone.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by mcclone on 17-8-4.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring.xml")
public class HelloServiceTest {

    @Autowired
    private HelloService helloService;

    @Test
    public void sayHello() throws Exception {
        helloService.sayHello(Thread.currentThread().getName());
    }

}