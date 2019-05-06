package com.java.email.service.impl;

import com.java.dto.ResultMsg;
import com.java.dto.mail.MailSenderDto;
import com.java.email.service.IEmailSenderService;
import com.java.utils.EmptyUtils;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Map;
import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import org.apache.poi.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 * 邮件发送实现类
 */
@Service
public class EmailSenderServiceImpl implements IEmailSenderService {

  private static final Logger logger = LoggerFactory.getLogger(EmailSenderServiceImpl.class);

  @Resource
  private JavaMailSender mailSender;

  @Resource
  private TemplateEngine templateEngine;

  @Value("${mail.fromMail.addr}")
  private String fromAddr;

  @Override
  public ResultMsg sendEmail(MailSenderDto mailSenderDto) {
    logger.info(">>邮件发送开始....");
    if (null == mailSenderDto) {
      String noticeMsg = "无法解析发送参数,发送参数为空";
      logger.error(noticeMsg);
      return ResultMsg.error(noticeMsg);
    }
    if ((mailSenderDto.getRecevers() == null || mailSenderDto.getRecevers().length == 0)
        && (mailSenderDto.getCc() == null || mailSenderDto.getCc().length == 0)
        && (mailSenderDto.getBcc() == null || mailSenderDto.getBcc().length == 0)) {
      String noticeMsg = "接收人、抄送人、密送人必须存在";
      logger.error(noticeMsg);
      return ResultMsg.error(noticeMsg);
    }
    //解析模板
    if (mailSenderDto.getTemplate() != null
        && EmptyUtils.isNotEmpty(mailSenderDto.getTemplate().getTemplateName())) {
      Context context = new Context();
      if (EmptyUtils.isNotEmpty(mailSenderDto.getTemplate().getValues())) {
        for (Map.Entry<String, Object> variable : mailSenderDto.getTemplate().getValues()
            .entrySet()) {
          String key = variable.getKey();
          Object value = variable.getValue();
          context.setVariable(key, value);
        }
      }
      String content = templateEngine
          .process(mailSenderDto.getTemplate().getTemplateName(), context);
      if (EmptyUtils.isEmpty(content)) {
        String noticeMsg = "邮件模板解析失败";
        logger.error(noticeMsg);
        return ResultMsg.error(noticeMsg);
      }
      mailSenderDto.setContent(content);
    } else if (EmptyUtils.isEmpty(mailSenderDto.getContent())) {
      String noticeMsg = "邮件内容为空";
      logger.error(noticeMsg);
      return ResultMsg.error(noticeMsg);
    }
    return this.send(mailSenderDto.getTitle(), mailSenderDto.getContent(), mailSenderDto
        .getRecevers(), mailSenderDto.getCc(), mailSenderDto.getBcc(), mailSenderDto.getFiles());
  }

  private ResultMsg send(String title, String content, String[] recevers, String[] cc, String[] Bcc,
      Map<String, InputStream> files) {
    logger
        .info("邮件发送：title:{}\nrecevers:{}\ncc:{}\nbcc:{}", title, Arrays.toString(recevers), Arrays
            .toString(cc), Arrays.toString(Bcc));
    try {
      MimeMessage mimeMessage = mailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
      helper.setFrom(fromAddr);
      if (recevers != null && recevers.length > 0) {
        helper.setTo(recevers);
      }
      if (cc != null && cc.length > 0) {
        helper.setCc(cc);
      }
      if (Bcc != null && Bcc.length > 0) {
        helper.setBcc(Bcc);
      }
      helper.setSubject(title);
      helper.setText(content, true);

      /**
       * 添加附件
       */
      if (EmptyUtils.isNotEmpty(files)) {
        for (Map.Entry<String, InputStream> fileInfo : files.entrySet()) {
          String fileName = fileInfo.getKey();
          InputStream is = fileInfo.getValue();
          helper.addAttachment(fileName, new ByteArrayResource(IOUtils.toByteArray(is)));
        }
      }
      mailSender.send(mimeMessage);
      logger.info(">>邮件发送完毕");
      return ResultMsg.ok();
    } catch (Exception e) {
      String noticeMsg = "发送邮件时发生异常";
      logger.error(noticeMsg);
      e.printStackTrace();
      return ResultMsg.error(noticeMsg);
    }
  }
}
