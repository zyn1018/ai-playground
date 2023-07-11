/**
 * @(#)StatEventTypeEnum.java, 2021/3/1.
 * <p/>
 * Copyright 2021 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.yinan.play.demo.meta.enums;

/**
 * 类说明 缓存事件类型枚举类
 *
 * @author 何瑞青(heruiqing @ corp.netease.com)
 * @version 创建时间：2021/3/1
 */
public enum StatEventTypeEnum {

    /**
     * 缓存访问事件
     */
    CACHE_ACCESS_EVENT("CacheAccess", "缓存访问事件"),

    /**
     * 缓存调度事件
     */
    CACHE_SCHEDULE_EVENT("CacheSchedule", "缓存调度事件"),

    /**
     * 缓存Size事件
     */
    CACHE_KEY_SIZE_EVENT("CacheKeySize", "缓存Size事件");

    private final String code;

    private final String msg;

    StatEventTypeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getValue() {
        return code;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
