/**
 * @(#)Item.java, 2019/11/19.
 * <p>
 * Copyright 2019 NetEase, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.yinan.play.demo.meta;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Yinan Zhang (zhangyinan01@corp.netease.com)
 */
public class Item {
    private long itemId;

    private String name;

    private List<Integer> skuList;

    private BigDecimal price;

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getSkuList() {
        return skuList;
    }

    public void setSkuList(List<Integer> skuList) {
        this.skuList = skuList;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
