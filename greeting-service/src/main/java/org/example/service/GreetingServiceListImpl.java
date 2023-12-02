package org.example.service;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.example.GreetingRequestList;
import org.example.GreetingResponseList;
import org.example.GreetingServiceListGrpc;

import java.util.List;
import java.util.stream.Collectors;

@GrpcService
public class GreetingServiceListImpl extends GreetingServiceListGrpc.GreetingServiceListImplBase {

    @Override
    public void multipleGreeting(GreetingRequestList request, StreamObserver<GreetingResponseList> responseObserver) {
        List<String> requestList = request.getMessageList();

        requestList = requestList.stream().map(element ->
        {
            System.out.println("Message received: " + element);
            return "Message received:" + element;
        }).collect(Collectors.toList());

        // Create a response message with the same list
        GreetingResponseList responseList = GreetingResponseList.newBuilder()
                .addAllMessage(requestList)
                .build();

        responseObserver.onNext(responseList);
        responseObserver.onCompleted();
    }
}
