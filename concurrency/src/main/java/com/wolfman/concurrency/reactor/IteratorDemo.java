package com.wolfman.concurrency.reactor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class IteratorDemo {

    public static void main(String[] args) {
        List<Integer> values = new ArrayList<>();
        values.add(1);
        values.add(2);
        values.add(3);
        values.add(4);
        values.add(5);

        //迭代
        //消费数据
        Iterator<Integer> iterator = values.iterator();
        while (iterator.hasNext()){
            //这个过程就是向服务器请求是否还有数据
            //拉模式
            Integer value = iterator.next();//主动获取数据
            System.out.println(value);
        }

    }


}
