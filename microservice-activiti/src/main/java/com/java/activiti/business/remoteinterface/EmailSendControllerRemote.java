package com.java.activiti.business.remoteinterface;

import com.java.dto.ResultMsg;
import com.java.dto.mail.MailSenderDto;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Created by lu.xu on 2018/1/19. TODO:工作流执行接口
 */

@FeignClient(name = "microservice-email")
public interface EmailSendControllerRemote {

  static final String contentPath = "/microservice-email";

  /**
   * 发送邮件
   */
  @PostMapping(value = contentPath + "/sendMail")
  ResultMsg sendMail(MailSenderDto mailSenderDto);
}
