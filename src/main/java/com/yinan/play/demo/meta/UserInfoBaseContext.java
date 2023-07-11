package com.yinan.play.demo.meta;

import lombok.Data;

/**
 * @author 麦琳 (hzmailin@corp.netease.com)
 */
@Data
public class UserInfoBaseContext {

    public static final String DEVICE_APP = "mt.shop.app";

    public static final String DEVICE_WEB = "mt.shop.web";

    public static final String DEVICE_WAP = "mt.shop.wap";

    /**
     * ip、deviceId、userid、端类型、设备型号、设备系统版本、APP版本、APP类型、WIFI SSID，需要在所有的日志事件中都有
     */
    private String ip;

    /**
     * 设备id
     */
    private String deviceId;

    /**
     * 用户ID
     */
    private long userId;

    /**
     * 设备型号
     */
    private String device;

    /**
     * 设备型号版本
     */
    private String deviceOS;

    /**
     * app版本号
     */
    private String version;

    /**
     * 设备类型, iOS, android
     */
    private String deviceType;

    /**
     * 纬度
     */
    private String lat;

    /**
     * 经度
     */
    private String lng;

    /**
     * MT-Request-ID，请求链路ID，客户端生成
     */
    private String tid;

    /**
     * 动作名称
     */
    private String action = "";

    /**
     * 原始 userAgent
     */
    private String useragent;

    /**
     * 应用风险 是否 root 0正常 1 异常
     */
    private String root;

    /**
     * 应用风险 是否 root 0正常 1 异常
     */
    private String debug;

    /**
     * 应用风险 是否 root 0正常 1 异常
     */
    private String proxy;

    /**
     * 应用风险 是否 root 0正常 1 异常
     */
    private String inject;

    /**
     * 账号类型，需要业务自己写入
     */
    private int accountType;

    /**
     * 账号
     */
    private String account;

    /**
     * mt.shop.app, mt.shop.web
     */
    private String bizId;

    /**
     * 来自不可靠APP客户端，0可靠，1不可靠
     */
    private int fromInvalidApp;
}
