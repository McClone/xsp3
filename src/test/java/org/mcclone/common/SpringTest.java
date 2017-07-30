package org.mcclone.common;

import org.junit.Test;
import org.springframework.util.MethodInvoker;

/**
 * Created by Administrator on 2017/7/30.
 */
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
