package com.wolfman.concurrency.cyclicbarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierDemoTwo {

    private static CyclicBarrier cb;

    public static void main(String[] args) {

        cb = new CyclicBarrier(5, new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " cb.getParties()：" + cb.getParties());
            }
        });

        for (int i = 0; i < 5; i++) {
            new InnerThreadTwo().start();
        }
        //运行结果
        //Thread-0 wait for CyclicBarrier.
        //Thread-1 wait for CyclicBarrier.
        //Thread-2 wait for CyclicBarrier.
        //Thread-3 wait for CyclicBarrier.
        //Thread-4 wait for CyclicBarrier.
        //Thread-4 cb.getParties()：5
        //Thread-4 continued.
        //Thread-0 continued.
        //Thread-1 continued.
        //Thread-2 continued.
        //Thread-3 continued.

    }

    static class InnerThreadTwo extends Thread{
        @Override
        public void run() {
            try {
                System.out.println(Thread.currentThread().getName() + " wait for CyclicBarrier.");
                // 将cb的参与者数量加1
                cb.await();

                System.out.println(Thread.currentThread().getName() + " continued.");

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }


}
