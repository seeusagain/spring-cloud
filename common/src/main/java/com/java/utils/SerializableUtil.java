package com.java.utils;

import org.apache.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by lu.xu on 2017/6/21.
 * TODO:序列化与反序列化
 */
public class SerializableUtil {
    private static Logger logger = Logger.getLogger(SerializableUtil.class);

    /**
     * 序列化
     *
     * @param obj
     * @return
     * @throws Exception
     */
    public static byte[] objToByte(Object obj) throws Exception {
        if (null == obj) {
            return null;
        }
        byte[] bytes = null;
        ByteArrayOutputStream bos = null;
        ObjectOutputStream oos = null;
        try {
            bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            bytes = bos.toByteArray();
        } catch (IOException ex) {
            logger.error(">>序列化异常..");
            throw ex;
        } finally {
            oos.flush();
            oos.close();
            bos.close();

        }
        return bytes;
    }

    /**
     * 反序列化
     *
     * @param context
     * @return
     * @throws Exception
     */
    public static Object byteToObj(byte[] context) throws Exception {
        if (null == context || context.length == 0) {
            return null;
        }
        Object obj = null;
        ByteArrayInputStream bis = null;
        ObjectInputStream ois = null;
        try {
            bis = new ByteArrayInputStream(context);
            ois = new ObjectInputStream(bis);
            obj = ois.readObject();
        } catch (Exception ex) {
            logger.error(">>反序列化异常..");
            throw ex;
        } finally {
            bis.close();
            ois.close();
        }
        return obj;
    }

}
