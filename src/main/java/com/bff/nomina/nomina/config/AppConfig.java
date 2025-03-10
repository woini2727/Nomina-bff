package com.bff.nomina.nomina.config;

import com.bff.nomina.nomina.config.async.AsyncConfig;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.cloud.sleuth.instrument.async.LazyTraceExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.Executor;

@Configuration
public class AppConfig implements WebMvcConfigurer {
    private static final int CORE_POOL_SIZE = 20;
    private static final int MAX_POOL_SIZE = 1000;
    private static final String ASYNC_PREFIX = "async-";
    private static final boolean WAIT_FOR_TASK_TO_COMPLETE_ON_SHUTDOWN = true;

    private final BeanFactory beanFactory;

    public AppConfig(
            BeanFactory beanFactory
    ) {
        this.beanFactory = beanFactory;
    }

    @Bean("asyncExecutor")
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(CORE_POOL_SIZE);
        executor.setMaxPoolSize(MAX_POOL_SIZE);
        executor.setWaitForTasksToCompleteOnShutdown(WAIT_FOR_TASK_TO_COMPLETE_ON_SHUTDOWN);
        executor.setThreadNamePrefix(ASYNC_PREFIX);
        executor.initialize();

        return new LazyTraceExecutor(beanFactory, executor);
    }

}
