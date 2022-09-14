package com.itlaoqi;

import com.itlaoqi.sms.proto.SmsProto;
import com.itlaoqi.sms.proto.SmsServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
/*客户端RPC流式通信*/
public class ClientMode3 {
    private SmsServiceGrpc.SmsServiceStub asyncStub = null;
    private static final String host = "localhost";
    private static final int serverPort = 9999;


    public static void main(String[] args) {
        ClientMode3 client = new ClientMode3();
        ManagedChannel channel = ManagedChannelBuilder.forAddress(host, serverPort).usePlaintext()
                .build();
        //客户端RPC流式通信必须创建异步Stub实现
        client.asyncStub = SmsServiceGrpc.newStub(channel);
        try {
            client.createPhone();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void createPhone() throws InterruptedException {

        //实例化RequestObject，发起请求
        StreamObserver<SmsProto.PhoneNumberRequest> requestObserver = asyncStub.createPhone(responseObserver);
        //模拟本次批量创建10个电话号码，对应创建10个请求对象
        for(int i = 1 ; i <= 10 ; i++){
            //创建请求对象
            SmsProto.PhoneNumberRequest request = SmsProto.PhoneNumberRequest.newBuilder().setPhoneNumber(String.valueOf(13388888888l + i)).build();
            //发送请求，服务器端会接收
            requestObserver.onNext(request);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                requestObserver.onError(e);
                throw e;
            }
        }
        //通知服务器，所有请求都发完啦
        requestObserver.onCompleted();
        Thread.sleep(1000);
    }

    /**
     * 监听服务器返回的响应
     */
    StreamObserver<SmsProto.PhoneNumberResponse> responseObserver = new StreamObserver<SmsProto.PhoneNumberResponse>() {
        //接收到服务器处理完毕的响应消息
        @Override
        public void onNext(SmsProto.PhoneNumberResponse response) {
            System.out.println(response.getResult());
        }

        @Override
        public void onError(Throwable throwable) {

        }

        @Override
        public void onCompleted() {
            System.out.println("处理完毕");
        }
    };

}
