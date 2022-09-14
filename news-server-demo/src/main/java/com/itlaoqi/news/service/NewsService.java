package com.itlaoqi.news.service;

import com.itlaoqi.news.proto.NewsProto;
import com.itlaoqi.news.proto.NewsServiceGrpc;
import io.grpc.stub.StreamObserver;

import java.util.Date;

public class NewsService extends NewsServiceGrpc.NewsServiceImplBase {
    @Override
    public void list(NewsProto.NewsRequest request, StreamObserver<NewsProto.NewsResponse> responseObserver) {
        String date = request.getDate();
        NewsProto.NewsResponse newList = null;
        try {
            NewsProto.NewsResponse.Builder newListbuilder = NewsProto.NewsResponse.newBuilder();
            for (int i = 1; i <= 100; i++) {
                NewsProto.News news = NewsProto.News.newBuilder().setId(i)
                        .setContent(date + "当日新闻内容" + i)
                        .setTitle("新闻标题" + i)
                        .setCreateTime(new Date().getTime())
                        .build();
                newListbuilder.addNews(news);
            }
            newList = newListbuilder.build();
        } catch (Exception e) {
            responseObserver.onError(e);
        } finally {
            responseObserver.onNext(newList);
        }
        responseObserver.onCompleted();
    }

    @Override
    public void hello(NewsProto.StringRequest request, StreamObserver<NewsProto.StringResponse> responseObserver) {
        String name = request.getName();
        NewsProto.StringResponse response = NewsProto.StringResponse.newBuilder().setResult("hi!" + name).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
