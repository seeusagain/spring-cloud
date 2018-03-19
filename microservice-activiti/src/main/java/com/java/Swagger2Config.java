package com.java;

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
 * Created by lu.xu on 2017/12/18.
 * TODO:Swagger2 ui 配置信息
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {
    
    @Value("${spring.application.name}")
    private String applicationName;
    
    @Value("${server.context-path}")
    private String contextPath;

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("接口文档 for " + applicationName + contextPath)
                //            .description("API Description")
                //            .termsOfServiceUrl(" API terms of service")
                //            .contact("administrator")
                .version("1.0")
                .build();
    }

    @Bean
    public Docket createRestAPI() {
//        ParameterBuilder tokenPar = new ParameterBuilder();
//        List<Parameter> pars = new ArrayList<Parameter>();
//        tokenPar.name("token")
//            .defaultValue("user-eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbjAifQ.EzOtp4tB1dD7xTGWIc5Dlceoi7undj9ikhDdkuz23N_te3iLoE61nqSd-X-9hmC_ERIdKMXu62ZHbuV4vqWzhQ")
//            .description("令牌")
//            .modelRef(new ModelRef("string"))
//            .parameterType("header")
//            .required(false)
//            .build();
//        pars.add(tokenPar.build());
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage("com.java.activiti"))
                .paths(PathSelectors.any())
                .build()
//            .globalOperationParameters(pars)
                .apiInfo(apiInfo());
    }


}
