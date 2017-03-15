package org.mcclone.context;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.support.PropertiesLoaderSupport;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Properties;

/**
 * @author McClone
 */
public class PropertyContextHolder implements ApplicationContextAware, InitializingBean {

    private ApplicationContext applicationContext;

    private static Properties properties;

    public static String getProperty(String key) {
        if (properties == null) return null;
        return properties.getProperty(key);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        loadProperties();
    }

    protected void loadProperties() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String[] beanNames = applicationContext.getBeanNamesForType(PropertiesLoaderSupport.class);
        Properties properties = null;
        for (String beanName : beanNames) {
            PropertiesLoaderSupport propertiesLoaderSupport = applicationContext.getBean(beanName, PropertiesLoaderSupport.class);
            Method method = PropertiesLoaderSupport.class.getDeclaredMethod("mergeProperties");
            method.setAccessible(true);
            if (properties != null) {
                Properties loadProperties = (Properties) method.invoke(propertiesLoaderSupport);
                CollectionUtils.mergePropertiesIntoMap(loadProperties, properties);
            } else {
                properties = (Properties) method.invoke(propertiesLoaderSupport);
            }
        }
        PropertyContextHolder.properties = properties;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
