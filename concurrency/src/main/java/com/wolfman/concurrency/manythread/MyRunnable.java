package com.wolfman.concurrency.manythread;

public class MyRunnable implements Runnable {

    private int ticket = 10;

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            if (this.ticket>0){
                System.out.println(Thread.currentThread().getName()+" 卖票：ticket"+this.ticket--);
            }
        }
    }


    public static void main(String[] args) {
        MyRunnable runnable = new MyRunnable();
        // 启动3个线程t1,t2,t3(它们共用一个Runnable对象)，这3个线程一共卖10张票！
        Thread m1 = new Thread(runnable);
        Thread m2 = new Thread(runnable);
        Thread m3 = new Thread(runnable);

        m1.start();
        m2.start();
        m3.start();

//        结果说明：
//        (01) 和上面“MyThread继承于Thread”不同；这里的MyThread实现了Thread接口。
//        (02) 主线程main创建并启动3个子线程，而且这3个子线程都是基于“mt这个Runnable对象”而创建的。
//              运行结果是这3个子线程一共卖出了10张票。这说明它们是共享了MyThread接口的。

    }

}
