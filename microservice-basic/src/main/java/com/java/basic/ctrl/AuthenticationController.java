package com.java.basic.ctrl;

import com.java.basic.service.IAuthenticationService;
import com.java.dto.ResultMsg;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 */
@Api(description = "登录、注销接口")
@RestController
@RequestMapping("/authentication")
public class AuthenticationController {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
    
    @Resource
    private IAuthenticationService loginService;
    
    /**
     * 登录
     *
     * @param request
     * @param response
     * @param userName
     * @param password
     * @return
     */
    @ApiOperation(value = "登录", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({@ApiImplicitParam(name = "userName", value = "用户名", paramType = "query"),
        @ApiImplicitParam(name = "password", value = "用户密码", paramType = "query")})
    @PostMapping(value = "/login")
    public ResultMsg login(HttpServletRequest request, HttpServletResponse response,
        @RequestParam(value = "userName", required = false) String userName,
        @RequestParam(value = "password", required = false) String password) {
        return this.loginService.login(userName, password);
    }
    
    /**
     * 登出
     *
     * @param request
     * @param response
     * @return
     */
    @ApiOperation(value = "注销", notes = "需要accessToken参数", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({@ApiImplicitParam(name = "accessToken", value = "", paramType = "query")})
    @PostMapping(value = "/logout")
    public ResultMsg logout(HttpServletRequest request, HttpServletResponse response,
        @RequestParam(value = "accessToken", required = false) String accessToken) {
        return this.loginService.logout(accessToken);
    }
    
    @ApiOperation(value = "此接口睡眠3s返回数据", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @GetMapping(value = "/sleep")
    public String sleep() throws Exception {
        logger.info("开始执行basic sleep方法，线程将睡眠3s");
        Thread.sleep(3000);
        return "basic sleep 3s over...";
    }
}
