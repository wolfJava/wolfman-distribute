package com.wolfman.concurrency.reactor;

import com.wolfman.concurrency.utils.Utils;
import reactor.core.publisher.Flux;

import java.util.Random;

public class FluxApiDemo {

    public static void main(String[] args) {
        Random random = new Random();

        Flux.generate(
                () -> 0,
                (value, sink) -> {
                    if (value == 3){
                        sink.complete();
                    }else{
                       sink.next("value:"+value);
                    }
                    return value+1;
//                    sink.next("3 x " + state + " = " + 3*state);
//                    if (state == 10) sink.complete();
//                    return state + 1;
                }).subscribe(Utils::printf,Utils::printf,()->{
                Utils.printf("Subscripetion is complated");
        });



    }




}
