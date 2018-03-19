package com.java.dto;

/**
 * TODO(自定义消息对象，一般运用于返回操作结果以及附带信息)    .
 *
 * @author 许路
 * @version v 1.0
 * @ClassName: ResultMsg
 * @date: 2016年4月18日 下午1:43:35
 */
public class ResultMsg {
    /**是否操作成功,默认失败*/
    private boolean success = false;
    /**操作状态*/
    private String status;
    /**附带消息*/
    private String msg;
    /** 其他内容*/
    private Object rutContent;
    
    public boolean isSuccess() {
        return success;
    }
    
    public void setSuccess(boolean success) {
        this.success = success;
    }
    
    public String getMsg() {
        return msg;
    }
    
    public void setMsg(String msg) {
        this.msg = msg;
    }
    
    public Object getRutContent() {
        return rutContent;
    }
    
    public void setRutContent(Object rutContent) {
        this.rutContent = rutContent;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    /**
     * Instantiates a new ResultMsg.
     *
     * @param success    the success
     * @param status     the status
     * @param msg        the msg
     * @param rutContent the rut content
     */
    public ResultMsg(boolean success, String status, String msg, Object rutContent) {
        super();
        this.success = success;
        this.status = status;
        this.msg = msg;
        this.rutContent = rutContent;
    }
    
    /**
     * Instantiates a new ResultMsg.
     *
     * @param success    the success
     * @param msg        the msg
     * @param rutContent the rut content
     */
    public ResultMsg(boolean success, String msg, Object rutContent) {
        super();
        this.success = success;
        this.msg = msg;
        this.rutContent = rutContent;
    }
    
    /**
     * Instantiates a new ResultMsg.
     *
     * @param success the success
     * @param msg     the msg
     */
    public ResultMsg(boolean success, String msg) {
        super();
        this.success = success;
        this.msg = msg;
    }
    
    /**
     * Instantiates a new ResultMsg.
     */
    public ResultMsg() {
        super();
    }
    
    /**
     * Instantiates a new ResultMsg.
     *
     * @param success the success
     */
    public ResultMsg(boolean success) {
        super();
        this.success = success;
    }
    
}
