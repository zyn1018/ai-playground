/**
 * @(#)BitOperationUtil.java, 2016年11月10日.
 * <p>
 * Copyright 2016 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.yinan.util;

/**
 * 按位操作工具类
 *
 * @author hzyangming1
 */
public class BitOperationUtil {

    /**
     * 按位设置
     *
     * @param data
     *            源数据
     * @param bit
     *            需要设置的位，从0开始，0表示低位
     * @param value
     *            0-清除，1-置位
     * @return
     */
    public static long setBit(long data, int bit, boolean value) {
        if (value) { // 置位
            return data | (1 << bit);
        } else { // 清零
            return data & (~(1 << bit));
        }
    }

    /**
     * 按位读取
     *
     * @param data
     *            源数据
     * @param bit
     *            需要读取的位，从0开始，0表示低位
     * @return
     */
    public static boolean getBit(long data, int bit) {
        return ((data >>> bit) & 1L) == 1L;
    }
}
