package com.wolfman.concurrency.manythread.consumerproducer;

public class Producer {

    private Depot depot;

    public Producer(Depot depot) {
        this.depot = depot;
    }

    public void produce(final int val){
        new Thread(()->{
            depot.producer(val);
        }).start();
    }



}
