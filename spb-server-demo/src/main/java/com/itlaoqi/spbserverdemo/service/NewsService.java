package com.itlaoqi.spbserverdemo.service;


import com.itlaoqi.spbserverdemo.proto.NewsProto;
import com.itlaoqi.spbserverdemo.proto.NewsServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class NewsService extends NewsServiceGrpc.NewsServiceImplBase {
    @Override
    public void hello(NewsProto.StringRequest request, StreamObserver<NewsProto.StringResponse> responseObserver) {
        String name = request.getName();
        NewsProto.StringResponse response = NewsProto.StringResponse.newBuilder().setResult("hi!" + name).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
