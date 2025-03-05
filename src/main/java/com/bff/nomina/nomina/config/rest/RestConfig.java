package com.bff.nomina.nomina.config.rest;

import com.bff.nomina.nomina.adapter.rest.handler.RestTemplateErrorHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class RestConfig {

    @Bean
    public RestTemplate getRestTemplate(
            final RestTemplateBuilder restTemplateBuilder,
            @Value("${rest.client.connect-timeout}") final int connectTimeout,
            @Value("${rest.client.read-timeout}") final int readTimeout,
            final ObjectMapper objectMapper
    ) {
        return restTemplateBuilder.setConnectTimeout(Duration.ofMillis(connectTimeout))
                .setReadTimeout(Duration.ofMillis(readTimeout))
                .interceptors(new LogRestTemplateInterceptor())
                .requestFactory(() -> new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()))
                .errorHandler(new RestTemplateErrorHandler(objectMapper))
                .build();
    }

}
