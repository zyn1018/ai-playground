/**
 * @(#)SkuOperationAtrributeUtil.java, 2020/8/15.
 * <p>
 * Copyright 2020 NetEase, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.yinan.util;

import com.yinan.constant.SkuOperationAttribute;

/**
 * @author Yinan Zhang (zhangyinan01@corp.netease.com)
 */
public class SkuOperationAtrributeUtil {
    /**
     * 获取sku是否排斥使用红包
     *
     * @param operationAttribute
     * @return true-排斥红包
     */
    public static boolean getConflictRedPacket(long operationAttribute) {
        return BitOperationUtil.getBit(operationAttribute,
                SkuOperationAttribute.SKU_CONFLICT_RED_PACKET);
    }
}
