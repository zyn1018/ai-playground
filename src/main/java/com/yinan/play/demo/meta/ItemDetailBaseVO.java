/**
 * @(#)ItemDetailBaseVO.java, 2019/7/23.
 * <p/>
 * Copyright 2019 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.yinan.play.demo.meta;

/**
 * @author 黄景(huangjing @ corp.netease.com) 2019/7/23 10:30 AM
 */
public class ItemDetailBaseVO {
    /**
     * 购买方式
     * 0默认 1全额返 2普通
     */
    private int purchaseType;

    public int getPurchaseType() {
        return purchaseType;
    }

    public void setPurchaseType(int purchaseType) {
        this.purchaseType = purchaseType;
    }
}
