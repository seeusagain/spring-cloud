package com.java.utils.encrypt;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * TODO(AES加、解密)    .
 *
 * @author 许路
 * @version v 1.0
 * @ClassName: AESEncription
 * @date: 2016年4月18日 下午1:35:01
 */
public class AESEncription {
    
    /**
     * 自定义密钥
     */
    public static final String SECRET_KEY = "Butterfly";
    
    /**
     * TODO：加密（使用默认秘钥）
     * @param content
     * @return
     * @throws Exception
     */
    public static String encrypt(String content) throws Exception {
        return encrypt(content, SECRET_KEY);
    }
    
    /**
     * TODO：加密（使用自定义秘钥）
     * @param content
     * @param secretKey
     * @return
     * @throws Exception
     */
    public static String encryptWithKey(String content, String secretKey) throws Exception {
        return encrypt(content, secretKey);
    }
    
    private static String encrypt(String content, String secretKeyStr) throws Exception {
        SecretKey secretKey = getSecretKey(secretKeyStr);
        byte[] enCodeFormat = secretKey.getEncoded();
        SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
        // 创建密码器
        Cipher cipher = Cipher.getInstance("AES");
        byte[] byteContent = content.getBytes("utf-8");
        // 初始化
        cipher.init(Cipher.ENCRYPT_MODE, key);
        // 加密
        byte[] result = cipher.doFinal(byteContent);
        //二进制转16进制
        return parseByte2HexStr(result);
    }
    
    /**
     * TODO:解密（使用默认秘钥）
     * @param content
     * @return
     * @throws Exception
     */
    public static String decrypt(String content) throws Exception {
        return decrypt(content, SECRET_KEY);
    }
    
    /**
     * TODO:解密（使用自定义秘钥）
     * @param content
     * @return
     * @throws Exception
     */
    public static String decryptWithKey(String content, String secretKey) throws Exception {
        return decrypt(content, secretKey);
    }
    
    private static String decrypt(String content, String secretKeyStr) throws Exception {
        try {
            SecretKey secretKey = getSecretKey(secretKeyStr);
            byte[] enCodeFormat = secretKey.getEncoded();
            //16进制转2进制
            byte[] decryptFrom = parseHexStr2Byte(content);
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            // 创建密码器
            Cipher cipher = Cipher.getInstance("AES");
            // 初始化
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] result = cipher.doFinal(decryptFrom);
            // 解密
            return new String(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * TODO:加密文件（使用默认秘钥）
     * @param content
     * @return
     * @throws Exception
     */
    public static byte[] encryptFile(byte[] content) throws Exception {
        return encryptFile(content, SECRET_KEY);
    }
    
    /**
     * TODO:加密文件（使用自定义秘钥）
     * @param content
     * @return
     * @throws Exception
     */
    public static byte[] encryptFileWithKey(byte[] content, String secretKey) throws Exception {
        return encryptFile(content, secretKey);
    }
    
    private static byte[] encryptFile(byte[] content, String secretKeyStr) throws Exception {
        SecretKey secretKey = getSecretKey(secretKeyStr);
        byte[] enCodeFormat = secretKey.getEncoded();
        SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
        // 创建密码器
        Cipher cipher = Cipher.getInstance("AES");
        // 初始化
        cipher.init(Cipher.ENCRYPT_MODE, key);
        // 加密
        return cipher.doFinal(content);
    }
    
    /**
     * TODO:解密文件（使用默认秘钥）
     * @param content
     * @return
     * @throws Exception
     */
    public static byte[] decryptFile(byte[] content) throws Exception {
        return encryptFile(content, SECRET_KEY);
    }
    
    /**
     * TODO:解密文件（使用自定义秘钥）
     * @param content
     * @return
     * @throws Exception
     */
    public static byte[] decryptFileWithKey(byte[] content, String secretKey) throws Exception {
        return encryptFile(content, secretKey);
    }
    
    private static byte[] decryptFile(byte[] content, String secretKeyStr) throws Exception {
        SecretKey secretKey = getSecretKey(secretKeyStr);
        byte[] enCodeFormat = secretKey.getEncoded();
        SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
        // 创建密码器
        Cipher cipher = Cipher.getInstance("AES");
        // 初始化
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(content);
    }
    
    /**
     * 获取秘钥
     * @param secretKey
     * @return
     */
    public static SecretKey getSecretKey(String secretKey) throws Exception {
        if (null == secretKey || "".equals(secretKey)) {
            throw new RuntimeException(" secretKey can not be null...");
        }
        //获取密钥生成器
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        //获取强加密随机数生成器
        SecureRandom srd = SecureRandom.getInstance("SHA1PRNG");
        //根据自定义密码重新设置随机对象的种子
        srd.setSeed(secretKey.getBytes());
        kgen.init(128, srd);
        return kgen.generateKey();
    }
    
    /**
     * TODO(将二进制转换成16进制)    .
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
     * TODO(将二进制转换成16进制)    .
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
    
    public static void main(String[] args) {
        try {
            String str = "测试中文TEST-chinese";
            String str2 = AESEncription.encrypt(str);
            String str3 = AESEncription.decrypt(str2);
            System.out.println(str2);
            System.out.println(str3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
