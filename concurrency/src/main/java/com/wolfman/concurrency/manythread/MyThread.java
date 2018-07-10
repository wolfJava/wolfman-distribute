package com.wolfman.concurrency.manythread;

public class MyThread extends Thread {

    private int ticket = 10;

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            if (this.ticket>0){
                System.out.println(this.getName()+" 卖票：ticket"+this.ticket--);
            }
        }
    }

    public static void main(String[] args) {
        // 启动3个线程t1,t2,t3；每个线程各卖10张票！
        MyThread m1 = new MyThread();
        MyThread m2 = new MyThread();
        MyThread m3 = new MyThread();

        m1.start();
        m2.start();
        m3.start();

//        结果说明：
//        (01) MyThread继承于Thread，它是自定义个线程。每个MyThread都会卖出10张票。
//        (02) 主线程main创建并启动3个MyThread子线程。每个子线程都各自卖出了10张票。

    }



}
