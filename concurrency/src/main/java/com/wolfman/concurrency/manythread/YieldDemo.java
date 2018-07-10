package com.wolfman.concurrency.manythread;

public class YieldDemo extends Thread {

    public YieldDemo(String name){
        super(name);
    }
    public synchronized void run(){
        for(int i=0; i <10; i++){
            System.out.printf("%s [%d]:%d\n", this.getName(), this.getPriority(), i);
            // i整除4时，调用yield
            if (i%4 == 0)
                Thread.yield();
        }
    }

    public static void main(String[] args) {
        YieldDemo t1 = new YieldDemo("t1");
        YieldDemo t2 = new YieldDemo("t2");
        t1.start();
        t2.start();
    }



}
