package com.wolfman.concurrency.utils;

public class Utils {

    public static void printf(Object message){
        System.out.printf("[Thread：%s] : %s \n ", Thread.currentThread().getName(), String.valueOf(message));
    }


}
