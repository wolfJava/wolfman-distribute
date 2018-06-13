package com.wolfman.dubbo.consumer;

import com.alibaba.dubbo.rpc.RpcContext;
import com.wolfman.dubbo.api.DoRequest;
import com.wolfman.dubbo.api.DoResponse;
import com.wolfman.dubbo.api.IHelloWorldService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class DubboSingleConsumer {

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("dubbo-consumer.xml");
//        异步回掉
//        IHelloWorldService service = (IHelloWorldService) context.getBean("orderServices");
//        long start=System.currentTimeMillis();
//        DoRequest request = new DoRequest();
//        request.setName("huhao");
//        service.sayHello(request);
//        Future<DoResponse> response = RpcContext.getContext().getFuture();
//        System.out.println("aaaaaaaaaa");
//        DoResponse response1 = response.get();
//        System.out.println(response1);
//        long end=System.currentTimeMillis();
//        System.out.println("总共耗时："+(end-start)/1000+"秒");

        //正常流程
        IHelloWorldService service = (IHelloWorldService) context.getBean("orderServices");
        DoRequest request = new DoRequest();
        request.setName("huhao");
        DoResponse response = service.sayHello(request);
        System.out.println(response);
        System.in.read();

    }


}
