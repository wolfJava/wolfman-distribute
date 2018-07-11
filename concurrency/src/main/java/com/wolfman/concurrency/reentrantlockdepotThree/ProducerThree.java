package com.wolfman.concurrency.reentrantlockdepotThree;

import com.wolfman.concurrency.reentrantlockdepotTow.DepotTwo;

/**
 * 生产者
 */
public class ProducerThree {

    private DepotThree depot;

    public ProducerThree(DepotThree depot) {
        this.depot = depot;
    }

    //新建一个线程向仓库中生产产品。
    public void produce(final int val){
        new Thread(()->{
           depot.produce(val);
        }).start();
    }


}
