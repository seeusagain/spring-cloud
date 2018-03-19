package com.java;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.scheduling.annotation.EnableAsync;


@EnableDiscoveryClient
@SpringBootApplication
@EnableCaching
@EnableAsync
/**mybatis 扫描mapper接口文件*/
@MapperScan("com.java.basic.dao")
public class ApplicationBasic {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationBasic.class, args);
    }
}

//public class ApplicationBasic  extends SpringBootServletInitializer {
//    private static final Logger logger = LoggerFactory.getLogger(ApplicationBasic.class);
//
//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//        logger.info(">> basic系统初始化...");
//        return application.sources(ApplicationBasic.class);
//    }
//
//    public static void main(String[] args) {
//        SpringApplication.run(ApplicationBasic.class, args);
//    }
//}
