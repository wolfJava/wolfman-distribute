package com.wolfman.concurrency.manythread.consumerproducer;

public class Test {

    public static void main(String[] args) {
        Depot depot = new Depot(100);

        Producer producer = new Producer(depot);
        Consumer consumer = new Consumer(depot);

        producer.produce(60);
        producer.produce(120);
        consumer.comsume(60);
        consumer.comsume(150);
        producer.produce(110);

    }

}
