package com.apigateway.filter;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;

import com.apigateway.dto.UserDto;

import lombok.Setter;
import reactor.core.publisher.Mono;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Setter(onMethod_={@Autowired})
    private RouteValidator routeValidator;

    @Setter(onMethod_={@Autowired})
    private RestTemplate restTemplate;

    @Value("${user-service.base-url}")
    private String userServiceUrl;

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            //check if header contains token
            if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                return unauthorizedResponse(exchange);
            }
            String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7);
            }
            try {
                String userId = getUserId(validateUserTokenAndGetDetails(token));
                exchange.getRequest().mutate().header("user-id", userId).build();
            } catch (Exception e) {
                return unauthorizedResponse(exchange);
            }
            return chain.filter(exchange);
        });
    }

    private ResponseEntity<UserDto> validateUserTokenAndGetDetails(String token) {
        return  restTemplate.postForEntity(String.format("%s/api/v1/auth/validate/%s", userServiceUrl, token),
                null,
                UserDto.class,
                HttpMethod.POST);
    }

    private String getUserId(ResponseEntity<UserDto> userDtoResponseEntity) {
        return Optional.of(userDtoResponseEntity)
                .map(HttpEntity::getBody)
                .map(UserDto::id)
                .map(String::valueOf)
                .orElseThrow(() -> new RuntimeException("Couldn't get user data"));
    }

    private Mono<Void> unauthorizedResponse(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }

    public static class Config {}
}
