package com.example.backend.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.http.HttpStatus;

@SpringBootApplication
@EntityScan(value = "model")
public class BackendServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(BackendServerApplication.class, args);
    }

}
