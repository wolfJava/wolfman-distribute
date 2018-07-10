package com.wolfman.concurrency.manythread.consumerproducer;

/**
 * 仓库
 */
public class Depot {

    private int capacity;

    private int size;

    public Depot(int capacity) {
        this.capacity = capacity;
        this.size = 0;
    }

    public synchronized void producer(int val){
        try {
            //left 表示“想要生产的数量”(有可能生产量太多，需多次生产)
            int left = val;
            while (left > 0){
                //库存已满时，等待消费者消费产品
                while (size>=capacity)
                    wait();
                // 获取“实际生产的数量”(即库存中新增的数量)
                // 如果“库存”+“想要生产的数量”>“总的容量”，则“实际增量”=“总的容量”-“当前容量”。(此时填满仓库)
                // 否则“实际增量”=“想要生产的数量”
                int inc = size + left > capacity ? (capacity - size) : left;
                size += inc;
                left -= inc;
                System.out.printf("%s produce(%3d) --> left=%3d, inc=%3d, size=%3d\n",
                        Thread.currentThread().getName(), val, left, inc, size);
                // 通知“消费者”可以消费了。
                notifyAll();
            }
        }catch (InterruptedException  e){
        }
    }

    public synchronized void comsumer(int val){
        try{
            // left 表示“客户要消费数量”(有可能消费量太大，库存不够，需多此消费)
            int left = val;

            while (left > 0){
                while (size <= 0){
                    wait();
                }
                int dec = (left > size) ? size : left;
                size -= dec;
                left -= dec;
                System.out.printf("%s consume(%3d) <-- left=%3d, dec=%3d, size=%3d\n",
                        Thread.currentThread().getName(), val, left, dec, size);
                notifyAll();
            }
        }catch (InterruptedException  e){

        }
    }



}
