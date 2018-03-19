package com.java.activiti.business.ctrl;

import com.java.activiti.business.service.IRunTaskManagementService;
import com.java.dto.PageInfo;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by lu.xu on 2018/3/2.
 * TODO:运行中流程管理接口
 */

@RestController
@RequestMapping("/task")
@Api(description = "工作流-运行中流程管理接口")
public class RunTaskManagementController {
    
    private static final Logger logger = LoggerFactory.getLogger(TaskQueryController.class);
    
    @Resource
    private IRunTaskManagementService runTaskManagementService;
    
    @ApiOperation(value = "查询运行中的工作流", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({@ApiImplicitParam(name = "page", value = "开始页码", paramType = "query"),
        @ApiImplicitParam(name = "size", value = "每页显示数量，最多不超过20", paramType = "query"),
        @ApiImplicitParam(name = "keywords", value = "模糊查询关键字", paramType = "query")})
    @PostMapping("/queryRunTaskList")
    public Object queryRunTaskList(HttpServletRequest request, HttpServletResponse response,
        @RequestParam(value = "keywords", required = false) String keywords, PageInfo pageInfo) {
        return this.runTaskManagementService.queryRunTaskList(pageInfo, keywords);
    }
    
    @ApiOperation(value = "更改工作流受理人", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({@ApiImplicitParam(name = "accessToken", value = "访问令牌", paramType = "query"),
        @ApiImplicitParam(name = "taskId", value = "taskId", paramType = "query"),
        @ApiImplicitParam(name = "assigneeUserId", value = "受理人userId", paramType = "query")})
    @PostMapping("/updateAssignee")
    public Object updateAssignee(HttpServletRequest request, HttpServletResponse response,
        @RequestParam(value = "accessToken", required = false) String accessToken,
        @RequestParam(value = "taskId", required = false) String taskId,
        @RequestParam(value = "assigneeUserId", required = false) String assigneeUserId) {
        return this.runTaskManagementService.updateAssignee(accessToken, taskId, assigneeUserId);
    }
    
}
