package com.java.utils.encrypt;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

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
     * The Constant keyword.
     */
    public static final String keyword = "butterFly"; //自定义密钥

    /**
     * TODO(生成加密密钥)
     *
     * @return SecretKey 返回值
     * @author 许路
     * @Title: getSecretKey
     */
    public static SecretKey getSecretKey() {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES"); //获取密钥生成器
            SecureRandom srd = SecureRandom.getInstance("SHA1PRNG"); //获取强加密随机数生成器
            srd.setSeed(keyword.getBytes()); //根据自定义密码重新设置随机对象的种子
            kgen.init(128, srd);
            return kgen.generateKey();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * TODO(加密 )    .
     *
     * @param content 需要加密的内容
     * @return String
     * String 返回值
     * @author 许路
     * @Title: encrypt
     */
    public static String encrypt(String content) throws Exception {
        SecretKey secretKey = getSecretKey();
        byte[] enCodeFormat = secretKey.getEncoded();
        SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
        Cipher cipher = Cipher.getInstance("AES"); // 创建密码器
        byte[] byteContent = content.getBytes("GBK");
        cipher.init(Cipher.ENCRYPT_MODE, key); // 初始化
        byte[] result = cipher.doFinal(byteContent); // 加密
        String encryptResultStr = parseByte2HexStr(result); //二进制转16进制
        return encryptResultStr;
    }

    /**
     * TODO(解密 )    .
     *
     * @param content the content
     * @return String
     * String 返回值
     * @author 许路
     * @Title: decrypt
     */
    public static String decrypt(String content) {
        try {
            byte[] decryptFrom = parseHexStr2Byte(content); //16进制转2进制
            SecretKey secretKey = getSecretKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES"); // 创建密码器
            cipher.init(Cipher.DECRYPT_MODE, key); // 初始化
            byte[] result = cipher.doFinal(decryptFrom);
            return new String(result); // 加密
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * TODO(文件加密)    .
     *
     * @param content the content
     * @return byte[]
     * byte[] 返回值
     * @author 许路
     * @Title: encryptFile
     */
    public static byte[] encryptFile(byte[] content) {
        try {
            SecretKey secretKey = getSecretKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES"); // 创建密码器
            cipher.init(Cipher.ENCRYPT_MODE, key); // 初始化
            byte[] result = cipher.doFinal(content); // 加密
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * TODO(文件解密)    .
     *
     * @param content the content
     * @return byte[]
     * byte[] 返回值
     * @author 许路
     * @Title: decryptFile
     */
    public static byte[] decryptFile(byte[] content) {
        try {
            SecretKey secretKey = getSecretKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES"); // 创建密码器
            cipher.init(Cipher.DECRYPT_MODE, key); // 初始化
            byte[] result = cipher.doFinal(content);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
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
}
