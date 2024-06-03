package com.webfluxcourse.webfluxdemo.webtestclient;

import com.webfluxcourse.webfluxdemo.controller.ReactiveMathController;
import com.webfluxcourse.webfluxdemo.dto.MultiplyRequestDto;
import com.webfluxcourse.webfluxdemo.dto.Response;
import com.webfluxcourse.webfluxdemo.service.ReactiveMathService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@WebFluxTest(ReactiveMathController.class)
public class Lec03ControllerPostTest {
    @Autowired
    private WebTestClient client;

    @MockBean
    private ReactiveMathService mathService;

    @Test
    public void postTest() {
        Mockito.when(mathService.multiply(Mockito.any())).thenReturn(Mono.just(new Response(1)));
        client
                .post()
                .uri("/reactive-math/multiply")
                .accept(MediaType.APPLICATION_JSON)
                .headers(h -> h.setBasicAuth("username", "password"))
                .headers(h -> h.set("someKey", "someValue"))
                .bodyValue(new MultiplyRequestDto())
                .exchange()
                .expectStatus().is2xxSuccessful();
    }
}
