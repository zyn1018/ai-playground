/**
 * @(#)StatEvent.java, 2021/3/1.
 * <p/>
 * Copyright 2021 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.yinan.play.demo.meta;

import com.yinan.play.demo.meta.enums.CacheOpeTypeEnum;
import com.yinan.play.demo.meta.enums.StatEventTypeEnum;

/**
 * @author 何瑞青(heruiqing @ corp.netease.com)
 * @version 创建时间：2021/3/1
 */
public class StatEvent {

    /**
     * 事件时间
     */
    protected long time;

    /**
     * 事件类型
     */
    protected StatEventTypeEnum type;

    /**
     * 缓存操作类型
     */
    protected CacheOpeTypeEnum ope;

    /**
     * 缓存名
     */
    protected String cacheName;

    /**
     * 缓存键
     */
    protected String cacheKey;

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public StatEventTypeEnum getType() {
        return type;
    }

    public void setType(StatEventTypeEnum type) {
        this.type = type;
    }

    public CacheOpeTypeEnum getOpe() {
        return ope;
    }

    public void setOpe(CacheOpeTypeEnum ope) {
        this.ope = ope;
    }

    public String getCacheName() {
        return cacheName;
    }

    public void setCacheName(String cacheName) {
        this.cacheName = cacheName;
    }

    public String getCacheKey() {
        return cacheKey;
    }

    public void setCacheKey(String cacheKey) {
        this.cacheKey = cacheKey;
    }
}
