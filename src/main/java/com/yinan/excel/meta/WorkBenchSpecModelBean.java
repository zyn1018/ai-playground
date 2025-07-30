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

import java.util.List;

/**
 * @author 陈振辉 (chenzhenhui01@corp.netease.com)
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkBenchSpecModelBean {


    private List<SpuSaleCategoryBean> category;

    /**
     * 规格名称
     */
    private String specName;

    /**
     * 规格值列表
     */
    private List<WorkBenchSpecValueModelBean> specValueList;

    /**
     * 供应商id
     */
    private Long supplierId;
}
