package com.car.adverts.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.car.adverts.common", "com.car.adverts.auth"})
public class CarAdvertsAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarAdvertsAuthApplication.class, args);
    }

}
