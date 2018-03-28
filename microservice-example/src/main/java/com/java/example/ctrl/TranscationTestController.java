package com.java.example.ctrl;

import com.java.dto.ResultMsg;
import com.java.example.service.ITranscationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by lu.xu on 2018/3/28.
 * TODO:
 */

@Api(description = "事务测试")
@RestController
@RequestMapping("/transcationTest")
public class TranscationTestController {

    @Resource
    private ITranscationService transcationService;

    @ApiOperation(value = "测试事务回滚", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @GetMapping(value = "/testRollback")
    public ResultMsg testRollback() throws Exception {
        return this.transcationService.testRollback();
    }
}
