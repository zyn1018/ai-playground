/**
 * @(#)Order.java, 2023/7/18.
 * <p/>
 * Copyright 2023 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.yinan.play.demo.meta;

import java.util.List;

/**
 * @author 王宜鼎 (wangyiding02@corp.netease.com)
 */
public class Order {
    private OrderAddressInfo orderAddressInfo;
    private long orderId;
    private List<OrderItem> orderItemList;
    private OrderPriceInfo orderPriceInfo;
    private int orderStatus;
    private String orderStatusDesc;
    private long submitTime;
    private long updateTime;
    private UserInfo userInfo;

    public OrderAddressInfo getOrderAddressInfo() {
        return orderAddressInfo;
    }

    public void setOrderAddressInfo(OrderAddressInfo orderAddressInfo) {
        this.orderAddressInfo = orderAddressInfo;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public List<OrderItem> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<OrderItem> orderItemList) {
        this.orderItemList = orderItemList;
    }

    public OrderPriceInfo getOrderPriceInfo() {
        return orderPriceInfo;
    }

    public void setOrderPriceInfo(OrderPriceInfo orderPriceInfo) {
        this.orderPriceInfo = orderPriceInfo;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderStatusDesc() {
        return orderStatusDesc;
    }

    public void setOrderStatusDesc(String orderStatusDesc) {
        this.orderStatusDesc = orderStatusDesc;
    }

    public long getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(long submitTime) {
        this.submitTime = submitTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
    // getter and setter methods
}
