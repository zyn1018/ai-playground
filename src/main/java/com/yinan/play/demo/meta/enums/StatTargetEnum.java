package com.yinan.play.demo.meta.enums;

/**
 * 类说明
 *
 * @author Zhu, Zhen
 * @version 创建时间：2021/3/8
 * @ E-mail:hzzhuzhen@corp.netease.com
 */
public enum StatTargetEnum {
    BIG_CACHE("BigCache", "大Key指标"),

    HOT_CACHE("HotCache", "缓存写入指标"),

    CACHE_STATISTIC("CacheStatistic", "缓存统计指标"),

    CACHE_SCHEDULE("CacheSchedule", "缓存定时任务指标");

    private final String code;

    private final String msg;

    StatTargetEnum(String code, String msg) {
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
