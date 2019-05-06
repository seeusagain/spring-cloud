package com.java.email.ctrl;

import com.java.dto.ResultMsg;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.java.dto.mail.MailSenderDto;
import com.java.email.service.IEmailSenderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * Created by lu.xu on 2017/9/22.
 * TODO: 测试
 */

@Api(description = "邮件发送接口")
@RestController
public class EmailSendController {
    private static final Logger logger = LoggerFactory.getLogger(EmailSendController.class);
    
    @Resource
    private IEmailSenderService iEmailSenderService;
    
    @ApiOperation(value = "发送邮件", notes = "接收人必填、内容或者内容模板必须填写其一，发送优先级是先模板》内容",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({@ApiImplicitParam(name = "mailSenderDto", value = "如果是服务调用，可以封装成这个对象传参", paramType = "query"),
        @ApiImplicitParam(name = "title", value = "邮件标题", dataType = "文本", paramType = "query"),
        @ApiImplicitParam(name = "content", value = "邮件内容", dataType = "文本", paramType = "query"),
        @ApiImplicitParam(name = "template.templateName", value = "邮件模板名称，如果使用模板，则不会使用content内容", dataType = "文本",
            paramType = "query"),
        @ApiImplicitParam(name = "template.values", value = "模板中的占位符内容", dataType = "Map<String ,Object>",
            paramType = "query"),
        @ApiImplicitParam(name = "recevers", value = "接收人", dataType = "String[]", paramType = "query"),
        @ApiImplicitParam(name = "cc", value = "抄送人", dataType = "String[]", paramType = "query"), @ApiImplicitParam(
            name = "files", value = "附件（如有）", dataType = "Map<String, InputStream> key：文件名称，带后缀", paramType = "query")})
    @PostMapping(value = "/sendMail")
    public ResultMsg sendMail(HttpServletRequest request, HttpServletResponse response,
        @RequestBody MailSenderDto mailSenderDto) {
        return this.iEmailSenderService.sendEmail(mailSenderDto);
    }
}
