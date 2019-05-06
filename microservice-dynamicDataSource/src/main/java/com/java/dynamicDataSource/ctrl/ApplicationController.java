package com.java.dynamicDataSource.ctrl;

import com.java.dto.ResultMsg;
import com.java.dynamicDataSource.service.IDynamicTestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lu.xu on 2017/9/22. TODO: 测试
 */

@Api(description = "默认接口")
@RestController
public class ApplicationController {

  private static final Logger logger = LoggerFactory.getLogger(ApplicationController.class);

  @Resource
  private IDynamicTestService dynamicTestService;

  @ApiOperation(value = "默认接口", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @RequestMapping(value = "/", method = RequestMethod.GET)
  public ResultMsg defaultRequest(HttpServletRequest request, HttpServletResponse response) {
    return   ResultMsg.ok("欢迎访问动态数据源测试");
  }

  @ApiOperation(value = "手动切换数据源", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @RequestMapping(value = "/manualChangeDataSource", method = RequestMethod.GET)
  public ResultMsg manualChangeDataSource(HttpServletRequest request,
      HttpServletResponse response) {
    return this.dynamicTestService.manualChangeDataSource();
  }

  @ApiOperation(value = "注解切换数据源", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @RequestMapping(value = "/autoChangeDataSource", method = RequestMethod.GET)
  public ResultMsg autoChangeDataSource(HttpServletRequest request, HttpServletResponse response) {
    return this.dynamicTestService.autoChangeDataSource();
  }

}
