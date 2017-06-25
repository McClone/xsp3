package org.mcclone.common;

import org.apache.commons.collections.OrderedBidiMap;
import org.apache.commons.collections.bidimap.TreeBidiMap;
import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.SystemUtils;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import java.util.List;

/**
 * Created by mcclone on 17-6-25.
 */
public class ApacheCommonsTest {

    @Test
    public void test() throws Exception {
        System.out.println(SystemUtils.getJavaHome());

        System.out.println(RandomStringUtils.randomNumeric(10));

        List<Class> interfaces = ClassUtils.getAllInterfaces(ApplicationContext.class);
        interfaces.forEach(aClass -> System.out.println(aClass.getName()));

        OrderedBidiMap orderedBidiMap = new TreeBidiMap();
        orderedBidiMap.put("123", 1);
        orderedBidiMap.put("1234", 1);
        System.out.println(orderedBidiMap.getKey(1));
    }
}
