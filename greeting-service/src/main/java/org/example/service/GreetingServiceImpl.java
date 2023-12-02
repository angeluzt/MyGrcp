package org.example.service;


import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.example.GreetingRequest;
import org.example.GreetingResponse;
import org.example.GreetingServiceGrpc;

@GrpcService
public class GreetingServiceImpl extends GreetingServiceGrpc.GreetingServiceImplBase {

    @Override
    public void greeting(GreetingRequest request, StreamObserver<GreetingResponse> responseObserver) {
        String message = request.getMessage();
        System.out.println("Received message: " + message);

        GreetingResponse response = GreetingResponse
                .newBuilder()
                .setMessage("Hello Im the server, and you message was: " + message)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
