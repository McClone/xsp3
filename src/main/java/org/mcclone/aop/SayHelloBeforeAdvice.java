package org.mcclone.aop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 * Created by mcclone on 17-8-4.
 */
public class SayHelloBeforeAdvice implements MethodBeforeAdvice {

    public static final Logger logger = LoggerFactory.getLogger(SayHelloBeforeAdvice.class);

    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        logger.info(method.getName());
    }
}
