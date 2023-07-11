/**
 * @(#)ExtKeyUtil.java, 2019年6月1日.
 *
 * Copyright 2019 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.yinan.play.demo.util;

/**
 * 扩展key工具类
 */
public class CacheExtKeyUtil {

    private static final int SKU_BASIS_MOD = 2003;

    private static final int SKU_INVENTORY_MOD = 1009;

    /**
     * 获取sku基本信息缓存扩展key
     * 
     * @param skuId
     * @return
     */
    public static int getSkuBasisModKey(long skuId) {
        return Math.abs(String.valueOf(skuId).hashCode() % SKU_BASIS_MOD);
    }

    /**
     * 获取sku库存缓存扩展key
     * 
     * @param skuId
     * @return
     */
    public static int getSkuInventoryModKey(long skuId) {
        return Math.abs(String.valueOf(skuId).hashCode() % SKU_INVENTORY_MOD);
    }

}
