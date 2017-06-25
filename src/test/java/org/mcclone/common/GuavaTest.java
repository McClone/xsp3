package org.mcclone.common;

import com.google.common.base.CharMatcher;
import com.google.common.base.Joiner;
import com.google.common.collect.*;
import com.google.common.hash.Hashing;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by mcclone on 17-6-25.
 */
public class GuavaTest {

    @Test
    public void testJoiner() throws Exception {
        List<String> stringList = new ArrayList<>(Arrays.asList("1", "2", "3"));
        String result = Joiner.on("+").skipNulls().join(stringList);
        System.out.println(result);

    }

    @Test
    public void testCharMatcher() throws Exception {
        String str = "123SSdfddfdf-~&*##";
        //删除数字
        System.out.println(CharMatcher.DIGIT.removeFrom(str));
        //保留数字和小写字母
        System.out.println(CharMatcher.DIGIT.or(CharMatcher.JAVA_LOWER_CASE).retainFrom(str));
    }

    @Test
    public void testHashing() throws Exception {
        String hashStr = Hashing.md5().hashUnencodedChars("123").toString();
        System.out.println(hashStr);
    }

    @Test
    public void testBiMap() throws Exception {
        BiMap<String, Integer> userId = HashBiMap.create();
        userId.put("admin", 1);
//        userId.put("admin1", 1);
        System.out.println(userId.inverse().get(1));
    }

    @Test
    public void testMultiset() throws Exception {
        Multiset<String> multiset = HashMultiset.create();
        multiset.addAll(Lists.newArrayList("1", "1", "2", "2"));
        System.out.println(multiset.elementSet());
        System.out.println(multiset.entrySet());
    }

    @Test
    public void testMultimap() throws Exception {
        Multimap<String, Integer> multimap = HashMultimap.create();
        multimap.put("foo", 1);
        multimap.put("foo", 2);
        multimap.put("foo", 3);
        multimap.put("bar", 4);
        multimap.put("bar", 6);
        multimap.put("milk", 5);
        System.out.println(multimap.get("foo"));
    }

    @Test
    public void testImmutableList() throws Exception{
        ImmutableList<String> immutableList = ImmutableList.of("t1", "t2");
        System.out.println(immutableList);
    }
}
