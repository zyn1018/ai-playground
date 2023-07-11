/**
 * @(#)SchemeUrlBuilder.java, 2020/5/25.
 * <p>
 * Copyright 2020 NetEase, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.yinan.play.demo.util;

import com.yinan.play.demo.meta.enums.SchemeType;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Yinan Zhang (zhangyinan01@corp.netease.com)
 */
public class SchemeUrlBuilder {

    public static String buildWebViewJump(String targetUrl, String title,
                                          String scanQrUrl) {
        Map<String, String> params = new HashMap<String, String>();
        if (targetUrl != null) {
            params.put("url", targetUrl);
        }
        if (!StringUtils.isEmpty(title)) {
            params.put("title", title);
        }
        if (!StringUtils.isEmpty(scanQrUrl)) {
            params.put("scanQrUrl", scanQrUrl);
        }
        return buildSchemeUrl(SchemeType.WEBVIEW, params);

    }


    public static String buildSchemeUrl(String type,
                                        Map<String, String> params) {
        StringBuilder sb = new StringBuilder();
        sb.append("yanxuan://").append(type);
        if (params != null && params.size() > 0) {
            sb.append('?');
            try {
                for (Map.Entry<String, String> param : params.entrySet()) {
                    sb.append(param.getKey()).append("=")
                            .append(URLEncoder.encode(param.getValue(), "UTF-8"))
                            .append('&');
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        if (sb.charAt(sb.length() - 1) == '&') {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }
}
