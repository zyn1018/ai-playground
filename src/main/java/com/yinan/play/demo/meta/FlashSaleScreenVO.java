/**
 * @(#)FlashSaleScreenVO, 2016年12月03日.
 * Copyright 2015 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.yinan.play.demo.meta;

/**
 * @desc 限时购场次vo
 * @author hzxulei1
 * @create 2016.12.03 下午4:37
 */
public class FlashSaleScreenVO {
    /**
     * id
     */
    private long id;

    /**
     * 场次开始时间
     */
    private long startTime;

    /**
     * 0-已开抢，1-抢购中，2-即将开始
     */
    private int status;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
