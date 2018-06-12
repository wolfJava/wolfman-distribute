package com.wolfman.distribute.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class CreateZookeeperSession {

    private final static String CONNECTSTRING="39.107.31.208:2181,39.107.32.43:2181,47.95.39.176:2181";

    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    public static void main(String[] args) {

        try {
            ZooKeeper zooKeeper = new ZooKeeper(CONNECTSTRING, 5000, new Watcher() {
                public void process(WatchedEvent watchedEvent) {

                    if (watchedEvent.getState() == Event.KeeperState.SyncConnected){
                        countDownLatch.countDown();
                        System.out.println("SyncConnected:" + watchedEvent.getState());
                    }
                    System.out.println(watchedEvent.getState());
                }
            });
            countDownLatch.await();
            //添加节点
            zooKeeper.create("/huhao-learn","0".getBytes(),ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
            Thread.sleep(1000);
            Stat stat = new Stat();
            //获取节点
            byte[] data = zooKeeper.getData("/huhao-learn",null,stat);
            System.out.println(new String(data));
            //修改节点
            zooKeeper.setData("/huhao-learn","1".getBytes(),stat.getVersion());
            //获取节点
            byte[] data1 = zooKeeper.getData("/huhao-learn",null,stat);
            System.out.println(new String(data1));
            //删除节点
            zooKeeper.delete("/huhao-learn",stat.getVersion());
            System.out.println(zooKeeper.getState());

            zooKeeper.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }


    }



}
