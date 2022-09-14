package com.itlaoqi;

import com.itlaoqi.news.proto.NewsProto;
import com.itlaoqi.news.proto.NewsServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.List;
/*一元RPC模式*/
public class ClientMode1 {
    private static final String host = "localhost";
    private static final int serverPort = 9999;

    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(host, serverPort).usePlaintext()
                //⽆需加密或认证
                .build();
        try {
            NewsServiceGrpc.NewsServiceBlockingStub blockingStub = NewsServiceGrpc.newBlockingStub(channel);
            NewsProto.StringResponse response = blockingStub.hello(NewsProto.StringRequest.newBuilder().setName("Lily").build());
            System.out.println(response.getResult());
        }finally {
            channel.shutdown();
        }
    }
}
