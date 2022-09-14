package com.itlaoqi.spbclientdemo.controller;

import com.itlaoqi.spbserverdemo.proto.NewsProto;
import com.itlaoqi.spbserverdemo.proto.NewsServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class NewsController {
    @GrpcClient("global")
    private NewsServiceGrpc.NewsServiceBlockingStub newsStub;

    @GetMapping("/say")
    public String sayHello(String name){
        NewsProto.StringResponse response = newsStub.hello(NewsProto.StringRequest.newBuilder().setName(name).build());
        return response.getResult();
    }
}
