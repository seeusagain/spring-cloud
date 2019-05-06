package com.java.example.ctrl;

import com.java.dto.ResultMsg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lu.xu on 2018/3/16. TODO: 测试接口
 */
@Api(description = "测试接口")
@RestController
@RequestMapping("/example")
public class ApplicationController {

  private static final Logger logger = LoggerFactory.getLogger(ApplicationController.class);


  @ApiOperation(value = "测试接口", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @ApiImplicitParams({
      @ApiImplicitParam(name = "accessToken", value = "accessToken", paramType = "accessToken")})
  @GetMapping(value = "/test")
  public ResultMsg test(HttpServletRequest request, HttpServletResponse response) {
    return ResultMsg.ok("欢迎访问 microservice-example");
  }
}
