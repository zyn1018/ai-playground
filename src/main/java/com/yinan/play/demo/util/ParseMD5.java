/**
 * 
 */
package com.yinan.play.demo.util;

import java.security.MessageDigest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description: 将字符串转化为MD5加密后字符串
 */
public class ParseMD5 {

    /**
     * 日志记录
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ParseMD5.class);

    /**
     * 32位小写MD5
     */
    public static String parseStrToMd5L32(String str) {

        if (null == str) {
            LOGGER.warn(".parseStrToMd5L32() str is null.");
            return str;
        }

        String reStr;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(str.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte b: bytes) {
                int bt = b & 0xff;
                if (bt < 16) {
                    sb.append(0);
                }
                sb.append(Integer.toHexString(bt));
            }

            reStr = sb.toString();

            return reStr;

        } catch (Exception e) {
            LOGGER.error(".parseStrToMd5L32() Exception={},param={}", e, str, e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 32位大写MD5
     */
    public static String parseStrToMd5U32(String str) {
        String reStr = parseStrToMd5L32(str);
        if (null != reStr) {
            reStr = reStr.toUpperCase();
        }
        return reStr;
    }

    /**
     * 16位小写MD5
     */
    public static String parseStrToMd5U16(String str) {
        String reStr = parseStrToMd5L32(str);
        if (reStr != null) {
            reStr = reStr.toUpperCase().substring(8, 24);
        }
        return reStr;
    }

    /**
     * 16位大写MD5
     */
    public static String parseStrToMd5L16(String str) {
        String reStr = parseStrToMd5L32(str);
        if (reStr != null) {
            reStr = reStr.substring(8, 24);
        }
        return reStr;
    }

}
