package com.wolfman.distribute.jedis;


import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class Test {

    public static void main(String[] args) throws IOException {

        CountDownLatch countDownLatch = new CountDownLatch(3);
        for (int i =0 ;i<3;i++){
            new Thread(()->{
                try {
                    countDownLatch.await();
//                    DistributeLockDemo distributeLockDemo = new DistributeLockDemo();
//                    distributeLockDemo.lock();
//                    distributeLockDemo.unlock();
                    DistributeLockDemo distributeLockDemo = new DistributeLockDemo("lock",10000);
                    String value = distributeLockDemo.tryLock();
                    System.out.println(Thread.currentThread().getName()+"获得锁的值：" + value);
                    Thread.sleep(10);
                    distributeLockDemo.releaseLock(value);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            },"Thread-"+i).start();
            countDownLatch.countDown();
        }
        System.in.read();


    }



}
