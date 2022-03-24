package com.zhejianglab.spring3web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.zhejianglab"})
@MapperScan("com.zhejianglab.spring3dao.mapper")
public class Spring3WebApplication {

    public static void main(String[] args) {
        SpringApplication.run(Spring3WebApplication.class, args);
    }

}
