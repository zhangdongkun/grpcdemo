package com.itlaoqi.spbservereureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SpbServerEurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpbServerEurekaApplication.class, args);
    }

}
