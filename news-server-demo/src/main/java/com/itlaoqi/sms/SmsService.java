package com.itlaoqi.sms;

import com.google.protobuf.ProtocolStringList;
import com.itlaoqi.sms.proto.SmsProto;
import com.itlaoqi.sms.proto.SmsServiceGrpc;
import io.grpc.stub.StreamObserver;

public class SmsService extends SmsServiceGrpc.SmsServiceImplBase {
    @Override
    public void sendSms(SmsProto.SmsRequest request, StreamObserver<SmsProto.SmsResponse> responseObserver) {
        ProtocolStringList phoneNumberList = request.getPhoneNumberList();
        for(String phoneNumber:phoneNumberList){
            SmsProto.SmsResponse response = SmsProto.SmsResponse.newBuilder().setResult(request.getContent() + "," + phoneNumber + "已发送").build();
            responseObserver.onNext(response);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        responseObserver.onCompleted();
    }


    @Override
    public StreamObserver<SmsProto.PhoneNumberRequest> createPhone(StreamObserver<SmsProto.PhoneNumberResponse> responseObserver) {
        /**
         * 异步通信基于responseObserver事件回调
         */
        return new StreamObserver<SmsProto.PhoneNumberRequest>() {
            int i = 0;
            @Override
            //接收到客户端发来的新的电话号码时，触发onNext
            public void onNext(SmsProto.PhoneNumberRequest phoneNumberRequest) {
                System.out.println(phoneNumberRequest.getPhoneNumber() + "手机号已登记");
                i = i + 1;
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }

            //客户端传输完毕时，完成消息统计
            @Override
            public void onCompleted() {
                responseObserver.onNext(SmsProto.PhoneNumberResponse.newBuilder().setResult("您本次批量导入" + i + "个员工电话").build());
                responseObserver.onCompleted();
            }
        };

    }

    @Override
    public StreamObserver<SmsProto.PhoneNumberRequest> createAndSendSms(StreamObserver<SmsProto.PhoneNumberResponse> responseObserver) {
        /**
         * 异步通信基于responseObserver事件回调
         */
        return new StreamObserver<SmsProto.PhoneNumberRequest>() {
            int i = 0;
            @Override
            //接收到客户端发来的新的电话号码时，触发onNext，再创建多个响应
            public void onNext(SmsProto.PhoneNumberRequest phoneNumberRequest) {
                System.out.println(phoneNumberRequest.getPhoneNumber() + "手机号已登记");
                responseObserver.onNext(SmsProto.PhoneNumberResponse.newBuilder().setResult(phoneNumberRequest.getPhoneNumber() + "手机号码已登记，此消息已发送给部门经理").build());
                responseObserver.onNext(SmsProto.PhoneNumberResponse.newBuilder().setResult(phoneNumberRequest.getPhoneNumber() + "手机号码已登记，此消息已发送给副总经理").build());
                responseObserver.onNext(SmsProto.PhoneNumberResponse.newBuilder().setResult(phoneNumberRequest.getPhoneNumber() + "手机号码已登记，此消息已发送给总经理").build());
                i = i + 1;
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }

            //客户端传输完毕时
            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
    }
}
