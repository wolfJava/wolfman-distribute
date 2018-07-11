package com.wolfman.concurrency.reentrantlockdepotThree;

import com.wolfman.concurrency.reentrantlockdepotTow.DepotTwo;

/**
 * 生产者
 */
public class ConsumerThree {

    private DepotThree depot;

    public ConsumerThree(DepotThree depot) {
        this.depot = depot;
    }

    //新建一个线程从仓库中消费产品。
    public void consume(final int val){
        new Thread(()->{
           depot.consume(val);
        }).start();
    }

}
