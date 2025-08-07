package com.caring.apigateway_service.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

@Slf4j
@Component
@RequiredArgsConstructor
public class GatewaySignatureFilter extends AbstractGatewayFilterFactory<Object> {

    @Value("${token.secret-gateway}")
    private String gatewaySecret;

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            long timestamp = System.currentTimeMillis();

            if (gatewaySecret == null || gatewaySecret.isEmpty()) {
                log.error("token.secret-gateway is not configured in environment");
                return chain.filter(exchange); // 또는 에러 응답 처리
            }

            String signature = new HmacUtils(HmacAlgorithms.HMAC_SHA_256, gatewaySecret)
                    .hmacHex(String.valueOf(timestamp));

            ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
                    .header("X-Gateway-Timestamp", String.valueOf(timestamp))
                    .header("X-Gateway-Signature", signature)
                    .build();

            ServerWebExchange mutatedExchange = exchange.mutate().request(mutatedRequest).build();
            return chain.filter(mutatedExchange);
        };
    }
}
