package com.yinan.play.demo.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * @author 沈月东(shenyuedong @ corp.netease.com)
 */
public class SchemeUtils {


    public static String urlToSchemeUrl(String targetUrl) {
        if (StringUtils.isBlank(targetUrl)) {
            return "";
        }
        if (targetUrl.startsWith("moutaiapp")) {
            return targetUrl;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("moutaiapp://webview?url=");
        try {
            sb.append(URLEncoder.encode(targetUrl, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            // 一般不会出现
        }
        return sb.toString();
    }

    public static String urlToSchemeUrl(String targetUrl,
                                        Map<String, String> targetParams) {
        StringBuilder sb = new StringBuilder();
        sb.append(targetUrl);
        if (targetParams != null && targetParams.size() > 0) {
            sb.append('?');
            try {
                for (Map.Entry<String, String> param: targetParams.entrySet()) {
                    sb.append(param.getKey()).append("=")
                            .append(URLEncoder.encode(param.getValue(), "UTF-8"))
                            .append('&');
                }
            } catch (UnsupportedEncodingException e) {
                // 一般不会出现
            }
        }
        if (sb.charAt(sb.length() - 1) == '&') {
            sb.deleteCharAt(sb.length() - 1);
        }
        return urlToSchemeUrl(sb.toString());
    }

    public static String buildSchemeUrl(String type,
        Map<String, String> params) {
        StringBuilder sb = new StringBuilder();
        sb.append("moutaiapp://").append(type);
        if (params != null && params.size() > 0) {
            sb.append('?');
            try {
                for (Map.Entry<String, String> param: params.entrySet()) {
                    sb.append(param.getKey()).append("=")
                        .append(URLEncoder.encode(param.getValue(), "UTF-8"))
                        .append('&');
                }
            } catch (UnsupportedEncodingException e) {
                // 一般不会出现
            }
        }
        if (sb.charAt(sb.length() - 1) == '&') {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    /**
     * 构造完整链接
     *
     * @param path
     *            基础路径
     * @param params
     *            参数Map
     * @return
     */
    private static String buildFullUrl(String path,
        Map<String, String> params) {

        if (StringUtils.isEmpty(path) || params == null || params.isEmpty()) {
            return path;
        }

        StringBuilder url = new StringBuilder(path);
        url.append("?");
        for (Map.Entry<String, String> entry: params.entrySet()) {
            if (StringUtils.isEmpty(entry.getKey())
                || StringUtils.isEmpty(entry.getValue())) {
                continue;
            }
            url.append(entry.getKey()).append("=").append(entry.getValue())
                .append("&");
        }

        char lastChar = url.charAt((url.length() - 1));
        if (lastChar == '&' || lastChar == '?') {
            url.deleteCharAt(url.length() - 1);
        }
        return url.toString();
    }
}
