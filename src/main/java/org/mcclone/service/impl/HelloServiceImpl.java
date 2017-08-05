package org.mcclone.service.impl;

import org.mcclone.service.HelloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * Created by mcclone on 17-8-4.
 */
@Service("helloService")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class HelloServiceImpl implements HelloService {

    private static final Logger logger = LoggerFactory.getLogger(HelloServiceImpl.class);

    @Override
    public void sayHello(String name) {
        logger.info("hello," + name);
    }
}
