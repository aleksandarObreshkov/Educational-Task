package com.example.backend;

import com.example.backend.resolver.QueryResolver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@EntityScan(value = "model")
@ComponentScan(value = "repositories")
@ComponentScan(basePackageClasses = QueryResolver.class)
public class BackendServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(BackendServerApplication.class, args);
    }
}
