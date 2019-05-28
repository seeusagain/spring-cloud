package com.java.ctrl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "测试接口")
@RestController
public class ApplicationController {

  private static final Logger logger = LoggerFactory.getLogger(ApplicationController.class);

  @ApiOperation(value = "测试接口", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @GetMapping(value = "/test")
  public String test(HttpServletRequest request, HttpServletResponse response) {
    return "microservice-mybatis-plus";
  }
}
