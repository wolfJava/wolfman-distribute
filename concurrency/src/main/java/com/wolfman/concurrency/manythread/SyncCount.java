package com.wolfman.concurrency.manythread;

public class SyncCount {

    // 含有synchronized同步块的方法
    public void synMethod(){
        synchronized(this) {
            for (int i = 0; i < 5; i++) {
                try {
                    Thread.sleep(100); // 休眠100ms
                    System.out.println(Thread.currentThread().getName() + " synMethod loop " + i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void nonSynMethod(){
        synchronized(this) {
            for (int i = 0; i < 5; i++) {
                try {
                    Thread.sleep(100); // 休眠100ms
                    System.out.println(Thread.currentThread().getName() + " synMethod loop " + i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        final SyncCount count = new SyncCount();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                count.synMethod();
            }
        },"t1");
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                count.nonSynMethod();
            }
        },"t2");
        t1.start();
        t2.start();

        //结果说明：
        //主线程中新建了两个子线程t1和t2。t1和t2运行时都调用synchronized(this)，
        // 这个this是SyncCount对象(count)，而t1和t2共用count。
        // 因此，在t1运行时，t2会被阻塞，等待t1运行释放“count对象的同步锁”，t2才能运行。

    }


}
