package com.wolfman.concurrency.manythread;

public class SyncThread extends Thread {

    public SyncThread(String name) {
        super(name);
    }

    @Override
    public void run() {
        synchronized (this){
            for (int i = 0; i < 5; i++) {
                try {
                    Thread.sleep(10);
                    System.out.println(Thread.currentThread().getName() + "loop" + i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        Thread t1 = new SyncThread("t1");
        Thread t2 = new SyncThread("t2");
        t1.start();
        t2.start();

        //结果说明：
        //如果这个结果一点也不令你感到惊讶，那么我相信你对synchronized和this的认识已经比较深刻了。
        // 否则的话，请继续阅读这里的分析。
        //synchronized(this)中的this是指“当前的类对象”，即synchronized(this)所在的类对应的当前对象。
        // 它的作用是获取“当前对象的同步锁”。
        //对于Demo1_2中，synchronized(this)中的this代表的是MyThread对象，
        // 而t1和t2是两个不同的MyThread对象，因此t1和t2在执行synchronized(this)时，
        // 获取的是不同对象的同步锁。对于Demo1_1对而言，synchronized(this)中的this代表的是MyRunable对象；
        // t1和t2共同一个MyRunable对象，因此，一个线程获取了对象的同步锁，会造成另外一个线程等待。


    }


}
