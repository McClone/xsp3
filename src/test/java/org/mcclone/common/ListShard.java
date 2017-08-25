package org.mcclone.common;

import redis.clients.util.Hashing;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by mcclone on 17-8-25.
 */
public class ListShard {

    public static void main(String[] args) {
        SortedMap<Long, List> holder = new TreeMap<>();
        List<String> list1 = new ArrayList<>();
        list1.add("1");
        List<String> list2 = new ArrayList<>();
        list2.add("2");
        List<String> list3 = new ArrayList<>();
        list3.add("3");

        for (int i = 0; i < 160 * 2; i++) {
            holder.put(Hashing.MD5.hash("1" + i), list1);
        }
        for (int i = 0; i < 160 * 2; i++) {
            holder.put(Hashing.MD5.hash("2" + i), list2);
        }
        for (int i = 0; i < 160 * 2; i++) {
            holder.put(Hashing.MD5.hash("3" + i), list3);
        }

        long l = Hashing.MD5.hash("123");
        System.out.println(l);
        SortedMap<Long, List> result = holder.tailMap(l);
        List list = result.get(result.firstKey());
        System.out.println(list);

    }
}
