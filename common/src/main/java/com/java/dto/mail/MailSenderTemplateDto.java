package com.java.dto.mail;

import java.util.Map;

/**
 * Created by lu.xu on 2017/12/19.
 * TODO: 邮件发送的模板信息封装类
 */
public class MailSenderTemplateDto {
    /**
     * 模板名称
     */
    private String templateName;
    
    /**
     * 模板中表达式占位符的值
     * key：占位符名称
     * value ：值
     */
    private Map<String, Object> values;
    
    public String getTemplateName() {
        return templateName;
    }
    
    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }
    
    public Map<String, Object> getValues() {
        return values;
    }
    
    public void setValues(Map<String, Object> values) {
        this.values = values;
    }
    
    public MailSenderTemplateDto(String templateName) {
        this.templateName = templateName;
    }
    
    public MailSenderTemplateDto() {
    }
}
