/**
 * @(#)ScheduleTypeEnum.java, 2020/11/4.
 * <p/>
 * Copyright 2020 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.yinan.play.demo.meta.enums;

/**
 * 调度类型
 * <p>
 * 区分本地调度和分布式远程调度
 * </p>
 * 
 * @author hzwangliyuan @ corp.netease.com on 2020/11/4.
 */
public enum ScheduleTypeEnum {

    /**
     * 本地缓存同步调度
     */
    LOCAL("LOCAL", "本地缓存同步调度"),

    /**
     * 分布式缓存调度
     */
    REMOTE("REMOTE", "分布式缓存刷新调度");

    /** 代码 */
    private final String code;

    /** 对应的说明 */
    private final String msg;

    ScheduleTypeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
