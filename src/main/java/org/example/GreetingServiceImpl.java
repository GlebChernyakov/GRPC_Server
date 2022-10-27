package org.example;

import com.example.grpc.GreetingServiceGrpc;
import com.example.grpc.GreetingServiceOuterClass;
import io.grpc.stub.StreamObserver;

/**
 * Класс с переопределением метода, принимает запрос от клиента и передает поток данных
 * [GreetingServiceOuterClass.HelloRequest request] - запрос от клиента
 * [StreamObserver<GreetingServiceOuterClass.HelloResponse> responseStreamObserver] - сервер передает ответ в виде потока клиенту
 *
 * @author Gleb Chernyakov
 */
public class GreetingServiceImpl extends GreetingServiceGrpc.GreetingServiceImplBase {

    @Override
    public void greeting(GreetingServiceOuterClass.HelloRequest request,
                         StreamObserver<GreetingServiceOuterClass.HelloResponse> responseStreamObserver) {

        for (int i = 0; i < 100; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            GreetingServiceOuterClass.HelloResponse response = GreetingServiceOuterClass
                    .HelloResponse.newBuilder()
                    .setGreeting("Hello from server! "  + request.getName() + ": " + i).build();

            responseStreamObserver.onNext(response);
        }

        responseStreamObserver.onCompleted();
    }
}
