//package com.wolfman.util.map.source;
//
//import java.util.*;
//
//public class HHTreeMap<K,V> extends AbstractMap<K,V>
//        implements NavigableMap<K,V>, Cloneable, java.io.Serializable{
//
//
//    //======================================属性开始======================================
//
//    // 比较器。用来给TreeMap排序
//    private final Comparator<? super K> comparator;
//
//    /**
//     * TreeMap是红黑树实现的，root是红黑书的根节点
//     */
//    private transient Entry<K,V> root;
//
//    /**
//     * 红黑树的节点总数
//     */
//    private transient int size = 0;
//
//    /**
//     * 记录红黑树的修改次数
//     */
//    private transient int modCount = 0;
//
//    //======================================属性开始======================================
//    //=============================构造函数===========================================================================
//
//    // 默认构造函数
//    public HHTreeMap() {
//        comparator = null;
//    }
//
//    // 带比较器的构造函数
//    public HHTreeMap(Comparator<? super K> comparator) {
//        this.comparator = comparator;
//    }
//
//    // 带Map的构造函数，Map会成为TreeMap的子集
//    public HHTreeMap(Map<? extends K, ? extends V> m) {
//        comparator = null;
//        putAll(m);
//    }
//
//    /**
//     * 带SortedMap的构造函数，SortedMap会成为TreeMap的子集
//     * @param m
//     */
//    public HHTreeMap(SortedMap<K, ? extends V> m) {
//        comparator = m.comparator();
//        try {
//            buildFromSorted(m.size(), m.entrySet().iterator(), null, null);
//        } catch (java.io.IOException cannotHappen) {
//        } catch (ClassNotFoundException cannotHappen) {
//        }
//    }
//
//    //=============================构造函数===========================================================================
//
//
//    //=============================容量相关============================================================================
//    //=============================容量相关============================================================================
//
//
//    //=============================方法相关============================================================================
//
//    public int size() {
//        return size;
//    }
//
//    /**
//     * 返回TreeMap中是否包括“键(key)”
//     * @param key
//     * @return
//     */
//    public boolean containsKey(Object key) {
//        return getEntry(key) != null;
//    }
//
//    /**
//     * 返回TreeMap中是否包含"值(value)"
//     * @param value
//     * @return
//     */
//    public boolean containsValue(Object value) {
//        for (Entry<K,V> e = getFirstEntry(); e != null; e = successor(e))
//            if (valEquals(value, e.value))
//                return true;
//        return false;
//    }
//
//    /**
//     * 获取“键(key)”对应的“值(value)”
//     * @param key
//     * @return
//     */
//    public V get(Object key) {
//        // 获取“键”为key的节点(p)
//        Entry<K,V> p = getEntry(key);
//        // 若节点(p)为null，返回null；否则，返回节点对应的值
//        return (p==null ? null : p.value);
//    }
//
//
//    public Comparator<? super K> comparator() {
//        return comparator;
//    }
//
//    /**
//     * 获取第一个节点对应的key
//     * @return
//     */
//    public K firstKey() {
//        return key(getFirstEntry());
//    }
//
//    /**
//     * 获取最后一个节点对应的key
//     * @return
//     */
//    public K lastKey() {
//        return key(getLastEntry());
//    }
//
//    static <K> K key(Entry<K,?> e) {
//        if (e==null)
//            throw new NoSuchElementException();
//        return e.key;
//    }
//
//
//
//    /**
//     * 返回“红黑树的第一个节点”
//     * @return
//     */
//    final Entry<K,V> getFirstEntry() {
//        Entry<K,V> p = root;
//        if (p != null)
//            while (p.left != null)
//                p = p.left;
//        return p;
//    }
//
//    /**
//     * 返回“红黑树的最后一个节点”
//     * @return
//     */
//    final Entry<K,V> getLastEntry() {
//        Entry<K,V> p = root;
//        if (p != null)
//            while (p.right != null)
//                p = p.right;
//        return p;
//    }
//
//    /**
//     * 将map中的全部节点添加到TreeMap中
//     * @param map
//     */
//    public void putAll(Map<? extends K, ? extends V> map) {
//        // 获取map的大小
//        int mapSize = map.size();
//        // 如果TreeMap的大小是0,且map的大小不是0,且map是已排序的“key-value对”
//        if (size==0 && mapSize!=0 && map instanceof SortedMap) {
//            Comparator<?> c = ((SortedMap<?,?>)map).comparator();
//            // 如果TreeMap和map的比较器相等；
//            // 则将map的元素全部拷贝到TreeMap中，然后返回！
//            if (c == comparator || (c != null && c.equals(comparator))) {
//                ++modCount;
//                try {
//                    buildFromSorted(mapSize, map.entrySet().iterator(),
//                            null, null);
//                } catch (java.io.IOException cannotHappen) {
//                } catch (ClassNotFoundException cannotHappen) {
//                }
//                return;
//            }
//        }
//        // 调用AbstractMap中的putAll();
//        // AbstractMap中的putAll()又会调用到TreeMap的put()
//        super.putAll(map);
//    }
//
//    /**
//     * 获取TreeMap中不小于key的最小的节点；
//     * 若不存在(即TreeMap中所有节点的键都比key大)，就返回null
//     * @param key
//     * @return
//     */
//    final Entry<K,V> getCeilingEntry(K key) {
//        Entry<K,V> p = root;
//        while (p != null) {
//            int cmp = compare(key, p.key);
//            // 情况一：若“p的key” > key。
//            // 若 p 存在左孩子，则设 p=“p的左孩子”；
//            // 否则，返回p
//            if (cmp < 0) {
//                if (p.left != null)
//                    p = p.left;
//                else
//                    return p;
//                // 情况二：若“p的key” < key。
//            } else if (cmp > 0) {
//                if (p.right != null) {
//                    p = p.right;
//                } else {
//                    Entry<K,V> parent = p.parent;
//                    Entry<K,V> ch = p;
//                    while (parent != null && ch == parent.right) {
//                        ch = parent;
//                        parent = parent.parent;
//                    }
//                    return parent;
//                }
//                // 情况三：若“p的key” = key。
//            } else
//                return p;
//        }
//        return null;
//    }
//
//
//    /**
//     * 获取TreeMap中不大于key的最大的节点；
//     * 若不存在(即TreeMap中所有节点的键都比key小)，就返回null
//     * @param key
//     * @return
//     */
//    final Entry<K,V> getFloorEntry(K key) {
//        Entry<K,V> p = root;
//        while (p != null) {
//            int cmp = compare(key, p.key);
//            if (cmp > 0) {
//                if (p.right != null)
//                    p = p.right;
//                else
//                    return p;
//            } else if (cmp < 0) {
//                if (p.left != null) {
//                    p = p.left;
//                } else {
//                    Entry<K,V> parent = p.parent;
//                    Entry<K,V> ch = p;
//                    while (parent != null && ch == parent.left) {
//                        ch = parent;
//                        parent = parent.parent;
//                    }
//                    return parent;
//                }
//            } else
//                return p;
//
//        }
//        return null;
//    }
//
//    /**
//     * 获取TreeMap中大于key的最小的节点。
//     * 若不存在，就返回null。
//     * @param key
//     * @return
//     */
//    final Entry<K,V> getHigherEntry(K key) {
//        Entry<K,V> p = root;
//        while (p != null) {
//            int cmp = compare(key, p.key);
//            if (cmp < 0) {
//                if (p.left != null)
//                    p = p.left;
//                else
//                    return p;
//            } else {
//                if (p.right != null) {
//                    p = p.right;
//                } else {
//                    Entry<K,V> parent = p.parent;
//                    Entry<K,V> ch = p;
//                    while (parent != null && ch == parent.right) {
//                        ch = parent;
//                        parent = parent.parent;
//                    }
//                    return parent;
//                }
//            }
//        }
//        return null;
//    }
//
//    /**
//     * 获取TreeMap中小于key的最大的节点。
//     * 若不存在，就返回null。
//     * @param key
//     * @return
//     */
//    final Entry<K,V> getLowerEntry(K key) {
//        Entry<K,V> p = root;
//        while (p != null) {
//            int cmp = compare(key, p.key);
//            if (cmp > 0) {
//                if (p.right != null)
//                    p = p.right;
//                else
//                    return p;
//            } else {
//                if (p.left != null) {
//                    p = p.left;
//                } else {
//                    Entry<K,V> parent = p.parent;
//                    Entry<K,V> ch = p;
//                    while (parent != null && ch == parent.left) {
//                        ch = parent;
//                        parent = parent.parent;
//                    }
//                    return parent;
//                }
//            }
//        }
//        return null;
//    }
//
//    /**
//     * 将“key, value”添加到TreeMap中
//     * 理解TreeMap的前提是掌握“红黑树”。
//     * 若理解“红黑树中添加节点”的算法，则很容易理解put。
//     * @param key
//     * @param value
//     * @return
//     */
//    public V put(K key, V value) {
//        Entry<K,V> t = root;
//        // 若红黑树为空，则插入根节点
//        if (t == null) {
//            compare(key, key); // type (and possibly null) check
//            root = new Entry<>(key, value, null);
//            size = 1;
//            modCount++;
//            return null;
//        }
//        int cmp;
//        Entry<K,V> parent;
//        // split comparator and comparable paths
//        Comparator<? super K> cpr = comparator;
//        if (cpr != null) {
//            do {
//                parent = t;
//                cmp = cpr.compare(key, t.key);
//                if (cmp < 0)
//                    t = t.left;
//                else if (cmp > 0)
//                    t = t.right;
//                else
//                    return t.setValue(value);
//            } while (t != null);
//        }
//        else {
//            if (key == null)
//                throw new NullPointerException();
//            @SuppressWarnings("unchecked")
//            Comparable<? super K> k = (Comparable<? super K>) key;
//            do {
//                parent = t;
//                cmp = k.compareTo(t.key);
//                if (cmp < 0)
//                    t = t.left;
//                else if (cmp > 0)
//                    t = t.right;
//                else
//                    return t.setValue(value);
//            } while (t != null);
//        }
//        Entry<K,V> e = new Entry<>(key, value, parent);
//        if (cmp < 0)
//            parent.left = e;
//        else
//            parent.right = e;
//        fixAfterInsertion(e);
//        size++;
//        modCount++;
//        return null;
//    }
//
//    /**
//     * 删除TreeMap中的键为key的节点，并返回节点的值
//     * @param key
//     * @return
//     */
//    public V remove(Object key) {
//        Entry<K,V> p = getEntry(key);
//        if (p == null)
//            return null;
//
//        V oldValue = p.value;
//        deleteEntry(p);
//        return oldValue;
//    }
//
//    /**
//     * 清空红黑树
//     */
//    public void clear() {
//        modCount++;
//        size = 0;
//        root = null;
//    }
//
//    public Object clone() {
//        HHTreeMap<?,?> clone;
//        try {
//            clone = (HHTreeMap<?,?>) super.clone();
//        } catch (CloneNotSupportedException e) {
//            throw new InternalError(e);
//        }
//
//        // Put clone into "virgin" state (except for comparator)
//        clone.root = null;
//        clone.size = 0;
//        clone.modCount = 0;
//        clone.entrySet = null;
//        clone.navigableKeySet = null;
//        clone.descendingMap = null;
//
//        // Initialize clone with our mappings
//        try {
//            clone.buildFromSorted(size, entrySet().iterator(), null, null);
//        } catch (java.io.IOException cannotHappen) {
//        } catch (ClassNotFoundException cannotHappen) {
//        }
//
//        return clone;
//    }
//
//    private transient EntrySet entrySet;
//    private transient KeySet<K> navigableKeySet;
//    private transient NavigableMap<K,V> descendingMap;
//
//
//    private void deleteEntry(Entry<K,V> p) {
//        modCount++;
//        size--;
//
//        // If strictly internal, copy successor's element to p and then make p
//        // point to successor.
//        if (p.left != null && p.right != null) {
//            Entry<K,V> s = successor(p);
//            p.key = s.key;
//            p.value = s.value;
//            p = s;
//        } // p has 2 children
//
//        // Start fixup at replacement node, if it exists.
//        Entry<K,V> replacement = (p.left != null ? p.left : p.right);
//
//        if (replacement != null) {
//            // Link replacement to parent
//            replacement.parent = p.parent;
//            if (p.parent == null)
//                root = replacement;
//            else if (p == p.parent.left)
//                p.parent.left  = replacement;
//            else
//                p.parent.right = replacement;
//
//            // Null out links so they are OK to use by fixAfterDeletion.
//            p.left = p.right = p.parent = null;
//
//            // Fix replacement
//            if (p.color == BLACK)
//                fixAfterDeletion(replacement);
//        } else if (p.parent == null) { // return if we are the only node.
//            root = null;
//        } else { //  No children. Use self as phantom replacement and unlink.
//            if (p.color == BLACK)
//                fixAfterDeletion(p);
//
//            if (p.parent != null) {
//                if (p == p.parent.left)
//                    p.parent.left = null;
//                else if (p == p.parent.right)
//                    p.parent.right = null;
//                p.parent = null;
//            }
//        }
//    }
//
//    /** From CLR */
//    private void fixAfterDeletion(Entry<K,V> x) {
//        while (x != root && colorOf(x) == BLACK) {
//            if (x == leftOf(parentOf(x))) {
//                Entry<K,V> sib = rightOf(parentOf(x));
//
//                if (colorOf(sib) == RED) {
//                    setColor(sib, BLACK);
//                    setColor(parentOf(x), RED);
//                    rotateLeft(parentOf(x));
//                    sib = rightOf(parentOf(x));
//                }
//
//                if (colorOf(leftOf(sib))  == BLACK &&
//                        colorOf(rightOf(sib)) == BLACK) {
//                    setColor(sib, RED);
//                    x = parentOf(x);
//                } else {
//                    if (colorOf(rightOf(sib)) == BLACK) {
//                        setColor(leftOf(sib), BLACK);
//                        setColor(sib, RED);
//                        rotateRight(sib);
//                        sib = rightOf(parentOf(x));
//                    }
//                    setColor(sib, colorOf(parentOf(x)));
//                    setColor(parentOf(x), BLACK);
//                    setColor(rightOf(sib), BLACK);
//                    rotateLeft(parentOf(x));
//                    x = root;
//                }
//            } else { // symmetric
//                Entry<K,V> sib = leftOf(parentOf(x));
//
//                if (colorOf(sib) == RED) {
//                    setColor(sib, BLACK);
//                    setColor(parentOf(x), RED);
//                    rotateRight(parentOf(x));
//                    sib = leftOf(parentOf(x));
//                }
//
//                if (colorOf(rightOf(sib)) == BLACK &&
//                        colorOf(leftOf(sib)) == BLACK) {
//                    setColor(sib, RED);
//                    x = parentOf(x);
//                } else {
//                    if (colorOf(leftOf(sib)) == BLACK) {
//                        setColor(rightOf(sib), BLACK);
//                        setColor(sib, RED);
//                        rotateLeft(sib);
//                        sib = leftOf(parentOf(x));
//                    }
//                    setColor(sib, colorOf(parentOf(x)));
//                    setColor(parentOf(x), BLACK);
//                    setColor(leftOf(sib), BLACK);
//                    rotateRight(parentOf(x));
//                    x = root;
//                }
//            }
//        }
//
//        setColor(x, BLACK);
//    }
//
//
//    /**
//     * 插入之后的修正操作。
//     * 目的是保证：红黑树插入节点之后，仍然是一颗红黑树
//     * @param x
//     */
//    private void fixAfterInsertion(Entry<K,V> x) {
//        x.color = RED;
//        while (x != null && x != root && x.parent.color == RED) {
//            if (parentOf(x) == leftOf(parentOf(parentOf(x)))) {
//                Entry<K,V> y = rightOf(parentOf(parentOf(x)));
//                if (colorOf(y) == RED) {
//                    setColor(parentOf(x), BLACK);
//                    setColor(y, BLACK);
//                    setColor(parentOf(parentOf(x)), RED);
//                    x = parentOf(parentOf(x));
//                } else {
//                    if (x == rightOf(parentOf(x))) {
//                        x = parentOf(x);
//                        rotateLeft(x);
//                    }
//                    setColor(parentOf(x), BLACK);
//                    setColor(parentOf(parentOf(x)), RED);
//                    rotateRight(parentOf(parentOf(x)));
//                }
//            } else {
//                Entry<K,V> y = leftOf(parentOf(parentOf(x)));
//                if (colorOf(y) == RED) {
//                    setColor(parentOf(x), BLACK);
//                    setColor(y, BLACK);
//                    setColor(parentOf(parentOf(x)), RED);
//                    x = parentOf(parentOf(x));
//                } else {
//                    if (x == leftOf(parentOf(x))) {
//                        x = parentOf(x);
//                        rotateRight(x);
//                    }
//                    setColor(parentOf(x), BLACK);
//                    setColor(parentOf(parentOf(x)), RED);
//                    rotateLeft(parentOf(parentOf(x)));
//                }
//            }
//        }
//        root.color = BLACK;
//    }
//
//    private static <K,V> Entry<K,V> parentOf(Entry<K,V> p) {
//        return (p == null ? null: p.parent);
//    }
//
//    private static <K,V> boolean colorOf(Entry<K,V> p) {
//        return (p == null ? BLACK : p.color);
//    }
//
//
//    private static <K,V> void setColor(Entry<K,V> p, boolean c) {
//        if (p != null)
//            p.color = c;
//    }
//
//    private static <K,V> Entry<K,V> leftOf(Entry<K,V> p) {
//        return (p == null) ? null: p.left;
//    }
//
//    private static <K,V> Entry<K,V> rightOf(Entry<K,V> p) {
//        return (p == null) ? null: p.right;
//    }
//
//    /** From CLR */
//    private void rotateLeft(Entry<K,V> p) {
//        if (p != null) {
//            Entry<K,V> r = p.right;
//            p.right = r.left;
//            if (r.left != null)
//                r.left.parent = p;
//            r.parent = p.parent;
//            if (p.parent == null)
//                root = r;
//            else if (p.parent.left == p)
//                p.parent.left = r;
//            else
//                p.parent.right = r;
//            r.left = p;
//            p.parent = r;
//        }
//    }
//
//    /** From CLR */
//    private void rotateRight(Entry<K,V> p) {
//        if (p != null) {
//            Entry<K,V> l = p.left;
//            p.left = l.right;
//            if (l.right != null) l.right.parent = p;
//            l.parent = p.parent;
//            if (p.parent == null)
//                root = l;
//            else if (p.parent.right == p)
//                p.parent.right = l;
//            else p.parent.left = l;
//            l.right = p;
//            p.parent = l;
//        }
//    }
//
//
//
//    @SuppressWarnings("unchecked")
//    final int compare(Object k1, Object k2) {
//        return comparator==null ? ((Comparable<? super K>)k1).compareTo((K)k2)
//                : comparator.compare((K)k1, (K)k2);
//    }
//
//    /**
//     * 返回“节点t的后继节点”
//     * @param t
//     * @param <K>
//     * @param <V>
//     * @return
//     */
//    static <K,V> HHTreeMap.Entry<K,V> successor(Entry<K,V> t) {
//        if (t == null)
//            return null;
//        else if (t.right != null) {
//            Entry<K,V> p = t.right;
//            while (p.left != null)
//                p = p.left;
//            return p;
//        } else {
//            Entry<K,V> p = t.parent;
//            Entry<K,V> ch = t;
//            while (p != null && ch == p.right) {
//                ch = p;
//                p = p.parent;
//            }
//            return p;
//        }
//    }
//
//
//    /**
//     * 获取TreeMap中“键”为key的节点
//     * @param key
//     * @return
//     */
//    final Entry<K,V> getEntry(Object key) {
//        // Offload comparator-based version for sake of performance
//        // 若“比较器”为null，则通过getEntryUsingComparator()获取“键”为key的节点
//        if (comparator != null)
//            return getEntryUsingComparator(key);
//        if (key == null)
//            throw new NullPointerException();
//        @SuppressWarnings("unchecked")
//        Comparable<? super K> k = (Comparable<? super K>) key;
//        // 将p设为根节点
//        Entry<K,V> p = root;
//        while (p != null) {
//            int cmp = k.compareTo(p.key);
//            // 若“p的key” < key，则p=“p的左孩子”
//            if (cmp < 0)
//                p = p.left;
//            // 若“p的key” > key，则p=“p的右孩子”
//            else if (cmp > 0)
//                p = p.right;
//            // 若“p的key” = key，则返回节点p
//            else
//                return p;
//        }
//        return null;
//    }
//
//    /**
//     * 获取TreeMap中“键”为key的节点(对应TreeMap的比较器不是null的情况)
//     * @param key
//     * @return
//     */
//    final Entry<K,V> getEntryUsingComparator(Object key) {
//        @SuppressWarnings("unchecked")
//        K k = (K) key;
//        Comparator<? super K> cpr = comparator;
//        if (cpr != null) {
//            // 将p设为根节点
//            Entry<K,V> p = root;
//            while (p != null) {
//                int cmp = cpr.compare(k, p.key);
//                // 若“p的key” < key，则p=“p的左孩子”
//                if (cmp < 0)
//                    p = p.left;
//                // 若“p的key” > key，则p=“p的左孩子”
//                else if (cmp > 0)
//                    p = p.right;
//                // 若“p的key” = key，则返回节点p
//                else
//                    return p;
//            }
//        }
//        return null;
//    }
//
//
//    static final boolean valEquals(Object o1, Object o2) {
//        return (o1==null ? o2==null : o1.equals(o2));
//    }
//
//    /**
//     * 根据已经一个排好序的map创建一个TreeMap
//     * @param size
//     * @param it
//     * @param str
//     * @param defaultVal
//     * @throws java.io.IOException
//     * @throws ClassNotFoundException
//     */
//    private void buildFromSorted(int size, Iterator<?> it,
//                                 java.io.ObjectInputStream str,
//                                 V defaultVal)
//            throws  java.io.IOException, ClassNotFoundException {
//        this.size = size;
//        root = buildFromSorted(0, 0, size-1, computeRedLevel(size),
//                it, str, defaultVal);
//    }
//
//    /**
//     * 根据已经一个排好序的map创建一个TreeMap
//     * 将map中的元素逐个添加到TreeMap中，并返回map的中间元素作为根节点。
//     * @param level
//     * @param lo
//     * @param hi
//     * @param redLevel
//     * @param it
//     * @param str
//     * @param defaultVal
//     * @return
//     * @throws java.io.IOException
//     * @throws ClassNotFoundException
//     */
//    @SuppressWarnings("unchecked")
//    private final Entry<K,V> buildFromSorted(int level, int lo, int hi,
//                                             int redLevel,
//                                             Iterator<?> it,
//                                             java.io.ObjectInputStream str,
//                                             V defaultVal)
//            throws  java.io.IOException, ClassNotFoundException {
//
//        if (hi < lo) return null;
//
//        // 获取中间元素
//        int mid = (lo + hi) >>> 1;
//
//        Entry<K,V> left  = null;
//        // 若lo小于mid，则递归调用获取(middel的)左孩子。
//        if (lo < mid)
//            left = buildFromSorted(level+1, lo, mid - 1, redLevel,
//                    it, str, defaultVal);
//
//        // extract key and/or value from iterator or stream
//        // 获取middle节点对应的key和value
//        K key;
//        V value;
//        if (it != null) {
//            if (defaultVal==null) {
//                Map.Entry<?,?> entry = (Map.Entry<?,?>)it.next();
//                key = (K)entry.getKey();
//                value = (V)entry.getValue();
//            } else {
//                key = (K)it.next();
//                value = defaultVal;
//            }
//        } else { // use stream
//            key = (K) str.readObject();
//            value = (defaultVal != null ? defaultVal : (V) str.readObject());
//        }
//        // 创建middle节点
//        Entry<K,V> middle =  new Entry<>(key, value, null);
//
//        // color nodes in non-full bottommost level red
//        // 若当前节点的深度=红色节点的深度，则将节点着色为红色。
//        if (level == redLevel)
//            middle.color = RED;
//
//        // 设置middle为left的父亲，left为middle的左孩子
//        if (left != null) {
//            middle.left = left;
//            left.parent = middle;
//        }
//
//        if (mid < hi) {
//            // 递归调用获取(middel的)右孩子。
//            Entry<K,V> right = buildFromSorted(level+1, mid+1, hi, redLevel,
//                    it, str, defaultVal);
//            // 设置middle为right的父亲，right为middle的右孩子
//            middle.right = right;
//            right.parent = middle;
//        }
//
//        return middle;
//    }
//
//    /**
//     * 计算节点树为sz的最大深度，也是红色节点的深度值。
//     * @param sz
//     * @return
//     */
//    private static int computeRedLevel(int sz) {
//        int level = 0;
//        for (int m = sz - 1; m >= 0; m = m / 2 - 1)
//            level++;
//        return level;
//    }
//
//    Iterator<K> keyIterator() {
//        return new HHTreeMap.KeyIterator(getFirstEntry());
//    }
//
//    Iterator<K> descendingKeyIterator() {
//        return new HHTreeMap.DescendingKeyIterator(getLastEntry());
//    }
//
//
//    //=============================方法相关============================================================================
//
//
//
//    //=============================内部类相关============================================================================
//    // Red-black mechanics
//    private static final boolean RED   = false;
//    private static final boolean BLACK = true;
//
//    /**
//     * “红黑树的节点”对应的类。
//     * 包含了 key(键)、value(值)、left(左孩子)、right(右孩子)、parent(父节点)、color(颜色)
//     * @param <K>
//     * @param <V>
//     */
//    static final class Entry<K,V> implements Map.Entry<K,V> {
//        K key;// 键
//        V value;// 值
//        Entry<K,V> left;// 左孩子
//        Entry<K,V> right;// 右孩子
//        Entry<K,V> parent;// 父节点
//        boolean color = BLACK;// 当前节点颜色
//
//        /**
//         * 构造函数
//         */
//        Entry(K key, V value, Entry<K,V> parent) {
//            this.key = key;
//            this.value = value;
//            this.parent = parent;
//        }
//
//        /**
//         * 返回“键”
//         */
//        public K getKey() {
//            return key;
//        }
//
//        /**
//         * 返回“值”
//         */
//        public V getValue() {
//            return value;
//        }
//
//        /**
//         * 更新“值”，返回旧的值
//         */
//        public V setValue(V value) {
//            V oldValue = this.value;
//            this.value = value;
//            return oldValue;
//        }
//
//        /**
//         * 判断两个节点是否相等的函数，覆盖equals()函数。
//         * 若两个节点的“key相等”并且“value相等”，则两个节点相等
//         * @param o
//         * @return
//         */
//        public boolean equals(Object o) {
//            if (!(o instanceof Map.Entry))
//                return false;
//            Map.Entry<?,?> e = (Map.Entry<?,?>)o;
//
//            return valEquals(key,e.getKey()) && valEquals(value,e.getValue());
//        }
//
//        public int hashCode() {
//            int keyHash = (key==null ? 0 : key.hashCode());
//            int valueHash = (value==null ? 0 : value.hashCode());
//            return keyHash ^ valueHash;
//        }
//
//        public String toString() {
//            return key + "=" + value;
//        }
//    }
//
//    static final class KeySet<E> extends AbstractSet<E> implements NavigableSet<E> {
//        private final NavigableMap<E, ?> m;
//        KeySet(NavigableMap<E,?> map) { m = map; }
//
//        public Iterator<E> iterator() {
//            if (m instanceof TreeMap)
//                return ((HHTreeMap<E,?>)m).keyIterator();
//            else
//                return ((TreeMap.NavigableSubMap<E,?>)m).keyIterator();
//        }
//
//        public Iterator<E> descendingIterator() {
//            if (m instanceof TreeMap)
//                return ((TreeMap<E,?>)m).descendingKeyIterator();
//            else
//                return ((TreeMap.NavigableSubMap<E,?>)m).descendingKeyIterator();
//        }
//
//        public int size() { return m.size(); }
//        public boolean isEmpty() { return m.isEmpty(); }
//        public boolean contains(Object o) { return m.containsKey(o); }
//        public void clear() { m.clear(); }
//        public E lower(E e) { return m.lowerKey(e); }
//        public E floor(E e) { return m.floorKey(e); }
//        public E ceiling(E e) { return m.ceilingKey(e); }
//        public E higher(E e) { return m.higherKey(e); }
//        public E first() { return m.firstKey(); }
//        public E last() { return m.lastKey(); }
//        public Comparator<? super E> comparator() { return m.comparator(); }
//        public E pollFirst() {
//            Map.Entry<E,?> e = m.pollFirstEntry();
//            return (e == null) ? null : e.getKey();
//        }
//        public E pollLast() {
//            Map.Entry<E,?> e = m.pollLastEntry();
//            return (e == null) ? null : e.getKey();
//        }
//        public boolean remove(Object o) {
//            int oldSize = size();
//            m.remove(o);
//            return size() != oldSize;
//        }
//        public NavigableSet<E> subSet(E fromElement, boolean fromInclusive,
//                                      E toElement,   boolean toInclusive) {
//            return new KeySet<>(m.subMap(fromElement, fromInclusive,
//                    toElement,   toInclusive));
//        }
//        public NavigableSet<E> headSet(E toElement, boolean inclusive) {
//            return new KeySet<>(m.headMap(toElement, inclusive));
//        }
//        public NavigableSet<E> tailSet(E fromElement, boolean inclusive) {
//            return new KeySet<>(m.tailMap(fromElement, inclusive));
//        }
//        public SortedSet<E> subSet(E fromElement, E toElement) {
//            return subSet(fromElement, true, toElement, false);
//        }
//        public SortedSet<E> headSet(E toElement) {
//            return headSet(toElement, false);
//        }
//        public SortedSet<E> tailSet(E fromElement) {
//            return tailSet(fromElement, true);
//        }
//        public NavigableSet<E> descendingSet() {
//            return new KeySet<>(m.descendingMap());
//        }
//
//        public Spliterator<E> spliterator() {
//            return keySpliteratorFor(m);
//        }
//    }
//
//
//    //=============================内部类相关============================================================================
//
//
//
//
//
//}
