package com.duman.coursecatalog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class CourseCatalogApplication {

    public static void main(String[] args) {
        SpringApplication.run(CourseCatalogApplication.class, args);
    }

}
