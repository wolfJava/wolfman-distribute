package com.wolfman.concurrency.reentrantlockdepotTow;

public class DemoTwoTest {

    public static void main(String[] args) {
        DepotTwo depot = new DepotTwo();
        ProducerTwo producer = new ProducerTwo(depot);
        ConsumerTwo mCus = new ConsumerTwo(depot);
        producer.produce(60);
        producer.produce(120);
        mCus.consume(90);
        mCus.consume(150);
        producer.produce(110);

        //运行结果
        //Thread-0 produce(60) --> size=60
        //Thread-4 produce(110) --> size=50
        //Thread-3 produce(150) --> size=-60
        //Thread-2 produce(90) --> size=90
        //Thread-1 produce(120) --> size=180

    }


}
