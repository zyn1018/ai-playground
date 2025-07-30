/**
 * @(#)CategoryTO.java, 2024/3/14.
 * <p/>
 * Copyright 2024 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.yinan.play.demo.meta;

import java.util.List;

import lombok.Data;

/**
 * @author 洪启帆 (hongqifan@corp.netease.com)
 */
@Data
public class CategoryDTO {

    /**
     * 类目id
     */
    private long id;

    /**
     * 类目名
     */
    private String name;

    /**
     * 级别
     */
    private int level;

    /**
     * 上级类目id
     */
    private long parentId;

    /**
     * 类目状态 0-未启用 1-启用（v1 默认所有启用）
     */
    private Integer enable;

    /**
     * 下级类目列表
     */
    private List<CategoryDTO> subCategoryList;
}
