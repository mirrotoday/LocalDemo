package com.example.localdemo;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@EnableSwagger2WebMvc
@EnableScheduling
@SpringBootApplication
@MapperScan(basePackages = {"com.example.localdemo.mapper"})
public class LocalDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(LocalDemoApplication.class, args);
    }

}
