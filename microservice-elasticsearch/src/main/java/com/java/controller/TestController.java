package com.java.controller;

import com.java.dto.ResultMsg2;
import com.java.service.ITestService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * create by:xulu TODO:
 */
@RestController
@RequestMapping("/testEs")
public class TestController {

  @Autowired
  private ITestService testService;

  @ApiOperation(value = "测试属性查询", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @ApiImplicitParams({
      @ApiImplicitParam(name = "userName", value = "姓名", paramType = "query"),
      @ApiImplicitParam(name = "contractNumber", value = "合同号", paramType = "query")})
  @RequestMapping(value = "/query", method = RequestMethod.POST)
  public ResultMsg2 query(
      @RequestParam(value = "userName", required = false) String userName,
      @RequestParam(value = "contractNumber", required = false) String contractNumber) {
    return this.testService.query(userName, contractNumber);
  }

  @ApiOperation(value = "测试ID查询", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @ApiImplicitParams({
      @ApiImplicitParam(name = "esId", value = "esId", paramType = "query")})
  @RequestMapping(value = "/queryById", method = RequestMethod.POST)
  public ResultMsg2 queryById(
      @RequestParam(value = "esId", required = true) String esId) {
    return this.testService.queryById(esId);
  }

}
