package org.mcclone.guava;

import com.google.common.base.CharMatcher;
import com.google.common.base.Joiner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by mcclone on 17-4-4.
 */
public class UtilsExample {

    public static void main(String[] args) {
        List<String> stringList = new ArrayList<>(Arrays.asList("1", "2", "3"));
        String result = Joiner.on("+").skipNulls().join(stringList);
        System.out.println(result);

        String str = "123SSdfddfdf-~&*##";
        //删除数字
        System.out.println(CharMatcher.DIGIT.removeFrom(str));
        //保留数字和小写字母
        System.out.println(CharMatcher.DIGIT.or(CharMatcher.JAVA_LOWER_CASE).retainFrom(str));
    }
}