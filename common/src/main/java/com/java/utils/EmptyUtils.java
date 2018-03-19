package com.java.utils;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

/**
 * TODO(判断是否为空工具类).
 *
 * @author 许路
 * @version v 1.0
 * @ClassName: EmptyUtils
 * @date: 2016年4月18日 下午2:08:26
 */
public class EmptyUtils {
    
    /**
     * TODO(这里用一句话描述这个方法的作用).
     *
     * @param colls the colls
     * @return boolean
     * @author mali
     * @title: isEmpty
     * @date: 2016-5-20 15:11:47
     */
    //判断集合
    public static boolean isEmpty(Collection colls) {
        return null == colls || colls.isEmpty();
    }
    
    /**
     * TODO(这里用一句话描述这个方法的作用).
     *
     * @param colls the colls
     * @return boolean
     * @author mali
     * @title: isNotEmpty
     * @date: 2016-5-20 15:11:47
     */
    public static boolean isNotEmpty(Collection colls) {
        return !isEmpty(colls);
    }
    
    /**
     * 判断string 是否null或者空
     * @param str
     * @return true 是，false 否
     */
    public static boolean isEmpty(String str) {
        return null == str || "".equals(str);
    }
    
    /**
     * 判断参数是否为空
     *
     * @param args
     * @return true 为空/都为空；false 有任何一个不为空
     */
    @Deprecated
    public static boolean isEmpty(String... args) {
        if (null == args || args.length == 0) {
            return true;
        }
        boolean flag = true;
        for (String str : args) {
            flag = isEmpty(str);
            if (!flag) {
                break;
            }
        }
        return flag;
    }
    
    /**
     * TODO:都为空
     * @param args
     * @return true ：都为空；false：有任何一个不为空
     */
    public static boolean isAllEmpty(String... args) {
        if (null == args || args.length == 0) {
            return true;
        }
        boolean flag = true;
        for (String str : args) {
            flag = isEmpty(str);
            if (!flag) {
                break;
            }
        }
        return flag;
    }
    
    /**
     *TODO:都不为空
     * 其实是：isNotEmpty(String... args) 换了个名字
     * @param args
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
     * 拆分自 ：isEmpty(String... args)，使名字更易理解
     * @param args
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
     * @param args
     * @return true:有任何一个不为空；false:都为空
     */
    public static boolean isAnyNotEmpty(String... args) {
        return !isAllEmpty(args);
    }
    
    /**
     * TODO:是否不为空
     * @param str
     * @return true是，false否
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }
    
    /**
     * 判断参数是否不为空
     *
     * @param args
     * @return ture 都不为空，false 有任何一个为空
     */
    @Deprecated
    public static boolean isNotEmpty(String... args) {
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
     * TODO(这里用一句话描述这个方法的作用).
     *
     * @param l the l
     * @return boolean
     * @author mali
     * @title: isEmpty
     * @date: 2016-5-20 15:11:47
     */
    //判断Long
    public static boolean isEmpty(Long l) {
        return null == l;
    }
    
    /**
     * TODO(这里用一句话描述这个方法的作用).
     *
     * @param l the l
     * @return boolean
     * @author mali
     * @title: isNotEmpty
     * @date: 2016-5-20 15:11:47
     */
    public static boolean isNotEmpty(Long l) {
        return !isEmpty(l);
    }
    
    /**
     * TODO(这里用一句话描述这个方法的作用).
     *
     * @param map the map
     * @return boolean
     * @author mali
     * @title: isEmpty
     * @date: 2016-5-20 15:11:47
     */
    //判断Map
    public static boolean isEmpty(Map map) {
        return map == null || map.isEmpty();
    }
    
    /**
     * TODO(这里用一句话描述这个方法的作用).
     *
     * @param map the map
     * @return boolean
     * @author mali
     * @title: isNotEmpty
     * @date: 2016-5-20 15:11:47
     */
    public static boolean isNotEmpty(Map map) {
        return !isEmpty(map);
    }
    
    /**
     * TODO(这里用一句话描述这个方法的作用).
     *
     * @param obj the obj
     * @return boolean
     * @author mali
     * @title: isEmpty
     * @date: 2016-5-20 15:11:47
     */
    //判断数组
    public static boolean isEmpty(Object[] obj) {
        return obj == null || obj.length <= 0;
    }
    
    /**
     * TODO(这里用一句话描述这个方法的作用).
     *
     * @param obj the obj
     * @return boolean
     * @author mali
     * @title: isNotEmpty
     * @date: 2016-5-20 15:11:47
     */
    public static boolean isNotEmpty(Object[] obj) {
        return !isEmpty(obj);
    }
    
    /**
     * TODO(这里用一句话描述这个方法的作用).
     *
     * @param date the date
     * @return boolean
     * @author mali
     * @title: isEmpty
     * @date: 2016-5-20 15:11:47
     */
    //判断日期
    public static boolean isEmpty(Date date) {
        return date == null;
    }
    
    /**
     * TODO(这里用一句话描述这个方法的作用).
     *
     * @param date the date
     * @return boolean
     * @author mali
     * @title: isNotEmpty
     * @date: 2016-5-20 15:11:47
     */
    public static boolean isNotEmpty(Date date) {
        return !isEmpty(date);
    }
    
    /**
     * TODO(这里用一句话描述这个方法的作用).
     *
     * @param itg the itg
     * @return boolean
     * @author mali
     * @title: isEmpty
     * @date: 2016-5-20 15:11:47
     */
    //判断Integer
    public static boolean isEmpty(Integer itg) {
        return itg == null;
    }
    
    /**
     * TODO(这里用一句话描述这个方法的作用).
     *
     * @param itg the itg
     * @return boolean
     * @author mali
     * @title: isNotEmpty
     * @date: 2016-5-20 15:11:47
     */
    public static boolean isNotEmpty(Integer itg) {
        return !isEmpty(itg);
    }
    
    /**
     * 判断字符串是否为空白
     * 如 null, "", "   "都将返回true.
     *
     * @param str the str
     * @return boolean
     * boolean 返回值
     * @author jiangqiubai
     * @Title: isTrimBlank
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

