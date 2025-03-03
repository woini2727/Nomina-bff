package com.bff.nomina.nomina.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;


@Data
@Validated
@Configuration
@ConfigurationProperties("nomina")
public class Config {

    private NominaRepositoryConfig nominaRepositoryConfig;

    @Data
    public static class NominaRepositoryConfig {
        @NotEmpty
        private String url;

    }
}
