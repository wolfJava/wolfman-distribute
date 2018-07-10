package com.wolfman.concurrency.manythread;

public class SomethingTwo {

    public synchronized void isSyncA(){
        try {
            for (int i = 0; i < 5; i++) {
                Thread.sleep(100);
                System.out.println(Thread.currentThread().getName()+" : isSyncA");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void isSyncB(){
        try {
            for (int i = 0; i < 5; i++) {
                Thread.sleep(100);
                System.out.println(Thread.currentThread().getName()+" : isSyncB");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SomethingTwo x = new SomethingTwo();
        SomethingTwo y = new SomethingTwo();
        Thread t1 = new Thread(()->{
            x.isSyncA();
        }, "t1");
        Thread t2 = new Thread(()->{
            y.isSyncB();
        }, "t2");
        t1.start();
        t2.start();
    }


}
