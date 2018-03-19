package com.java.example.ctrl;

import com.java.example.service.IHystrixTestService;
import com.java.dto.ResultMsg;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(description = "熔断测试")
@RestController
@RequestMapping("/hystrixTest")
public class HystrixTestController {
    private static final Logger logger = LoggerFactory.getLogger(HystrixTestController.class);
    
    @Resource
    private IHystrixTestService hystrixTestService;
    
    @ApiOperation(value = "降级测试", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @GetMapping(value = "/fallback")
    public Object fallback() {
        return this.hystrixTestService.fallback();
    }
    
    @ApiOperation(value = "熔断测试", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @GetMapping(value = "/circuitBreakerTest")
    public Object circuitBreakerTest() throws Exception {
        return this.hystrixTestService.circuitBreakerTest();
    }
    
}
