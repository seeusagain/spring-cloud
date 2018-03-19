//package com.java.email;
//
//import com.java.ApplicationEmail;
//import com.java.dto.ResultMsg;
//import com.java.dto.mail.MailSenderDto;
//import com.java.dto.mail.MailSenderTemplateDto;
//import com.java.email.service.IEmailSenderService;
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.thymeleaf.TemplateEngine;
//
//import javax.annotation.Resource;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.InputStream;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * Created by lu.xu on 2017/9/22.
// * TODO:email 发送测试
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest(classes = ApplicationEmail.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
//public class MialSendTest {
//    @Resource
//    private IEmailSenderService emailSenderService;
//
//    @Resource
//    private TemplateEngine templateEngine;
//
//    /**
//     * 测试发送普通邮件
//     */
//    @Test
//    public void sendMailTest() {
//        MailSenderDto mailSenderDto = this.getMailSenderDto();
//        ResultMsg rmg = emailSenderService.sendEmail(mailSenderDto);
//        Assert.assertTrue(rmg.isSuccess());
//    }
//
//    /**
//     * 测试发送带附件邮件
//     */
//    @Test
//    public void sendMailWithFilesTest() {
//        MailSenderDto mailSenderDto = this.getMailSenderDto();
//        Map<String, InputStream> files = new HashMap<>();
//        try {
//            File file = new File("D:\\mailtest\\01test.txt");
//            InputStream inputStream1 = new FileInputStream(file);
//            File file2 = new File("D:\\mailtest\\02test.xlsx");
//            InputStream inputStream2 = new FileInputStream(file2);
//            files.put("文本附件1.txt", inputStream1);
//            files.put("excel附件2.xlsx", inputStream2);
//            mailSenderDto.setFiles(files);
//        } catch (Exception e) {
//
//        }
//        ResultMsg rmg = emailSenderService.sendEmail(mailSenderDto);
//        Assert.assertTrue(rmg.isSuccess());
//    }
//
//    /**
//     * 构造通用的发送参数
//      * @return
//     */
//    private MailSenderDto getMailSenderDto() {
//        MailSenderDto mailSenderDto = new MailSenderDto();
//        mailSenderDto.setTitle("打包测试发送邮件");
//        String[] recevers = new String[2];
//        recevers[0] = "test1@qq.coma";
//        recevers[1] = "test2@qq.coma";
//        mailSenderDto.setRecevers(recevers);
//
//        MailSenderTemplateDto mailSenderTemplateDto = new MailSenderTemplateDto();
//        mailSenderTemplateDto.setTemplateName("testTemplate");
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("name", "test-name");
//        map.put("age", 18);
//        mailSenderTemplateDto.setValues(map);
//        mailSenderDto.setTemplate(mailSenderTemplateDto);
//        return mailSenderDto;
//    }
//}
