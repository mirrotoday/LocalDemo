package com.example.localdemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
//import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;



//@EnableSwagger2WebMvc
@EnableScheduling
@SpringBootApplication
//@MapperScan({"com.gitee.sunchenbin.mybatis.actable.dao.*"})//固定的
@MapperScan({"com.example.localdemo.mapper"})//固定的
//@ComponentScan("com.gitee.sunchenbin.mybatis.actable.manager.*")//固定的
public class LocalDemoApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(LocalDemoApplication.class, args);

    }

}
