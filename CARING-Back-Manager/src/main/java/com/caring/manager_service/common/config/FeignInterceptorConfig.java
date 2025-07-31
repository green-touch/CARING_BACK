package com.caring.manager_service.common.config;

import feign.RequestInterceptor;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class FeignInterceptorConfig {

    private final Environment env;

    public FeignInterceptorConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public RequestInterceptor internalAuthHeaderInjector() {
        return template -> {
            long timestamp = System.currentTimeMillis();
            String timestampStr = String.valueOf(timestamp);

            String secret = env.getProperty("token.secret-internal");
            if (secret == null) {
                throw new IllegalStateException("Missing property: token.secret-internal");
            }

            String signature = new HmacUtils(HmacAlgorithms.HMAC_SHA_256, secret)
                    .hmacHex(timestampStr);

            template.header("X-Internal-Timestamp", timestampStr);
            template.header("X-Internal-Signature", signature);
        };
    }
}
