package com.java.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * create by:xulu TODO:
 */
@RestController
@RequestMapping("/test")
@Api(description = "test")
public class TestController {


  @ApiOperation(value = "默认接口", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @RequestMapping(value = "/", method = RequestMethod.GET)
  public Object def() {
    return "weelcome test";
  }

}
