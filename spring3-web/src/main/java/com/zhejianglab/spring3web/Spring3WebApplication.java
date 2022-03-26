package com.zhejianglab.spring3web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.zhejianglab"})
public class Spring3WebApplication {
    public static void main(String[] args) {
        SpringApplication.run(Spring3WebApplication.class, args);
    }
}
