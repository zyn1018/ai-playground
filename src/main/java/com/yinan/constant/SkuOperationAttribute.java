/**
 * @(#)SkuOperationAttribute.java, 2016年11月10日.
 *
 * Copyright 2016 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.yinan.constant;

/**
 * sku操作属性位标识
 * 
 * @author hzyangming1
 */
public class SkuOperationAttribute {
    /**
     * 加购物车开关
     */
    public static final int ADD_CART_SWITCH = 0;

    /**
     * 立即购买开关
     */
    public static final int DIRECT_BUY_SWITCH = 1;

    /**
     * 是否可使用优惠券
     */
    public static final int ORDER_INIT_COUPON_SWITCH = 5;

    /**
     * 是否显示礼品卡模块（下单页）
     */
    public static final int ORDER_INIT_GIFTCARD_PAY_SWITCH = 6;

    /**
     * 是否显示地址选择模块（下单页）
     */
    public static final int ORDER_INIT_ADDRESS_SWITCH = 7;

    /**
     * 是否不支持开发票
     */
    public static final int ORDER_INIT_INVOIDE_SWITCH = 8;

    /**
     * 是否礼品卡
     */
    public static final int SKU_GIFTCARD_FLAG_SWITCH = 9;

    /**
     * 是否虚拟商品
     */
    public static final int SKU_VIRTUAL_SWITCH = 10;

    /**
     * 是否不参加所有活动
     */
    public static final int SKU_CONFLICT_ALL_PROMOTION_SWITCH = 11;

    /**
     * 是否不能线上退货
     */
    public static final int SKU_PROHIBIT_ONLINE_RETURN_SWITCH = 12;

    /**
     * 是否立即绑定到账户（ 针对虚拟商品）
     */
    public static final int SKU_BIND_AFTER_BUY_SWITCH = 13;

    /**
     * 是否是点卡商品
     */
    public static final int SKU_IS_POINT_CARD_FLAG = 14;

    /**
     * 是否不支持企业特权
     */
    public static final int SKU_CONFLICT_QIYE_PRIVILEGE_SWITCH = 15;

    /**
     * 是否不支持学生特权
     */
    public static final int SKU_CONFLICT_STD_PRIVILEGE_SWITCH = 16;

    /**
     * 是否不能线上换货
     */
    public static final int SKU_PROHIBIT_ONLINE_EXCHANGE_SWITCH = 17;

    /**
     * 是否不支持开电子发票
     */
    public static final int ORDER_ELECTRONIC_INVOICE_SWITCH = 18;

    /**
     * 是否不支持开纸质发票
     */
    public static final int ORDER_PAPER_INVOICE_SWITCH = 19;

    /**
     * 是否不支持开增值税专票
     */
    public static final int ORDER_VALUE_ADDED_TAX_INVOICE_SWITCH = 20;

    /**
     * 是否不支持使用红包
     */
    public static final int SKU_CONFLICT_RED_PACKET = 21;

    /**
     * 是否是话费充值商品
     */
    public static final int SKU_RECHARGE_FLAG = 22;

    /**
     * 是否眼镜商品
     */
    public static final int SKU_GLASS_SWITCH = 23;

    /**
     * 是否刻字定制商品
     */
    public static final int SKU_CUSTOM_LETTERING_SWITCH = 24;

    /**
     * 是否是配件
     */
    public static final int SKU_ACCESSORY_FLAG = 25;

    /**
     * 是否回馈金包商品
     */
    public static final int FEEDBACK_BONUS_PKG_FLAG = 26;

    /**
     * 是否不支持回馈金支付，0-支持，1-不支持
     */
    public static final int SKU_SUPPORT_REWARD = 27;

    /**
     * 是否可使用提货券支付（0-可以，1-不可以）
     */
    public static final int ORDER_INIT_CATEGORYGIFTCARD_PAY_SWITCH = 29;

    /**
     * 虚拟券卡标识
     */
    public static final int VIRTUAL_CARD_FLAG = 30;
}
