package com.example.backend;

import com.example.backend.resolver.QueryResolver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EntityScan(value = "model")
// TODO That's why it's a good idea to have a base package name that's shared across all of your modules - com.alex, for
// example. You'd then be able to have @ComponentScan("com.alex") and that would scan all of your modules for Spring
// bean definitions.
// TODO Also, it's a good idea to use marker classes instead of hardcoded package names, because you could decide to
// rename the package and forget to rename it here in this annotation.
// Example of a marker class:
//
// package com.alex;
//
// public class PackageMarker {
// }
//
// You can then use it here like this:
// @ComponentScan(basePackageClasses = PackageMarker.class)
// If you then rename the package, PackageMarker will be automatically refactored by your IDE and moved to the new
// package, therefore this annotation will also keep "working".
@ComponentScan(value = "repositories")
@ComponentScan(basePackageClasses = QueryResolver.class)
public class BackendServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendServerApplication.class, args);
    }

}
