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
 * 商品服务政策参数
 *
 * @author zhaoran
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpuPolicyEditParam {

    /**
     * 主键id
     */
    private long id;

    /**
     * 是否勾选 true-勾选 false-未勾选
     */
    private boolean checked;
}
