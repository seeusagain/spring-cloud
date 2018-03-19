package com.java.utils;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by lu.xu on 2017/9/25.
 * TODO:ping IP
 */
public class PingIPUtils {
    private static Logger logger = Logger.getLogger(PingIPUtils.class);

    private static final int timeOutMillisecond = 3000;
    private static final int pingTimes = 3;

    public static boolean ping(String ip) {
        return ping(ip, pingTimes, timeOutMillisecond);
    }

    public static boolean ping(String ip, int timeOutMillisecond) {
        return ping(ip, pingTimes, timeOutMillisecond);
    }

    public static boolean ping(String ip, int pingTimes, int timeOutMillisecond) {
        // 获取当前程序的运行进对象
        Runtime runtime = Runtime.getRuntime();
        // 声明处理类对象
        Process process = null;
        // 返回行信息
        String line = null;
        // 输入流
        InputStream is = null;
        // 字节流
        InputStreamReader isr = null;
        BufferedReader br = null;
        boolean res = false;

        String pingCommand = "ping " + ip + " -n " + pingTimes + " -w " + timeOutMillisecond;

        try {
            // PING
            process = runtime.exec(pingCommand);
            // 实例化输入流
            is = process.getInputStream();
            // 把输入流转换成字节流,传入参数为了解决"gbk"中文乱码问题
            isr = new InputStreamReader(is, "gbk");
            // 从字节中读取文本
            br = new BufferedReader(isr);
            while ((line = br.readLine()) != null) {
                line = new String(line.getBytes("UTF-8"), "UTF-8");
                //通了
                if (line.contains("TTL")) {
                    res = true;
                    break;
                }
            }
        } catch (IOException e) {
            logger.error("ping Ip出现异常:\n" + e.getMessage());
            runtime.exit(1);
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (isr != null) {
                    isr.close();
                }
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {

            }
        }
        return res;
    }

    public static void main(String[] args) {
        String ip = "127.0.0.1";
        System.out.println(PingIPUtils.ping(ip, 1, 5000));
    }

}
