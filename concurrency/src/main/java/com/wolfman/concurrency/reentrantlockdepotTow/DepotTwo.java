package com.wolfman.concurrency.reentrantlockdepotTow;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 仓库
 */
public class DepotTwo {

    private int size;   //仓库的实际数量

    private Lock lock;  //独占锁

    public DepotTwo() {
        this.size = 0;
        this.lock = new ReentrantLock();
    }

    public void produce(int val){
//        lock.lock();
//        try {
            size +=val;
            System.out.printf("%s produce(%d) --> size=%d\n",Thread.currentThread().getName(), val, size);
//        }finally {
//            lock.unlock();
//        }
    }

    public void consume(int val){
//        lock.lock();
//        try {
            size -=val;
            System.out.printf("%s produce(%d) --> size=%d\n",Thread.currentThread().getName(), val, size);
//        }finally {
//            lock.unlock();
//        }
    }


}
