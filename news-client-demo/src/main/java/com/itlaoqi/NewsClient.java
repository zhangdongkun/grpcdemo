package com.itlaoqi;

import com.itlaoqi.news.proto.NewsProto;
import com.itlaoqi.news.proto.NewsServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.List;
/*基本RPC通信演示*/
public class NewsClient {
    private static final String host = "localhost";
    private static final int serverPort = 9999;

    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(host, serverPort).usePlaintext()
                //⽆需加密或认证
                .build();
        try {
            NewsServiceGrpc.NewsServiceBlockingStub blockingStub = NewsServiceGrpc.newBlockingStub(channel);
            NewsProto.NewsRequest request = NewsProto.NewsRequest.newBuilder().setDate("20220103").build();
            NewsProto.NewsResponse response = blockingStub.list(request);
            List<NewsProto.News> newsList = response.getNewsList();
            for (NewsProto.News news : newsList) {
                System.out.println(news.getTitle() + ":" + news.getContent());
            }
        }finally {
            channel.shutdown();
        }
    }
}
