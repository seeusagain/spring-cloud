package com.java.activiti.business.ctrl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.java.activiti.business.enums.CsmFlowTaskStatusEnums;
import com.java.activiti.business.enums.WorkflowTaskTypeEnums;
import com.java.activiti.business.service.ITaskQueryService;
import com.java.dto.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * Created by lu.xu on 2017/9/28.
 * TODO:
 */
@RestController
@RequestMapping("/task")
@Api(description = "工作流-查询任务接口")
public class TaskQueryController {
    private static final Logger logger = LoggerFactory.getLogger(TaskQueryController.class);
    
    @Resource
    private ITaskQueryService taskQueryService;
    
    @ApiOperation(value = "工作流-我的空间查询", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({@ApiImplicitParam(name = "accessToken", value = "用户令牌", paramType = "query"),
        @ApiImplicitParam(name = "taskType", value = "任务类型（待办、我参与、我发起）， 查看：/queryTaskType 接口", paramType = "query"),
        @ApiImplicitParam(name = "taskStatus", value = "任务状态（进行中、已完成），查看：/queryTaskStatus 接口，不传代表查所有",
            paramType = "query"),
        @ApiImplicitParam(name = "page", value = "开始页码", paramType = "query"),
        @ApiImplicitParam(name = "size", value = "每页显示数量，最多不超过20", paramType = "query"),
        @ApiImplicitParam(name = "keywords", value = "模糊查询关键字", paramType = "query")})
    @PostMapping("/queryTaskList")
    public Object queryTaskList(HttpServletRequest request, HttpServletResponse response,
        @RequestParam(value = "accessToken", required = false) String accessToken,
        @RequestParam(value = "taskType", required = false) String taskType,
        @RequestParam(value = "taskStatus", required = false) String taskStatus,
        @RequestParam(value = "keywords", required = false) String keywords, PageInfo pageInfo) {
        return this.taskQueryService.queryTaskList(accessToken, taskType, taskStatus, keywords, pageInfo);
    }
    
    @ApiOperation(value = "查看任务类型枚举接口", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PostMapping("/queryTaskType")
    public Object queryTaskType(HttpServletRequest request, HttpServletResponse response) {
        List<String> list = new ArrayList<>();
        for (WorkflowTaskTypeEnums taskType : WorkflowTaskTypeEnums.values()) {
            list.add("参数值：" + taskType.getCode() + " 备注：" + taskType.getCodeName());
        }
        return list;
    }
    
    @ApiOperation(value = "查看任务状态枚举接口", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PostMapping("/queryTaskStatus")
    public Object queryTaskStatus(HttpServletRequest request, HttpServletResponse response) {
        List<String> list = new ArrayList<>();
        for (CsmFlowTaskStatusEnums taskStatusEnums : CsmFlowTaskStatusEnums.values()) {
            list.add("参数值：" + taskStatusEnums.getCode() + " 备注：" + taskStatusEnums.getCodeName());
        }
        return list;
    }
    
    @ApiOperation(value = "查看工作流当前进度，以及高亮显示当前节点", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({@ApiImplicitParam(name = "executionId", value = "流程实例ID", paramType = "query")})
    @GetMapping(value = "/queryProcess", produces = "application/json;charset=utf-8")
    public void queryProcess(HttpServletRequest request, HttpServletResponse response) throws Exception {
        InputStream imageStream = this.taskQueryService.queryProcess(request.getParameter("executionId"));
        if (null == imageStream) {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().write("未查询到流程");
        } else {
            byte[] b = new byte[1024];
            int len;
            while ((len = imageStream.read(b, 0, 1024)) != -1) {
                response.getOutputStream().write(b, 0, len);
            }
        }
        
    }
    
    @ApiOperation(value = "查看当前任务审批人", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({
        @ApiImplicitParam(name = "processInstanceId", value = "流程实例ID", required = true, paramType = "query")})
    @PostMapping("/queryApproveUserInfo")
    public Object queryApproveUserInfo(HttpServletRequest request, HttpServletResponse response,
        @RequestParam(value = "processInstanceId", required = false) String processInstanceId) throws Exception {
        return this.taskQueryService.queryApproveUserInfo(processInstanceId);
    }
    
    @ApiOperation(value = "查看当前任务审批记录", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({
        @ApiImplicitParam(name = "processInstanceId", value = "流程实例ID", required = true, paramType = "query")})
    @PostMapping("/queryApproveRecords")
    public Object queryApproveRecords(HttpServletRequest request, HttpServletResponse response,
        @RequestParam(value = "processInstanceId", required = false) String processInstanceId) throws Exception {
        return this.taskQueryService.queryApproveRecords(processInstanceId);
    }
}
