/**
 * @(#)NewItemListResultVO.java, 2019/12/9.
 * <p/>
 * Copyright 2019 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.yinan.play.demo.meta;

/**
 * @author lushuai(lushuai @ corp.netease.com)
 */
public class NewItemListResultVO {
    /**
     * 是否有下一页
     */
    private boolean hasMore;

    /**
     * 所有商品列表，分页之间保证算法排序一致，下一页请求时放到参数中
     */
    private String itemIds;

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    public String getItemIds() {
        return itemIds;
    }

    public void setItemIds(String itemIds) {
        this.itemIds = itemIds;
    }
}
