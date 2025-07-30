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

import java.math.BigDecimal;
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
public class SkuEditParam {

    private String skuId;

    /**
     * sku名称,
     */
    private String name;

    /**
     * sku货号
     */
    private String skuCode;

    /**
     * 市场价（元）,eg:500 本次新增
     */
    private BigDecimal marketPrice;

    /**
     * 编辑前 成本销售价（元）,eg:500 本次新增
     */
    private BigDecimal oldCostPrice;

    /**
     * 编辑后 成本销售价（元）,eg:500 本次新增
     */
    private BigDecimal newCostPrice;

    /**
     * 销售价（元）,eg:500 本次新增
     */
    private BigDecimal salePrice;

    /**
     * 图片链接
     */
    private String pictureUrl;

    /**
     * 是否组合装0-否，1-是
     */
    private int combine;

    /**
     * sku规格关联关系
     */
    private List<SkuSpecCombineEditParam> skuSpecCombineList;

    /**
     * 毛重
     */
    private BigDecimal weight;

    /**
     * 库存
     */
    private Long inventoryAmount;

}
