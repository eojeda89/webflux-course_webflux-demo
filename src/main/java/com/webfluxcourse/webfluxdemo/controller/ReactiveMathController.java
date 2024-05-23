package com.webfluxcourse.webfluxdemo.controller;

import com.webfluxcourse.webfluxdemo.dto.MultiplyRequestDto;
import com.webfluxcourse.webfluxdemo.dto.Response;
import com.webfluxcourse.webfluxdemo.service.ReactiveMathService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.support.HttpRequestHandlerServlet;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("reactive-math")
@RequiredArgsConstructor
public class ReactiveMathController {

    private final ReactiveMathService reactiveMathService;

    @GetMapping("square/{input}")
    public Mono<Response> findSquare(@PathVariable int input) {
        return reactiveMathService.findSquare(input);
    }

    @GetMapping("table/{input}")
    public Flux<Response> multiplicationTable(@PathVariable int input) {
        return reactiveMathService.multiplicationTable(input);
    }
    @GetMapping(path = "table/{input}/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Response> multiplicationTableStream(@PathVariable int input) {
        return reactiveMathService.multiplicationTable(input);
    }

    @PostMapping("multiply")
    public Mono<Response> multiply(@RequestBody Mono<MultiplyRequestDto> dto, @RequestHeader Map<String, String> headers) {
        System.out.println(headers);
        return reactiveMathService.multiply(dto);
    }
}
