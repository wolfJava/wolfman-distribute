package com.wolfman.concurrency.reentrantlockdepotTow;

/**
 * 生产者
 */
public class ConsumerTwo {

    private DepotTwo depot;

    public ConsumerTwo(DepotTwo depot) {
        this.depot = depot;
    }

    //新建一个线程从仓库中消费产品。
    public void consume(final int val){
        new Thread(()->{
           depot.consume(val);
        }).start();
    }

}
