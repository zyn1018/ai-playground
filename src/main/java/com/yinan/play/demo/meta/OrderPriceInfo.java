/**
 * @(#)OrderPriceInfo.java, 2023/7/18.
 * <p/>
 * Copyright 2023 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.yinan.play.demo.meta;

import java.math.BigDecimal;

/**
 * @author 王宜鼎 (wangyiding02@corp.netease.com)
 */
public class OrderPriceInfo {
    private BigDecimal freightPrice;
    private BigDecimal item;
    private BigDecimal totalPrice;

    public BigDecimal getFreightPrice() {
        return freightPrice;
    }

    public void setFreightPrice(BigDecimal freightPrice) {
        this.freightPrice = freightPrice;
    }

    public BigDecimal getItem() {
        return item;
    }

    public void setItem(BigDecimal item) {
        this.item = item;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    // getter and setter methods
}
