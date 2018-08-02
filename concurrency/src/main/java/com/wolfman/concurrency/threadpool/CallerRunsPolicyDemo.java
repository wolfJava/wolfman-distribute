package com.wolfman.concurrency.threadpool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CallerRunsPolicyDemo {

    private static final int THREADS_SIZE = 1;
    private static final int CAPACITY = 1;

    public static void main(String[] args) {
        // 创建线程池。线程池的"最大池大小"和"核心池大小"都为1(THREADS_SIZE)，"线程池"的阻塞队列容量为1(CAPACITY)。
        ThreadPoolExecutor pool =
                new ThreadPoolExecutor(THREADS_SIZE,THREADS_SIZE,0,TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(CAPACITY));
        // 设置线程池的拒绝策略为"CallerRunsPolicy"
        pool.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 新建10个任务，并将它们添加到线程池中。
        for (int i = 0; i < 10; i++) {
            Runnable runnable = new MyRunnable("task-"+i);
            pool.execute(runnable);
        }
        // 关闭线程池
        pool.shutdown();

        //运行结果：
        //task-2is running.main
        //task-0is running.pool-1-thread-1
        //task-3is running.main
        //task-1is running.pool-1-thread-1
        //task-5is running.main
        //task-4is running.pool-1-thread-1
        //task-7is running.main
        //task-6is running.pool-1-thread-1
        //task-9is running.main
        //task-8is running.pool-1-thread-1
    }

}
