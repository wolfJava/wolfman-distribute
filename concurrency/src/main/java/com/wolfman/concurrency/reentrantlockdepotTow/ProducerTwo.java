package com.wolfman.concurrency.reentrantlockdepotTow;

/**
 * 生产者
 */
public class ProducerTwo {

    private DepotTwo depot;

    public ProducerTwo(DepotTwo depot) {
        this.depot = depot;
    }

    //新建一个线程向仓库中生产产品。
    public void produce(final int val){
        new Thread(()->{
           depot.produce(val);
        }).start();
    }


}
