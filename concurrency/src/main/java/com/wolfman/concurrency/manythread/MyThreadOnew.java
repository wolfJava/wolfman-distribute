package com.wolfman.concurrency.manythread;

/**
 * Thread中start()和run()的区别示例
 */
public class MyThreadOnew extends Thread {


    public MyThreadOnew(String name) {
        super(name);
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+" is running");
    }

    public static void main(String[] args) {
        MyThreadOnew mythread=new MyThreadOnew("mythread");
        System.out.println(Thread.currentThread().getName()+" call mythread.run()");
        mythread.run();
        System.out.println(Thread.currentThread().getName()+" call mythread.start()");
        mythread.start();
    }




}
