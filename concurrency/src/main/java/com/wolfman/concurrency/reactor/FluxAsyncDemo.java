package com.wolfman.concurrency.reactor;

import com.wolfman.concurrency.utils.Utils;
import org.springframework.scheduling.annotation.Scheduled;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/**
 * 异步操作
 */
public class FluxAsyncDemo {

    public static void main(String[] args) throws InterruptedException {

        //Schedulers.immediate() 当前线程直接执行
//        Flux.range(0,10)
//                .publishOn(Schedulers.immediate())
//                .subscribe(Utils::printf);

        //单线程异步执行
//        Flux.range(0,10)
//                .publishOn(Schedulers.single())
//                .subscribe(Utils::printf);

        //弹性线程池异步执行
//        Flux.range(0,10)
//                .publishOn(Schedulers.elastic())
//                .subscribe(Utils::printf);

        //并行线程池异步执行
        Flux.range(0,10)
                .publishOn(Schedulers.parallel())
                .subscribe(Utils::printf);

        // 强制让主线程执行完毕
        Thread.currentThread().join(1* 1000L);


    }



}
