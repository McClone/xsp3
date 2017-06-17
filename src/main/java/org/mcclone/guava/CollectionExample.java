package org.mcclone.guava;

import com.google.common.collect.*;

/**
 * Created by mcclone on 17-6-17.
 */
public class CollectionExample {

    public static void main(String[] args) {
        BiMap<String, Integer> userId = HashBiMap.create();
        userId.put("admin", 1);
        System.out.println(userId.inverse().get(1));

        Multiset<String> multiset = HashMultiset.create();
        multiset.addAll(Lists.newArrayList("1", "1", "2", "2"));
        System.out.println(multiset.elementSet());
        System.out.println(multiset.entrySet());

        Multimap<String, Integer> multimap = HashMultimap.create();
        multimap.put("foo", 1);
        multimap.put("foo", 2);
        multimap.put("foo", 3);
        multimap.put("bar", 4);
        multimap.put("bar", 6);
        multimap.put("milk", 5);

        System.out.println(multimap.get("foo"));

        ImmutableList<String> immutableList = ImmutableList.of("t1", "t2");
        System.out.println(immutableList);

    }
}
