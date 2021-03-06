package com.kgc.kmall.swagger_demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author shkstart
 * @create 2020-12-24 15:05
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.kgc.kmall.swagger_demo.controller"))
                .paths(PathSelectors.any())
                .build();
    }


    private ApiInfo apiInfo(){
        return new ApiInfoBuilder().
                title("测试swagger大标题")
                .description("springboot Swagger")
                .version("1.0")
                .termsOfServiceUrl("http://localhost:9090")
                .build();
    }
}
