/**
 * @(#)CategoryL2ItemQueryParamVO.java, 2019/2/21.
 * 
 * Copyright 2019 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.yinan.play.demo.meta;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 二级类目频道页查询参数
 * 
 * @author 陈新超(hzchenxinchao@corp.netease.com)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CategoryL2ItemQueryParamVO {

    //二级类目id
    private long categoryId;

    //最后一个商品的id
    private long itemId;

    //获取列表大小
    private long size;

    //跳转来源，根据不同的跳转来源，后端处理不同的逻辑。1.source=1，付费会员落地页，表示过滤非付费会员的商品
    private int source;

    //source==2有效 置顶商品
    private long topItemId;

    /**
     * 综合排序方式下，默认类目商品是否走算法排序，
     * true：走算法排序，false：不走算法排序
     */
    private boolean defaultByRcmd = true;

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public long getTopItemId() {
        return topItemId;
    }

    public void setTopItemId(long topItemId) {
        this.topItemId = topItemId;
    }

    public boolean isDefaultByRcmd() {
        return defaultByRcmd;
    }

    public void setDefaultByRcmd(boolean defaultByRcmd) {
        this.defaultByRcmd = defaultByRcmd;
    }
}
