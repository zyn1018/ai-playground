 /**
 * @(#)SpuBean.java, 2022/6/7.
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
 * @author 陈振辉 (chenzhenhui01@corp.netease.com)
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkBenchSpecValueModelBean {



    /**
     * 规格名称
     */
    private String specValue;
}
