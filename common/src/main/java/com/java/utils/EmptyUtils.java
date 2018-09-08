package com.java.utils;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

/**
 * TODO：判断是否为空工具类
 */
public class EmptyUtils {

  public static boolean isEmpty(String str) {
    return null == str || "".equals(str);
  }

  public static boolean isNotEmpty(String str) {
    return !isEmpty(str);
  }

  /**
   * TODO:字符串是否都为空
   *
   * @return true ：都为空；false：有任何一个不为空
   */
  public static boolean isAllEmpty(String... args) {
    if (null == args || args.length == 0) {
      return true;
    }
    for (String str : args) {
      if (isNotEmpty(str)) {
        return false;
      }
    }
    return true;
  }

  /**
   * TODO:都不为空
   *
   * @return true：都不为空；false：有任何一个为空
   */
  public static boolean isAllNotEmpty(String... args) {
    if (null == args || args.length == 0) {
      return false;
    }
    for (String str : args) {
      if (isEmpty(str)) {
        return false;
      }
    }
    return true;
  }

  /**
   * TODO:有任何一个为空
   *
   * @return true：有任何一个为空；false:都不为空
   */
  public static boolean isAnyEmpty(String... args) {
    if (null == args || args.length == 0) {
      return true;
    }
    for (String str : args) {
      if (isEmpty(str)) {
        return true;
      }
    }
    return false;
  }

  /**
   * TODO:有任何一个不为空
   *
   * @return true:有任何一个不为空；false:都为空
   */
  public static boolean isAnyNotEmpty(String... args) {
    return !isAllEmpty(args);
  }

  public static boolean isEmpty(Collection colls) {
    return null == colls || colls.isEmpty();
  }

  public static boolean isNotEmpty(Collection colls) {
    return !isEmpty(colls);
  }

  public static boolean isEmpty(Long l) {
    return null == l;
  }

  public static boolean isNotEmpty(Long l) {
    return !isEmpty(l);
  }

  public static boolean isEmpty(Map map) {
    return map == null || map.isEmpty();
  }

  public static boolean isNotEmpty(Map map) {
    return !isEmpty(map);
  }

  public static boolean isEmpty(Object[] obj) {
    return obj == null || obj.length <= 0;
  }

  public static boolean isNotEmpty(Object[] obj) {
    return !isEmpty(obj);
  }

  public static boolean isEmpty(Date date) {
    return date == null;
  }

  public static boolean isNotEmpty(Date date) {
    return !isEmpty(date);
  }

  public static boolean isEmpty(Integer itg) {
    return itg == null;
  }

  public static boolean isNotEmpty(Integer itg) {
    return !isEmpty(itg);
  }

  /**
   * 判断数组
   */
  public static boolean isEmptyArray(Object[] obj) {
    return obj == null || obj.length <= 0;
  }

  public static boolean isAnyEmptyArray(Object[]... objs) {
    for (Object[] obj : objs) {
      if (isEmptyArray(obj)) {
        return false;
      }
    }
    return true;
  }

  /**
   * TODO:判断字符串是否为空白 如 null, "", "   "都将返回true.
   */
  public static boolean isTrimBlank(String str) {
    if (str == null || str.trim().length() == 0) {
      return true;
    } else {
      return false;
    }
  }

  public static void main(String[] args) {
    String a = null;
    String b = "";
    String c = "c";
    String d = "d";
    //        System.out.println(isEmpty(a));
    //        System.out.println(isEmpty(a, b));
    //        System.out.println(isEmpty(a, b, c));
    //        System.out.println(isEmpty(c, d));
    //        System.out.println();
    //        System.out.println(isNotEmpty(a));
    //        System.out.println(isNotEmpty(a, b));
    //        System.out.println(isNotEmpty(a, b, c));
    //        System.out.println(isNotEmpty(c, d));

    //        是否都为空
    //        System.out.println(isAllEmpty(c,d));
    //        System.out.println(isAllEmpty(a,c,d));
    //        System.out.println(isAllEmpty(a,b,c,d));
    //        System.out.println(isAllEmpty(b));
    //        System.out.println(isAllEmpty(a,b));
    //是否都不为空
    //        System.out.println(isAllNotEmpty(c,d));
    //        System.out.println(isAllNotEmpty(a,c,d));
    //        System.out.println(isAllNotEmpty(a,b,c,d));
    //        System.out.println(isAllNotEmpty(b));
    //        System.out.println(isAllNotEmpty(a,b));
    //是否有任何一个为空
    //        System.out.println(isAnyEmpty(c, d));
    //        System.out.println(isAnyEmpty(a, c, d));
    //        System.out.println(isAnyEmpty(a, b, c, d));
    //        System.out.println(isAnyEmpty(b));
    //        System.out.println(isAnyEmpty(a, b));
    //是否有任何一个不为空
    System.out.println(isAnyNotEmpty(c, d));
    System.out.println(isAnyNotEmpty(a, c, d));
    System.out.println(isAnyNotEmpty(a, b, c, d));
    System.out.println(isAnyNotEmpty(b));
    System.out.println(isAnyNotEmpty(a, b));
  }
}

