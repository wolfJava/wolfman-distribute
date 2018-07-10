package com.wolfman.concurrency.manythread;

public class SomethingThree {

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

    public static synchronized void cSyncA(){
        try {
            for (int i = 0; i < 5; i++) {
                Thread.sleep(100);
                System.out.println(Thread.currentThread().getName()+" : cSyncA");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static synchronized void cSyncB(){
        try {
            for (int i = 0; i < 5; i++) {
                Thread.sleep(100);
                System.out.println(Thread.currentThread().getName()+" : cSyncB");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SomethingThree x = new SomethingThree();
        SomethingThree y = new SomethingThree();
        Thread t1 = new Thread(()->{
            x.cSyncA();
        }, "t1");
        Thread t2 = new Thread(()->{
            y.cSyncB();
        }, "t2");
        t1.start();
        t2.start();
    }


}
