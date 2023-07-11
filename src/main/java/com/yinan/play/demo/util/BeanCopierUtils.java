/**
 * @(#)BeanCopierUtils.java, 2018年03月10日.
 *
 * Copyright 2018 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.yinan.play.demo.util;

import org.springframework.cglib.beans.BeanCopier;

/**
 * cglib拷贝工具类
 * 
 * @author hzzhangxuehao
 */
public class BeanCopierUtils {

    /**
     * 把orig对象的属性浅拷贝到dest对象，不递归拷贝。只拷贝名称和类型都相同的属性，名称相同而类型不同的属性不会被拷贝
     * <p>
     * 对象类型未设置值字段，默认设置null; 基本数据类型未设置值字段，默认设置0或0.0
     * 
     * @param dest
     *            赋值目标实例
     * @param orig
     *            属性来源实例
     */
    public static void copyProperties(Object dest, Object orig) {
        if (dest == null || orig == null) {
            return;
        }

        BeanCopier copier = BeanCopier.create(orig.getClass(), dest.getClass(),
            false);
        copier.copy(orig, dest, null);
    }
}
