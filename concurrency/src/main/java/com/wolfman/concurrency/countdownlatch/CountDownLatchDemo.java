package com.wolfman.concurrency.countdownlatch;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchDemo {

    private static int LATCH_SIZE = 5;
    private static CountDownLatch doneSignal;
    public static void main(String[] args) {

        try {
            doneSignal = new CountDownLatch(LATCH_SIZE);

            // 新建5个任务
            for(int i=0; i<LATCH_SIZE; i++)
                new InnerThread().start();

            System.out.println("main await begin.");
            // "主线程"等待线程池中5个任务的完成
            doneSignal.await();

            System.out.println("main await finished.");

            //运行结果
            //main await begin.
            //Thread-4 sleep 1000ms.
            //Thread-3 sleep 1000ms.
            //Thread-2 sleep 1000ms.
            //Thread-1 sleep 1000ms.
            //Thread-0 sleep 1000ms.
            //main await finished.


        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static class InnerThread extends Thread{
        public void run() {
            try {
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName() + " sleep 1000ms.");
                // 将CountDownLatch的数值减1
                doneSignal.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
