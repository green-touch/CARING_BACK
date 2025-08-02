package com.caring.shelter_service.common.config;

import com.caring.shelter_service.common.consts.StaticVariable;
import com.caring.shelter_service.common.service.MicroServiceIpResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

import static com.caring.shelter_service.common.consts.StaticVariable.PERMIT_ALL_PATHS;
import static com.caring.shelter_service.common.consts.StaticVariable.SWAGGER_PATHS;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;


@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final MicroServiceIpResolver microServiceIpResolver;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        configureCorsAndSecurity(httpSecurity);
        configureAuth(httpSecurity);
        return httpSecurity.build();
    }
    private static void configureCorsAndSecurity(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .headers(
                        httpSecurityHeadersConfigurer ->
                                httpSecurityHeadersConfigurer.frameOptions(
                                        HeadersConfigurer.FrameOptionsConfig::disable
                                )
                )
                // stateless한 rest api 이므로 csrf 공격 옵션 비활성화
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(Customizer.withDefaults())
                .httpBasic(HttpBasicConfigurer::disable)
                .cors(Customizer.withDefaults())
                .sessionManagement(configurer -> configurer
                        .sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                );
    }

    private void configureAuth(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests(Customizer.withDefaults())
                .authorizeHttpRequests(authorizeRequest -> {
                    authorizeRequest
                            .requestMatchers("/", "/.well-known/**", "/css/**", "/*.ico",
                                    "/error", "/images/**").permitAll()
                            .requestMatchers(PERMIT_ALL_PATHS).permitAll()
                            .requestMatchers(SWAGGER_PATHS).permitAll()
                            .anyRequest().access((authentication, request) -> {
                                String clientIp = request.getRequest().getRemoteHost();
                                String gatewayIp = microServiceIpResolver.resolveGatewayIp();
                                log.info("client ip is = {} gateway ip is = {}", clientIp, gatewayIp);
                                boolean isAllowed = clientIp.equals(gatewayIp);
                                // TODO: 보안 설정에서 localhost(127.0.0.1) 허용은 개발 환경에만 적용해야함
                                if(clientIp.equals("127.0.0.1")) {
                                    isAllowed = true;
                                }

                                return new AuthorizationDecision(isAllowed);
                            });
                });
    }


//    private RequestMatcher[] authRelatedEndpoints() {
//        List<RequestMatcher> requestMatchers = List.of(
//                antMatcher("/v1/api/users"),
//                antMatcher("/v1/api/shelters/**"),
//                antMatcher(HttpMethod.GET, "/v1/api/managers/submissions"),
//                antMatcher(HttpMethod.POST, "/v1/api/managers/submissions/{uuid}/permission")
//        );
//        return requestMatchers.toArray(RequestMatcher[]::new);
//    }
}
