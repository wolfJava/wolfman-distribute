package com.wolfman.concurrency.map;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapDemo {

    private static Map<String, Object> map = new ConcurrentHashMap<>();

    public static void main(String[] args) {

        new ThreadOne("t1").start();
        new ThreadOne("t2").start();
    }

    private static void printAll(){
        String key,value;
        Iterator iter = map.entrySet().iterator();
        while (iter.hasNext()){
            Map.Entry entry = (Map.Entry) iter.next();
            key = (String) entry.getKey();
            value = (String) entry.getValue();
            System.out.print(key + " - " + value +", ");
        }
        System.out.println();
    }

    private static class ThreadOne extends Thread{

        public ThreadOne(String name) {
            super(name);
        }

        @Override
        public void run() {
            int i = 0;
            while (i++<6){
                // “线程名” + "-" + "序号"
                String val = Thread.currentThread().getName();
                map.put(String.valueOf(i), val);
                printAll();
            }


        }
    }



}
