package org.mcclone.aop;

/**
 * Created by mcclone on 17-8-4.
 */

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Aspect
@Service
public class HelloServiceAspect {

    private static final Logger logger = LoggerFactory.getLogger(HelloServiceAspect.class);

    @Around("execution(* org.mcclone.service.HelloService.sayHello(..))")
    public Object logHelloService(ProceedingJoinPoint pjp) throws Throwable {
        logger.info("before proceed.");
        Object result = pjp.proceed();
        logger.info("after proceed.");
        return result;
    }

}
