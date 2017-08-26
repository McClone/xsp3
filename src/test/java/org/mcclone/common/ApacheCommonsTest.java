package org.mcclone.common;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.OrderedBidiMap;
import org.apache.commons.collections.bidimap.TreeBidiMap;
import org.apache.commons.collections.map.LRUMap;
import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.SystemUtils;
import org.junit.Test;
import org.springframework.cglib.beans.BeanCopier;
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

    @Test
    public void testDigest() throws Exception {
        System.out.println(DigestUtils.md5Hex("hello"));
        System.out.println(DigestUtils.sha1Hex("hello"));
    }

    @Test
    public void testLRUMap() throws Exception {
        LRUMap lruMap = new LRUMap();
        for (int i = 0; i < 200; i++) {
            lruMap.put(i, i);
        }
        System.out.println(lruMap);
    }

    @Test
    public void testCopyBean() throws Exception {
        BeanCopier.create(Object.class, String.class, true);
    }
}
