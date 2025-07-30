/**
 * @(#)OrderExceptionType.java, 2025/7/17.
 * <p/>
 * Copyright 2025 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.yinan.order.admin;

/**
 * @author 吴文铠 (wuwenkai@corp.netease.com)
 */
public enum OrderExceptionType {

    ORDER_PRE_CREATE("ORDER_PRE_CREATE", "订单预占"),
    ORDER_PRE_CONFIRM("ORDER_PRE_CONFIRM", "订单预占确认"),
    ORDER_CREATE("ORDER_CREATE", "直接下单"),
    ORDER_CANCEL("ORDER_CANCEL", "订单取消"),
    ORDER_CONFIRM_PACKAGE_RECEIPT("ORDER_CONFIRM_PACKAGE_RECEIPT", "订单确认收货"),
    ORDER_CANCEL_NOTIFY("ORDER_CANCEL_NOTIFY", "订单取消通知"),
    ORDER_CHANGE_ADDRESS("ORDER_CHANGE_ADDRESS", "订单修改地址"),
    ORDER_THIRD_PARTY_CHANGE_ADDRESS("ORDER_THIRD_PARTY_CHANGE_ADDRESS", "外渠订单修改地址"),
    RETURN_GOODS_REFUND_CREATE("RETURN_GOODS_REFUND_CREATE", "退货退款售后单创建"),
    REFUND_CALLBACK("REFUND_CALLBACK", "售后单回调"),
    OMS_ORDER_CALLBACK("OMS_ORDER_CALLBACK", "oms订单回调");

    private final String typeName;

    private final String desc;

    OrderExceptionType(String typeName, String desc) {
        this.typeName = typeName;
        this.desc = desc;
    }

    public String getTypeName() {
        return typeName;
    }

    public String getDesc() {
        return desc;
    }

    public static OrderExceptionType of(String typeName) {
        for (OrderExceptionType orderExceptionType: OrderExceptionType.values()) {
            if (orderExceptionType.getTypeName().equals(typeName)) {
                return orderExceptionType;
            }
        }
        return null;
    }
}