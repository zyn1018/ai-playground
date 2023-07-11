package com.yinan.util;

import java.security.MessageDigest;

/**
 * 这个类用于加密解密
 * 
 * @author kai.wang
 */
public class DigestUtils {
    public static final String MD5 = "MD5";

    public static final String SHA1 = "SHA1";

    private static final char[] DIGITS_LOWER = { '0', '1', '2', '3', '4', '5',
        '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    /**
     * 将字节数组转换为16进制字符串的形式.
     */
    public static final String bytesToHexStr(byte[] bcd) {
        StringBuffer s = new StringBuffer(bcd.length * 2);

        for (int i = 0; i < bcd.length; i++) {
            s.append(DIGITS_LOWER[(bcd[i] >>> 4) & 0x0f]);
            s.append(DIGITS_LOWER[bcd[i] & 0x0f]);
        }

        return s.toString();
    }

    /**
     * 将16进制字符串还原为字节数组.
     */
    public static final byte[] hexStrToBytes(String s) {
        byte[] bytes;

        bytes = new byte[s.length() / 2];

        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(s.substring(2 * i, 2 * i + 2),
                16);
        }

        return bytes;
    }

    /**
     * 对字符串进行md5加密
     * 
     * @param orign
     * @return
     */
    public static String md5(String orign) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(orign.getBytes("UTF-8"));
            byte[] data = md.digest();

            return bytesToHexStr(data);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 对字符串进行sha1加密
     */
    public static String sha1(String orign) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(orign.getBytes("UTF-8"));
            byte[] data = md.digest();
            int l = data.length;
            char[] out = new char[l << 1];

            int i = 0;
            for (int j = 0; i < l; ++i) {
                out[(j++)] = DIGITS_LOWER[((0xF0 & data[i]) >>> 4)];
                out[(j++)] = DIGITS_LOWER[(0xF & data[i])];
            }
            return new String(out);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 对字符串进行sha1加密
     */
    public static String sha256(String orign) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(orign.getBytes("UTF-8"));
            byte[] data = md.digest();
            int l = data.length;
            char[] out = new char[l << 1];

            int i = 0;
            for (int j = 0; i < l; ++i) {
                out[(j++)] = DIGITS_LOWER[((0xF0 & data[i]) >>> 4)];
                out[(j++)] = DIGITS_LOWER[(0xF & data[i])];
            }
            return new String(out);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
