package com.caring.user_service.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

@EnableAsync
@EnableScheduling
@Configuration
public class AsyncConfig {

    @Bean("detectorPool")
    public org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor detectorPool() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(8);
        taskExecutor.setMaxPoolSize(32);
        taskExecutor.setQueueCapacity(1000);
        taskExecutor.setThreadNamePrefix("detect-");
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        taskExecutor.initialize();
        return taskExecutor;
    }
}
