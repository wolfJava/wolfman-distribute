package com.wolfman.concurrency.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolDemo {
    public static void main(String[] args) {
        // 创建一个可重用固定线程数的线程池
        ExecutorService threadPool = Executors.newFixedThreadPool(2);
        // 创建实现了Runnable接口对象，Thread对象当然也实现了Runnable接口
        Thread t0 = new MyThread();
        Thread t1 = new MyThread();
        Thread t2 = new MyThread();
        Thread t3 = new MyThread();
        Thread t4 = new MyThread();
        Thread t5 = new MyThread();
        //将线程放入池中进行执行
        threadPool.execute(t0);
        threadPool.execute(t1);
        threadPool.execute(t2);
        threadPool.execute(t3);
        threadPool.execute(t4);
        threadPool.execute(t5);
        //关闭线程池
        threadPool.shutdown();
        //运行结果：
        //当前线程pool-1-thread-1
        //当前线程pool-1-thread-2
        //当前线程pool-1-thread-1
        //当前线程pool-1-thread-2
        //当前线程pool-1-thread-2
        //当前线程pool-1-thread-1
    }

    static class MyThread extends Thread{
        @Override
        public void run() {
            System.out.printf("当前线程%s\n",Thread.currentThread().getName());
        }
    }
}
