package com.wolfman.concurrency.locksupport;

import java.util.concurrent.locks.LockSupport;

public class LockSupportDemoTwo {

    private static Thread mainThread;

    public static void main(String[] args) {
        ThreadLockSupportT ta = new ThreadLockSupportT("ta");
        // 获取主线程
        mainThread = Thread.currentThread();

        System.out.println(Thread.currentThread().getName()+" start ta");
        ta.start();
        System.out.println(Thread.currentThread().getName()+" block");
        // 主线程阻塞
        LockSupport.park(mainThread);
        System.out.println(Thread.currentThread().getName()+" continue");

        //运行结果：
        // main start ta
        // main block
        // ta wakup others
        // main continue

    }


    static class ThreadLockSupportT extends Thread{

        public ThreadLockSupportT(String name) {
            super(name);
        }

        @Override
        public void run() {
            synchronized (this){    // 通过synchronized(this)获取“当前对象的同步锁”
                System.out.println(Thread.currentThread().getName() + "wakeup others");
                // 唤醒“主线程”
                LockSupport.unpark(mainThread);
            }

        }
    }


}
