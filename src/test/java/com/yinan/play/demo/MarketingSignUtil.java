package com.yinan.play.demo;

import com.yinan.util.DigestUtils;

/**
 * @author Yinan Zhang (zhangyinan01@corp.netease.com)
 */
public class MarketingSignUtil {

    public static void genSign() {
        String appKey = "c883f2402d484833ac692cffc15e0295";
        String appSecret = "835bc7f92d4143f39aae9d00026a7729";
        String uid = "40017726257";
        long timestamp = 1682214670000L;

        String sign = DigestUtils.md5(appKey + uid + timestamp + appSecret);
        System.err.println(sign);
    }

    public static void main(String[] args) {
        genSign();
    }
}
