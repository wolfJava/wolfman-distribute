package com.wolfman.concurrency.manythread;

public class InterruptDemoThree extends Thread  {

    private volatile boolean flag = true;

    private void stopFlag(){
        flag = false;
    }

    public InterruptDemoThree(String name) {
        super(name);
    }

    @Override
    public void run() {
        try {
            int i=0;
            while (flag) {
                Thread.sleep(100); // 休眠100ms
                i++;
                System.out.println(Thread.currentThread().getName()+" ("+this.getState()+") loop " + i);
            }
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() +" ("+this.getState()+") catch InterruptedException.");
        }
    }

    public static void main(String[] args) {
        try {
            InterruptDemoThree t1 = new InterruptDemoThree("t1");  // 新建“线程t1”
            System.out.println(t1.getName() +" ("+t1.getState()+") is new.");

            t1.start();                      // 启动“线程t1”
            System.out.println(t1.getName() +" ("+t1.getState()+") is started.");

            // 主线程休眠300ms，然后主线程给t1发“中断”指令。
            Thread.sleep(300);
            t1.stopFlag();
            System.out.println(t1.getName() +" ("+t1.getState()+") is interrupted.");

            // 主线程休眠300ms，然后查看t1的状态。
            Thread.sleep(300);
            System.out.println(t1.getName() +" ("+t1.getState()+") is interrupted now.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
