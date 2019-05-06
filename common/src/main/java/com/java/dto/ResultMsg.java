package com.java.dto;

import lombok.Data;

/**
 * TODO(自定义消息对象，一般运用于返回操作结果以及附带信息)    .
 */
@Data
public class ResultMsg {

  private static final int SUCCESS_CODE = 200;
  private static final int ERROR_CODE = 500;

  /**
   * 操作状态 200成功,500异常
   */
  private int status;
  /**
   * 附带消息
   */
  private String msg;
  /**
   * 返回内容
   */
  private Object content;

  public ResultMsg(int status, String msg, Object content) {
    this.status = status;
    this.msg = msg;
    this.content = content;
  }


  public static ResultMsg ok() {
    return new ResultMsg(SUCCESS_CODE, null, null);
  }

  public static ResultMsg ok(String msg) {
    return new ResultMsg(SUCCESS_CODE, msg, null);
  }

  public static ResultMsg ok(String msg, Object content) {
    return new ResultMsg(SUCCESS_CODE, msg, content);
  }

  public static ResultMsg error(String msg) {
    return new ResultMsg(ERROR_CODE, msg, null);
  }

  public static ResultMsg error(String msg, Object content) {
    return new ResultMsg(ERROR_CODE, msg, content);
  }

  public boolean isSuccess() {
    return this.getStatus() == SUCCESS_CODE;
  }
}
