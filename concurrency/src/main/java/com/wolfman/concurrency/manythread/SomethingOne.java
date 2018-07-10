package com.wolfman.concurrency.manythread;

public class SomethingOne {

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
        SomethingOne x = new SomethingOne();
        Thread t1 = new Thread(()->{
            x.isSyncA();
        }, "t1");
        Thread t2 = new Thread(()->{
            x.isSyncB();
        }, "t2");
        t1.start();
        t2.start();
    }


}
