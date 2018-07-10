package com.wolfman.concurrency.manythread;

public class InterruptDemoTwo extends Thread  {

    public InterruptDemoTwo(String name) {
        super(name);
    }

    @Override
    public void run() {
        int i=0;
        while (!isInterrupted()) {
            try {
                Thread.sleep(100); // 休眠100ms
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() +" ("+this.getState()+") catch InterruptedException.");
            }
            i++;
            System.out.println(Thread.currentThread().getName()+" ("+this.getState()+") loop " + i);
        }

    }

    public static void main(String[] args) {
        try {
            InterruptDemoTwo t1 = new InterruptDemoTwo("t1");  // 新建“线程t1”
            System.out.println(t1.getName() +" ("+t1.getState()+") is new.");

            t1.start();                      // 启动“线程t1”
            System.out.println(t1.getName() +" ("+t1.getState()+") is started.");

            // 主线程休眠300ms，然后主线程给t1发“中断”指令。
            Thread.sleep(300);
            t1.interrupt();
            System.out.println(t1.getName() +" ("+t1.getState()+") is interrupted.");

            // 主线程休眠300ms，然后查看t1的状态。
            Thread.sleep(300);
            System.out.println(t1.getName() +" ("+t1.getState()+") is interrupted now.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
