package com.wolfman.util.list;

import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @desc java集合中Fast-Fail的测试程序。
 *
 * fast-fail事件产生的条件：当多个线程对Collection进行操作时，若其中某一个线程通过iterator去遍历集合时，
 * 该集合的内容被其他线程所改变；则会抛出ConcurrentModificationException异常。
 *
 * fast-fail解决办法：通过util.concurrent集合包下的相应类去处理，则不会产生fast-fail事件。
 *
 *本例中，分别测试ArrayList和CopyOnWriteArrayList这两种情况。ArrayList会产生fast-fail事件，而CopyOnWriteArrayList不会产生fast-fail事件。
 * (01) 使用ArrayList时，会产生fast-fail事件，抛出ConcurrentModificationException异常；定义如下：
 * private static List<String> list = new ArrayList<String>();
 * (02) 使用时CopyOnWriteArrayList，不会产生fast-fail事件；定义如下：
 * private static List<String> list = new CopyOnWriteArrayList<String>();
 *
 *
 */
public class FastFailTest {

    private static List<Integer> list = new ArrayList<>();

    public static void main(String[] args) {

        new ThreadOne().start();
        new ThreadTwo().start();

    }

    private static void printAll() {
        System.out.println("");
        Integer value = null;
        Iterator iter = list.iterator();
        while (iter.hasNext()){
            value = (Integer) iter.next();
            System.out.print(value+", ");
        }

    }

    private static class ThreadOne extends Thread{
        @Override
        public void run() {

            int i = 0;
            while (i<=6){
                list.add(i);
                printAll();
                i++;
            }

        }
    }

    private static class ThreadTwo extends Thread{
        @Override
        public void run() {
            int i = 10;
            while (i<=16){
                list.add(i);
                printAll();
                i++;
            }

        }
    }






}
