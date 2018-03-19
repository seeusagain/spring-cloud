package com.java;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
@EnableDiscoveryClient
/**
 * 启用feign远程调用
 */
@EnableFeignClients
/**
 * 本地直接运行
 */
public class ApplicationEmail {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationEmail.class, args);
    }
}

/**
 * 不使用内置tomcat运行，需SpringBootServletInitializer加载
 */
//public class ApplicationEmail extends SpringBootServletInitializer {
//    private static final Logger logger = LoggerFactory.getLogger(ApplicationEmail.class);
//
//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//        logger.info(">> 系统初始化...");
//        return application.sources(ApplicationEmail.class);
//    }
//
//    public static void main(String[] args) {
//        SpringApplication.run(ApplicationEmail.class, args);
//    }
//}
