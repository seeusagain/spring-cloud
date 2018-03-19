package com.java.email.ctrl;

import com.java.dto.ResultMsg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by lu.xu on 2017/9/22.
 * TODO: 测试
 */

@Api(description = "测试服务接口")
@RestController
public class ApplicationController {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationController.class);
    
    @ApiOperation(value = "默认访问测试接口", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Object defaultRequest(HttpServletRequest request, HttpServletResponse response) {
        return new ResultMsg(true, "aom boot Email 服务");
    }
    
    @RequestMapping(value = "/jump/{modelName}", method = RequestMethod.GET)
    public ModelAndView jump(HttpServletRequest request, HttpServletResponse response,
        @PathVariable(name = "modelName", required = false) String modelName) {
        return new ModelAndView(modelName);
    }
    
}
