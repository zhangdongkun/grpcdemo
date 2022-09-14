package com.itlaoqi;

import com.itlaoqi.news.service.NewsService;
import com.itlaoqi.sms.SmsService;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class GrpcServer {
    private static  final int port = 9999;
    public static void main(String[] args) throws InterruptedException, IOException {
        io.grpc.Server server = ServerBuilder.forPort(port)
                .addService(new NewsService())
                .addService(new SmsService())
                .build()
                .start();
        System.out.println(String.format("GRPC服务端启动成功, 端⼝号: %d.", port));
        server.awaitTermination();
    }
}
