package com.webfluxcourse.webfluxdemo.controller;

import com.webfluxcourse.webfluxdemo.dto.InputFailedValidationResponse;
import com.webfluxcourse.webfluxdemo.dto.Response;
import com.webfluxcourse.webfluxdemo.exception.InputValidationException;
import com.webfluxcourse.webfluxdemo.service.ReactiveMathService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("reactive-math")
@RequiredArgsConstructor
public class ReactiveMathValidationController {

    private final ReactiveMathService reactiveMathService;

    @GetMapping("square/{input}/throw")
    public Mono<Response> findSquare(@PathVariable int input) {
        if (input < 10 || input > 20) {
            throw new InputValidationException(input);
        }
        return reactiveMathService.findSquare(input);
    }

    @GetMapping("square/{input}/mono-error")
    public Mono<Response> monoError(@PathVariable int input) {
        return Mono.just(input)
                .handle((integer, sink) -> {
                    if (integer >= 10 && integer <= 20) {
                        sink.next(integer);
                    } else {
                        sink.error(new InputValidationException(integer));
                    }
                })
                .cast(Integer.class)
                .flatMap(reactiveMathService::findSquare);
    }

    @GetMapping("square/{input}/assigment")
    public Mono<ResponseEntity<Response>> assigment(@PathVariable int input) {
        return Mono.just(input)
                .filter(integer -> integer >= 10 && integer <= 20)
                .flatMap(integer -> reactiveMathService.findSquare(input))
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

}
