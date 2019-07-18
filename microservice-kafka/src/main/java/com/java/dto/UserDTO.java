package com.java.dto;

import lombok.Data;

/**
 * @author xulu
 * @date 2019/6/6 15:30
 * @description: TODO
 */
@Data
public class UserDTO {

  private String userId;
  private String userName;
  private int age;

  public UserDTO(String userId, String userName, int age) {
    this.userId = userId;
    this.userName = userName;
    this.age = age;
  }
}
