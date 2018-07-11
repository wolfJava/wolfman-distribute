package com.wolfman.concurrency.reentrantlockdepotThree;

import com.wolfman.concurrency.reentrantlockdepotTow.ConsumerTwo;
import com.wolfman.concurrency.reentrantlockdepotTow.DepotTwo;
import com.wolfman.concurrency.reentrantlockdepotTow.ProducerTwo;

public class DemoThreeTest {

    public static void main(String[] args) {
        DepotThree depot = new DepotThree(100);
        ProducerThree producer = new ProducerThree(depot);
        ConsumerThree consumer = new ConsumerThree(depot);
        producer.produce(60);
        producer.produce(120);
        consumer.consume(90);
        consumer.consume(150);
        producer.produce(110);

        //运行结果
        //Thread-0 produce( 60) --> left=  0, inc= 60, size= 60
        //Thread-1 produce(120) --> left= 80, inc= 40, size=100
        //Thread-2 produce( 90) --> left=  0, inc= 90, size= 10
        //Thread-3 produce(150) --> left=140, inc= 10, size=  0
        //Thread-4 produce(110) --> left= 10, inc=100, size=100
        //Thread-3 produce(150) --> left= 40, inc=100, size=  0
        //Thread-4 produce(110) --> left=  0, inc= 10, size= 10
        //Thread-3 produce(150) --> left= 30, inc= 10, size=  0
        //Thread-1 produce(120) --> left=  0, inc= 80, size= 80
        //Thread-3 produce(150) --> left=  0, inc= 30, size= 50

    }


}
