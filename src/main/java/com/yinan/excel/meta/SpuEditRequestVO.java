/**
 * @(#)SpuEditRequestVO.java, 2022/6/9.
 * <p/>
 * Copyright 2022 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.yinan.excel.meta;

import lombok.Data;

/**
 * Spu编辑请求参数
 *
 * @author panjiansheng(panjiansheng @ corp.netease.com)
 */
@Data
public class SpuEditRequestVO {

    /**
     * spu编辑请求参数
     */
    private SpuEditParam spu;

    /**
     * 编辑场景。0-新增 1-编辑
     */
    private int pageType;

    /**
     * 操作人
     */
    private String operator;

    /**
     * 操作人姓名
     */
    private String operatorName;

}