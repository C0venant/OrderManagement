package com.apigateway.filter;

import java.util.List;
import java.util.function.Predicate;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
public class RouteValidator {

    //add here endpoints which do not require authentication
    private static final List<String> endpoints = List.of("");

    public Predicate<ServerHttpRequest> isSecured =
            request -> endpoints.stream().noneMatch(uri -> request.getURI().getPath().contains(uri));
}
