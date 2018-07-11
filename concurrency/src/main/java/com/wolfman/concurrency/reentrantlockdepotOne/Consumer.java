package com.wolfman.concurrency.reentrantlockdepotOne;

/**
 * 生产者
 */
public class Consumer {

    private Depot depot;

    public Consumer(Depot depot) {
        this.depot = depot;
    }

    //新建一个线程从仓库中消费产品。
    public void consume(final int val){
        new Thread(()->{
           depot.consume(val);
        }).start();
    }

}
