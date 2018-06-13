package com.wolfman.dubbo.consumer;

import com.wolfman.dubbo.api.DoRequest;
import com.wolfman.dubbo.api.DoResponse;
import com.wolfman.dubbo.api.IHelloWorldService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class DubboSingleConsumer {

    public static void main(String[] args) throws IOException {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("dubbo-consumer.xml");

        IHelloWorldService service = (IHelloWorldService) context.getBean("orderServices");

        DoRequest request = new DoRequest();
        request.setName("huhao");
        DoResponse response = service.sayHello(request);
        System.out.println(response);
        System.in.read();

    }


}
