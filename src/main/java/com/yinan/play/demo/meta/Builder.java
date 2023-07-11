/**
 * @(#)Builder.java, 2021/3/2.
 * <p/>
 * Copyright 2021 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.yinan.play.demo.meta;

/**
 * @author 何瑞青(heruiqing @ corp.netease.com)
 * @version 创建时间：2021/3/2
 */
public interface Builder<T> {

    /**
     * 构造器
     * @return 构造的类
     */
    T build();
}