package com.wolfman.concurrency.condition;

import java.io.IOException;

public class WaitDemoOne {

    public static void main(String[] args) throws IOException {

        ThreadA ta = new ThreadA("ta");

        synchronized (ta){ // 通过synchronized(ta)获取“对象ta的同步锁”
            try{
                System.out.println(Thread.currentThread().getName()+" start ta");
                ta.start();

                System.out.println(Thread.currentThread().getName()+" block");
                ta.wait();    // 等待

                System.out.println(Thread.currentThread().getName()+" continue");

                //运行结果：
                //main start ta
                //main block
                //ta wakup others
                //main continue


            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    static class ThreadA extends Thread{

        public ThreadA(String name) {
            super(name);
        }

        @Override
        public void run() {
            synchronized (this){    // 通过synchronized(this)获取“当前对象的同步锁”
                System.out.println(Thread.currentThread().getName()+" wakup others");
                notify();   // 唤醒“当前对象上的等待线程”
            }
        }
    }


}
