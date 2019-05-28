package com.java.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

@Data
@TableName(value = "user_info")
public class UserInfo {

  @TableId(value = "user_id", type = IdType.INPUT)
  private String userId;

  @TableField(value = "user_pwd")
  private String userPwd;

  @TableField(value = "user_name")
  private String userName;

  @TableField(value = "email")
  private String email;

  @TableField(value = "mobile")
  private String mobile;

  @TableField(value = "create_date")
  private Date createDate;

  @TableField(value = "last_login_date")
  private Date lastLoginDate;

  public static final String COL_USER_PWD = "user_pwd";

  public static final String COL_USER_NAME = "user_name";

  public static final String COL_EMAIL = "email";

  public static final String COL_MOBILE = "mobile";

  public static final String COL_CREATE_DATE = "create_date";

  public static final String COL_LAST_LOGIN_DATE = "last_login_date";
}