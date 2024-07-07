package com.apigateway.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;

import lombok.Setter;
import reactor.core.publisher.Mono;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Setter(onMethod_={@Autowired})
    private RouteValidator routeValidator;

    @Setter(onMethod_={@Autowired})
    private RestTemplate restTemplate;

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            if (routeValidator.isSecured.test(exchange.getRequest())) {
                //check header contains token
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    return unuthorizedResponse(exchange);
                }
                String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
                if (token != null && token.startsWith("Bearer ")) {
                    token = token.substring(7);
                }
                try {
                    //extract URI
                    ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8000/api/v1/auth/validate/" + token, null, String.class, HttpMethod.POST);
                    //setIdHere
                } catch (Exception e) {
                    return unuthorizedResponse(exchange);
                }
            }
            return chain.filter(exchange);
        });
    }

    private Mono<Void> unuthorizedResponse(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }

    public static class Config {}
}
