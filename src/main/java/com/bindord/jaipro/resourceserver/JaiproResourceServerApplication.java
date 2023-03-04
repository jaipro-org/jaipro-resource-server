package com.bindord.jaipro.resourceserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(lazyInit = true)
@SpringBootApplication
public class JaiproResourceServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(JaiproResourceServerApplication.class, args);
    }
}
