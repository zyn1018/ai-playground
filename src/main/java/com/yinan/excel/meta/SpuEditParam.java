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
import java.util.Map;

/**
 * 商品信息编辑参数
 *
 * @author panjiansheng(panjiansheng @ corp.netease.com)
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpuEditParam {

    /**
     * 一级类目id
     */
    private Long firstSaleCategoryId;

    /**
     * 二级类目id
     */
    private Long secondSaleCategoryId;

    private String spuId;

    private Long shopId;

    private Long supplierId;

    /**
     * 商品名称,eg:53%vol 2.5L贵州茅台酒
     */
    private String name;

    /**
     * 货号
     */
    private String itemCode;

    /**
     * 推荐原因
     */
    private String recommendReason;

    /**
     * 主sku id
     */
    private String primarySkuId;

    /**
     * 商品图片
     */
    private List<String> primaryPicUrl;

    /**
     * 商品详情图片切片后的html
     */
    private String detailHtml;

    /**
     * 商品详情页原始图片与切片图映射
     */
    private Map<String, List<String>> detailPicUrls;

    /**
     * 规格列表
     */
    private List<SpuSpecEditParam> specList;

    /**
     * sku 列表
     */
    private List<SkuEditParam> skuList;

    /**
     * 服务政策列表
     */
    private List<SpuPolicyEditParam> policyList;

    /**
     * 退货地址id 本次新增
     */
    private long returnAddressId;

    /**
     * 运费模板id 本次新增
     */
    private long shippingTemplateId;

    /**
     * 是否支持7天无忧退换货
     * 0-不支持 1-支持
     */
    private int support7DaysReturn;
}
