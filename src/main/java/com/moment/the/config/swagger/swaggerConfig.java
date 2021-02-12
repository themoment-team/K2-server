package com.moment.the.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class swaggerConfig {
    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("학교가 불편한 순간")
                .description("the_moment when school is uncomfortable")
                .build();
    }
    @Bean
    public Docket commonApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("the_moment")
                .apiInfo(this.apiInfo())
                .select()
                .apis(RequestHandlerSelectors
                .basePackage("com.moment.the.controller"))
                .paths(PathSelectors.ant("/api/**"))
                .build();
    }
}
