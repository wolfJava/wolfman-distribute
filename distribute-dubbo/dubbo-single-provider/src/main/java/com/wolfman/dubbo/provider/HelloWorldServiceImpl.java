package com.wolfman.dubbo.provider;

import com.wolfman.dubbo.api.DoRequest;
import com.wolfman.dubbo.api.DoResponse;
import com.wolfman.dubbo.api.IHelloWorldService;

public class HelloWorldServiceImpl implements IHelloWorldService {
    public DoResponse sayHello(DoRequest request) {

        System.out.println("曾经来过："+request);
        DoResponse response=new DoResponse();
        response.setCode("1000");
        response.setMemo("处理成功");
        return response;
    }
}
