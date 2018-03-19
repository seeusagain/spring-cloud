package com.java.dto.mail;

import java.io.InputStream;
import java.io.Serializable;
import java.util.Map;

/**
 * Created by lu.xu on 2017/12/19.
 * TODO: 发送邮件时候参数
 */
public class MailSenderDto implements Serializable {
    /**
     * 标题
     */
    private String title;
    
    /**
     * 内容，支持html内容
     */
    private String content;
    
    /**
     * 模板信息，如果有，则上面content字段将不会被使用到
     */
    private MailSenderTemplateDto template;
    
    /**
     * 接收人
     */
    private String[] recevers;
    
    /**
     * 抄送人
     */
    private String[] cc;
    
    /**
     * 附件
     * key fileName，记得加后缀名
     * value inputStream，文件流
     */
    Map<String, InputStream> files;
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public MailSenderTemplateDto getTemplate() {
        return template;
    }
    
    public void setTemplate(MailSenderTemplateDto template) {
        this.template = template;
    }
    
    public String[] getRecevers() {
        return recevers;
    }
    
    public void setRecevers(String[] recevers) {
        this.recevers = recevers;
    }
    
    public String[] getCc() {
        return cc;
    }
    
    public void setCc(String[] cc) {
        this.cc = cc;
    }
    
    public Map<String, InputStream> getFiles() {
        return files;
    }
    
    public void setFiles(Map<String, InputStream> files) {
        this.files = files;
    }
    
    public MailSenderDto(String title, String content, MailSenderTemplateDto template, String[] recevers, String[] cc,
        Map<String, InputStream> files) {
        this.title = title;
        this.content = content;
        this.template = template;
        this.recevers = recevers;
        this.cc = cc;
        this.files = files;
    }
    
    public MailSenderDto() {
    }
}
