package com.wolfman.distribute.zookeeper.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * Curator-recipes 同步锁
 */


public class CuratorDistributDemo{

    private final static String CONNECTSTRING = "39.107.31.208:2181,39.107.32.43:2181,47.95.39.176:2181";

    CuratorFramework curatorFramework = null;
    public CuratorDistributDemo() {
        this.curatorFramework = CuratorFrameworkFactory.builder()
                .connectString(CONNECTSTRING)
                .sessionTimeoutMs(4000)
                .retryPolicy(new ExponentialBackoffRetry(1000,3))
                .namespace("curator").build();
        curatorFramework.start();
    }

    public void lock() throws Exception {
        InterProcessMutex interProcessMutex = new InterProcessMutex(curatorFramework,"/locks");
        System.out.println(Thread.currentThread().getName() + "===" + interProcessMutex.getParticipantNodes() + "。尝试竞争锁。");
        interProcessMutex.acquire();
        System.out.println(Thread.currentThread().getName() + "===" + "竞争到了");
        interProcessMutex.release();

        System.out.println(Thread.currentThread().getName() + "===" + "释放了到了");

    }




}
