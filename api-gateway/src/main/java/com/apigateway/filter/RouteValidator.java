package com.apigateway.filter;

import java.util.List;
import java.util.function.Predicate;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

//todo remove
@Component
public class RouteValidator {

    private static final List<String> endpoints = List.of("auth/authenticate", "auth/token");

    public Predicate<ServerHttpRequest> isSecured =
            request -> endpoints.stream().noneMatch(uri -> request.getURI().getPath().contains(uri));
}
