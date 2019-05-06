package com.java.email.service;

import com.java.dto.ResultMsg;
import com.java.dto.mail.MailSenderDto;

/**
 * 邮件发送接口
 */
public interface IEmailSenderService {

  /**
   * 发送邮件接口
   *
   * @param mailSenderDto 参数封装
   * @return {@link ResultMsg}
   */
  ResultMsg sendEmail(MailSenderDto mailSenderDto);

}
