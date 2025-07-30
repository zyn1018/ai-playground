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

import java.util.List;

/**
 * 商品信息编辑参数
 *
 * @author panjiansheng(panjiansheng @ corp.netease.com)
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpuSpecEditParam {

    /**
     * 主键id
     */
    private long id;

    /**
     * 前端规格ID
     * 新增规格时frontId为时间戳，如1687747415219
     */
    private long frontId;

    /**
     * 规格名称
     */
    private String name;


    /**
     * 显示顺序
     */
    private int viewOrder;


    /**
     * 规格值列表
     */
    private List<SpuSpecValueEditParam> specValueList;

}
