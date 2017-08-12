package org.mcclone.aop;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Created by mcclone on 17-8-12.
 */
public class HelloNameCacheAdvice implements MethodInterceptor {

    public static final Logger logger = LoggerFactory.getLogger(HelloNameCacheAdvice.class);

    private static final Cache<String, Object> cache = CacheBuilder
            .newBuilder()
            .expireAfterWrite(1, TimeUnit.MINUTES)
            .build();

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object[] arguments = invocation.getArguments();
        StringBuilder key = new StringBuilder();
        for (Object args : arguments) {
            key.append(args.toString());
        }
        String cacheId = DigestUtils.md2Hex(key.toString());
        Object result = cache.getIfPresent(cacheId);
        if (result == null) {
            logger.info("loading data.");
            result = invocation.proceed();
            cache.put(cacheId, result);
        }
        return result;
    }
}
