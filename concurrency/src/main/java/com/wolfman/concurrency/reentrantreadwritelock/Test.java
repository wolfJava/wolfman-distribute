package com.wolfman.concurrency.reentrantreadwritelock;

public class Test {

    public static void main(String[] args) {

        MyCount myCount = new MyCount("88888888",5000);
        User user = new User("huhao",myCount);
        // 分别启动3个“读取账户金钱”的线程 和 3个“设置账户金钱”的线程
        for (int i = 0; i < 3; i++) {
            user.getCash();
            user.setCash((i+1)*1000);
        }
        //运行结果
        //Thread-0 getCash start
        //Thread-0 getCash cash=5000
        //Thread-0 getCash end
        //Thread-1 setCash start
        //Thread-1 setCash cash=1000
        //Thread-1 setCash end
        //Thread-2 getCash start
        //Thread-2 getCash cash=6000
        //Thread-4 getCash start
        //Thread-4 getCash cash=6000
        //Thread-2 getCash end
        //Thread-4 getCash end
        //Thread-3 setCash start
        //Thread-3 setCash cash=2000
        //Thread-3 setCash end
        //Thread-5 setCash start
        //Thread-5 setCash cash=3000
        //Thread-5 setCash end


    }


}
