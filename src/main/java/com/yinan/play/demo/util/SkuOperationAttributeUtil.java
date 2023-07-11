/**
 * @(#)SkuOperationAttributeUtil.java, 2021/8/21.
 * <p>
 * Copyright 2021 NetEase, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.yinan.play.demo.util;

import com.yinan.constant.SkuOperationAttribute;
import com.yinan.util.BitOperationUtil;

/**
 * @author Yinan Zhang (zhangyinan01@corp.netease.com)
 */
public class SkuOperationAttributeUtil {

    /**
     * @param operationAttribute
     * @return
     */
    public static boolean getAccessorySkuFlag(long operationAttribute) {
        return BitOperationUtil.getBit(operationAttribute,
                SkuOperationAttribute.SKU_ACCESSORY_FLAG);
    }

    public static void main(String[] args) {
        System.out.println(SkuOperationAttributeUtil.getAccessorySkuFlag(33689600L));
    }
}
