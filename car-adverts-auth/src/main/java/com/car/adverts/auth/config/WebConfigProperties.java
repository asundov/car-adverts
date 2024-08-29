package com.car.adverts.auth.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Component
@ConfigurationProperties(prefix = "web")
@Getter
@Setter
public class WebConfigProperties {

    private Cors cors;

    @Getter
    @Setter
    public static class Cors {
        private String[] allowedOrigins;

        private String[] allowedMethods;

        private String[] allowedHeaders;

        private String[] exposedHeaders;

        private long maxAge;

        private boolean allowCredentials;
    }
}