package com.java.activiti.business.ctrl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.java.activiti.business.entity.jpa.CsmActAssignee;
import com.java.activiti.business.entity.jpa.CsmActAssigneeObject;
import com.java.activiti.business.enums.CsmActAssigneeMultiEnums;
import com.java.activiti.business.enums.CsmActAssigneeObjectTypeEnums;
import com.java.activiti.business.enums.WorkflowTaskTypeEnums;
import com.java.activiti.business.service.IProcessAssigneeService;
import com.java.dto.PageInfo;
import io.swagger.annotations.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lu.xu on 2017/12/26.
 * TODO: 节点受理对象维护
 */

@RestController
@RequestMapping("/processAssignee")
@Api(description = "工作流-流程节点受理对象维护")
public class ProcessAssigneeController {

    @Resource
    private IProcessAssigneeService nodePermissionsService;

    @ApiOperation(value = "获取系统中现有的流程", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({@ApiImplicitParam(name = "accessToken", value = "用户令牌", paramType = "query")})
    @PostMapping("/getAllProcessDefinition")
    public Object getAllProcessDefinition(HttpServletRequest request, HttpServletResponse response) {
        return this.nodePermissionsService.getAllProcessDefinition();

    }

    @ApiOperation(value = "根据流程KEY，获取所有节点", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({@ApiImplicitParam(name = "accessToken", value = "用户令牌", paramType = "query"),
            @ApiImplicitParam(name = "processDefinitionKey", value = "流程定义key", paramType = "query")})
    @PostMapping("/getElementByProcesskey")
    public Object getElementByProcesskey(HttpServletRequest request, HttpServletResponse response,
                                         @RequestParam(value = "processDefinitionKey", required = false) String processDefinitionKey) {
        return this.nodePermissionsService.getElementByProcesskey(processDefinitionKey);
    }

    @ApiOperation(value = "01列表-分页查询已授权模板", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({@ApiImplicitParam(name = "accessToken", value = "用户令牌", paramType = "query"),
            @ApiImplicitParam(name = "keywords", value = "模糊查询关键字", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "页码", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "每页显示数量，最大20", paramType = "query")})
    @PostMapping("/queryProcessAssigneeList")
    public Object queryModelList(HttpServletRequest request, HttpServletResponse response, PageInfo pageInfo,
                                 @RequestParam(value = "keywords", required = false) String keywords) {
        return this.nodePermissionsService.queryProcessAssigneeList(keywords, pageInfo);
    }

    @ApiOperation(value = "02列表-查询已授权节点", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({@ApiImplicitParam(name = "accessToken", value = "用户令牌", paramType = "query"),
            @ApiImplicitParam(name = "processKey", value = "流程模板代码", paramType = "query")})
    @PostMapping("/queryElementList")
    public Object queryElementList(HttpServletRequest request, HttpServletResponse response,
                                   @RequestParam(value = "accessToken", required = false) String accessToken,
                                   @RequestParam(value = "processKey", required = false) String processKey) {
        return this.nodePermissionsService.queryElementList(processKey);
    }

    @ApiOperation(value = "03列表-查询授权对象", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({@ApiImplicitParam(name = "accessToken", value = "用户令牌", paramType = "query"),
            @ApiImplicitParam(name = "assigneeId", value = "授权节点ID", paramType = "query")})
    @PostMapping("/queryAssigneeList")
    public Object queryAssigneeList(HttpServletRequest request, HttpServletResponse response,
                                    @RequestParam(value = "assigneeId", required = false) String assigneeId) {
        return this.nodePermissionsService.queryAssigneeList(assigneeId);
    }

    @ApiOperation(value = "添加节点授权", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({@ApiImplicitParam(name = "accessToken", value = "用户令牌", paramType = "query"),
            @ApiImplicitParam(name = "processKey", value = "流程定义编码", paramType = "query"),
            @ApiImplicitParam(name = "processName", value = "流程定义名称", paramType = "query"),
            @ApiImplicitParam(name = "elementCode", value = "节点编码", paramType = "query"),
            @ApiImplicitParam(name = "elementName", value = "节点名称", paramType = "query"),
            @ApiImplicitParam(name = "isMultiSign", value = "是否会签,查看：/isMultiSign接口", paramType = "query"),
            @ApiImplicitParam(name = "assigneeCode", value = "唯一标识", paramType = "query"),
            @ApiImplicitParam(name = "remarks", value = "备注", paramType = "query")})
    @PostMapping("/addAssignee")
    public Object addAssignee(HttpServletRequest request, HttpServletResponse response, CsmActAssignee csmActAssignee,
                              @RequestParam(value = "accessToken", required = false) String accessToken) {
        return this.nodePermissionsService.addAssignee(csmActAssignee, accessToken);
    }

    @ApiOperation(value = "查看节点类型接口", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PostMapping("/isMultiSign")
    public Object isMultiSign(HttpServletRequest request, HttpServletResponse response) {
        JSONArray jsonArray = new JSONArray();
        for (CsmActAssigneeMultiEnums taskType : CsmActAssigneeMultiEnums.values()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("CODE", taskType.getCode());
            jsonObject.put("NAME", taskType.getCodeName());
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }

    @ApiOperation(value = "删除授权节点", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({@ApiImplicitParam(name = "accessToken", value = "用户令牌", paramType = "query"),
            @ApiImplicitParam(name = "assigneeId", value = "授权节点ID", paramType = "query")})
    @PostMapping("/delAssignee")
    public Object delAssignee(HttpServletRequest request, HttpServletResponse response,
                              @RequestParam(value = "assigneeId", required = false) String assigneeId,
                              @RequestParam(value = "accessToken", required = false) String accessToken) {
        return this.nodePermissionsService.delAssignee(assigneeId, accessToken);
    }

    @ApiOperation(value = "添加节点授权对象", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({@ApiImplicitParam(name = "accessToken", value = "用户令牌", paramType = "query"),
            @ApiImplicitParam(name = "assigneeId", value = "授权节点ID", paramType = "query"),
            @ApiImplicitParam(name = "assigneeType", value = "授权类型，查看：/assigneeType", paramType = "query"),
            @ApiImplicitParam(name = "assigneeObjectId", value = "授权对象ID", paramType = "query"),
            @ApiImplicitParam(name = "assigneeObjectName", value = "授权对象Name", paramType = "query")})
    @PostMapping("/addAssigneeObject")
    public Object addAssigneeObject(HttpServletRequest request, HttpServletResponse response,
                                    CsmActAssigneeObject csmActAssigneeObject,
                                    @RequestParam(value = "accessToken", required = false) String accessToken) {
        return this.nodePermissionsService.addAssigneeObject(csmActAssigneeObject, accessToken);
    }

    @ApiOperation(value = "查看授权类型", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PostMapping("/assigneeType")
    public Object assigneeType(HttpServletRequest request, HttpServletResponse response) {
        JSONArray jsonArray = new JSONArray();
        for (CsmActAssigneeObjectTypeEnums objectTypeEnums : CsmActAssigneeObjectTypeEnums.values()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("CODE", objectTypeEnums.getCode());
            jsonObject.put("NAME", objectTypeEnums.getCodeName());
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }

    @ApiOperation(value = "删除授权节点对象", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({@ApiImplicitParam(name = "accessToken", value = "用户令牌", paramType = "query"),
            @ApiImplicitParam(name = "assigneeObjId", value = "授权对象信息主键", paramType = "query")})
    @PostMapping("/delAssigneeObj")
    public Object delAssigneeObj(HttpServletRequest request, HttpServletResponse response,
                                 @RequestParam(value = "assigneeObjId", required = false) String assigneeObjId,
                                 @RequestParam(value = "accessToken", required = false) String accessToken) {
        return this.nodePermissionsService.delAssigneeObj(assigneeObjId, accessToken);
    }
}
