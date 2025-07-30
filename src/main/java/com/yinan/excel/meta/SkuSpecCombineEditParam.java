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
public class SkuSpecCombineEditParam {

    /**
     * skuId
     */
    private String skuId;

    /**
     * 规格id
     */
    private long specId;

    /**
     * 规格值id
     */
    private long specValueId;

    /**
     * 生成唯一键
     *
     * @return
     */
    public String genUniqKey() {
        return specId + "_" + specValueId;
    }

}
