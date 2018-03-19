package com.java.utils;

import org.apache.log4j.Logger;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

// TODO: Auto-generated Javadoc

/**
 * TODO(HttpRequest工具类).
 *
 * @author xulu
 * @version v 1.0
 * @ClassName: HttpRequestUtil
 * @date: 2017-5-4 15:24:36
 */
public class HttpRequestUtil {
    /**
     * The logger.
     */
    private static Logger logger = Logger.getLogger(HttpRequestUtil.class);

    /**
     * 获取访问者IP地址
     *
     * @param request
     * @return
     */
    public static String getRemortIP(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("PRoxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * TODO:获取本机ip地址
     * @return
     */
    public static String getLocalIp() {
        String ip = null;
        try {
            //获取的是本地的IP地址 //PC-20140317PXKX/192.168.0.121
            InetAddress address = InetAddress.getLocalHost();
            ip = address.getHostAddress();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取本机IP地址异常");
        } finally {
            return ip;
        }
    }

    /**
     * 主要是为了调试打印一下
     *
     * @param request
     */
    public static void showRequestParameters(HttpServletRequest request) {
        Map<?, ?> map = request.getParameterMap();
        Iterator<?> iterator = map.keySet().iterator();
        logger.info(">>>>>>>>>>>>>>>>>>获取参数列表>>>>>>>>>>>>>>>>>>");
        while (iterator.hasNext()) {
            String key = String.valueOf(iterator.next());
            logger.info(key + " " + request.getParameter(key));
        }
        logger.info(">>>>>>>>>>>>>>>>>>end>>>>>>>>>>>>>>>>>>");
    }

    /**
     * 判断是否ajax请求
     *
     * @param request
     * @return
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        String header = request.getHeader("X-Requested-With");
        return "XMLHttpRequest".equals(header) ? true : false;
    }

    /**
     * 判断是否ajax请求
     *
     * @param request
     * @return
     */
    public static boolean isAjaxRequest(ServletRequest request) {
        return isAjaxRequest((HttpServletRequest) request);
    }

    /**
     * 测试URL是否有效
     *
     * @param url
     * @return
     */
    private static final int timeOutMillSeconds = 5000;

    public static boolean validityURL(String url, int timeOutMillSeconds) throws Exception {
        if (EmptyUtils.isEmpty(url)) {
            logger.error("校验URL,url空");
            return false;
        }
        long lo = System.currentTimeMillis();
        URL testUrl;
        try {
            testUrl = new URL(url);
            URLConnection co = testUrl.openConnection();
            co.setConnectTimeout(timeOutMillSeconds);
            co.connect();
            logger.info(">>校验URL 连接可用");
            return true;
        } catch (Exception e1) {
            logger.info(">>校验URL 连接不可用");
            return false;
        }
    }

    public static boolean validityURL(String url) throws Exception {
        return validityURL(url, timeOutMillSeconds);
    }

    private static final int httpConnectTimeOUT = 5000;

    private static final String httpConnectCharset = "utf-8";

    public static String httpURLConnectionGET(String url) {
        return httpURLConnectionGET(url, httpConnectTimeOUT, httpConnectCharset);
    }

    public static String httpURLConnectionGET(String url, String httpConnectCharset) {
        return httpURLConnectionGET(url, httpConnectTimeOUT, httpConnectCharset);
    }

    public static String httpURLConnectionGET(String url, int httpConnectTimeOUT) {
        return httpURLConnectionGET(url, httpConnectTimeOUT, httpConnectCharset);
    }

    /**
     * 调用http接口
     * GET请求
     *
     * @param url
     * @param connectTimeOUT
     * @param httpConnectCharset
     * @return
     */
    public static String httpURLConnectionGET(String url, int connectTimeOUT, String httpConnectCharset) {
        if (EmptyUtils.isEmpty(url)) {
            return null;
        }
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(), httpConnectCharset));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }
}
