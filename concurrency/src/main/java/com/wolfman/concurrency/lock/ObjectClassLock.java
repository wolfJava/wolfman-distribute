package com.wolfman.concurrency.lock;

import java.text.DateFormat;
import java.util.Currency;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 对象锁与类锁
 */
public class ObjectClassLock {

    /**
     * 类锁
     */
    public synchronized static void doLongTimeTaskA(){
        System.out.println("name=" + Thread.currentThread().getName() + ",begain!" + " time=" + new Date().getTime());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("name=" + Thread.currentThread().getName() + ",end!" + " time=" + new Date().getTime());
    }

    /**
     * 类锁
     */
    public synchronized static void doLongTimeTaskB(){
        System.out.println("name=" + Thread.currentThread().getName() + ",begain!" + " time=" + new Date().getTime());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("name=" + Thread.currentThread().getName() + ",end!" + " time=" + new Date().getTime());
    }

    /**
     * 对象锁
     */
    public synchronized void doLongTimeTaskC() {
        System.out.println("name=" + Thread.currentThread().getName() + ",begain!" + " time=" + new Date().getTime());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("name=" + Thread.currentThread().getName() + ",end!" + " time=" + new Date().getTime());
    }

    public static void main(String[] args) {
//        ObjectClassLock objectClassLock = new ObjectClassLock();
////        ThreadOne threadOne = new ThreadOne(objectClassLock);
////        ThreadTwo threadTwo = new ThreadTwo(objectClassLock);
////        ThreadThree threadThree = new ThreadThree(objectClassLock);
////        threadOne.setName("One");
////        threadTwo.setName("Two");
////        threadThree.setName("Three");
////        threadOne.start();
////        threadTwo.start();
////        threadThree.start();


//        ObjectClassLock mTaska = new ObjectClassLock();
//        ObjectClassLock mTaskb = new ObjectClassLock();
//        ThreadObjectOne ta = new ThreadObjectOne(mTaska );
//        ThreadObjectTwo tb = new ThreadObjectTwo(mTaskb );
//        ta.setName("A");
//        tb.setName("B");
//        ta.start();
//        tb.start();

        ObjectClassLock mTaska = new ObjectClassLock();
        ObjectClassLock mTaskb = new ObjectClassLock();
        ThreadOne ta = new ThreadOne(mTaska );
        ThreadTwo tb = new ThreadTwo(mTaskb );
        ta.setName("A");
        tb.setName("B");
        ta.start();
        tb.start();


    }


}

class ThreadOne extends Thread{

    private ObjectClassLock objectClassLock;

    public ThreadOne(ObjectClassLock objectClassLock){
        this.objectClassLock = objectClassLock;
    }

    public void run() {
        objectClassLock.doLongTimeTaskA();
    }
}

class ThreadTwo extends Thread{

    private ObjectClassLock objectClassLock;

    public ThreadTwo(ObjectClassLock objectClassLock){
        this.objectClassLock = objectClassLock;
    }

    public void run() {
        objectClassLock.doLongTimeTaskB();
    }
}

class ThreadThree extends Thread{

    private ObjectClassLock objectClassLock;

    public ThreadThree(ObjectClassLock objectClassLock){
        this.objectClassLock = objectClassLock;
    }

    public void run() {
        objectClassLock.doLongTimeTaskC();
    }
}

class ThreadObjectOne extends Thread{

    private ObjectClassLock objectClassLock;

    public ThreadObjectOne(ObjectClassLock objectClassLock){
        this.objectClassLock = objectClassLock;
    }

    public void run() {
        objectClassLock.doLongTimeTaskC();
    }
}

class ThreadObjectTwo extends Thread{

    private ObjectClassLock objectClassLock;

    public ThreadObjectTwo(ObjectClassLock objectClassLock){
        this.objectClassLock = objectClassLock;
    }

    public void run() {
        objectClassLock.doLongTimeTaskC();
    }
}






