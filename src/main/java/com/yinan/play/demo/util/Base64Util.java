/**
 * @(#)Base64Util.java, 2016年5月14日.
 *
 * Copyright 2016 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.yinan.play.demo.util;

import java.io.UnsupportedEncodingException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * base64加密解密工具类
 * 
 * @author hzyurui
 */
public class Base64Util {
    /**
     * 对字符串做BASE64加密
     * 
     * @param s
     * @return
     */
    @SuppressWarnings("restriction")
    public static String encrypt(String s) {
        try {
            byte[] bytes = s.getBytes("UTF-8");
            return encode(bytes);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 对字符串做BASE64解密
     * 
     * @param s
     * @return
     */
    @SuppressWarnings("restriction")
    public static String decrypt(String s) {
        if (s != null) {
            try {
                byte[] bytes = decode(s);
                return new String(bytes, "UTF-8");
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    /**
     * 对字符串做BASE64解密
     * 
     * @param s
     * @return
     */
    @SuppressWarnings("restriction")
    public static byte[] decryptToBytes(String s) {
        if (s != null) {
            try {
                byte[] bytes = decode(s);
                return bytes;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public static String encode(byte[] bytes) {
        return new BASE64Encoder().encode(bytes);
    }

    public static byte[] decode(String s) {
        try {
            return new BASE64Decoder().decodeBuffer(s);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
