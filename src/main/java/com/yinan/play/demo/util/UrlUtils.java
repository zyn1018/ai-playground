/**
 * @(#)UrlUtils.java, 2020/5/16.
 * <p>
 * Copyright 2020 NetEase, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.yinan.play.demo.util;

import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @author Yinan Zhang (zhangyinan01@corp.netease.com)
 */
public class UrlUtils {
    public static String appendParamsToUrl(String url, Map<String, String> params) {
        if (StringUtils.isEmpty(url)) {
            return "";
        }
        if (params == null || params.size() == 0) {
            return url.trim();
        }

        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> param : params.entrySet()) {
            sb.append(param.getKey()).append("=").append(param.getValue()).append("&");
        }
        sb.deleteCharAt(sb.length() - 1);

        url = url.trim();
        int length = url.length();
        int index = url.indexOf("?");
        if (index > -1) {
            //原始url包含问号
            if ((length - 1) == index) {
                //原始url最后一个符号为问号，如：https://you.163.com?
                url += sb.toString();
            } else {
                //原始url带有其他参数，如：http://you.163.com?abc=11
                url += "&" + sb.toString();
            }
        } else {
            //原始url不包含问号，如：https://you.163.com
            url += "?" + sb.toString();
        }
        return url;
    }

    /**
     * 获取url参数名对应的参数值
     *
     * @param paramName
     * @return
     */
    public static String getUrlParam(String url, String paramName) {
        if (StringUtils.isEmpty(paramName) || StringUtils.isEmpty(url)) {
            return "";
        }
        String[] urlParts = url.split("\\?");
        if (urlParts.length <= 1) {
            // url无参数
            return "";
        }
        String[] params = urlParts[1].split("&");
        for (String param : params) {
            String[] keyValue = param.split("=");
            if (keyValue.length != 2) {
                continue;
            }
            if (keyValue[0].equals(paramName)) {
                return keyValue[1];
            }
        }
        return "";
    }
}
