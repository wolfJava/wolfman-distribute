package com.wolfman.util.list.source;

import java.util.EmptyStackException;

/**
 * 栈
 * @param <E>
 */
public class HHStack<E>  extends HHVector<E>  {

    /**
     * 构造函数
     */
    public HHStack() {    }

    /**
     * 入栈：增加元素到栈顶，并返回这个元素
     * @param item
     * @return
     */
    public E push(E item) {
        addElement(item);//调用vector的addElement方法添加
        return item;
    }

    /**
     * 出栈：移除栈顶元素，返回移除的栈顶元素
     * @return
     */
    public synchronized E pop() {
        E       obj;
        int     len = size();//vector的方法,元素多少
        obj = peek();//查看栈顶元素
        removeElementAt(len - 1);//将栈顶元素移除
        return obj;
    }

    /**
     * 查看栈顶元素，但是不将栈顶元素移除
     * @return
     */
    public synchronized E peek() {
        int     len = size();
        if (len == 0)
            throw new EmptyStackException();
        return elementAt(len - 1);
    }

    /**
     * 判断这个栈是不是空栈
     * @return
     */
    public boolean empty() {
        return size() == 0;
    }

    /**
     * 从栈顶开始寻找第一次出现的指定元素，并返回栈顶和指定元素的距离
     * -- 如果栈中没有这个元素，返回-1
     * -- 最小的距离是1(如果栈顶元素就是要找的元素，距离为1)
     * @param o
     * @return
     */
    public synchronized int search(Object o) {
        //调用父类方法，从栈顶开始查找第一次出现指定元素的位置
        int i = lastIndexOf(o);

        if (i >= 0) {//如果有指定元素
            return size() - i;//返回栈顶和指定元素的距离
        }
        return -1;
    }

    /**
     * 序列化
     */
    private static final long serialVersionUID = 1224463164541339165L;

}
