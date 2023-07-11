/**
 * @(#)ItemSkuSimpleMap.java, 2021/3/29.
 * <p>
 * Copyright 2021 NetEase, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.yinan.play.demo.meta;

/**
 * @author Yinan Zhang (zhangyinan01@corp.netease.com)
 */
public class ItemSkuSimpleParam {
    /**
     * 商品id
     */
    private Long itemId;

    /**
     * 指定skuId
     */
    private Long skuId;

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }
}
