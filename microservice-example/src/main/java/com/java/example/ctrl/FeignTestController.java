package com.java.example.ctrl;

import com.java.example.service.IFeignTestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(description = "feign远程调用其他组件测试")
@RestController
@RequestMapping("/feignTest")
public class FeignTestController {
    private static final Logger logger = LoggerFactory.getLogger(FeignTestController.class);

    @Resource
    private IFeignTestService feignTestService;

    @ApiOperation(value = "调用basic组件的登录方法", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({@ApiImplicitParam(name = "userName", value = "用户名", paramType = "query"),
            @ApiImplicitParam(name = "password", value = "密码", paramType = "query")})
    @PostMapping(value = "/test")
    public Object details(@RequestParam(value = "userName", required = false) String userName,
                          @RequestParam(value = "password", required = false) String password) {
        return this.feignTestService.feignRemoteTest(userName, password);
    }

}
