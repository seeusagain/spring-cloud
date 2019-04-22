package com.java.config;

import org.springframework.beans.factory.annotation.Value;
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
 * TODO:Swagger2 ui 配置信息
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {
    
    @Value("${spring.application.name}")
    private String applicationName;

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("测试")
            .version("1.0")
            .build();
    }
    
    @Bean
    public Docket createRestAPI() {
        return new Docket(DocumentationType.SWAGGER_2).select()
            .apis(RequestHandlerSelectors.basePackage("com.java"))
            .paths(PathSelectors.any())
            .build()
            //            .globalOperationParameters(pars)
            .apiInfo(apiInfo());
    }
    
}
