package com.wolfman.concurrency.manythread.consumerproducer;

public class Consumer {

    private Depot depot;

    public Consumer(Depot depot) {
        this.depot = depot;
    }

    public void comsume(final int val){
        new Thread(()->{
            depot.comsumer(val);
        }).start();
    }

}
