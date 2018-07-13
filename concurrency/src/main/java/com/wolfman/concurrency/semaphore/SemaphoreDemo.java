package com.wolfman.concurrency.semaphore;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class SemaphoreDemo {

    private static final int SEM_MAX = 10;

    public static void main(String[] args) {
        Semaphore sem = new Semaphore(SEM_MAX);
        //创建线程池
        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        //在线程池中执行任务
        threadPool.execute(new SemaphoreThread(sem,5));
        threadPool.execute(new SemaphoreThread(sem,4));
        threadPool.execute(new SemaphoreThread(sem,7));
        //关闭池
        threadPool.shutdown();

        //运行结果
        //pool-1-thread-1 acquire count=5
        //pool-1-thread-2 acquire count=4
        //pool-1-thread-1 release 5
        //pool-1-thread-2 release 4
        //pool-1-thread-3 acquire count=7
        //pool-1-thread-3 release 7

    }
}

class SemaphoreThread extends Thread{

    private volatile Semaphore sem;   // 信号量

    private int count;  // 申请信号量的大小

    public SemaphoreThread(Semaphore sem, final int count) {
        this.count = count;
        this.sem = sem;
    }

    @Override
    public void run() {
        try {
            // 从信号量中获取count个许可
            sem.acquire(count);
            Thread.sleep(2000);
            System.out.println(Thread.currentThread().getName() + " acquire count="+count);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            // 释放给定数目的许可，将其返回到信号量。
            sem.release(count);
            System.out.println(Thread.currentThread().getName() + " release " + count);
        }


    }
}