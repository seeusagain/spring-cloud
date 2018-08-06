package com.java.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by lu.xu on 2017/9/26. TODO:文件操作类
 */
public class FileUtils {

  /**
   * TODO: 删除文件夹或者文件
   */
  public static boolean deleteFile(String dir) {
    return deleteFile(dir, true);
  }

  /**
   * TODO: 删除文件夹或者文件
   *
   * @param dir 路径
   * @param deleteDir 是否删除文件夹或者文件夹下面的子文件夹
   */
  public static boolean deleteFile(String dir, boolean deleteDir) {
    if (null == dir || "".equals(dir)) {
      throw new RuntimeException("dir can not be null");
    }
    File file = new File(dir);
    if (!file.exists()) {
      return true;
    }
    if (file.isDirectory()) {
      String[] children = file.list();
      if (null != children && children.length > 0) {
        for (int i = 0; i < children.length; i++) {
          deleteFile(dir + File.separator + children[i], deleteDir);
        }
      }
      if (deleteDir) {
        return file.delete();
      } else {
        return true;
      }
    } else {
      return file.delete();
    }
  }


  /**
   * TODO:生成文件
   *
   * @param dir 目录
   * @param fileName 文件名称
   * @param inputStream 流
   */
  public static boolean generatedFile(String dir, String fileName, InputStream inputStream) {
    OutputStream out = null;
    try {
      File file = new File(dir);
      if (!file.exists()) {
        file.mkdir();
      }
      String fullPath = dir + File.separator + fileName;
      file = new File(fullPath);
      if (file.exists()) {
        deleteFile(fullPath, false);
      }
      file.createNewFile();

      out = new FileOutputStream(fullPath);
      int ch = 0;
      while ((ch = inputStream.read()) != -1) {
        out.write(ch);
      }
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    } finally {
      try {
        if (null != out) {
          out.flush();
          out.close();
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  public static void main(String[] args) throws Exception {
//        System.out.println(FileUtils.deleteFile("D://test", true));

//        File file = new File("D://test.zip");
//        InputStream in = new FileInputStream(file);
//        System.out.println(writeFile("D://", "t.zip", in));

  }
}
