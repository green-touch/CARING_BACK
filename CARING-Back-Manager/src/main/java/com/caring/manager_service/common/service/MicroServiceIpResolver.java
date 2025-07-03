package com.caring.manager_service.common.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Component;

import static com.caring.manager_service.common.consts.StaticVariable.GATEWAY_SERVICE_APPLICATION_NAME;

@Component
@RequiredArgsConstructor
public class MicroServiceIpResolver {

    private final LoadBalancerClient loadBalancerClient;

    public String resolveGatewayIp() {
        ServiceInstance instance = loadBalancerClient.choose(GATEWAY_SERVICE_APPLICATION_NAME); // 서비스 선택
        if (instance == null) {
            throw new RuntimeException("No instances of gateway-service found in LoadBalancer");
        }
        return instance.getHost(); // 선택된 인스턴스의 호스트(IP)
    }
}
