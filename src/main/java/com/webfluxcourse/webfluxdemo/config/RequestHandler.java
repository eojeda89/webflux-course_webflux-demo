package com.webfluxcourse.webfluxdemo.config;

import com.webfluxcourse.webfluxdemo.dto.MultiplyRequestDto;
import com.webfluxcourse.webfluxdemo.dto.Response;
import com.webfluxcourse.webfluxdemo.service.ReactiveMathService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class RequestHandler {
    private final ReactiveMathService mathService;

    public Mono<ServerResponse> squareHandler(ServerRequest serverRequest) {
        int input = Integer.parseInt(serverRequest.pathVariable("input"));
        Mono<Response> responseMono = mathService.findSquare(input);
        return ServerResponse.ok().body(responseMono, Response.class);
    }

    public Mono<ServerResponse> tableHandler(ServerRequest serverRequest) {
        int input = Integer.parseInt(serverRequest.pathVariable("input"));
        Flux<Response> responseFlux = mathService.multiplicationTable(input);
        return ServerResponse.ok().body(responseFlux, Response.class);
    }
    public Mono<ServerResponse> tableStreamHandler(ServerRequest serverRequest) {
        int input = Integer.parseInt(serverRequest.pathVariable("input"));
        Flux<Response> responseFlux = mathService.multiplicationTable(input);
        return ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM).body(responseFlux, Response.class);
    }

    public Mono<ServerResponse> multiplyHandler(ServerRequest serverRequest) {
        Mono<MultiplyRequestDto> multiplyRequestDtoMono = serverRequest.bodyToMono(MultiplyRequestDto.class);
        Mono<Response> responseMono = mathService.multiply(multiplyRequestDtoMono);
        return ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM).body(responseMono, Response.class);
    }

    public Mono<ServerResponse> sumOPHandler(ServerRequest serverRequest) {
        int first = Integer.parseInt(serverRequest.pathVariable("first"));
        int second = Integer.parseInt(serverRequest.pathVariable("second"));
        Mono<Response> responseMono = Mono.fromSupplier(() -> first + second)
                .map(Response::new);
        return ServerResponse.ok().body(responseMono, Response.class);
    }
    public Mono<ServerResponse> restOPHandler(ServerRequest serverRequest) {
        int first = Integer.parseInt(serverRequest.pathVariable("first"));
        int second = Integer.parseInt(serverRequest.pathVariable("second"));
        Mono<Response> responseMono = Mono.fromSupplier(() -> first - second)
                .map(Response::new);
        return ServerResponse.ok().body(responseMono, Response.class);
    }
    public Mono<ServerResponse> multiplyOPHandler(ServerRequest serverRequest) {
        int first = Integer.parseInt(serverRequest.pathVariable("first"));
        int second = Integer.parseInt(serverRequest.pathVariable("second"));
        Mono<Response> responseMono = Mono.fromSupplier(() -> first * second)
                .map(Response::new);
        return ServerResponse.ok().body(responseMono, Response.class);
    }
    public Mono<ServerResponse> divisionOPHandler(ServerRequest serverRequest) {
        int first = Integer.parseInt(serverRequest.pathVariable("first"));
        int second = Integer.parseInt(serverRequest.pathVariable("second"));
        Mono<Response> responseMono = Mono.fromSupplier(() -> first / second)
                .map(Response::new);
        return ServerResponse.ok().body(responseMono, Response.class);
    }
}
