package org.mcclone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author McClone
 */
@Service
public class SimpleApplicationEventListen implements ApplicationListener<SimpleApplicationEvent> {

    private static final Logger logger = LoggerFactory.getLogger(SimpleApplicationEventListen.class);

    @Value("${application.name}")
    private String applicationName;

    @Async
    @Override
    public void onApplicationEvent(SimpleApplicationEvent event) {
        logger.info("SimpleApplicationEventListen...................." + applicationName);
    }
}
