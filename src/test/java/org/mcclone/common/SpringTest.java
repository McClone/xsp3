package org.mcclone.common;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.MethodInvoker;

/**
 * @author mcclone
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring.xml")
public class SpringTest {

    @Test
    public void testMethodInvoker() throws Exception {
        MethodInvoker methodInvoker = new MethodInvoker();
        String str = "12345";
        methodInvoker.setTargetObject(str);
        methodInvoker.setTargetMethod("concat");
        methodInvoker.setArguments(new String[]{"1"});
        methodInvoker.prepare();
        System.out.println(methodInvoker.invoke());
    }
}
