package org.mcclone.event;

import org.mcclone.context.PropertyContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.annotation.Async;

/**
 * @author McClone
 */
public class ContextRefreshedEventListener implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger logger = LoggerFactory.getLogger(ContextRefreshedEventListener.class);

    @Async
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        logger.info("{} Load success.", PropertyContextHolder.getProperty("application.name"));
    }
}
