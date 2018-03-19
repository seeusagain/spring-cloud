package com.java.utils.encrypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * TODO(MD5加密类).
 *
 * @author 许路
 * @version v 1.0
 * @ClassName: MdRandmBo
 * @date: 2016年4月18日 上午11:07:58
 */
public class Md5Encryption {

    /**
     * TODO(加密，可用于用户密码加密)    .
     *
     * @param str the str
     * @return String
     * String 返回值
     * @author 许路
     * @Title: Encription
     */
    public static String MD5Encription(String str) {
        MessageDigest md = null;
        String ss = "";
        try {
            md = MessageDigest.getInstance("MD5"); // 获取MD5加密实例
            byte[] b = md.digest(str.getBytes()); // 加密
            ss = parseByte2HexStr(b); // 转换字符编码
            ss = ss.substring(0, 18); // 返回8位加密
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return coverString(ss);
    }

    /**
     * TODO(字符串大小转换).
     *
     * @param str the str
     * @return String
     * String 返回值
     * @author 许路
     * @Title: coverString
     */
    public static String coverString(String str) {
        String upStr = str.toUpperCase();
        String lowStr = str.toLowerCase();
        StringBuffer buf = new StringBuffer();

        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == upStr.charAt(i)) {
                buf.append(lowStr.charAt(i));
            } else {
                buf.append(upStr.charAt(i));
            }
        }
        return buf.toString().toUpperCase();
    }

    /**
     * TODO(将二进制转换成16进制).
     *
     * @param buf the buf
     * @return String
     * String 返回值
     * @author 许路
     * @Title: parseByte2HexStr
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

    /**
     * TODO(将16进制转换为二进制).
     *
     * @param hexStr the hex str
     * @return byte[]
     * byte[] 返回值
     * @author 许路
     * @Title: parseHexStr2Byte
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1) {
            return null;
        }
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
        System.out.println(MD5Encription("123456"));
    }
}
