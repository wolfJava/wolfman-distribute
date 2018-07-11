package com.wolfman.concurrency.reentrantlockdepotThree;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 仓库
 */
public class DepotThree {

    private int capacity; // 仓库的容量

    private int size;   //仓库的实际数量

    private Lock lock;  //独占锁

    private Condition fullCondition;    // 生产条件

    private Condition emptyCondtion; //消费条件

    public DepotThree(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        this.lock = new ReentrantLock();
        this.fullCondition = lock.newCondition();
        this.emptyCondtion = lock.newCondition();
    }

    public void produce(int val){
        lock.lock();
        try {
            int left = val;//想要生成的数量
            while (left > 0){
                // 库存已满时，等待“消费者”消费产品。
                while (size>=capacity)
                    fullCondition.await();
                // 获取“实际生产的数量”(即库存中新增的数量)
                // 如果“库存”+“想要生产的数量”>“总的容量”，则“实际增量”=“总的容量”-“当前容量”。(此时填满仓库)
                // 否则“实际增量”=“想要生产的数量”
                int inc = (size+left)>capacity ? (capacity-size) : left;
                size += inc;
                left -= inc;
                System.out.printf("%s produce(%3d) --> left=%3d, inc=%3d, size=%3d\n",
                    Thread.currentThread().getName(), val, left, inc, size);
                // 通知“消费者”可以消费了。
                emptyCondtion.signal();
            }
        }catch (InterruptedException e){

        }finally {
            lock.unlock();
        }




    }

    public void consume(int val){
        lock.lock();
        try {
            int left = val;//想要消费的数量
            while (left > 0){
                // 库存已满时，等待“消费者”消费产品。
                while (size <= 0)
                    emptyCondtion.await();
                // 获取“实际生产的数量”(即库存中新增的数量)
                // 如果“库存”+“想要生产的数量”>“总的容量”，则“实际增量”=“总的容量”-“当前容量”。(此时填满仓库)
                // 否则“实际增量”=“想要生产的数量”
                int dec = (size<left) ? size : left;
                size -= dec;
                left -= dec;
                System.out.printf("%s produce(%3d) --> left=%3d, inc=%3d, size=%3d\n",
                        Thread.currentThread().getName(), val, left, dec, size);
                // 通知“消费者”可以消费了。
                fullCondition.signal();
            }
        }catch (InterruptedException e){

        }finally {
            lock.unlock();
        }

    }


}
