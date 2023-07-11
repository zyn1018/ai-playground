package com.yinan.util;

import java.nio.charset.StandardCharsets;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang3.StringUtils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * @author Yinan Zhang (zhangyinan01@corp.netease.com)
 */
public class AesUtils {
    public static String decrypt(String cipherSource, String appSecret) {
        if (StringUtils.isBlank(cipherSource) || StringUtils.isBlank(appSecret)) {
            return StringUtils.EMPTY;
        }
        try {
            String key = appSecret.replaceAll("-", "");
            byte[] raw = key.getBytes(StandardCharsets.US_ASCII);
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            int blockSize = cipher.getBlockSize();
            IvParameterSpec iv = new IvParameterSpec(key.substring(0, blockSize).getBytes());
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            //先用base64解密 
            byte[] encrypted = new BASE64Decoder().decodeBuffer(cipherSource);
            byte[] original = cipher.doFinal(encrypted);
            return new String(original, StandardCharsets.UTF_8);
        } catch (Exception ex) {
            return StringUtils.EMPTY;
        }
    }

    public static String encrypt(String content, String appSecret) {
        if (StringUtils.isBlank(content) || StringUtils.isBlank(appSecret)) {
            return StringUtils.EMPTY;
        }
        try {
            String key = appSecret.replaceAll("-", "");
            byte[] raw = key.getBytes();
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            //"算法/模式/补码方式"
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            int blockSize = cipher.getBlockSize();
            //使用CBC模式，需要一个向量iv，可增加加密算法的强度
            IvParameterSpec iv = new IvParameterSpec(key.substring(0, blockSize).getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            byte[] encrypted = cipher.doFinal(content.getBytes());
            return new BASE64Encoder().encode(encrypted);
        } catch (Exception ex) {
            return StringUtils.EMPTY;
        }
    }

    public static void main(String[] args) {
        String appSecret = "20edee00-2fcf-405f-8407-31f826c35b5f";
        String encrypt = encrypt(
            "{\"msg_type\":1,\"msg\":\"{\\\"app_id\\\":7135692742461490719,\\\"order_info\\\":{\\\"order_id\\\":685993314284298888,\\\"shop_id\\\":28716195,\\\"service_start_time\\\":1671439629,\\\"service_end_time\\\":1676736000,\\\"status\\\":5,\\\"phone\\\":\\\"13117428566\\\",\\\"pay_amount\\\":1000,\\\"pay_time\\\":1671439653,\\\"order_create_time\\\":1671439653,\\\"pay_type\\\":1,\\\"push_sku_info\\\":{\\\"spec_type\\\":0,\\\"spec_value\\\":\\\"标准版\\\",\\\"price\\\":1000,\\\"duration\\\":3,\\\"duration_unit\\\":1,\\\"title\\\":\\\"标准版 三个月\\\"}}}\"}",
            appSecret);
        System.out.println(encrypt);

        System.out.println(decrypt(encrypt, appSecret));
    }
}
