package com.wolfman.concurrency.manythread;

public class SyncAndNonSyncCount {

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
        for (int i = 0; i < 5; i++) {
            try {
                Thread.sleep(100); // 休眠100ms
                System.out.println(Thread.currentThread().getName() + " synMethod loop " + i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        final SyncAndNonSyncCount count = new SyncAndNonSyncCount();
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
        //主线程中新建了两个子线程t1和t2。t1会调用count对象的synMethod()方法，该方法内含有同步块；
        // 而t2则会调用count对象的nonSynMethod()方法，该方法不是同步方法。
        // t1运行时，虽然调用synchronized(this)获取“count的同步锁”；
        // 但是并没有造成t2的阻塞，因为t2没有用到“count”同步锁。

    }


}
