package com.example.ydhy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.stereotype.Component;

@SpringBootApplication
@EnableAutoConfiguration
@MapperScan("com.example.ydhy.dao")
public class YdhyWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(YdhyWebApplication.class, args);
    }
}
