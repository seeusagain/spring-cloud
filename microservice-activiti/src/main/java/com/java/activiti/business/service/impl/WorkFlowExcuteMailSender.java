package com.java.activiti.business.service.impl;

import com.java.activiti.business.dao.UserInfoForAssigneeMapper;
import com.java.activiti.business.entity.jpa.CsmFlowTask;
import com.java.activiti.business.entity.jpa.UserInfo;
import com.java.activiti.business.entity.mybatis.UserInfoForAssigneeEntity;
import com.java.activiti.business.remoteinterface.EmailSendControllerRemote;
import com.java.activiti.business.repository.CsmFlowTaskRespository;
import com.java.activiti.business.repository.UserInfoRespository;
import com.java.activiti.business.utils.SpringUtil;
import com.java.dto.mail.MailSenderDto;
import com.java.dto.mail.MailSenderTemplateDto;
import com.java.utils.EmptyUtils;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by lu.xu on 2018/2/2.
 * TODO:工作流执行完，异步发送邮件
 */
public class WorkFlowExcuteMailSender implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(WorkFlowExcuteMailSender.class);
    
    /**
     * 执行流程后，审批人收到的邮件模板
     */
    private static final String ACTIVITI_TODO_TEMPLATE = "ActivitiTodoTemplate";
    
    /**
     * 执行流程后，发起人收到的邮件模板
     */
    private static final String ACTIVITI_TOCREATER_TEMPLATE = "ActivitiToCreaterTemplate";
    
    /**
     * 流程实例id，根据这个id去查询接收人
     * 这个参数由主线程传入，构造函数赋值
     */
    private String processInstanceId;
    
    private UserInfoForAssigneeMapper userInfoForAssigneeMapper;
    
    private EmailSendControllerRemote emailSendControllerRemote;
    
    private CsmFlowTaskRespository csmFlowTaskRespository;
    
    private UserInfoRespository userInfoRespository;
    
    private TaskService taskService;
    
    @Override
    public void run() {
        logger.info("activiti 异步发送邮件开始..");
        try {
            CsmFlowTask csmFlowTask = this.csmFlowTaskRespository.queryByProcessInstanceId(this.processInstanceId);
            if (null == csmFlowTask) {
                throw new RuntimeException("query csmFlowTask by processInstanceId,resultSet is null");
            }
            UserInfo createUser = this.userInfoRespository.findOne(csmFlowTask.getCreateUser());
            Task task = this.taskService.createTaskQuery().processInstanceId(processInstanceId).list().get(0);
            List<UserInfoForAssigneeEntity> recevers =
                this.userInfoForAssigneeMapper.queryUserInfoByprocessInstanceId(processInstanceId);
            if (EmptyUtils.isEmpty(recevers)) {
                logger.error("根据流程实例未查询到需要受理人信息，processInstanceId：{}", processInstanceId);
                return;
            }
            
            //构造邮件发送参数对象
            MailSenderTemplateDto mailSenderTemplateDto = new MailSenderTemplateDto();
            mailSenderTemplateDto.setTemplateName(ACTIVITI_TODO_TEMPLATE);
            HashMap<String, Object> values = new HashMap<>();
            values.put("flowName", csmFlowTask.getFlowName());
            if (createUser != null) {
                values.put("createUser", createUser.getUserName());
            }
            values.put("currentNode", task.getName());
            mailSenderTemplateDto.setValues(values);
            
            MailSenderDto mailSenderDto = new MailSenderDto();
            mailSenderDto.setTitle(csmFlowTask.getFlowName());
            mailSenderDto.setTemplate(mailSenderTemplateDto);
            
            //邮件服务支持一封邮件发送多人，此处分开发送，目的是为了避免收件人如果地址错误或者是不存在导致批量发送失败
            logger.info("开始给待办用户发送邮件..");
            ArrayList<String> receversEmailAddress = new ArrayList<String>();
            for (UserInfoForAssigneeEntity user : recevers) {
                try {
                    String emailAddress = user.getEmailAddress();
                    if (EmptyUtils.isEmpty(emailAddress)) {
                        logger.error("用户没有配置邮件地址，用户ID:{},用户名称:{}", user.getUserId(), user.getUserName());
                        continue;
                    }
                    //限制不同的用户如果配置了相同的邮箱，就发一份就好了（主要是针对测试环境）
                    if (receversEmailAddress.contains(emailAddress)) {
                        logger.info("重复发送，就不再发了，用户ID:{},用户名称:{},邮件地址：{}", user.getUserId(), user
                            .getUserName(), emailAddress);
                        continue;
                    }
                    receversEmailAddress.add(emailAddress);
                    
                    mailSenderDto.setRecevers(new String[] {emailAddress});
                    logger.info(">>开始调用邮件服务..接收人：{}，邮件地址：{}", user.getUserName(), emailAddress);
                    emailSendControllerRemote.sendMail(mailSenderDto);
                } catch (Exception e) {
                    logger.error(">> activiti循环发送出错 recever:{}", user.getEmailAddress());
                    e.printStackTrace();
                } finally {
                    //为了避免qq邮箱发送过快被封等问题，发一封停2s
                    Thread.sleep(2000);
                }
            }
            logger.info("给待办用户发送邮件完毕..");
            
            logger.info("开始给申请人发送邮件..");
            if (createUser != null) {
                String createEmail = createUser.getEmail();
                if (EmptyUtils.isEmpty(createEmail)) {
                    logger.error("给申请人发送邮件失败，用户邮件地址为空，userName:{}", createUser.getUserName());
                } else {
                    logger.info("收件人：{}，收件地址：{}", createUser.getUserName(), createUser.getEmail());
                    mailSenderDto.getTemplate().setTemplateName(ACTIVITI_TOCREATER_TEMPLATE);
                    mailSenderDto.setRecevers(new String[] {createUser.getEmail()});
                    emailSendControllerRemote.sendMail(mailSenderDto);
                }
            }
            logger.info("给申请人发送邮件完毕..");
            
            logger.info("activiti 异步发送邮件结束..");
        } catch (Exception e) {
            logger.error("activiti 异步发送邮件异常..");
            e.printStackTrace();
        } finally {
            
        }
    }
    
    public WorkFlowExcuteMailSender() {
    }
    
    public WorkFlowExcuteMailSender(String processInstanceId) {
        if (EmptyUtils.isEmpty(processInstanceId)) {
            throw new RuntimeException("processInstanceId can not be null");
        }
        this.processInstanceId = processInstanceId;
        
        UserInfoForAssigneeMapper userInfoForAssigneeMapper =
            (UserInfoForAssigneeMapper) SpringUtil.getBean("userInfoForAssigneeMapper");
        EmailSendControllerRemote emailSendControllerRemote =
            (EmailSendControllerRemote) SpringUtil.getBean(EmailSendControllerRemote.class);
        CsmFlowTaskRespository csmFlowTaskRespository =
            (CsmFlowTaskRespository) SpringUtil.getBean("csmFlowTaskRespository");
        UserInfoRespository userInfoRespository = (UserInfoRespository) SpringUtil.getBean("userInfoRespository");
        TaskService taskService = (TaskService) SpringUtil.getBean(TaskService.class);
        this.userInfoForAssigneeMapper = userInfoForAssigneeMapper;
        this.emailSendControllerRemote = emailSendControllerRemote;
        this.csmFlowTaskRespository = csmFlowTaskRespository;
        this.userInfoRespository = userInfoRespository;
        this.taskService = taskService;
    }
}
