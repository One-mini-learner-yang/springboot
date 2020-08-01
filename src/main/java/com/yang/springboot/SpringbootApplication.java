package com.yang.springboot;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ImportResource;
@EnableCaching
@EnableRabbit
@SpringBootApplication
public class SpringbootApplication {

    public static void main(String[] args) {
//        String version= SpringBootVersion.getVersion();
//        System.out.println(version);
        SpringApplication.run(SpringbootApplication.class, args);
    }

}
