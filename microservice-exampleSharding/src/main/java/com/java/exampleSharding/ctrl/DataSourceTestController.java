package com.java.exampleSharding.ctrl;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.java.exampleSharding.service.IDataSourceTestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.java.dto.ResultMsg;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description = "测试默认数据源以及数据源切换")
@RestController
@RequestMapping("/dataSourceTest")
public class DataSourceTestController {
    private static final Logger logger = LoggerFactory.getLogger(DataSourceTestController.class);
    
    @Resource
    private IDataSourceTestService dataSourceTestService;
    
    @ApiOperation(value = "默认数据源测试", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PostMapping(value = "/defaultDataSourceTest")
    public ResultMsg defaultDataSourceTest(HttpServletRequest request, HttpServletResponse response) {
        return this.dataSourceTestService.defaultDataSourceTest();
    }
    
    @ApiOperation(value = "切换数据源测试", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PostMapping(value = "/switchDataSourceTest")
    public ResultMsg switchDataSourceTest(HttpServletRequest request, HttpServletResponse response) {
        return this.dataSourceTestService.switchDataSourceTest();
    }
    
}
