package com.wolfman.util.list.source;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

/**
 * 自己手动分析vector数组
 * vector：向量
 * @param <E>
 */
public class HHVector<E>
        extends AbstractList<E>
        implements List<E>, RandomAccess, Cloneable, java.io.Serializable {

    //=============================属性开始==============================================================================

    // Vector的序列版本号
    private static final long serialVersionUID = -2767605614048989439L;

    // 保存Vector中数据的数组
    protected Object[] elementData;

    // 实际数据的数量
    protected int elementCount;

    // 容量增长系数
    protected int capacityIncrement;

    // 数组最大容量
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

    //=============================属性结束==============================================================================

    //=============================构造函数开始===========================================================================

    // 指定Vector"容量大小"和"增长系数"的构造函数
    public HHVector(int initialCapacity, int capacityIncrement) {
        super();
        if (initialCapacity < 0)
            throw new IllegalArgumentException("Illegal Capacity: "+
                    initialCapacity);
        this.elementData = new Object[initialCapacity];
        this.capacityIncrement = capacityIncrement;
    }

    // 指定Vector容量大小的构造函数
    public HHVector(int initialCapacity) {
        this(initialCapacity, 0);
    }

    // Vector构造函数。默认容量是10。
    public HHVector() {
        this(10);
    }

    // 指定集合的Vector构造函数。
    public HHVector(Collection<? extends E> c) {
        elementData = c.toArray();
        elementCount = elementData.length;
        // c.toArray might (incorrectly) not return Object[] (see 6260652)
        if (elementData.getClass() != Object[].class)
            elementData = Arrays.copyOf(elementData, elementCount, Object[].class);
    }

    //=============================构造函数结束===========================================================================

    //=============================容量相关===========================================================================
    // 将当前容量值设为：实际元素个数
    public synchronized void trimToSize() {
        modCount++;
        int oldCapacity = elementData.length;
        if (elementCount < oldCapacity) {
            elementData = Arrays.copyOf(elementData, elementCount);
        }
    }

    //确保最小容量至少是minCapacity，如果不够，增长Vector的容量
    public synchronized void ensureCapacity(int minCapacity) {
        if (minCapacity > 0) {
            modCount++;
            ensureCapacityHelper(minCapacity);
        }
    }

    private void ensureCapacityHelper(int minCapacity) {
        // overflow-conscious code
        if (minCapacity - elementData.length > 0)
            //容量不够，就扩增，核心方法
            grow(minCapacity);
    }

    //容量核心方法，增加容量
    private void grow(int minCapacity) {
        // overflow-conscious code
        //原来的数组容量
        int oldCapacity = elementData.length;
        //如果指定了增长容量,扩容后的容量为原来容量+增长容量
        //如果未指定增长容量，则容量为原来容量的两倍
        int newCapacity = oldCapacity + ((capacityIncrement > 0) ?
                capacityIncrement : oldCapacity);
        //如果扩容后的容量还是比指定的最小容量小，新容量为指定的最小容量
        if (newCapacity - minCapacity < 0)
            //新容量=最小容量
            newCapacity = minCapacity;
        //如果扩容后的容量比最大数组容量还要大
        if (newCapacity - MAX_ARRAY_SIZE > 0)
            //新容量=最大容量
            newCapacity = hugeCapacity(minCapacity);
        elementData = Arrays.copyOf(elementData, newCapacity);
    }

    private static int hugeCapacity(int minCapacity) {
        if (minCapacity < 0) // overflow
            throw new OutOfMemoryError();
        return (minCapacity > MAX_ARRAY_SIZE) ?
                Integer.MAX_VALUE :
                MAX_ARRAY_SIZE;
    }

    // 设置容量值为 newSize
    public synchronized void setSize(int newSize) {
        modCount++;
        if (newSize > elementCount) {
            // 若 "newSize 大于 Vector容量"，则调整Vector的大小。
            ensureCapacityHelper(newSize);
        } else {
            // 若 "newSize 小于/等于 Vector容量"，则将newSize位置开始的元素都设置为null
            for (int i = newSize ; i < elementCount ; i++) {
                elementData[i] = null;
            }
        }
        elementCount = newSize;
    }

    /**
     * 获取vector的总容量
     */
    public synchronized int capacity() {
        return elementData.length;
    }

    /**
     * 返回“Vector的实际大小”，即Vector中元素个数
     * @return
     */
    public synchronized int size() {
        return elementCount;
    }

    // 判断Vector的实际大小是否为空
    public synchronized boolean isEmpty() {
        return elementCount == 0;
    }

    //=============================容量相关===========================================================================

    //=============================方法开始===========================================================================

    // 将数组Vector的全部元素都拷贝到数组anArray中
    public synchronized void copyInto(Object[] anArray) {
        System.arraycopy(elementData, 0, anArray, 0, elementCount);
    }

    /**
     * 返回一个包含vector所有元素的枚举
     * @return
     */
    public Enumeration<E> elements() {
        return new Enumeration<E>() {
            int count = 0;

            public boolean hasMoreElements() {
                return count < elementCount;
            }

            public E nextElement() {
                synchronized (HHVector.this) {
                    if (count < elementCount) {
                        return elementData(count++);
                    }
                }
                throw new NoSuchElementException("Vector Enumeration");
            }
        };
    }

    @SuppressWarnings("unchecked")
    E elementData(int index) {
        return (E) elementData[index];
    }

    /**
     * 返回这个vector是否包含指定元素
     * @param o
     * @return
     */
    public boolean contains(Object o) {
        return indexOf(o, 0) >= 0;
    }

    /**
     * 返回这个vector中第一次出现指定元素的下标，如果不包括这个元素返回-1
     * 从0开始查找
     * @param o
     * @return
     */
    public int indexOf(Object o) {
        return indexOf(o, 0);
    }

    /**
     * 从指定的位置向后开始查找vector中第一次出现这个元素的下标
     * 如果没有返回-1
     * @param o
     * @param index
     * @return
     */
    public synchronized int indexOf(Object o, int index) {
        if (o == null) {
            for (int i = index ; i < elementCount ; i++)
                if (elementData[i]==null)
                    return i;
        } else {
            for (int i = index ; i < elementCount ; i++)
                if (o.equals(elementData[i]))
                    return i;
        }
        return -1;
    }

    /**
     * 返回在vector中最后一次出现指定元素的角标
     * 如果vector不包含这个元素则返回-1
     * @param o
     * @return
     */
    public synchronized int lastIndexOf(Object o) {
        return lastIndexOf(o, elementCount-1);
    }

    /**
     * 返回在vector中最后一次出现指定元素的角标
     * - 就是从指定位置向前寻找第一次出现的指定元素的角标
     * - 如果找不到返回-1
     * @param o
     * @param index
     * @return
     */
    public synchronized int lastIndexOf(Object o, int index) {
        if (index >= elementCount)
            throw new IndexOutOfBoundsException(index + " >= "+ elementCount);

        if (o == null) {
            for (int i = index; i >= 0; i--)
                if (elementData[i]==null)
                    return i;
        } else {
            for (int i = index; i >= 0; i--)
                if (o.equals(elementData[i]))
                    return i;
        }
        return -1;
    }

    /**
     * 返回指定下标的元素
     * -- 这个只判断了index>elcmentCount
     * @param index
     * @return
     */
    public synchronized E elementAt(int index) {
        if (index >= elementCount) {
            throw new ArrayIndexOutOfBoundsException(index + " >= " + elementCount);
        }

        return elementData(index);
    }

    /**
     * 返回vector中的第一个元素
     * -- 如果没有元素会报错NoSuchElementException
     * @return
     */
    public synchronized E firstElement() {
        if (elementCount == 0) {
            throw new NoSuchElementException();
        }
        return elementData(0);
    }

    /**
     * 返回vector中的最后一个元素，
     * -- 如果没有会报错NoSuchElementException
     * @return
     */
    public synchronized E lastElement() {
        if (elementCount == 0) {
            throw new NoSuchElementException();
        }
        return elementData(elementCount - 1);
    }

    /**
     * 在指定位置设置指定元素，原来位置的元素会被丢弃
     * @param obj
     * @param index
     */
    public synchronized void setElementAt(E obj, int index) {
        if (index >= elementCount) {
            throw new ArrayIndexOutOfBoundsException(index + " >= " +
                    elementCount);
        }
        elementData[index] = obj;
    }

    /**
     * 删除指定位置的元素
     * -- 指定位置后面如果还有元素，将后面的元素都向下移动1
     * -- 元素长度-1
     * @param index
     */
    public synchronized void removeElementAt(int index) {
        modCount++;
        if (index >= elementCount) {
            throw new ArrayIndexOutOfBoundsException(index + " >= " +
                    elementCount);
        }
        else if (index < 0) {
            throw new ArrayIndexOutOfBoundsException(index);
        }
        int j = elementCount - index - 1;//指定位置后还有多少元素(不含指定元素)
        if (j > 0) {//如果指定位置后面还有元素
            //将指定位置后面的元素向前移动
            System.arraycopy(elementData, index + 1, elementData, index, j);
        }
        elementCount--;
        elementData[elementCount] = null;//将元素顶部置空，方便回收器回收
    }

    /**
     * 在指定位置插入元素
     * -- 将指定位置后面的元素(包括指定位置)向上移动一位
     * @param obj
     * @param index
     */
    public synchronized void insertElementAt(E obj, int index) {
        modCount++;
        if (index > elementCount) {
            throw new ArrayIndexOutOfBoundsException(index
                    + " > " + elementCount);
        }
        ensureCapacityHelper(elementCount + 1);//确保容量够用
        //将指定位置及其后面的元素向上移动一位
        System.arraycopy(elementData, index, elementData, index + 1, elementCount - index);
        //在指定位置插入元素
        elementData[index] = obj;
        //元素长度+1
        elementCount++;
    }

    /**
     * 在vector的元素末尾添加一个元素
     * @param obj
     */
    public synchronized void addElement(E obj) {
        modCount++;
        ensureCapacityHelper(elementCount + 1);
        elementData[elementCount++] = obj;
    }

    /**
     * 移除在vector中第一次出现的指定元素
     * -- 如果vector中真的包含这个元素，这个元素后面的都要向下移动一位
     * @param obj
     * @return
     */
    public synchronized boolean removeElement(Object obj) {
        modCount++;
        int i = indexOf(obj);//查找元素在vector中的位置，如果没有返回-1
        if (i >= 0) {
            //如果有这个元素，删除
            removeElementAt(i);
            return true;
        }
        return false;
    }

    /**
     * 移除vector中的所有元素，让它的元素大小为0
     */
    public synchronized void removeAllElements() {
        modCount++;
        // 让回收器回收
        for (int i = 0; i < elementCount; i++)
            elementData[i] = null;

        elementCount = 0;
    }

    /**
     * 由于实现了Cloneable,这个是深度克隆
     * @return
     */
    public synchronized Object clone() {
        try {
            @SuppressWarnings("unchecked")
            HHVector<E> v = (HHVector<E>) super.clone();
            v.elementData = Arrays.copyOf(elementData, elementCount);
            v.modCount = 0;
            return v;
        } catch (CloneNotSupportedException e) {
            // this shouldn't happen, since we are Cloneable
            throw new InternalError(e);
        }
    }

    /**
     * 返回一个数组，包含vector中的所有元素
     * @since 1.2
     * @return
     */
    public synchronized Object[] toArray() {
        return Arrays.copyOf(elementData, elementCount);
    }

    /**
     * 返回指定类型的数组
     * - 这里T[] 可以是vector中指定的类型的父类
     * @param a
     * @param <T>
     * @since 1.2
     * @return
     */
    @SuppressWarnings("unchecked")
    public synchronized <T> T[] toArray(T[] a) {
        if (a.length < elementCount)
            return (T[]) Arrays.copyOf(elementData, elementCount, a.getClass());

        System.arraycopy(elementData, 0, a, 0, elementCount);

        if (a.length > elementCount)
            a[elementCount] = null;

        return a;
    }

    /**
     * 获取指定位置的元素
     * @param index
     * @return
     * @since 1.2
     */
    public synchronized E get(int index) {
        if (index >= elementCount)
            throw new ArrayIndexOutOfBoundsException(index);

        return elementData(index);
    }

    /**
     * 在vector的指定位置代替指定的元素
     * -- 返回指定位置原来的元素
     * @param index
     * @param element
     * @return
     * @since 1.2
     */
    public synchronized E set(int index, E element) {
        if (index >= elementCount)
            throw new ArrayIndexOutOfBoundsException(index);

        E oldValue = elementData(index);
        elementData[index] = element;
        return oldValue;
    }

    /**
     * 增加指定的元素到vector
     * @param e
     * @return
     * @since 1.2
     */
    public synchronized boolean add(E e) {
        modCount++;
        ensureCapacityHelper(elementCount + 1);
        elementData[elementCount++] = e;
        return true;
    }

    /**
     * 移除vector中第一次出现的指定元素
     * @param o
     * @return
     * @since 1.2
     */
    public boolean remove(Object o) {
        return removeElement(o);
    }

    /**
     * 在指定位置插入指定元素
     * --向上移动当前元素和后续元素
     * @param index
     * @param element
     * @since 1.2
     */
    public void add(int index, E element) {
        insertElementAt(element, index);
    }


    /**
     * 移除指定位置的元素
     * -- 返回指定位的原来元素
     * -- 向下移动指定位置后续的元素
     * @since 1.2
     * @param index
     * @return
     */
    public synchronized E remove(int index) {
        modCount++;
        if (index >= elementCount)
            throw new ArrayIndexOutOfBoundsException(index);
        E oldValue = elementData(index);//获取指定位的元素

        int numMoved = elementCount - index - 1;//获取指定位置后面的元素个数
        if (numMoved > 0)
            //向下移动指定位置的后续元素
            System.arraycopy(elementData, index+1, elementData, index,
                    numMoved);
        elementData[--elementCount] = null; // Let gc do its work
        return oldValue;
    }

    /**
     * 清除vector中的所有元素
     * @since 1.2
     */
    public void clear() {
        removeAllElements();
    }

    /**
     * 这个方法是调用父类AbstractCollection的方法实现的
     * vector是否包含指定集合中的所有元素
     * -- 如果指定集合中有一个元素不包含则返回false
     * @param c
     * @return
     */
    public synchronized boolean containsAll(Collection<?> c) {
        return super.containsAll(c);
    }

    /**
     * 添加指定集合中的所有元素到vector的末尾，
     * --按照指定集合iterator返回的顺序
     * @param c
     * @return
     * @since 1.2
     */
    public synchronized boolean addAll(Collection<? extends E> c) {
        modCount++;
        Object[] a = c.toArray();
        int numNew = a.length;
        ensureCapacityHelper(elementCount + numNew);
        System.arraycopy(a, 0, elementData, elementCount, numNew);
        elementCount += numNew;
        return numNew != 0;
    }

    /**
     * 删除集合c的全部元素
     * @param c
     * @return
     * @since 1.2
     */
    public synchronized boolean removeAll(Collection<?> c) {
        return super.removeAll(c);
    }


    /**
     * 删除“非集合c中的元素”
     * @param c
     * @return
     * @since 1.2
     */
    public synchronized boolean retainAll(Collection<?> c) {
        return super.retainAll(c);
    }

    /**
     * 在vector的指定位置添加指定集合中的所有元素
     * -- 向右移动指定位置及其后继的元素
     * @param index
     * @param c
     * @return
     * @since 1.2
     */
    public synchronized boolean addAll(int index, Collection<? extends E> c) {
        modCount++;
        if (index < 0 || index > elementCount)
            throw new ArrayIndexOutOfBoundsException(index);

        Object[] a = c.toArray();
        int numNew = a.length;
        ensureCapacityHelper(elementCount + numNew);

        int numMoved = elementCount - index;//指定位置及其后继元素的个数
        if (numMoved > 0)
            //向后移动集合元素大小个距离
            System.arraycopy(elementData, index, elementData, index + numNew,
                    numMoved);
        //将集合元素添加到vector中
        System.arraycopy(a, 0, elementData, index, numNew);
        elementCount += numNew;
        return numNew != 0;
    }

    /**
     * 这个方法是调用父类AbstractList方法实现的
     * 比较vector和指定的对象
     * 如果这个对象是List对象而且集合中元素和vector中的元素一致则返回true，否则返回false
     * @param o
     * @return
     */
    public synchronized boolean equals(Object o) {
        return super.equals(o);
    }

    /**
     * 返回vector的hashCode
     * @return
     */
    public synchronized int hashCode() {
        return super.hashCode();
    }

    /**
     * 调用了父类的AbstractCollection中的方法
     * 返回了这个vector的表示string，包括每一个元素的表示
     * @return
     */
    public synchronized String toString() {
        return super.toString();
    }


    /**
     * synchronizedList 不能被外包所访问
     * 返回list中的[fromIndex,toIndex]的子视图
     * -- 如果fromIndex==toIndex则返回的是一个空视图
     * -- 这个子视图是这个List的反射
     * @param fromIndex
     * @param toIndex
     * @return
     */
    /*public synchronized List<E> subList(int fromIndex, int toIndex) {
        return Collections.synchronizedList(super.subList(fromIndex, toIndex),
                this);
    }*/

    /**
     * 移除list中[fromIndex,toIndex)范围的元素
     * toIndex及其后继元素向前移动toIndex-fromIndex位
     * @param fromIndex
     * @param toIndex
     */
    protected synchronized void removeRange(int fromIndex, int toIndex) {
        modCount++;
        int numMoved = elementCount - toIndex;
        System.arraycopy(elementData, toIndex, elementData, fromIndex,
                numMoved);
        // 回收
        int newElementCount = elementCount - (toIndex-fromIndex);
        while (elementCount != newElementCount)
            elementData[--elementCount] = null;
    }

    /**
     * 序列化
     * @param s
     * @throws java.io.IOException
     */
    private void writeObject(java.io.ObjectOutputStream s)
            throws java.io.IOException {
        final java.io.ObjectOutputStream.PutField fields = s.putFields();
        final Object[] data;
        synchronized (this) {
            fields.put("capacityIncrement", capacityIncrement);
            fields.put("elementCount", elementCount);
            data = elementData.clone();
        }
        fields.put("elementData", data);
        s.writeFields();
    }

    //=============================视图iterator===========================================================================

    /**
     * 迭代器
     * @param index
     * @return
     */
    public synchronized ListIterator<E> listIterator(int index) {
        if (index < 0 || index > elementCount)
            throw new IndexOutOfBoundsException("Index: "+index);
        return new ListItr(index);
    }

    public synchronized ListIterator<E> listIterator() {
        return new ListItr(0);
    }

    public synchronized Iterator<E> iterator() {
        return new Itr();
    }

    @Override
    public synchronized void forEach(Consumer<? super E> action) {
        Objects.requireNonNull(action);
        final int expectedModCount = modCount;
        @SuppressWarnings("unchecked")
        final E[] elementData = (E[]) this.elementData;
        final int elementCount = this.elementCount;
        for (int i=0; modCount == expectedModCount && i < elementCount; i++) {
            action.accept(elementData[i]);
        }
        if (modCount != expectedModCount) {
            throw new ConcurrentModificationException();
        }
    }



    private class Itr implements Iterator<E> {
        int cursor;       // index of next element to return
        int lastRet = -1; // index of last element returned; -1 if no such
        int expectedModCount = modCount;

        public boolean hasNext() {
            // Racy but within spec, since modifications are checked
            // within or after synchronization in next/previous
            return cursor != elementCount;
        }

        public E next() {
            synchronized (HHVector.this) {
                checkForComodification();
                int i = cursor;
                if (i >= elementCount)
                    throw new NoSuchElementException();
                cursor = i + 1;
                return elementData(lastRet = i);
            }
        }

        public void remove() {
            if (lastRet == -1)
                throw new IllegalStateException();
            synchronized (HHVector.this) {
                checkForComodification();
                HHVector.this.remove(lastRet);
                expectedModCount = modCount;
            }
            cursor = lastRet;
            lastRet = -1;
        }

        @Override
        public void forEachRemaining(Consumer<? super E> action) {
            Objects.requireNonNull(action);
            synchronized (HHVector.this) {
                final int size = elementCount;
                int i = cursor;
                if (i >= size) {
                    return;
                }
                @SuppressWarnings("unchecked")
                final E[] elementData = (E[]) HHVector.this.elementData;
                if (i >= elementData.length) {
                    throw new ConcurrentModificationException();
                }
                while (i != size && modCount == expectedModCount) {
                    action.accept(elementData[i++]);
                }
                // update once at end of iteration to reduce heap write traffic
                cursor = i;
                lastRet = i - 1;
                checkForComodification();
            }
        }

        final void checkForComodification() {
            if (modCount != expectedModCount)
                throw new ConcurrentModificationException();
        }
    }

    final class ListItr extends Itr implements ListIterator<E> {
        ListItr(int index) {
            super();
            cursor = index;
        }

        public boolean hasPrevious() {
            return cursor != 0;
        }

        public int nextIndex() {
            return cursor;
        }

        public int previousIndex() {
            return cursor - 1;
        }

        public E previous() {
            synchronized (HHVector.this) {
                checkForComodification();
                int i = cursor - 1;
                if (i < 0)
                    throw new NoSuchElementException();
                cursor = i;
                return elementData(lastRet = i);
            }
        }

        public void set(E e) {
            if (lastRet == -1)
                throw new IllegalStateException();
            synchronized (HHVector.this) {
                checkForComodification();
                HHVector.this.set(lastRet, e);
            }
        }

        public void add(E e) {
            int i = cursor;
            synchronized (HHVector.this) {
                checkForComodification();
                HHVector.this.add(i, e);
                expectedModCount = modCount;
            }
            cursor = i + 1;
            lastRet = -1;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public synchronized boolean removeIf(Predicate<? super E> filter) {
        Objects.requireNonNull(filter);
        // figure out which elements are to be removed
        // any exception thrown from the filter predicate at this stage
        // will leave the collection unmodified
        int removeCount = 0;
        final int size = elementCount;
        final BitSet removeSet = new BitSet(size);
        final int expectedModCount = modCount;
        for (int i=0; modCount == expectedModCount && i < size; i++) {
            @SuppressWarnings("unchecked")
            final E element = (E) elementData[i];
            if (filter.test(element)) {
                removeSet.set(i);
                removeCount++;
            }
        }
        if (modCount != expectedModCount) {
            throw new ConcurrentModificationException();
        }

        // shift surviving elements left over the spaces left by removed elements
        final boolean anyToRemove = removeCount > 0;
        if (anyToRemove) {
            final int newSize = size - removeCount;
            for (int i=0, j=0; (i < size) && (j < newSize); i++, j++) {
                i = removeSet.nextClearBit(i);
                elementData[j] = elementData[i];
            }
            for (int k=newSize; k < size; k++) {
                elementData[k] = null;  // Let gc do its work
            }
            elementCount = newSize;
            if (modCount != expectedModCount) {
                throw new ConcurrentModificationException();
            }
            modCount++;
        }

        return anyToRemove;
    }

    @Override
    @SuppressWarnings("unchecked")
    public synchronized void replaceAll(UnaryOperator<E> operator) {
        Objects.requireNonNull(operator);
        final int expectedModCount = modCount;
        final int size = elementCount;
        for (int i=0; modCount == expectedModCount && i < size; i++) {
            elementData[i] = operator.apply((E) elementData[i]);
        }
        if (modCount != expectedModCount) {
            throw new ConcurrentModificationException();
        }
        modCount++;
    }

    @SuppressWarnings("unchecked")
    @Override
    public synchronized void sort(Comparator<? super E> c) {
        final int expectedModCount = modCount;
        Arrays.sort((E[]) elementData, 0, elementCount, c);
        if (modCount != expectedModCount) {
            throw new ConcurrentModificationException();
        }
        modCount++;
    }

    /**
     * @since 1.8
     * @return
     */
    @Override
    public Spliterator<E> spliterator() {
        return new HHVector.VectorSpliterator<>(this, null, 0, -1, 0);
    }

    static final class VectorSpliterator<E> implements Spliterator<E> {
        private final HHVector<E> list;
        private Object[] array;
        private int index; // current index, modified on advance/split
        private int fence; // -1 until used; then one past last index
        private int expectedModCount; // initialized when fence set

        /** Create new spliterator covering the given  range */
        VectorSpliterator(HHVector<E> list, Object[] array, int origin, int fence,
                          int expectedModCount) {
            this.list = list;
            this.array = array;
            this.index = origin;
            this.fence = fence;
            this.expectedModCount = expectedModCount;
        }

        private int getFence() { // initialize on first use
            int hi;
            if ((hi = fence) < 0) {
                synchronized(list) {
                    array = list.elementData;
                    expectedModCount = list.modCount;
                    hi = fence = list.elementCount;
                }
            }
            return hi;
        }

        public Spliterator<E> trySplit() {
            int hi = getFence(), lo = index, mid = (lo + hi) >>> 1;
            return (lo >= mid) ? null :
                    new VectorSpliterator<E>(list, array, lo, index = mid,
                            expectedModCount);
        }

        @SuppressWarnings("unchecked")
        public boolean tryAdvance(Consumer<? super E> action) {
            int i;
            if (action == null)
                throw new NullPointerException();
            if (getFence() > (i = index)) {
                index = i + 1;
                action.accept((E)array[i]);
                if (list.modCount != expectedModCount)
                    throw new ConcurrentModificationException();
                return true;
            }
            return false;
        }

        @SuppressWarnings("unchecked")
        public void forEachRemaining(Consumer<? super E> action) {
            int i, hi; // hoist accesses and checks from loop
            HHVector<E> lst; Object[] a;
            if (action == null)
                throw new NullPointerException();
            if ((lst = list) != null) {
                if ((hi = fence) < 0) {
                    synchronized(lst) {
                        expectedModCount = lst.modCount;
                        a = array = lst.elementData;
                        hi = fence = lst.elementCount;
                    }
                }
                else
                    a = array;
                if (a != null && (i = index) >= 0 && (index = hi) <= a.length) {
                    while (i < hi)
                        action.accept((E) a[i++]);
                    if (lst.modCount == expectedModCount)
                        return;
                }
            }
            throw new ConcurrentModificationException();
        }

        public long estimateSize() {
            return (long) (getFence() - index);
        }

        public int characteristics() {
            return Spliterator.ORDERED | Spliterator.SIZED | Spliterator.SUBSIZED;
        }
    }

    //=============================视图iterator===========================================================================




}
