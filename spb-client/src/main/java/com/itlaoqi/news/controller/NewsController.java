package com.itlaoqi.news.controller;

import com.itlaoqi.news.proto.NewsProto;
import com.itlaoqi.news.proto.NewsServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NewsController {
    @GrpcClient("grpc-server")
    private NewsServiceGrpc.NewsServiceBlockingStub newsStub;

    @GetMapping("/say")
    public String sayHello(String name){
        NewsProto.StringResponse resposne = newsStub.hello(NewsProto.StringRequest.newBuilder().setName(name).build());
        return resposne.getResult();
    }
}
