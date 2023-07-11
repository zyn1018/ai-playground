package com.yinan.util;

import java.io.UnsupportedEncodingException;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Yinan Zhang (zhangyinan01@corp.netease.com)
 */
public class RandomNameUtil {
    public static String getRandomChinese(int len) {
        StringBuilder ret = new StringBuilder();
        for (int i = 0; i < len; i++) {
            String str = null;
            int highPos, lowPos; // 定义高低位
            Random random = new Random();
            highPos = (176 + Math.abs(random.nextInt(39))); // 获取高位值
            lowPos = (161 + Math.abs(random.nextInt(93))); // 获取低位值
            byte[] b = new byte[2];
            b[0] = (new Integer(highPos).byteValue());
            b[1] = (new Integer(lowPos).byteValue());
            try {
                str = new String(b, "GBK"); // 转成中文
            } catch (UnsupportedEncodingException ex) {
                ex.printStackTrace();
            }
            ret.append(str);
        }
        return ret.toString();
    }

    //生成随机用户名，数字和字母组成,
    public static String getStringRandom(int length) {
        StringBuilder val = new StringBuilder();
        Random random = new Random();

        //参数length，表示生成几位随机数
        for (int i = 0; i < length; i++) {

            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            //输出字母还是数字
            if ("char".equalsIgnoreCase(charOrNum)) {
                //输出是大写字母还是小写字母
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val.append((char) (random.nextInt(26) + temp));
            } else {
                val.append(random.nextInt(10));
            }
        }
        return val.toString();
    }


    public static String processRealName(String fullName) {
        if (StringUtils.isNotBlank(fullName)) {
            String name = StringUtils.left(fullName, 1);
            return StringUtils.rightPad(name, StringUtils.length(fullName), "*");
        }
        return fullName;
    }


    public static void main(String[] args) {
        Random random = new Random();
//        for (int i = 0; i < 20; i++) {
//            int randomNum = random.nextInt(6) + 3;
//            String originName = RandomNameUtil.getRandomChinese(randomNum);
//            System.out.println("origin name: " + originName);
//            System.out.println("processed name: " + RandomNameUtil.processRealName(originName));
//
//        }

        for (int i = 0; i < 20; i++) {
            int randomNum = random.nextInt(6) + 5;
            String originName = RandomNameUtil.getStringRandom(randomNum);
            System.out.println("origin name: " + originName);
            System.out.println("processed name: " + RandomNameUtil.processRealName(originName));

        }
    }
}
