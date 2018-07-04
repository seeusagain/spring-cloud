package com.java;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApplicationInterceptor {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationInterceptor.class, args);
    }
}

/**
 * 不使用内置tomcat运行，需SpringBootServletInitializer加载
 */
//public class Application extends SpringBootServletInitializer {
//    private static final Logger logger = LoggerFactory.getLogger(ApplicationInterceptor.class);
//
//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//        return application.sources(ApplicationInterceptor.class);
//    }
//
//    public static void main(String[] args) {
//        SpringApplication.run(ApplicationInterceptor.class, args);
//    }
//}
