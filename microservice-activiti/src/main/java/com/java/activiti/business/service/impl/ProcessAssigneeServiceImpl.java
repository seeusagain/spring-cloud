package com.java.activiti.business.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.java.activiti.business.dao.CsmActAssigneeMapper;
import com.java.activiti.business.entity.jpa.CsmActAssignee;
import com.java.activiti.business.entity.jpa.CsmActAssigneeObject;
import com.java.activiti.business.entity.mybatis.CsmActAssigneeEntity;
import com.java.activiti.business.enums.CsmActAssigneeMultiEnums;
import com.java.activiti.business.enums.CsmActAssigneeObjectTypeEnums;
import com.java.activiti.business.repository.CsmActAssigneeObjectRespository;
import com.java.activiti.business.repository.CsmActAssigneeRespository;
import com.java.activiti.business.service.IProcessAssigneeService;
import com.java.dto.PageInfo;
import com.java.utils.EmptyUtils;
import com.java.utils.IdGeneratorUtils;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.java.dto.ResultMsg;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
public class ProcessAssigneeServiceImpl implements IProcessAssigneeService {
    private static final Logger logger = LoggerFactory.getLogger(ProcessAssigneeServiceImpl.class);
    
    @Resource
    private CsmActAssigneeRespository csmActAssigneeRespository;
    
    @Resource
    private CsmActAssigneeMapper csmActAssigneeMapper;
    
    @Resource
    private CsmActAssigneeObjectRespository csmActAssigneeObjectRespository;
    
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    
    @Resource
    private ProcessEngine processEngine;
    
    @Override
    public ResultMsg addAssignee(CsmActAssignee assignee, String accessToken) {
        if (assignee == null) {
            return new ResultMsg(false, "参数异常，授权信息为空");
        }
        if (EmptyUtils.isEmpty(assignee.getProcessKey())) {
            return new ResultMsg(false, "参数异常，流程key缺失");
        }
        if (EmptyUtils.isEmpty(assignee.getProcessName())) {
            return new ResultMsg(false, "参数异常，流程名称缺失");
        }
        if (EmptyUtils.isEmpty(assignee.getElementCode())) {
            return new ResultMsg(false, "参数异常，节点编码缺失");
        }
        if (EmptyUtils.isEmpty(assignee.getElementName())) {
            return new ResultMsg(false, "参数异常，节点名称缺失");
        }
        
        String userId = stringRedisTemplate.opsForValue().get(accessToken);
        if (EmptyUtils.isNotEmpty(assignee.getAssigneeCode())) {
            assignee.setAssigneeCode(assignee.getAssigneeCode().trim());
        }
        assignee.setId(IdGeneratorUtils.getSerialNo());
        assignee.setCreateUser(userId);
        assignee.setCreateDate(new Date());
        this.csmActAssigneeRespository.save(assignee);
        return new ResultMsg(true, "添加成功");
    }
    
    @Override
    public ResultMsg delAssignee(String assigneeId, String accessToken) {
        if (EmptyUtils.isEmpty(assigneeId)) {
            return new ResultMsg(false, "ID 为空");
        }
        this.csmActAssigneeRespository.delete(assigneeId);
        this.csmActAssigneeObjectRespository.deleteByAssigneeId(assigneeId);
        return new ResultMsg(true, "删除成功");
    }
    
    @Override
    public ResultMsg addAssigneeObject(CsmActAssigneeObject assigneeObject, String accessToken) {
        if (null == assigneeObject) {
            return new ResultMsg(false, "对象为空");
        }
        if (EmptyUtils.isEmpty(accessToken)) {
            return new ResultMsg(false, "accessToken为空");
        }
        String userId = stringRedisTemplate.opsForValue().get(accessToken);
        if (EmptyUtils.isAnyEmpty(assigneeObject.getAssigneeId(), assigneeObject.getAssigneeType(), assigneeObject
            .getAssigneeObjectId(), assigneeObject.getAssigneeObjectName())) {
            return new ResultMsg(false, "节点ID、授权类型、授权对象ID、名称 不能为空");
        }
        
        boolean objectTypeFlag = false;
        for (CsmActAssigneeObjectTypeEnums objectTypeEnums : CsmActAssigneeObjectTypeEnums.values()) {
            if (objectTypeEnums.getCode().equals(assigneeObject.getAssigneeType())) {
                objectTypeFlag = true;
                break;
            }
        }
        if (!objectTypeFlag) {
            return new ResultMsg(false, "授权类型 参数错误");
        }
        
        int count = this.csmActAssigneeRespository.countById(assigneeObject.getAssigneeId());
        if (count == 0) {
            return new ResultMsg(false, "未查询到节点信息");
        }
        assigneeObject.setId(IdGeneratorUtils.getSerialNo());
        assigneeObject.setCreateUser(userId == null ? accessToken : userId);
        assigneeObject.setCreateDate(new Date());
        this.csmActAssigneeObjectRespository.save(assigneeObject);
        return new ResultMsg(true, "添加成功");
    }
    
