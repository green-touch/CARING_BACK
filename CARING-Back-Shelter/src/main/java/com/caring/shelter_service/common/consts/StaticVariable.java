package com.caring.shelter_service.common.consts;

public class StaticVariable {

    public final static String USER_MEMBER_CODE_PRESET = "CRU#";
    public final static String GATEWAY_SERVICE_APPLICATION_NAME = "api-gateway-service";
    public final static String REQUEST_HEADER_MEMBER_CODE = "member-code";
    public final static String REQUEST_HEADER_ROLES = "roles";
    public static final String[] PERMIT_ALL_PATHS = {
            "/health_check", "/actuator/**", "/welcome", "/v1/api/access/**"
    };

    public static final String[] SWAGGER_PATHS = {
            "/swagger-ui/**", "/swagger-ui", "/swagger-ui.html",
            "/swagger/**", "/swagger-resources/**", "/v3/api-docs/**", "/profile"
    };

}
