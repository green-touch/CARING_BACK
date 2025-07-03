package com.caring.user_service.common.service;

import com.caring.user_service.common.annotation.ManagerRoles;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.caring.user_service.common.consts.StaticVariable.REQUEST_HEADER_ROLES;

@Slf4j
public class RolesArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(ManagerRoles.class) != null &&
                (parameter.getParameterType().equals(String.class) || parameter.getParameterType().equals(List.class));
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        String rolesHeader = request.getHeader(REQUEST_HEADER_ROLES);
        return rolesHeader != null ? Arrays.asList(rolesHeader.split(",")) : Collections.emptyList();
    }
}
