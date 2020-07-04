package com.piggy.quincy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class QuincyApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuincyApplication.class, args);
    }

}