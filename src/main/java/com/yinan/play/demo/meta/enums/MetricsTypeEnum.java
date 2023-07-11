/**
 * @(#)MetricsTypeEnum.java, 2021/3/10.
 * <p/>
 * Copyright 2021 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.yinan.play.demo.meta.enums;

/**
 * @author 丁畅(dingchang @ corp.netease.com)
 */
public enum MetricsTypeEnum {

    Gauges("Gauges", ""),
    Counters("Counters", ""),
    Histograms("Histograms", "统计分布"),
    Timers("Timers", "");

    private final String code;

    private final String msg;

    MetricsTypeEnum(String code, String msg) {
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
