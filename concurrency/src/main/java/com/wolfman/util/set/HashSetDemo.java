package com.wolfman.util.set;

import java.util.HashSet;
import java.util.TreeMap;

public class HashSetDemo {

    public static void main(String[] args) {
        HashSet set = new HashSet();

        set.add("1");
        set.add("1");
        set.add("2");
        set.remove("1");
        System.out.println(set.size());

        TreeMap map = new TreeMap();

        map.put(null,null);

        map.size();
    }

}
