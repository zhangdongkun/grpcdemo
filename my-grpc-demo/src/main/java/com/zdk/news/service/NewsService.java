package com.zdk.news.service;

import com.zdk.news.proto.NewsProto;
import com.zdk.news.proto.NewsServiceGrpc;
import io.grpc.stub.StreamObserver;

public class NewsService extends NewsServiceGrpc.NewsServiceImplBase {
    @Override
    public void list(NewsProto.NewsRequest request, StreamObserver<NewsProto.NewsResponse> responseObserver) {
        String date = request.getDate();

        NewsProto.NewsResponse.newBuilder().addNews(NewsProto.News.newBuilder().build())


        super.list(request, responseObserver);
    }

    @Override
    public void hello(NewsProto.StringRequest request, StreamObserver<NewsProto.StringResponse> responseObserver) {

        super.hello(request, responseObserver);
    }
}
