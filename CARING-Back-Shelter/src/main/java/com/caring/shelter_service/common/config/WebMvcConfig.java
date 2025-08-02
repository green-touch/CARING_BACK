package com.caring.shelter_service.common.config;



import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.env.Environment;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final Environment env;

//    @Override
//    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
//        argumentResolvers.add(new MemberCodeArgumentResolver());
//        argumentResolvers.add(new RolesArgumentResolver());
//    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToListConverter());
    }

    private static class StringToListConverter implements Converter<String, List<String>> {
        @Override
        public List<String> convert(String source) {
            return Arrays.asList(source.split(","));
        }
    }

    //CORS setting
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                .addMapping("/**") //CORS 적용할 URL 패턴
                .allowedOriginPatterns("http://localhost:8000", env.getProperty("deploy.url")) //자원 공유 오리진 지정
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS") //요청 허용 메서드
                .allowedHeaders("*") //요청 허용 헤더
                .allowCredentials(true); //요청 허용 쿠키
    }
}
