/**
 * @(#)ScheduleTypeEnum.java, 2020/11/4.
 * <p/>
 * Copyright 2020 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.yinan.play.demo.meta.enums;

/**
 * 调度事件类型
 * <p>
 * 区分本地调度和分布式远程调度
 * </p>
 * 
 * @author hzwangliyuan @ corp.netease.com on 2020/11/4.
 */
public enum TriggerStageEnum {

    /**
     * 调度开始
     */
    TRIGGER_FIRED("TriggerFired", "本地缓存同步调度"),

    /**
     * 错过调度
     */
    TRIGGER_MISFIRED("TriggerMisfired", "分布式缓存刷新调度"),

    /**
     * 调度完成
     */
    TRIGGER_COMPLETE("TriggerComplete", "调度完成");

    private final String code;

    private final String msg;

    TriggerStageEnum(String code, String msg) {
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
