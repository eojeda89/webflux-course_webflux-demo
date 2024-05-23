package com.webfluxcourse.webfluxdemo;

import com.webfluxcourse.webfluxdemo.dto.InputFailedValidationResponse;
import com.webfluxcourse.webfluxdemo.dto.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lec06ExchangeTest extends BaseTest{
    @Autowired
    private WebClient webClient;
    @Test
    public void badRequestTest() {
        Mono<Object> responseMono = webClient.get()
                .uri("reactive-math/square/{number}/throw", 11)
                .exchangeToMono(this::exchange)
                .doOnNext(System.out::println)
                .doOnError(err -> System.out.println(err.getMessage()));

        StepVerifier.create(responseMono)
                .expectNextCount(1)
                .verifyComplete();
    }

    private Mono<Object> exchange(ClientResponse clientResponse) {
        if (clientResponse.statusCode().value() == 400) {
            return clientResponse.bodyToMono(InputFailedValidationResponse.class);
        } else {
            return clientResponse.bodyToMono(Response.class);
        }
    }
}
