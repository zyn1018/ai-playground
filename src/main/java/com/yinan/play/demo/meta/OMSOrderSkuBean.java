/**
 * @(#)OMSOrderSkuBean.java, 2018/10/17.
 * <p>
 * Copyright 2018 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.yinan.play.demo.meta;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * https://wiki.mail.netease.com/display/yxfx/ChannelOrderSku
 *
 * @author 雷琨(hzleikun @ corp.netease.com)
 */
public class OMSOrderSkuBean {

    private String subOrderId;

    private String skuId;

    private String outSkuId;

    private String productName;

    private Integer saleCount;

    private BigDecimal originPrice;

    private BigDecimal subtotalAmount;

    private BigDecimal couponTotalAmount;

    private BigDecimal activityTotalAmount;

    private BigDecimal channelBearActivityTotalAmount;

    private BigDecimal channelBearCouponTotalAmount;

    private String remark;

    private boolean combined;

    private boolean split;

    private boolean cancel;

    public boolean isCancel() {
        return cancel;
    }

    public void setCancel(boolean cancel) {
        this.cancel = cancel;
    }

    private List<OMSSkuDetailBean> skuDetailVOList;

    private Map<String, String> extInfo = new HashMap<>();

    public String getSubOrderId() {
        return subOrderId;
    }

    public void setSubOrderId(String subOrderId) {
        this.subOrderId = subOrderId;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getOutSkuId() {
        return outSkuId;
    }

    public void setOutSkuId(String outSkuId) {
        this.outSkuId = outSkuId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getSaleCount() {
        return saleCount;
    }

    public void setSaleCount(Integer saleCount) {
        this.saleCount = saleCount;
    }

    public BigDecimal getOriginPrice() {
        return originPrice;
    }

    public void setOriginPrice(BigDecimal originPrice) {
        this.originPrice = originPrice;
    }

    public BigDecimal getSubtotalAmount() {
        return subtotalAmount;
    }

    public void setSubtotalAmount(BigDecimal subtotalAmount) {
        this.subtotalAmount = subtotalAmount;
    }

    public BigDecimal getCouponTotalAmount() {
        return couponTotalAmount;
    }

    public void setCouponTotalAmount(BigDecimal couponTotalAmount) {
        this.couponTotalAmount = couponTotalAmount;
    }

    public BigDecimal getActivityTotalAmount() {
        return activityTotalAmount;
    }

    public void setActivityTotalAmount(BigDecimal activityTotalAmount) {
        this.activityTotalAmount = activityTotalAmount;
    }

    public BigDecimal getChannelBearActivityTotalAmount() {
        return channelBearActivityTotalAmount;
    }

    public void setChannelBearActivityTotalAmount(BigDecimal channelBearActivityTotalAmount) {
        this.channelBearActivityTotalAmount = channelBearActivityTotalAmount;
    }

    public BigDecimal getChannelBearCouponTotalAmount() {
        return channelBearCouponTotalAmount;
    }

    public void setChannelBearCouponTotalAmount(BigDecimal channelBearCouponTotalAmount) {
        this.channelBearCouponTotalAmount = channelBearCouponTotalAmount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public boolean isSplit() {
      return split;
    }

    public void setSplit(boolean split) {
      this.split = split;
    }

    public boolean isCombined() {
        return combined;
    }

    public void setCombined(boolean combined) {
        this.combined = combined;
    }

    public List<OMSSkuDetailBean> getSkuDetailVOList() {
        return skuDetailVOList;
    }

    public void setSkuDetailVOList(
        List<OMSSkuDetailBean> skuDetailVOList) {
        this.skuDetailVOList = skuDetailVOList;
    }

    public Map<String, String> getExtInfo() {
        return extInfo;
    }

    public void setExtInfo(Map<String, String> extInfo) {
        this.extInfo = extInfo;
    }
}
