package com.webfluxcourse.webfluxdemo.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.util.Objects;

@Configuration
@RequiredArgsConstructor
public class RouterConfig {

    private final RequestHandler requestHandler;
    @Bean
    public RouterFunction<ServerResponse> serverResponseRouterFunction() {
        return RouterFunctions.route()
                .GET("router/square/{input}", requestHandler::squareHandler)
                .GET("router/table/{input}", requestHandler::tableHandler)
                .GET("router/table/{input}/stream", requestHandler::tableStreamHandler)
                .POST("router/multiply", requestHandler::multiplyHandler)
                .GET("router/calculator/{first}/{second}",
                        RequestPredicates.headers(headers -> headers.asHttpHeaders().toSingleValueMap().get("OP").equals("+")),
                        requestHandler::sumOPHandler)
                .GET("router/calculator/{first}/{second}",
                        RequestPredicates.headers(headers -> headers.asHttpHeaders().toSingleValueMap().get("OP").equals("-")),
                        requestHandler::restOPHandler)
                .GET("router/calculator/{first}/{second}",
                        RequestPredicates.headers(headers -> headers.asHttpHeaders().toSingleValueMap().get("OP").equals("*")),
                        requestHandler::multiplyOPHandler)
                .GET("router/calculator/{first}/{second}",
                        RequestPredicates.headers(headers -> headers.asHttpHeaders().toSingleValueMap().get("OP").equals("/")),
                        requestHandler::divisionOPHandler)
                .build();
    }
}
