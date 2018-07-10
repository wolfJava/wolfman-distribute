package com.wolfman.concurrency.manythread;

public class PriorityDemo extends Thread {


    public PriorityDemo(String name) {
        super(name);
    }

    @Override
    public void run() {
        for (int i=0; i<5; i++) {
            System.out.println(Thread.currentThread().getName()
                    +"("+Thread.currentThread().getPriority()+ ")"
                    +", loop "+i);
        }
    }

    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName()
                +"("+Thread.currentThread().getPriority()+ ")");
        PriorityDemo t1 = new PriorityDemo("t1");// 新建t1
        PriorityDemo t2 = new PriorityDemo("t2"); // 新建t2
        t1.setPriority(1);                // 设置t1的优先级为1
        t2.setPriority(10);                // 设置t2的优先级为10
        t1.start();                        // 启动t1
        t2.start();                        // 启动t2
    }


}
