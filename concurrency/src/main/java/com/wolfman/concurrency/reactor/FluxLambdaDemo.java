package com.wolfman.concurrency.reactor;

import com.wolfman.concurrency.utils.Utils;
import reactor.core.publisher.Flux;

import java.util.Random;

public class FluxLambdaDemo {

    public static void main(String[] args) {
        Random random = new Random();
        //订阅并且处理（控制台输出）
        Flux.just(1,2,3).map(value ->{
            //当 随机数 == 3 抛出异常
            if (random.nextInt(4) ==3){
                throw new RuntimeException();
            }
            return value + 1;
        }).subscribe(
                Utils::printf,   //处理数据 onNext()
                Utils::printf,   //处理异常 onError()
                ()->{
                    Utils.printf("Subscripetion is complated");
            }
        );

//订阅并且处理（控制台输出）
//        Flux.just(1,2,3).subscribe(Utils::printf);

    }



}
