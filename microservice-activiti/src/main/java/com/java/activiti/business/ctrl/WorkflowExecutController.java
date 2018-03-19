package com.java.activiti.business.ctrl;

import com.java.activiti.business.service.IWorkflowExcuteService;
import com.java.constants.CommonConstant;
import com.java.dto.WorkFlowAssignee;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.Map;

/**
 * Created by lu.xu on 2017/9/28.
 * TODO:
 */
@RestController
@RequestMapping("/workflowExcute")
@Api(description = "工作流-执行接口")
public class WorkflowExecutController {
    private static final Logger logger = LoggerFactory.getLogger(WorkflowExecutController.class);

    @Resource
    private IWorkflowExcuteService workFlowService;

    @ApiOperation(value = "开始一个工作流", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({@ApiImplicitParam(name = "processDefinitionKey", value = "流程key", required = true, paramType = "query"),
            @ApiImplicitParam(name = "flowName", value = "工作流名称", required = true, paramType = "query"),
            @ApiImplicitParam(name = "businessKey", value = "业务ID", required = true, paramType = "query"),
            @ApiImplicitParam(name = "accessToken", value = "访问令牌", required = true, paramType = "query")})
    @PostMapping("/startWorkflow")
    public Object startWorkflow(HttpServletRequest request, HttpServletResponse response,
                                @RequestParam(value = "processDefinitionKey", required = false) String processDefinitionKey,
                                @RequestParam(value = "flowName", required = false) String flowName,
                                @RequestParam(value = "businessKey", required = false) String businessKey,
                                @RequestParam(value = "accessToken", required = false) String accessToken) {
        return this.workFlowService.startWorkflow(processDefinitionKey, flowName, businessKey, accessToken);
    }

    @ApiOperation(value = "执行工作流任务", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({@ApiImplicitParam(name = "taskId", value = "流程实例ID", required = true, paramType = "query"),
            @ApiImplicitParam(name = "opinion", value = "分支判断标记", paramType = "query"),
            @ApiImplicitParam(name = "opinionRemarks", value = "审批意见", paramType = "query"),
            @ApiImplicitParam(name = "accessToken", value = "访问令牌", required = true, paramType = "query")})
    @PostMapping("/completeTask")
    public Object completeTask(HttpServletRequest request, HttpServletResponse response,
                               @RequestParam(value = "taskId", required = false) String taskId,
                               @RequestParam(value = "opinion", required = false) String opinion,
                               @RequestParam(value = "opinionRemarks", required = false) String opinionRemarks,
                               @RequestBody WorkFlowAssignee assignee,
                               @RequestParam(value = "accessToken", required = false) String accessToken) {
        return this.workFlowService.completeTask(taskId, opinion, opinionRemarks, assignee, accessToken);
    }

}