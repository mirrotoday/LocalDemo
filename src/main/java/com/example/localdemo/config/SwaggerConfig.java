//package com.example.localdemo.config;
//
///**
// * @author xieteng
// * @date 2023/3/12 18:59
// * @description TODO
// */
//
//import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.oas.annotations.EnableOpenApi;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.service.Contact;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//
///**
// * swagger配置
// */
//@Configuration
//@EnableOpenApi
//public class SwaggerConfig {
//
//    Boolean swaggerEnabled = true;
//
//    @Bean
//    public Docket createRestApi() {
//        return new Docket(DocumentationType.OAS_30)
//                .apiInfo(apiInfo())
//                // 是否开启
//                .enable(swaggerEnabled)//true
//                .select()
//                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
//                .paths(PathSelectors.any())
//                .build();
//    }
//    @Bean
//    public Docket monitorDocket() {
//        return new Docket(DocumentationType.OAS_30)
//                .groupName("系统监控")
//                .apiInfo(apiInfo())
//                .select()
//                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
//                .paths(PathSelectors.any())
//                .build();
//    }
//
//    private ApiInfo apiInfo() {
//        return new ApiInfoBuilder()
//                .title("资源中心swagger业务")
//                //创建人
//                .contact(new Contact("Siyu", "http://www.baidu.com", "xpossess@gmail.com"))
//                .version("3.0")
//                .description("Siyu集成Knife4j+Swagger3")
//                .build();
//    }
//}
//
