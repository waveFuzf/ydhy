package com.example.ydhy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableAutoConfiguration
@MapperScan("com.example.ydhy.dao")
@EnableSwagger2
@ComponentScan
public class YdhyWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(YdhyWebApplication.class, args);
    }
}
