/**
 * @(#)Platform.java, 2017年5月9日.
 *
 * Copyright 2017 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.yinan.enums;

/**
 * 访问终端：适用于需要区分设备或系统的场景
 *
 * @author hzlixiaolong1
 */
public enum Platform {

    /**
     * NULL
     */
    NULL("NULL", "NULL"),

    /**
     * web主站
     */
    WEB("web", "web"),

    /**
     * wap
     */
    WAP("wap", "wap"),

    /**
     * ios客户端
     */
    IOS("ios", "ios"),

    /**
     * android客户端
     */
    ANDROID("android", "android"),

    /**
     * 未知客户端
     */
    APP("app", "app"),
    /**
     * 小程序终端
     */
    APP_MINI("app_mini", "app_mini");

    /**
     * 值
     */
    private final String value;

    /**
     * 描述
     */
    private final String desc;

    private Platform(String v, String d) {
        value = v;
        desc = d;
    }

    public String getValue() {
        return this.value;
    }

    public String getDesc() {
        return desc;
    }

}
