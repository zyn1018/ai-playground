package com.yinan.play.demo.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.yinan.play.demo.meta.UserInfoBaseContext;


/**
 * @author 麦琳 (hzmailin@corp.netease.com)
 */
public class UserInfoUtil {

    public static final String HEADER_NAME_GATEWAY_USER_ID = "mt-userid";

    public static final String HEADER_NAME_DEVICE_ID = "MT-Device-ID";

    public static final String HEADER_NAME_APP_VERSION = "MT-APP-Version";

    public static final String HEADER_NAME_MT_TID = "MT-Request-ID";

    public static final String HEADER_NAME_LNG = "MT-Lng";

    public static final String HEADER_NAME_LAT = "MT-Lat";

    public static final String HEADER_NAME_RISK = "MT-R";

    public static final String HEADER_NAME_USER_AGENT = "User-Agent";

    public static final String HEADER_NAME_BIZID = "MT-BIZID";

    public static final String HEADER_NAME_CLIENT_TIME = "MT-K";

    public static final String HEADER_NAME_TOKEN = "MT-V";

    /**
     * clips协议前缀
     */
    private static final String CLIPS = "clips_";

    /**
     * clips协议解密密钥
     */
    private static final int CLIPS_KEY = 72;

    /**
     * { "MT-Token":
     * "izQywL002xAAIj7wBoI2QGABB5Ps8j7yPiOX1CHcm%2FbRL8pPEUaNVrbijaXs3NIyDkz1r9WBlYC9qCqWZUA6RpkHbFJesj7yPrI%2B8j",//
     * 登录 token "User-Agent": "MTNetworking/1.9.3.48 (iOS;14.0;Apple;iPhone9,1)",//User-Agent "Content-Type":
     * "application/json", "MT-Device-ID": "trust_id_ksjkafdjfkdfjkdajkdjgkadkf", // 设备 id "MT-APP-Version":
     * "AppStore_20211115@MT_iPhone_5.24.0.7500", //客户端版本信息 "MT-Request-ID": "X2C1PhWIwAMDANABi7ppmDuD", //客户端随机生成的本次请求的
     * id，可以根据 X-Request-ID 查客户端本地日志 "MT-Network-Type": "WIFI", //网络状态 "MT-Risk":
     * "X2C1PhWIwAMDANABi7ppmDuDX2C1PhWIwAMDANABi7ppmDuDX2C1PhWIwAMDANABi7ppmDuD", //应用风险 "MT-Bundle-ID":
     * "com.moutai.mall", //应用包名 "MT-Team-ID": "2f5ae2348f427a666876d7da3563a7d2", //android是签名、ios是teamid，防破解 }
     * 
     * @param request
     *            http-request
     * @return
     */
    public static UserInfoBaseContext getReqContext(HttpServletRequest request) {
        UserInfoBaseContext context = new UserInfoBaseContext();

        String userId = getOriginalStr(request.getHeader(HEADER_NAME_GATEWAY_USER_ID));
        if (StringUtils.isNotBlank(userId) && !NumberUtils.isNumber(userId)) {
            userId = "0";
        }

        context.setUserId(NumberUtils.toLong(userId));
        context.setDeviceId(getOriginalStr(request.getHeader(HEADER_NAME_DEVICE_ID)));
        String userAgent = getOriginalStr(request.getHeader(HEADER_NAME_USER_AGENT));
        context.setUseragent(userAgent);
        // 设备信息
        if (userAgent.contains(";") && userAgent.split(";").length == 4) {
            String[] deviceInfo = userAgent.split(";");
            context.setDevice(deviceInfo[3]);
            context.setDeviceOS(deviceInfo[1]);
            context.setDeviceType(deviceInfo[0]);
        }
        context.setVersion(getOriginalStr(request.getHeader(HEADER_NAME_APP_VERSION)));

        context.setLat(getOriginalStr(request.getHeader(HEADER_NAME_LAT)));
        context.setLng(getOriginalStr(request.getHeader(HEADER_NAME_LNG)));
        context.setTid(getOriginalStr(request.getHeader(HEADER_NAME_MT_TID)));

        boolean validToken = tokenValidate(getOriginalStr(request.getHeader(HEADER_NAME_CLIENT_TIME)),
            context.getDeviceId(), getOriginalStr(request.getHeader(HEADER_NAME_TOKEN)));
        context.setFromInvalidApp(validToken ? 0 : 1);

        String risk = getOriginalStr(request.getHeader(HEADER_NAME_RISK));
        if (risk != null) {
            String[] riskInfo = risk.split(";");
            if (riskInfo.length > 0 && riskInfo[0].split("/").length == 2) {
                context.setRoot(riskInfo[0].split("/")[1]);
            }
            if (riskInfo.length > 1 && riskInfo[1].split("/").length == 2) {
                context.setDebug(riskInfo[1].split("/")[1]);
            }
            if (riskInfo.length > 2 && riskInfo[2].split("/").length == 2) {
                context.setProxy(riskInfo[2].split("/")[1]);
            }
            if (riskInfo.length > 3 && riskInfo[3].split("/").length == 2) {
                context.setInject(riskInfo[3].split("/")[1]);
            }
        }

        context.setBizId(getOriginalStr(request.getHeader(HEADER_NAME_BIZID)));

        return context;
    }

    public static String getOriginalStr(String headerStr) {
        if (headerStr == null) {
            return headerStr;
        }
        if (headerStr.startsWith(CLIPS)) {
            //来自于clips 的http协议，进行解密
            headerStr = headerStr.substring(CLIPS.length());
            String temp = Base64Util.decrypt(headerStr);
            headerStr = xorDecrypt(temp, CLIPS_KEY);
        }
        return headerStr;
    }

    private static String xorDecrypt(String input, int key) {
        // 十六进制转为string
        if (StringUtils.isBlank(input)) {
            return input;
        }
        StringBuilder builder = new StringBuilder();
        char[] inputChars = input.toCharArray();
        int keyTemp = key;

        for (char meta: inputChars) {
            int temp2 = meta ^ keyTemp;
            builder.append((char) temp2);
            keyTemp = meta;
        }
        return builder.toString();
    }

    /**
     * 加密 xor
     * 
     * @param input
     *            输入
     * @param key
     *            魔法值
     * @return
     */
    private static String xorEncrypt(String input, int key) {
        // 十六进制转为string
        if (StringUtils.isBlank(input)) {
            return input;
        }
        StringBuilder builder = new StringBuilder();
        char[] inputChars = input.toCharArray();
        int keyTemp = key;

        for (char meta: inputChars) {
            int temp2 = meta ^ keyTemp;
            builder.append((char) temp2);
            keyTemp = temp2;
        }
        return builder.toString();
    }

    /**
     * 校验鉴权token
     *
     * @param deviceTime
     *            客户端时间
     * @param deviceId
     *            设备id
     * @return token值
     */
    public static boolean tokenValidate(String deviceTime, String deviceId, String clientToken) {
        if (StringUtils.isBlank(deviceTime) || StringUtils.isBlank(deviceId) || StringUtils.isBlank(clientToken)) {
            return false;
        }
        String backendCalculatedToken = ParseMD5.parseStrToMd5L32(deviceId + deviceTime);
        if (StringUtils.isBlank(backendCalculatedToken)) {
            return false;
        }
        String subToken = backendCalculatedToken.substring(0, 30);
        if (StringUtils.isBlank(subToken)) {
            return false;
        }
        return subToken.equals(clientToken);
    }
}
