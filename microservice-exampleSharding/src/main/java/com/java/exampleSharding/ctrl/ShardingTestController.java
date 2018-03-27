package com.java.exampleSharding.ctrl;

import com.java.dto.ResultMsg;
import com.java.exampleSharding.service.IShardingTestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(description = "分片测试接口")
@RestController
@RequestMapping("/shardingTest")
public class ShardingTestController {
    private static final Logger logger = LoggerFactory.getLogger(ShardingTestController.class);
    
    @Resource
    private IShardingTestService shardingTestService;
    
    @ApiOperation(value = "添加数据", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @GetMapping(value = "/add")
    public ResultMsg add(HttpServletRequest request, HttpServletResponse response) {
        return this.shardingTestService.add();
    }
}
