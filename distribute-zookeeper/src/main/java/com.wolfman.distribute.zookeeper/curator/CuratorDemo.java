package com.wolfman.distribute.zookeeper.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;


/**
 * curator 简单API调用
 */
public class CuratorDemo {

    private final static String CONNECTSTRING = "39.107.31.208:2181,39.107.32.43:2181,47.95.39.176:2181";


    public static void main(String[] args) throws Exception {

        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder()
                .connectString(CONNECTSTRING)
                .sessionTimeoutMs(4000)
                .retryPolicy(new ExponentialBackoffRetry(1000,3))
                .namespace("curator").build();
        curatorFramework.start();

        //结果：/curator/wolf/node1
        curatorFramework.create().creatingParentsIfNeeded()
                .withMode(CreateMode.PERSISTENT).forPath("/wolf/node1","1".getBytes());

        //删除节点
        //curatorFramework.delete().deletingChildrenIfNeeded().forPath("/wolf/node1");

        Stat stat = new Stat();
        curatorFramework.getData().storingStatIn(stat).forPath("/wolf/node1");

        curatorFramework.setData().withVersion(stat.getVersion()).forPath("/wolf/node1","xx".getBytes());

        curatorFramework.close();

    }


}
