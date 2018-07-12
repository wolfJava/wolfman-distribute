package com.wolfman.concurrency.condition;

import java.io.IOException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionDemoOne {

    private static Lock lock = new ReentrantLock();

    private static Condition condition = lock.newCondition();

    public static void main(String[] args) throws IOException {
        ThreadB ta = new ThreadB("ta");
        lock.lock();
        try{
            System.out.println(Thread.currentThread().getName()+" start ta");
            ta.start();
            System.out.println(Thread.currentThread().getName()+" block");
            condition.await();    // 等待
            System.out.println(Thread.currentThread().getName()+" continue");

            //运行结果：
            //main start ta
            //main block
            //ta wakup others
            //main continue
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            lock.unlock();    // 释放锁
        }
    }

    static class ThreadB extends Thread{

        public ThreadB(String name) {
            super(name);
        }

        @Override
        public void run() {
            lock.lock();    // 获取锁
            try{
                System.out.println(Thread.currentThread().getName()+" wakup others");
                condition.signal();// 唤醒“condition所在锁上的其它线程”
            }finally {
                lock.unlock();    // 释放锁
            }
        }
    }


}
