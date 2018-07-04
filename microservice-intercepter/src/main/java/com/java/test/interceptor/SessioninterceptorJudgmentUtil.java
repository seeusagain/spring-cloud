package com.java.test.interceptor;

import java.util.HashMap;
import java.util.Map;

import com.java.constants.AuthenticationConstats;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by lu.xu on 2018/7/2.
 * TODO: 会话拦截器工具类
 */
public class SessioninterceptorJudgmentUtil {
    private static final Logger logger = LoggerFactory.getLogger(SessioninterceptorJudgmentUtil.class);
    
    /**
     * 定义可以直接访问的URL，支持前后通配符
     * key-接口
     * val-描述
     */
    private static final HashMap<String, String> ignoreUrl = new HashMap<>();
    
    private static final String wildcard = "*";
    
    static {
        ignoreUrl.put(AuthenticationConstats.AUTHENTICATION_LOGIN_URL, "登录接口");
        ignoreUrl.put(AuthenticationConstats.AUTHENTICATION_LOGOUT_PAGE_URL, "登录界面接口");
        ignoreUrl.put(AuthenticationConstats.AUTHENTICATION_LOGOUT_URL, "注销接口");
        ignoreUrl.put("/swagger" + wildcard, "swagger界面");
        ignoreUrl.put("/v2/api-docs", "swagger界面");
        
    }
    
    /**
     * 判断访问链接是否要进行拦截
     * @param accessUrl
     * @return true-URL可以直接访问，false-不可以直接访问（拦截）
     */
    public static boolean judgment(String accessUrl) {
        boolean flag = false;
        accessUrl = accessUrl.replaceAll("//", "/");
        //去掉jsessionid，例如访问的时候就会有：/;jsessionid=3DDB341C460AD3BA3EFA65FC2BD3D1DC/authentication/login
        final String sessionStr = "jsessionid";
        if (accessUrl.indexOf(sessionStr) > -1) {
            int index = accessUrl.indexOf(sessionStr);
            int startIndex = -1;
            int endIndex = -1;
            for (int i = index - 1; i >= 0; i--) {
                String currentChar = accessUrl.substring(i, i + 1);
                if (currentChar.equals("/")) {
                    startIndex = i;
                    break;
                }
            }
            
            for (int i = index + sessionStr.length(); i < accessUrl.length(); i++) {
                String currentChar = accessUrl.substring(i, i + 1);
                if (currentChar.equals("/")) {
                    endIndex = i;
                    break;
                }
            }
            if (startIndex < 0 || endIndex < 0) {
                logger.error("parameter error!  startIndex：{}；endIndex：{}", startIndex, endIndex);
                return false;
            } else {
                accessUrl = accessUrl.replace(accessUrl.substring(startIndex, endIndex), "");
                logger.info("访问地址带有session信息，过滤后地址：{}", accessUrl);
            }
        }
        
        for (Map.Entry<String, String> entry : ignoreUrl.entrySet()) {
            String currentIgnoreUrl = entry.getKey();
            if (currentIgnoreUrl.equals(wildcard)) {
                return true;
            }
            //前后都通配
            if (currentIgnoreUrl.startsWith(wildcard) && currentIgnoreUrl.endsWith(wildcard)) {
                int index = accessUrl.indexOf(currentIgnoreUrl.replaceAll(wildcard, ""));
                flag = index > -1 ? true : false;
            }
            //通配符号在前
            else if (currentIgnoreUrl.startsWith(wildcard)) {
                flag = accessUrl.endsWith(currentIgnoreUrl.replace(wildcard, ""));
            }
            //通配符号在后
            else if (currentIgnoreUrl.endsWith(wildcard)) {
                flag = accessUrl.startsWith(currentIgnoreUrl.replace(wildcard, ""));
            }
            //不通配
            else {
                flag = ignoreUrl.containsKey(accessUrl);
            }
            if (flag) {
                break;
            }
        }
        return flag;
    }
}
