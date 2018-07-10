package com.wolfman.concurrency.manythread;

public class SyncRunnable implements Runnable {

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
        SyncRunnable syncRunnable = new SyncRunnable();
        Thread thread0 = new Thread(syncRunnable,"thread0");
        Thread thread1 = new Thread(syncRunnable, "thread1");
        thread0.start();
        thread1.start();

        //结果说明：
        //run()方法中存在“synchronized(this)代码块”，而且t1和t2都是基于"demo这个Runnable对象"创建的线程。
        // 这就意味着，我们可以将synchronized(this)中的this看作是“demo这个Runnable对象”；
        // 因此，线程t1和t2共享“demo对象的同步锁”。
        // 所以，当一个线程运行的时候，另外一个线程必须等待“运行线程”释放“demo的同步锁”之后才能运行。
    }
}
