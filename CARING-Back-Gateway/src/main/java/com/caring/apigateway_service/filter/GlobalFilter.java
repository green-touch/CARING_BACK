package com.caring.apigateway_service.filter;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class GlobalFilter extends AbstractGatewayFilterFactory<GlobalFilter.Config> {

    private final Environment env;

    public GlobalFilter(Environment env) {
        super(Config.class);
        this.env = env;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            logPreFilter(config, request);

            ServerWebExchange mutatedExchange;

            if (config.isAddGateWaySignature()) {
                long timestamp = System.currentTimeMillis();

                String gatewaySecret = env.getProperty("token.secret-gateway");
                if (gatewaySecret == null) {
                    log.error("token.secret-gateway is not configured in environment");
                    return chain.filter(exchange); // 혹은 예외 처리
                }

                String signature = new HmacUtils(HmacAlgorithms.HMAC_SHA_256, gatewaySecret)
                        .hmacHex(String.valueOf(timestamp));

                ServerHttpRequest mutatedRequest = request.mutate()
                        .header("X-Gateway-Timestamp", String.valueOf(timestamp))
                        .header("X-Gateway-Signature", signature)
                        .build();

                mutatedExchange = exchange.mutate().request(mutatedRequest).build();
            } else {
                mutatedExchange = exchange;
            }

            return chain.filter(mutatedExchange)
                    .doOnError(throwable ->
                            log.error("Error occurred during request processing: {}", throwable.getMessage(), throwable)
                    )
                    .then(Mono.fromRunnable(() -> {
                        ServerHttpResponse response = mutatedExchange.getResponse();
                        logPostFilter(config, response);
                    }));
        };
    }

    private void logPreFilter(Config config, ServerHttpRequest request) {
        if (config.isPreLogger()) {
            log.info("Global Filter Pre-Logger: BaseMessage: {}, Request ID: {}", config.getBaseMessage(), request.getId());
        }
    }

    private void logPostFilter(Config config, ServerHttpResponse response) {
        if (config.isPostLogger()) {
            log.info("Global Filter Post-Logger: Response Status Code: {}", response.getStatusCode());
        }
    }

    @Data
    public static class Config {
        private String baseMessage;
        private boolean preLogger;
        private boolean postLogger;
        private boolean addGateWaySignature;
    }
}
