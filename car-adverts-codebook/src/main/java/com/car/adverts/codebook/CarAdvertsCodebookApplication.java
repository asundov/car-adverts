package com.car.adverts.codebook;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication(scanBasePackages = {"com.car.adverts.common", "com.car.adverts.codebook"})
public class CarAdvertsCodebookApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarAdvertsCodebookApplication.class, args);
    }

}
