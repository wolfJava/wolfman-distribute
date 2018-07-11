package com.wolfman.concurrency.spring;


import com.wolfman.concurrency.utils.Utils;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.lang.Nullable;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

/**
 * {@link org.springframework.util.concurrent.ListenableFuture}
 *
 */
public class ListenableFutureDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        SimpleAsyncTaskExecutor executor = new SimpleAsyncTaskExecutor("ListenableFutureDemo-");

        //ListenableFuture 实例
        ListenableFuture<Integer> future = executor.submitListenable(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return 1;
            }
        });

        //添加 Callback
        future.addCallback(new ListenableFutureCallback<Integer>() {
            @Override
            public void onFailure(Throwable ex) {
                Utils.printf(ex);
            }

            @Override
            public void onSuccess(@Nullable Integer result) {
                Utils.printf(result);
            }
        });

        // Future直到计算结束（阻塞）
        future.get();


    }




}
