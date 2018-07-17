package com.wolfman.concurrency.list;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * CopyOnWriteArraySet示例
 */
public class CopyOnWriteArraySetDemo {

    private static Set<String> set = new CopyOnWriteArraySet<String>();

    public static void main(String[] args) {
        // 同时启动两个线程对set进行操作！
        new MyThread("ta").start();
        new MyThread("tb").start();

        //运行结果
        //ta-1,
        //ta-1, ta-2,
        //ta-1, ta-2, ta-3,
        //ta-1, ta-2, ta-3, ta-4,
        //ta-1, ta-2, ta-3, ta-4, ta-5,
        //ta-1, ta-2, ta-3, ta-4, ta-5, ta-0,
        //ta-1, ta-2, ta-3, ta-4, ta-5, ta-0,
        //ta-1, ta-2, ta-3, ta-4, ta-5, ta-0,
        //ta-1, ta-2, ta-3, ta-4, ta-5, ta-0,
        //ta-1, ta-2, ta-3, ta-4, ta-5, ta-0,
        //ta-1, ta-2, ta-3, ta-4, ta-5, ta-0, tb-1,
        //ta-1, ta-2, ta-3, ta-4, ta-5, ta-0, tb-1, tb-2,
        //ta-1, ta-2, ta-3, ta-4, ta-5, ta-0, tb-1, tb-2, tb-3,
        //ta-1, ta-2, ta-3, ta-4, ta-5, ta-0, tb-1, tb-2, tb-3, tb-4,
        //ta-1, ta-2, ta-3, ta-4, ta-5, ta-0, tb-1, tb-2, tb-3, tb-4, tb-5,
        //ta-1, ta-2, ta-3, ta-4, ta-5, ta-0, tb-1, tb-2, tb-3, tb-4, tb-5, tb-0,
        //ta-1, ta-2, ta-3, ta-4, ta-5, ta-0, tb-1, tb-2, tb-3, tb-4, tb-5, tb-0,
        //ta-1, ta-2, ta-3, ta-4, ta-5, ta-0, tb-1, tb-2, tb-3, tb-4, tb-5, tb-0,
        //ta-1, ta-2, ta-3, ta-4, ta-5, ta-0, tb-1, tb-2, tb-3, tb-4, tb-5, tb-0,
        //ta-1, ta-2, ta-3, ta-4, ta-5, ta-0, tb-1, tb-2, tb-3, tb-4, tb-5, tb-0,
    }

    private static void printAll() {
        String value = null;
        Iterator iter = set.iterator();
        while(iter.hasNext()) {
            value = (String)iter.next();
            System.out.print(value+", ");
        }
        System.out.println();
    }

    private static class MyThread extends Thread {
        MyThread(String name) {
            super(name);
        }
        @Override
        public void run() {
            int i = 0;
            while (i++ < 10) {
                // “线程名” + "-" + "序号"
                String val = Thread.currentThread().getName() + "-" + (i%6);
                set.add(val);
                // 通过“Iterator”遍历set。
                printAll();
            }
        }
    }


}
