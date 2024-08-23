package com.car.adverts.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "web")
@Getter
@Setter
public class WebConfigProperties {

    private Cors cors;

    // public WebConfigProperties(Cors cors) {
//        this.cors = cors;
//    }

    //public Cors getCors() {
//        return cors;
//    }

    @Getter
    @Setter
    public static class Cors {
        private String[] allowedOrigins;

        private String[] allowedMethods;

        private String[] allowedHeaders;

        private String[] exposedHeaders;

        private long maxAge;

        private boolean allowCredentials;

//        public Cors(String[] allowedOrigins, String[] allowedMethods, long maxAge,
//                     String[] allowedHeaders, String[] exposedHeaders) {
//            this.allowedOrigins = allowedOrigins;
//            this.allowedMethods = allowedMethods;
//            this.maxAge = maxAge;
//            this.allowedHeaders = allowedHeaders;
//            this.exposedHeaders = exposedHeaders;
//        }

//        public String[] getAllowedOrigins() {
//            return allowedOrigins;
//        }
//
//        public String[] getAllowedMethods() {
//            return allowedMethods;
//        }
//
//        public long getMaxAge() {
//            return maxAge;
//        }
//
//        public String[] getAllowedHeaders() {
//            return allowedHeaders;
//        }
//
//        public String[] getExposedHeaders() {
//            return exposedHeaders;
//        }
    }
}