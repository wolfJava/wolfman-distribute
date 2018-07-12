package com.wolfman.concurrency.locksupport;

public class LockSupportDemoOne {

    public static void main(String[] args) {

        ThreadLockSupport ta = new ThreadLockSupport("ta");

        synchronized (ta){  // 通过synchronized(ta)获取“对象ta的同步锁”
            try{
                System.out.println(Thread.currentThread().getName()+" start ta");
                ta.start();

                System.out.println(Thread.currentThread().getName()+" block");
                // 主线程等待
                ta.wait();
                System.out.println(Thread.currentThread().getName()+" continue");

                //运行结果：
                // main start ta
                // main block
                // ta wakup others
                // main continue

            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    static class ThreadLockSupport extends Thread{

        public ThreadLockSupport(String name) {
            super(name);
        }

        @Override
        public void run() {
            synchronized (this){    // 通过synchronized(this)获取“当前对象的同步锁”
                System.out.println(Thread.currentThread().getName() + "wakeup others");
                notify();   // 唤醒“当前对象上的等待线程”
            }

        }
    }


}
