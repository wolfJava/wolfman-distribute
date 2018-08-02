package com.wolfman.concurrency.threadpool;

import java.util.stream.Stream;

public class StreamDemo {

    public static void main(String[] args) {
        Stream.of(0,1,2,3,4,5,6,7,8,9)
                .filter(v -> v%2 ==1)
                .map(v -> v -1)
                .reduce(Integer::max)
                .ifPresent(System.out::println);
                //.forEach(System.out::println);
    }


}
