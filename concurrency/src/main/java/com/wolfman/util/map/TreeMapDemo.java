package com.wolfman.util.map;

import sun.reflect.generics.tree.Tree;

import java.util.Comparator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class TreeMapDemo {

    public static void main(String[] args) {
        SortedMap<String,String> map = new TreeMap();
        map.put("1","2");
        map.put("2","2");
        map.put("3","2");
        map.put("4","2");
        map.put("5","2");
        map.put("6","2");
        map.put("7","2");
        TreeMap<String,String> treeMap = new TreeMap<>(map);







    }


}
