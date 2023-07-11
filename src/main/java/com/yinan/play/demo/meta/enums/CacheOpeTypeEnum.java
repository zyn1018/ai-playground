/**
 * @(#)CacheOpeTypeEnum.java, 2021/3/1.
 * <p/>
 * Copyright 2021 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.yinan.play.demo.meta.enums;

/**
 * 类说明 缓存操作类型枚举
 *
 * @author 何瑞青(heruiqing @ corp.netease.com)
 * @version 创建时间：2021/3/1
 */
public enum CacheOpeTypeEnum {

    /**
     * 缓存读取
     */
    GET("Get", "缓存读取"),

    /**
     * 缓存写入
     */
    PUT("Put", "缓存写入"),

    /**
     * 缓存失效
     */
    EVICT("Evict", "缓存失效"),

    /**
     * 缓存调度
     */
    SCHEDULE("Schedule", "缓存调度");

    private final String code;

    private final String msg;

    CacheOpeTypeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public String getCode() {
        return code;
    }
}
