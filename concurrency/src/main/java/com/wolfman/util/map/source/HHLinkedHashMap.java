package com.wolfman.util.map.source;

import java.util.Map;

/**
 * @ClassName HHLinkedHashMap
 * @Description TODU
 * @Author huhao
 * @Date Create in 2018/5/24 9:50
 * @Version 1.0
 */
public class HHLinkedHashMap<K,V> extends HHHashMap<K,V>
        implements Map<K,V> {


    static class Entry<K,V> extends HHHashMap.Node<K,V> {
        HHLinkedHashMap.Entry<K,V> before, after;

        Entry(int hash, K key, V value, HHHashMap.Node<K,V> next) {
            super(hash, key, value, next);
        }

    }




}
