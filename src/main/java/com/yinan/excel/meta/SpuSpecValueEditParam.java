/**
 * @(#)SpuEditParam.java, 2022/6/9.
 * <p/>
 * Copyright 2022 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.yinan.excel.meta;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商品信息编辑参数
 *
 * @author panjiansheng(panjiansheng @ corp.netease.com)
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpuSpecValueEditParam {

    /**
     * 规格值id
     */
    private long id;

    /**
     * 新建规格值时采用的id，临时使用
     * 新增规格值时frontId为时间戳，如1687747415219
     */
    private long frontId;

    /**
     * 所属的规格id
     */
    private long spuSpecId;

    /**
     * 显示顺序
     */
    private int viewOrder;

    /**
     * 值
     */
    private String value;

    /**
     * 前台显示用的名称
     */
    private String frontDesc;

}
