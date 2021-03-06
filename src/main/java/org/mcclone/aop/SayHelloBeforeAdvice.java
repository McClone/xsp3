package org.mcclone.aop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author mcclone
 */
@Component
public class SayHelloBeforeAdvice implements MethodBeforeAdvice {

    private static final Logger logger = LoggerFactory.getLogger(SayHelloBeforeAdvice.class);

    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        logger.info(method.getName());
    }
}
