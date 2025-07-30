/**
 * @(#)OMSOrderPackageBean.java, 2018/10/17.
 * <p>
 * Copyright 2018 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.yinan.play.demo.meta;

import java.util.List;

/**
 * https://wiki.mail.netease.com/display/yxfx/OmsToChannelOrderPackage
 *
 * @author 雷琨(hzleikun @ corp.netease.com)
 */
public class OMSOrderPackageBean {

    private String packageId;

    private String expressCompany;

    private String expressCode;

    private String expressNo;

    private String expCreateTime;

    private String confirmTime;

    private String packageStatus;

    private List<OMSOrderSkuBean> orderSkus;

    private List<OMSOrderExpressDetailInfoBean> expressDetailInfos;

    private List<OMSGiftCardDeliveryDetailBean> giftCardDeliveryDetailVOS;

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public String getExpressCompany() {
        return expressCompany;
    }

    public void setExpressCompany(String expressCompany) {
        this.expressCompany = expressCompany;
    }

    public String getExpressCode() {
        return expressCode;
    }

    public void setExpressCode(String expressCode) {
        this.expressCode = expressCode;
    }

    public String getExpressNo() {
        return expressNo;
    }

    public void setExpressNo(String expressNo) {
        this.expressNo = expressNo;
    }

    public String getExpCreateTime() {
        return expCreateTime;
    }

    public void setExpCreateTime(String expCreateTime) {
        this.expCreateTime = expCreateTime;
    }

    public String getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(String confirmTime) {
        this.confirmTime = confirmTime;
    }

    public String getPackageStatus() {
        return packageStatus;
    }

    public void setPackageStatus(String packageStatus) {
        this.packageStatus = packageStatus;
    }

    public List<OMSOrderSkuBean> getOrderSkus() {
        return orderSkus;
    }

    public void setOrderSkus(List<OMSOrderSkuBean> orderSkus) {
        this.orderSkus = orderSkus;
    }

    public List<OMSOrderExpressDetailInfoBean> getExpressDetailInfos() {
        return expressDetailInfos;
    }

    public void setExpressDetailInfos(
        List<OMSOrderExpressDetailInfoBean> expressDetailInfos) {
        this.expressDetailInfos = expressDetailInfos;
    }

    public List<OMSGiftCardDeliveryDetailBean> getGiftCardDeliveryDetailVOS() {
        return giftCardDeliveryDetailVOS;
    }

    public void setGiftCardDeliveryDetailVOS(List<OMSGiftCardDeliveryDetailBean> giftCardDeliveryDetailVOS) {
        this.giftCardDeliveryDetailVOS = giftCardDeliveryDetailVOS;
    }
}
