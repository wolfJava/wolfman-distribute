package com.wolfman.concurrency.reentrantlockdepotOne;

public class DemoTest {

    public static void main(String[] args) {
        Depot depot = new Depot();
        Producer producer = new Producer(depot);
        Consumer mCus = new Consumer(depot);
        producer.produce(60);
        producer.produce(120);
        mCus.consume(90);
        mCus.consume(150);
        producer.produce(110);

        //运行结果
        //Thread-0 produce(60) --> size=60
        //Thread-1 produce(120) --> size=180
        //Thread-3 consume(150) <-- size=30
        //Thread-2 consume(90) <-- size=-60
        //Thread-4 produce(110) --> size=50

    }


}
