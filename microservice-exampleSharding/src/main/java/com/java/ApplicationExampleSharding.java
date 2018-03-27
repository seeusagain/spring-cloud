package com.java;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@SpringBootApplication
@EnableCaching
@EnableDiscoveryClient
@EnableFeignClients
public class ApplicationExampleSharding {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationExampleSharding.class, args);
    }
}

/**
 * 不使用内置tomcat运行，需SpringBootServletInitializer加载
 */
//public class ApplicationExampleSharding  extends SpringBootServletInitializer {
//    private static final Logger logger = LoggerFactory.getLogger(ApplicationExampleSharding.class);
//
//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//        logger.info(">> basicOperation apply 系统初始化...");
//        return application.sources(ApplicationExampleSharding.class);
//    }
//
//    public static void main(String[] args) {
//        SpringApplication.run(ApplicationExampleSharding.class, args);
//    }
//}
