package com.java.activiti.rest.editor.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.java.dto.ResultMsg;
import com.java.utils.EmptyUtils;
import com.java.utils.HttpRequestUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 工作流模板服务
 */

@Api(description = "工作流-模型维护")
@RestController
@RequestMapping("/models")
public class ModelCreateController {

  @Value("${server.context-path}")
  private String contextPath;

  @Value("${server.port}")
  private String port;

  @Autowired
  ProcessEngine processEngine;

  @Autowired
  ObjectMapper objectMapper;

  @Resource
  private RepositoryService repositoryService;

  @ApiOperation(value = "新建一个空", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @ApiImplicitParams({
      @ApiImplicitParam(name = "modelName", value = "流程模型名称", required = true, paramType = "query"),
      @ApiImplicitParam(name = "modelCode", value = "编码", required = true, paramType = "query"),
      @ApiImplicitParam(name = "description", value = "描述", paramType = "query")})
  @PostMapping("/newModel")
  public Object newModel(HttpServletRequest request) throws UnsupportedEncodingException {
    RepositoryService repositoryService = processEngine.getRepositoryService();
    //初始化一个空模型
    Model model = repositoryService.newModel();

    //设置一些默认信息
    String modelName = request.getParameter("modelName");
    String description = request.getParameter("description");
    String modelCode = request.getParameter("modelCode");
    int revision = 1;
    if (EmptyUtils.isAnyEmpty(modelName, modelCode)) {
      return ResultMsg.error("参数必填 modelName:" + modelName + " modelCode:" + modelCode);
    }

    ObjectNode modelNode = objectMapper.createObjectNode();
    modelNode.put(ModelDataJsonConstants.MODEL_NAME, modelName);
    modelNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
    modelNode.put(ModelDataJsonConstants.MODEL_REVISION, revision);

    model.setName(modelName);
    model.setKey(modelCode);
    model.setMetaInfo(modelNode.toString());

    repositoryService.saveModel(model);
    String id = model.getId();

    //完善ModelEditorSource
    ObjectNode editorNode = objectMapper.createObjectNode();
    editorNode.put("id", "canvas");
    editorNode.put("resourceId", "canvas");
    ObjectNode stencilSetNode = objectMapper.createObjectNode();
    stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
    editorNode.put("stencilset", stencilSetNode);
    repositoryService.addModelEditorSource(id, editorNode.toString().getBytes("utf-8"));
    return ResultMsg.ok("添加成功");
  }

  @ApiOperation(value = "编辑现有模板接口,访问接口获得", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @PostMapping("/editModel")
  public Object editModel(HttpServletRequest request,
      @RequestParam(value = "modelId", required = false) String modelId)
      throws UnsupportedEncodingException {
    //因为通过zuul访问，但是zuul不支持静态资源的返回，所以编辑界面直接打开非zuul访问地址
    String ip = HttpRequestUtil.getLocalIp();
    String finalUrl =
        "http://" + ip + ":" + port + contextPath + "/static/modeler.html?modelId=" + modelId;
    return ResultMsg.ok("", finalUrl);
  }

  @ApiOperation(value = "获取所有工作流模板信息", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @ApiImplicitParams({
      @ApiImplicitParam(name = "keywords", value = "模板名称模糊查询", paramType = "query")})
  @PostMapping("/modelList")
  public List<Model> modelList(HttpServletRequest request,
      @RequestParam(value = "keywords", required = false) String keywords) {
    RepositoryService repositoryService = processEngine.getRepositoryService();
    List<Model> models = null;
    if (EmptyUtils.isEmpty(keywords)) {
      models = repositoryService.createModelQuery().orderByCreateTime().desc().list();
    } else {
      models = repositoryService.createModelQuery().modelNameLike("%" + keywords + "%")
          .orderByCreateTime().desc().list();
    }
    return models;
  }

  @PostMapping("/delete/{id}")
  @ApiOperation(value = "删除", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "流程ID", paramType = "query")})
  public Object deleteModel(@PathVariable("id") String id) {
    RepositoryService repositoryService = processEngine.getRepositoryService();
    Model model = repositoryService.createModelQuery().modelId(id).singleResult();
    if (model == null) {
      return ResultMsg.error("删除失败，未查询到相关模板");
    }
    repositoryService.deleteModel(id);
    return ResultMsg.ok("删除模板成功");
  }

  //    @PostMapping("/vacation/{id}")
  //    @ApiOperation(value = "挂起流程", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  //    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "流程ID")})
  //    public Object vacation(@PathVariable("id") String id) {
  //        RepositoryService repositoryService = processEngine.getRepositoryService();
  //        //根据ID获取名称
  //        String name = "";
  //        try {
  //            repositoryService.suspendProcessDefinitionByKey(name);
  //            return new ResultMsg(true);
  //        } catch (Exception e) {
  //            return new ResultMsg(false);
  //        }
  //    }

  @PostMapping("/suspend/{id}")
  @ApiOperation(value = "挂起流程", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "流程ID", paramType = "query")})
  public Object suspend(@PathVariable("id") String id) {
    RepositoryService repositoryService = processEngine.getRepositoryService();
    try {
      repositoryService.suspendProcessDefinitionById(id);
      return ResultMsg.ok();
    } catch (Exception e) {
      return ResultMsg.error("未知异常");
    }
  }

  @PostMapping("/activate/{id}")
  @ApiOperation(value = "激活挂起的流程", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "流程ID", paramType = "query")})
  public Object activate(@PathVariable("id") String id) {
    RuntimeService runtimeService = processEngine.getRuntimeService();
    try {
      runtimeService.activateProcessInstanceById(id);
      return ResultMsg.ok();
    } catch (Exception e) {
      return ResultMsg.error("未知异常");
    }
  }

  /**
   * 发布模型为流程定义
   */
  //    @PostMapping("{id}/deployment")
  //    public String deploy(@PathVariable("id")String id) throws Exception {
  //
  //        //获取模型
  //        RepositoryService repositoryService = processEngine.getRepositoryService();
  //        Model modelData = repositoryService.getModel(id);
  //        byte[] bytes = repositoryService.getModelEditorSource(modelData.getId());
  //
  //        if (bytes == null) {
  //            return ("模型数据为空，请先设计流程并成功保存，再进行发布。");
  //        }
  //
  //        JsonNode modelNode = new ObjectMapper().readTree(bytes);
  //
  //        BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
  //        if(model.getProcesses().size()==0){
  //            return ("数据模型不符要求，请至少设计一条主线流程。");
  //        }
  //        byte[] bpmnBytes = new BpmnXMLConverter().convertToXML(model);
  //
  //        //发布流程
  //        String processName = modelData.getName() + ".bpmn20.xml";
  //        Deployment deployment = repositoryService.createDeployment()
  //                .name(modelData.getName())
  //                .addString(processName, new String(bpmnBytes, "UTF-8"))
  //                .deploy();
  //        modelData.setDeploymentId(deployment.getId());
  //        repositoryService.saveModel(modelData);
  //
  //        return "success";
  //    }
  @ApiOperation(value = "导出模板", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @ApiImplicitParams({@ApiImplicitParam(name = "modelId", value = "模板ID", paramType = "query"),
      //        @ApiImplicitParam(name = "type", value = "类型(bpmn)")
  })
  @GetMapping(value = "/design/export/{modelId}")
  public void export(@PathVariable("modelId") String modelId,
      //             @PathVariable("type") String type,
      HttpServletResponse response) {
    //不通过传参获得
    String type = "bpmn";
    //        try {
    //            Model modelData = repositoryService.getModel(modelId);
    //            BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
    //            byte[] modelEditorSource = repositoryService.getModelEditorSource(modelData.getId());
    //
    //            JsonNode editorNode = new ObjectMapper().readTree(modelEditorSource);
    //            BpmnModel bpmnModel = jsonConverter.convertToBpmnModel(editorNode);
    //
    //            // 处理异常
    //            if (bpmnModel.getMainProcess() == null) {
    //                response.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
    //                response.getOutputStream().println("no mail process, can't export for type: " + type);
    //                response.flushBuffer();
    //                return;
    //            }
    //            String filename = "";
    //            byte[] exportBytes = null;
    //            String mainProcessId = bpmnModel.getMainProcess().getId();
    //            if (type.equals("bpmn")) {
    //                BpmnXMLConverter xmlConverter = new BpmnXMLConverter();
    //                exportBytes = xmlConverter.convertToXML(bpmnModel);
    //                filename = mainProcessId + ".bpmn20.xml";
    //            } else if (type.equals("json")) {
    //                exportBytes = modelEditorSource;
    //                filename = mainProcessId + ".json";
    //            }
    //            ByteArrayInputStream in = new ByteArrayInputStream(exportBytes);
    //            IOUtils.copy(in, response.getOutputStream());
    //            response.setHeader("Content-Disposition", "attachment; filename=" + filename);
    //            response.flushBuffer();
    //        } catch (Exception e) {
    //            e.printStackTrace();
    //        }

    //20180125 xulu overwrite

    ByteArrayInputStream in = null;
    BufferedOutputStream bos = null;
    BufferedInputStream bis = null;

    try {
      Model modelData = repositoryService.getModel(modelId);
      BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
      byte[] modelEditorSource = repositoryService.getModelEditorSource(modelData.getId());
      JsonNode editorNode = new ObjectMapper().readTree(modelEditorSource);
      BpmnModel bpmnModel = jsonConverter.convertToBpmnModel(editorNode);

      // 处理异常
      if (bpmnModel.getMainProcess() == null) {
        response.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
        response.getOutputStream().println("no mail process, can't export for type: " + type);
        response.flushBuffer();
        return;
      }

      String mainProcessId = bpmnModel.getMainProcess().getId();
      String filename = mainProcessId + ".bpmn20.xml";
      BpmnXMLConverter xmlConverter = new BpmnXMLConverter();
      byte[] exportBytes = xmlConverter.convertToXML(bpmnModel);
      if (null == exportBytes) {
        response.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
        response.getOutputStream().println("content is null");
        response.flushBuffer();
      }

      response.setHeader("Content-Disposition", "attachment; filename=" + filename);

      in = new ByteArrayInputStream(exportBytes);
      bos = new BufferedOutputStream(response.getOutputStream());
      bis = new BufferedInputStream(in);
      byte[] buff = new byte[2048];
      int bytesRead;
      while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
        bos.write(buff, 0, bytesRead);
      }
      response.flushBuffer();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        in.close();
        bos.close();
        bis.close();
      } catch (Exception e2) {
        e2.printStackTrace();
      }
    }

  }

}
