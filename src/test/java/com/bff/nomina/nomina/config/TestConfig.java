package com.bff.nomina.nomina.config;

import com.bff.nomina.nomina.adapter.rest.handler.RestTemplateErrorHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@TestConfiguration
public class TestConfig {

    /**
     * SCOPE_PROTOTYPE is used so that each adapter instance can customize the error handling.
     *
     * @param restTemplateBuilder Builder
     * @return RestTemplate
     */
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public RestTemplate getRestTemplate(
            final RestTemplateBuilder restTemplateBuilder,
            @Value("${rest.client.connect-timeout}") final int connectTimeout,
            @Value("${rest.client.read-timeout}") final int readTimeout,
            final ObjectMapper objectMapper
    ) {
        return restTemplateBuilder
                .setConnectTimeout(Duration.ofMillis(connectTimeout))
                .setReadTimeout(Duration.ofMillis(readTimeout))
                .requestFactory(() -> new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()))
                .errorHandler(new RestTemplateErrorHandler(objectMapper))
                .build();
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Config getConfig() {
        final String templateLocation = "src/test/resources";
        final Config config = new Config();

        final Config.NominaRepositoryConfig nominaRepositoryConfig = new Config.NominaRepositoryConfig();
        nominaRepositoryConfig.setUrl("http://localhost:4567");
        config.setNominaRepositoryConfig(nominaRepositoryConfig);

        return config;
    }
}
