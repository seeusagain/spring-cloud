package com.java;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@EnableDiscoveryClient
@SpringBootApplication
@EnableCaching
/**
 * 本地直接运行
 */
public class ApplicationFile {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationFile.class, args);
    }
}

/**
 * 不使用内置tomcat运行，需SpringBootServletInitializer加载
 */
//public class ApplicationFile  extends SpringBootServletInitializer {
//    private static final Logger logger = LoggerFactory.getLogger(ApplicationFile.class);
//
//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//        logger.info(">> fileserver系统初始化...");
//        return application.sources(ApplicationFile.class);
//    }
//
//    public static void main(String[] args) {
//        SpringApplication.run(ApplicationFile.class, args);
//    }
//}
