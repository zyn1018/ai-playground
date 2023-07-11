package com.yinan.play.demo.util;

import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.yinan.play.demo.meta.AddReservationParamVO;
import com.yinan.play.demo.meta.AddReservationUserParamVO;
import com.yinan.play.demo.meta.ReservationItemInfoVO;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * @author Yinan Zhang (zhangyinan01@corp.netease.com)
 */
public class AesUtil {
    /**
     * 加密
     *
     * @param content 加密文本
     * @param key     加密密钥，appSecret的前16位
     * @param iv      初始化向量，appSecret的后16位
     * @return
     * @throws Exception
     */
    public static String encrypt(String content, String key, String iv) throws Exception {
        byte[] raw = key.getBytes();
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        //"算法/模式/补码方式"
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        //使用CBC模式，需要一个向量iv，可增加加密算法的强度
        IvParameterSpec ivParam = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivParam);
        byte[] encrypted = cipher.doFinal(content.getBytes());

        return new BASE64Encoder().encode(encrypted);
    }

    public static String decrypt(String content, String key, String iv) throws Exception {
        byte[] raw = key.getBytes();
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        //"算法/模式/补码方式"
        IvParameterSpec ivParam = new IvParameterSpec(iv.getBytes());
        //使用CBC模式，需要一个向量iv，可增加加密算法的强度
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, ivParam);
        byte[] encrypted = new BASE64Decoder().decodeBuffer(content);
        //先用base64解密
        byte[] original = cipher.doFinal(encrypted);
        return new String(original);
    }

    public static void main(String[] args) throws Exception {
        AddReservationUserParamVO paramVO = new AddReservationUserParamVO();
        paramVO.setSessionId(3070L);
        paramVO.setShopId("100520300007");
        paramVO.setUserId(271848L);

        List<ReservationItemInfoVO> itemInfoVOS = Lists.newArrayList();
        ReservationItemInfoVO reservationItemInfoVO = new ReservationItemInfoVO();
        reservationItemInfoVO.setItemId("169");
        reservationItemInfoVO.setCount(1);
        itemInfoVOS.add(reservationItemInfoVO);
        paramVO.setItemInfoList(itemInfoVOS);

        String encrypt = AesUtil.encrypt(JSON.toJSONString(paramVO), "65ebf1fc37fc43$7ac22ca0880c718e#", "4766913974984646");
        AddReservationParamVO encryptedBody = new AddReservationParamVO();
        encryptedBody.setEncryptedParam(encrypt);
        System.out.println(JSON.toJSONString(encryptedBody));
        String decrypt = AesUtil.decrypt(encryptedBody.getEncryptedParam(), "65ebf1fc37fc43$7ac22ca0880c718e#", "4766913974984646");
        System.out.println(decrypt);
        AddReservationParamVO decryptedParam = JSON.parseObject(decrypt, AddReservationParamVO.class);
        System.out.println(JSON.toJSONString(decryptedParam));
    }
}
