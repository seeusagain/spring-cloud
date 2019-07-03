package com.java.utils.encrypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密类
 */
public class Md5Encryption {


  public static String encriptToLower(String str) {
    return encript(str).toLowerCase();
  }

  public static String encriptToUpper(String str) {
    return encript(str).toUpperCase();
  }

  private static String encript(String str) {
    MessageDigest md = null;
    String ss = "";
    try {
      md = MessageDigest.getInstance("MD5");
      byte[] b = md.digest(str.getBytes());
      ss = parseByte2HexStr(b);
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    return ss;
  }

  /**
   * 将二进制转换成16进制
   */
  public static String parseByte2HexStr(byte[] buf) {
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < buf.length; i++) {
      String hex = Integer.toHexString(buf[i] & 0xFF);
      if (hex.length() == 1) {
        hex = '0' + hex;
      }
      sb.append(hex.toUpperCase());
    }
    return sb.toString();
  }

  public static void main(String[] args) {
    System.out.println(encript("123456"));
  }
}
