package com.hodor.apimail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ApiMailApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiMailApplication.class, args);
    }

}
