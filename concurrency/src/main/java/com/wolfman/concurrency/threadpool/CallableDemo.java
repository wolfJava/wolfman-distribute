package com.wolfman.concurrency.threadpool;

import java.util.concurrent.*;

public class CallableDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //创建一个线程池
        ExecutorService pool = Executors.newSingleThreadExecutor();
        //创建有返回值的任务
        Callable c1 = new MyCallable();
        //执行任务并获取Future对象
        Future f1 = pool.submit(c1);
        // 输出结果
        System.out.println(f1.get());
        //关闭线程池
        pool.shutdown();

        //运行结果：
        //4950

    }
}

class MyCallable implements Callable{

    @Override
    public Integer call() throws Exception {
        int sum = 0;
        for (int i = 0; i < 100; i++) {
            sum += i;
        }
        return Integer.valueOf(sum);
    }
}
