/**
 * @(#)AbtResourceTO.java, 2020年01月09日.
 *
 * Copyright 2020 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.yinan.play.demo.meta;

import java.util.List;

/**
 * @author hzzhangxuehao
 */
public class AbtResourceTO {

    /**
     * 商品id
     */
    private long spuId;

    /**
     * abt方案id
     */
    private List<String> planIds;

    public long getSpuId() {
        return spuId;
    }

    public void setSpuId(long spuId) {
        this.spuId = spuId;
    }

    public List<String> getPlanIds() {
        return planIds;
    }

    public void setPlanIds(List<String> planIds) {
        this.planIds = planIds;
    }

}
