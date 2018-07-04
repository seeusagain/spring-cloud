package com.java.test.ctrl;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.java.constants.AuthenticationConstats;
import com.java.dto.LogonUser;
import com.java.test.entity.UserInfo;
import com.java.test.interceptor.AuthenticationHolder;
import com.java.test.service.IAuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.java.dto.ResultMsg;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * Created by lu.xu on 2018/7/2.
 * TODO:用户登录、注销接口
 */

@Api(description = "用户登录、注销接口")
@RestController
public class AuthenticationController {
    
    @Resource
    private IAuthenticationService authenticationService;
    
    @ApiOperation(value = "登录", notes = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({@ApiImplicitParam(name = "userId", value = "账号", required = true, paramType = "query"),
        @ApiImplicitParam(name = "userPwd", value = "密码", required = true, paramType = "query")})
    @PostMapping(value = AuthenticationConstats.AUTHENTICATION_LOGIN_URL)
    public ResultMsg login(HttpServletRequest request, UserInfo userInfo) {
        ResultMsg resultMsg = this.authenticationService.login(userInfo);
        if (resultMsg.isSuccess()) {
            LogonUser logonUser = AuthenticationHolder.getUser();
            request.getSession().setAttribute(AuthenticationConstats.USER_INFO_SESSION_KEY, logonUser);
            request.getSession().setAttribute(AuthenticationConstats.USER_NAME_SESSION_KEY, logonUser.getDisplayName());
        }
        return resultMsg;
    }
    
    @ApiOperation(value = "登录界面", notes = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @GetMapping(value = AuthenticationConstats.AUTHENTICATION_LOGOUT_PAGE_URL)
    public ModelAndView loginPage(HttpServletRequest request) {
        return new ModelAndView("login");
    }
    
    @ApiOperation(value = "注销", notes = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @GetMapping(value = AuthenticationConstats.AUTHENTICATION_LOGOUT_URL)
    public ModelAndView logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return new ModelAndView("login");
    }
}