    @Override
    public ResultMsg delAssigneeObj(String assigneeObjId, String accessToken) {
        if (EmptyUtils.isEmpty(assigneeObjId)) {
            return new ResultMsg(false, "ID 为空");
        }
        this.csmActAssigneeObjectRespository.delete(assigneeObjId);
        return new ResultMsg(true, "删除成功");
    }
    
    @Override
    public ResultMsg delAuthorization(String id) {
        this.csmActAssigneeRespository.delete(id);
        return new ResultMsg(true, "删除成功");
    }
    
    @Override
    public ResultMsg queryProcessAssigneeList(String keywords, PageInfo pageInfo) {
        if (null == pageInfo) {
            pageInfo = new PageInfo();
        }
        List<CsmActAssigneeEntity> results =
            this.csmActAssigneeMapper.queryDistinctList(keywords, pageInfo.getStartRecord(), pageInfo.getEndRecord());
        int total = this.csmActAssigneeMapper.countDistinctList(keywords);
        pageInfo.setTotal(total);
        pageInfo.setResultSets(results);
        return new ResultMsg(true, null, pageInfo);
    }
    
    @Override
    public ResultMsg queryElementList(String processKey) {
        List<CsmActAssignee> results = this.csmActAssigneeRespository.queryByProcessKeyOrderByElementCode(processKey);
        return new ResultMsg(true, null, results);
    }
    
    @Override
    public ResultMsg queryAssigneeList(String assigneeId) {
        List<CsmActAssigneeObject> results = this.csmActAssigneeObjectRespository.queryByAssigneeId(assigneeId);
        return new ResultMsg(true, null, results);
    }
    
    private static JSONArray allprocessDefinitionCache = null;
    
    @Override
    public JSONArray getAllProcessDefinition() {
        if (allprocessDefinitionCache != null) {
            logger.info("return allprocessDefinitionCache");
            return allprocessDefinitionCache;
        }
        synchronized (this) {
            if (allprocessDefinitionCache != null) {
                logger.info("return allprocessDefinitionCache in sync");
                return allprocessDefinitionCache;
            }
            
            try {
                String classPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
                String allProcessFilePath = classPath + File.separator + "processes";
                File file = new File(allProcessFilePath);
                logger.info(allProcessFilePath);
                if (!file.exists()) {
                    logger.error("无法找到工作流定义文件");
                    return null;
                }
                if (!file.isDirectory()) {
                    logger.error("无法找到工作流定义文件,路径不是目录");
                    return null;
                }
                
                File[] files = file.listFiles();
                if (files == null || files.length == 0) {
                    logger.info("listFiles empty");
                    return null;
                }
                JSONArray jsonArray = new JSONArray();
                for (File f : files) {
                    if (!f.exists()) {
                        logger.info("file not exists");
                        continue;
                    }
                    if (f.isDirectory()) {
                        logger.info("file is   Directory :" + f.getName());
                        continue;
                    }
                    String fileName = f.getName();
                    String prefix = fileName.substring(fileName.indexOf(".") + 1);
                    logger.info("fileName:" + fileName);
                    logger.info("prefix:" + prefix);
                    if (!"bpmn20.xml".equals(prefix)) {
                        logger.info("不是流程定义文件");
                        continue;
                    }
                    
                    SAXReader reader = new SAXReader();
                    Document document = reader.read(new File(allProcessFilePath + File.separator + f.getName()));
                    Element root = document.getRootElement();
                    if (root == null) {
                        logger.info("element node is null");
                        continue;
                    }
                    Element data = root.element("process");
                    String processKey = data.attributeValue("id");
                    String proceessName = data.attributeValue("name");
                    logger.info(proceessName + "-" + processKey);
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("processKey", processKey);
                    jsonObject.put("proceessName", proceessName);
                    jsonArray.add(jsonObject);
                }
                allprocessDefinitionCache = jsonArray;
            } catch (Exception e) {
                logger.error("解析系统现有流程信息异常");
                e.printStackTrace();
                allprocessDefinitionCache = new JSONArray();
            } finally {
                return allprocessDefinitionCache;
            }
            //end try-catch
        }
    }
    
    public Object getElementByProcesskey(String processDefinitionKey) {
        List<ProcessDefinition> processDefinitions = processEngine.getRepositoryService()
            .createProcessDefinitionQuery()
            .processDefinitionKey(processDefinitionKey)
            .orderByProcessDefinitionVersion()
            .desc()
            .list();
        
        if (EmptyUtils.isEmpty(processDefinitions)) {
            logger.info(processDefinitionKey + "无法查询到数据");
        }
        ProcessDefinition processDefinition = processDefinitions.get(0);
        if (processDefinition != null) {
            BpmnModel model = processEngine.getRepositoryService().getBpmnModel(processDefinition.getId());
            Collection<FlowElement> flowElements = model.getMainProcess().getFlowElements();
            JSONArray jsonArray = new JSONArray();
            for (FlowElement flowElement : flowElements) {
                if (EmptyUtils.isAnyEmpty(flowElement.getId(), flowElement.getName())) {
                    continue;
                }
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("elementId", flowElement.getId());
                jsonObject.put("elementName", flowElement.getName());
                jsonArray.add(jsonObject);
            }
            return jsonArray;
        }
        return null;
    }
    
}
