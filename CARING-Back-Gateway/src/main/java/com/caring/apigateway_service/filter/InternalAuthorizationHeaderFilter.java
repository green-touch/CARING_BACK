package com.caring.apigateway_service.filter;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.security.MessageDigest;

@Slf4j
@Component
public class InternalAuthorizationHeaderFilter extends AbstractGatewayFilterFactory<InternalAuthorizationHeaderFilter.Config> {

    private final Environment env;

    public InternalAuthorizationHeaderFilter(Environment environment) {
        super(Config.class);
        this.env = environment;
    }

    private static final String HEADER_SIGNATURE = "X-Internal-Signature";
    private static final String HEADER_TIMESTAMP = "X-Internal-Timestamp";

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            logPreFilter(config, request);
            String signature = request.getHeaders().getFirst(HEADER_SIGNATURE);
            String timestampStr = request.getHeaders().getFirst(HEADER_TIMESTAMP);

            if (signature == null || timestampStr == null) {
                return onError(exchange, "Missing signature or timestamp", HttpStatus.UNAUTHORIZED);
            }

            long now = System.currentTimeMillis();
            long timestamp;
            try {
                timestamp = Long.parseLong(timestampStr);
            } catch (NumberFormatException e) {
                log.warn("Invalid timestamp format: {}", timestampStr);
                return onError(exchange, "Invalid timestamp format", HttpStatus.UNAUTHORIZED);
            }

            String internalTokenSecret = env.getProperty("token.secret-internal");
            long expirationMs = Long.parseLong(env.getProperty("token.expiration_time", "30000"));

            if (Math.abs(now - timestamp) > expirationMs) {
                log.warn("Expired token: now={}, timestamp={}, allowed={}", now, timestamp, expirationMs);
                return onError(exchange, "Expired token", HttpStatus.UNAUTHORIZED);
            }

            String expectedSignature = new HmacUtils(HmacAlgorithms.HMAC_SHA_256, internalTokenSecret)
                    .hmacHex(timestampStr);

            if (!MessageDigest.isEqual(signature.getBytes(), expectedSignature.getBytes())) {
                log.warn("Invalid signature: expected={}, provided={}", expectedSignature, signature);
                return onError(exchange, "Invalid signature", HttpStatus.UNAUTHORIZED);
            }

            return chain.filter(exchange);
        };
    }

    private void logPreFilter(InternalAuthorizationHeaderFilter.Config config, ServerHttpRequest request) {
        if (config.isPreLogger()) {
            log.info("Internal Auth Filter Pre-Logger: BaseMessage: {}, Request ID: {}", config.getBaseMessage(), request.getId());
        }
    }

    private Mono<Void> onError(ServerWebExchange exchange, String message, HttpStatus status) {
        log.error("[InternalAuthorizationHeaderFilter] {}", message);
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(status);
        return response.setComplete();
    }

    @Data
    public static class Config {
        private String baseMessage;
        private boolean preLogger;
    }
}
