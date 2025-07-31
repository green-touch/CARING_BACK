package com.caring.manager_service.common.config;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.List;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final Environment env;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        configureCorsAndSecurity(httpSecurity);
        configureAuth(httpSecurity);
        return httpSecurity.build();
    }

    private static void configureCorsAndSecurity(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .headers(config -> config.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(Customizer.withDefaults())
                .httpBasic(HttpBasicConfigurer::disable)
                .cors(Customizer.withDefaults())
                .sessionManagement(config -> config.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
    }

    private void configureAuth(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests(Customizer.withDefaults())
                .authorizeHttpRequests(authorizeRequest -> {
                    authorizeRequest
                            .requestMatchers("/", "/.well-known/**", "/css/**", "/*.ico", "/error", "/images/**").permitAll()
                            .requestMatchers(permitAllRequest()).permitAll()
                            .requestMatchers(additionalSwaggerRequests()).permitAll()
                            .requestMatchers(internalRequests()).access((authentication, request) ->
                                    verifyHmacAuthorization(request.getRequest(), "X-Internal-Timestamp", "X-Internal-Signature", "token.secret-internal")
                            )
                            .anyRequest().access((authentication, request) ->
                                    verifyHmacAuthorization(request.getRequest(), "X-Gateway-Timestamp", "X-Gateway-Signature", "token.secret-gateway")
                            );
                });
    }

    private AuthorizationDecision verifyHmacAuthorization(HttpServletRequest request, String timestampHeader, String signatureHeader, String secretKeyName) {
        String timestamp = request.getHeader(timestampHeader);
        String signature = request.getHeader(signatureHeader);

        if (timestamp == null || signature == null) {
            log.warn("Missing header(s): [{}]={}, [{}]={}", timestampHeader, timestamp, signatureHeader, signature);
            return new AuthorizationDecision(false);
        }

        String secret = env.getProperty(secretKeyName);
        if (secret == null) {
            log.error("Secret not configured for key: {}", secretKeyName);
            return new AuthorizationDecision(false);
        }

        String expectedSignature = new HmacUtils(HmacAlgorithms.HMAC_SHA_256, secret).hmacHex(timestamp);
        boolean isValid = expectedSignature.equals(signature);

        try {
            long sentTime = Long.parseLong(timestamp);
            long now = System.currentTimeMillis();
            if (Math.abs(now - sentTime) > 5 * 60 * 1000) {
                log.warn("Expired HMAC signature: sent={}, now={}", sentTime, now);
                isValid = false;
            }
        } catch (NumberFormatException e) {
            log.warn("Invalid timestamp format: {}", timestamp);
            isValid = false;
        }

        return new AuthorizationDecision(isValid);
    }

    private RequestMatcher[] permitAllRequest() {
        return List.of(
                antMatcher("/health_check"),
                antMatcher("/actuator/**"),
                antMatcher("/welcome"),
                antMatcher("/v1/api/access/**")
        ).toArray(RequestMatcher[]::new);
    }

    private RequestMatcher[] additionalSwaggerRequests() {
        return List.of(
                antMatcher("/swagger-ui/**"),
                antMatcher("/swagger-ui"),
                antMatcher("/swagger-ui.html"),
                antMatcher("/swagger/**"),
                antMatcher("/swagger-resources/**"),
                antMatcher("/v3/api-docs/**"),
                antMatcher("/profile")
        ).toArray(RequestMatcher[]::new);
    }

    private RequestMatcher[] internalRequests() {
        return List.of(
                antMatcher("/internal/**")
        ).toArray(RequestMatcher[]::new);
    }
}
