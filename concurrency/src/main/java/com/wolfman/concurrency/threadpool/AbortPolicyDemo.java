package com.wolfman.concurrency.threadpool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class AbortPolicyDemo {

    private static final int THREADS_SIZE = 1;
    private static final int CAPACITY = 1;
    public static void main(String[] args) {
        // 创建线程池。线程池的"最大池大小"和"核心池大小"都为1(THREADS_SIZE)，"线程池"的阻塞队列容量为1(CAPACITY)。
        ThreadPoolExecutor pool =
                new ThreadPoolExecutor(THREADS_SIZE,THREADS_SIZE,0,TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(CAPACITY));
        // 设置线程池的拒绝策略为"抛出异常"
        pool.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        try {
            for (int i = 0; i < 10; i++) {
                Runnable run = new MyRunnable("task-"+i);
                pool.execute(run);
            }
        }catch (RejectedExecutionException e){
            e.printStackTrace();
            pool.shutdown();
        }
        //运行结果：
        //java.util.concurrent.RejectedExecutionException
        //    at java.util.concurrent.ThreadPoolExecutor$AbortPolicy.rejectedExecution(ThreadPoolExecutor.java:1774)
        //    at java.util.concurrent.ThreadPoolExecutor.reject(ThreadPoolExecutor.java:768)
        //    at java.util.concurrent.ThreadPoolExecutor.execute(ThreadPoolExecutor.java:656)
        //    at AbortPolicyDemo.main(AbortPolicyDemo.java:27)
        //task-0 is running.
        //task-1 is running.

    }
}

class MyRunnable implements Runnable{

    private String name;

    public MyRunnable(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        try {
            System.out.println(this.name + "is running."+Thread.currentThread().getName());
            Thread.sleep(200);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}