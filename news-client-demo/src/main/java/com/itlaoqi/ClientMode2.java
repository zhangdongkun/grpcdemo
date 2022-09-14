package com.itlaoqi;

import com.itlaoqi.sms.proto.SmsProto;
import com.itlaoqi.sms.proto.SmsServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.Iterator;
/*服务端推流模式*/
public class ClientMode2 {
    private static final String host = "localhost";
    private static final int serverPort = 9999;

    public static void main(String[] args) {

        ManagedChannel channel = ManagedChannelBuilder.forAddress(host, serverPort).usePlaintext()
                .build();
        /*BlockingStub支持服务器推流*/
        SmsServiceGrpc.SmsServiceBlockingStub smsService = SmsServiceGrpc.newBlockingStub(channel);
        /*传入参数，服务器推流在客户端使用迭代器接收*/
        Iterator<SmsProto.SmsResponse> itr = smsService.sendSms(SmsProto.SmsRequest.newBuilder()
                .setContent("下午三点开会")
                .addPhoneNumber("13388888888")
                .addPhoneNumber("13388888889")
                .addPhoneNumber("13388888890")
                .addPhoneNumber("13388888891")
                .addPhoneNumber("13388888892")
                .addPhoneNumber("13388888893")
                .addPhoneNumber("13388888894")
                .addPhoneNumber("13388888895")
                .build());

        while(itr.hasNext()){
            SmsProto.SmsResponse next = itr.next();
            System.out.println(next.getResult());
        }
    }
}
