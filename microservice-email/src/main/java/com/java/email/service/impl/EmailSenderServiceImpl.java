package com.java.email.service.impl;

import com.java.dto.ResultMsg;
import com.java.dto.mail.MailSenderDto;
import com.java.email.service.IEmailSenderService;
import com.java.utils.EmptyUtils;
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

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Map;

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
            return new ResultMsg(false, noticeMsg);
        }
        if (EmptyUtils.isEmpty(mailSenderDto.getRecevers())) {
            String noticeMsg = "接收人必填";
            logger.error(noticeMsg);
            return new ResultMsg(false, noticeMsg);
        }
        //解析模板
        if (mailSenderDto.getTemplate() != null
            && EmptyUtils.isNotEmpty(mailSenderDto.getTemplate().getTemplateName())) {
            Context context = new Context();
            if (EmptyUtils.isNotEmpty(mailSenderDto.getTemplate().getValues())) {
                for (Map.Entry<String, Object> variable : mailSenderDto.getTemplate().getValues().entrySet()) {
                    String key = variable.getKey();
                    Object value = variable.getValue();
                    context.setVariable(key, value);
                }
            }
            String content = templateEngine.process(mailSenderDto.getTemplate().getTemplateName(), context);
            if (EmptyUtils.isEmpty(content)) {
                String noticeMsg = "邮件模板解析失败";
                logger.error(noticeMsg);
                return new ResultMsg(false, noticeMsg);
            }
            mailSenderDto.setContent(content);
        } else if (EmptyUtils.isEmpty(mailSenderDto.getContent())) {
            String noticeMsg = "邮件内容为空";
            logger.error(noticeMsg);
            return new ResultMsg(false, noticeMsg);
        }
        return this.send(mailSenderDto.getTitle(), mailSenderDto.getContent(), mailSenderDto
            .getRecevers(), mailSenderDto.getCc(), mailSenderDto.getFiles());
    }
    
    private ResultMsg send(String title, String content, String[] recevers, String[] cc,
        Map<String, InputStream> files) {
        logger.info("邮件发送：title:{}\nrecevers:{}", title, Arrays.toString(recevers));
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(fromAddr);
            helper.setTo(recevers);
            if (EmptyUtils.isNotEmpty(cc)) {
                helper.setCc(cc);
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
            return new ResultMsg(true);
        } catch (Exception e) {
            String noticeMsg = "发送邮件时发生异常";
            logger.error(noticeMsg);
            e.printStackTrace();
            return new ResultMsg(false, noticeMsg);
        }
    }
}
