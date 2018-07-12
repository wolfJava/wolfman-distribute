package com.wolfman.concurrency.reentrantreadwritelock;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class User {

    private String name; //账户名称

    private MyCount myCount;//所要操作的账户

    private ReadWriteLock readWriteLock; //执行操作所需的锁对象

    public User(String name, MyCount myCount) {
        this.name = name;
        this.myCount = myCount;
        this.readWriteLock = new ReentrantReadWriteLock();
    }

    public void getCash(){
        new Thread(()->{
            try{
                readWriteLock.readLock().lock();
                System.out.println(Thread.currentThread().getName() +" getCash start");
                myCount.getCash();
                Thread.sleep(1);
                System.out.println(Thread.currentThread().getName() +" getCash end");
            }catch (InterruptedException e){
            }finally {
                readWriteLock.readLock().unlock();
            }
        }).start();
    }

    public void setCash(final int cash){
        new Thread(()->{
            try{
                readWriteLock.writeLock().lock();
                System.out.println(Thread.currentThread().getName() +" setCash start");
                myCount.setCash(cash);
                Thread.sleep(1);
                System.out.println(Thread.currentThread().getName() +" setCash end");
            }catch (InterruptedException e){
            }finally {
                readWriteLock.writeLock().unlock();
            }
        }).start();
    }



}
