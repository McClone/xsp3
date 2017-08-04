package org.mcclone.context;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.support.PropertiesLoaderSupport;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MethodInvoker;

import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

/**
 * @author McClone
 */
public class PropertyContextHolder implements ApplicationContextAware, InitializingBean {

    private static ApplicationContext applicationContext;

    private final static Properties properties = new Properties();

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        loadProperties();
    }

    private static void loadProperties() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, ClassNotFoundException {
        String[] beanNames = applicationContext.getBeanNamesForType(PropertiesLoaderSupport.class);
        for (String beanName : beanNames) {
            PropertiesLoaderSupport propertiesLoaderSupport = applicationContext.getBean(beanName, PropertiesLoaderSupport.class);
            MethodInvoker methodInvoker = new MethodInvoker();
            methodInvoker.setTargetObject(propertiesLoaderSupport);
            methodInvoker.setTargetMethod("mergeProperties");
            methodInvoker.prepare();
            Properties loadProperties = (Properties) methodInvoker.invoke();
            CollectionUtils.mergePropertiesIntoMap(loadProperties, properties);
        }
    }

    public static void reload() {
        try {
            loadProperties();
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        PropertyContextHolder.applicationContext = applicationContext;
    }
}
