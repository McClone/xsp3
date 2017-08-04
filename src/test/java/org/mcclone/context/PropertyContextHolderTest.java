package org.mcclone.context;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by mcclone on 17-8-4.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring.xml")
public class PropertyContextHolderTest {
    @Test
    public void getProperty() throws Exception {
        System.out.println(PropertyContextHolder.getProperty("zk.url"));
        Thread.sleep(5000);
        PropertyContextHolder.reload();
        System.out.println(PropertyContextHolder.getProperty("zk.url"));
    }

}