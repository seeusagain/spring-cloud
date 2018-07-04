package com.java.test.ctrl;

import com.java.dto.ResultMsg;
import com.java.test.service.IBusinessService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by lu.xu on 2018/7/4.
 * TODO:
 */
@Api(description = "测试业务接口")
@RestController
@RequestMapping("/business")
public class BusinessController {
    
    @Resource
    private IBusinessService businessService;
    
    @GetMapping(value = "/doBusiness")
    public ResultMsg doBusiness() {
        return this.businessService.doBusiness();
    }
}
