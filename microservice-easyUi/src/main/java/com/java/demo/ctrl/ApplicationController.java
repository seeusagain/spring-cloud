package com.java.demo.ctrl;

import com.java.demo.entity.DemoEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by lu.xu on 2017/9/22.
 * TODO: 测试
 */

@Api(description = "默认接口")
@RestController
public class ApplicationController {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationController.class);
    
    @ApiOperation(value = "默认接口", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView defaultRequest(HttpServletRequest request, HttpServletResponse response) {
        logger.info("access index ");
        return  new ModelAndView("index");
    }

    @ApiOperation(value = "demo界面", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @RequestMapping(value = "/demoPage", method = RequestMethod.GET)
    public ModelAndView demoPage(HttpServletRequest request, HttpServletResponse response) {
        return  new ModelAndView("views/list");
    }

    
    @ApiOperation(value = "获取数据", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @RequestMapping(value = "/getDataList", method = RequestMethod.GET)
    public Object manualChangeDataSource(HttpServletRequest request, HttpServletResponse response) {
        Map map = new HashMap();
        map.put("total", 1230);
        List<DemoEntity> list = new ArrayList<>();
        for (int i = 0; i <= 10; i++) {
            DemoEntity entity = new DemoEntity();
            entity.setUserId(String.valueOf(i));
            entity.setName("testName" + i);
            entity.setAge(i);
            entity.setRemarks("test-remarks " + i);
            entity.setStatus("NORMAL");
            entity.setCreateDate(new Date());
            list.add(entity);
        }
        map.put("rows",list);
        return map;
    }

    
}
