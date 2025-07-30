/**
 * @(#)OrderAttributeBean.java, 2020-12-10.
 * <p/>
 * Copyright 2020 Netease, Inc. All rights reserved. NETEASE PROPRIETARY/CONFIDENTIAL. Use is
 * subject to license terms.
 */
package com.yinan.play.demo.meta;

import java.math.BigDecimal;

/**
 * @author 张欢(zhanghuan04 @ corp.netease.com)
 */
public class OrderAttributeBean {

    private BigDecimal realAmount;

    private BigDecimal realWelfare;

    private BigDecimal expAmount;

    private BigDecimal expWelfare;

    private String erpParentOrderId;

    private String erpShopName;

    private String erpWarehouseName;

    private String erpCarrierName;

    private String modifyAddressOperator;

    private long modifyAddressTime;

    private String markOperator;

    private long markTime;

    private String skuRation;

    public BigDecimal getRealAmount() {
        return realAmount;
    }

    public void setRealAmount(BigDecimal realAmount) {
        this.realAmount = realAmount;
    }

    public BigDecimal getRealWelfare() {
        return realWelfare;
    }

    public void setRealWelfare(BigDecimal realWelfare) {
        this.realWelfare = realWelfare;
    }

    public BigDecimal getExpAmount() {
        return expAmount;
    }

    public void setExpAmount(BigDecimal expAmount) {
        this.expAmount = expAmount;
    }

    public BigDecimal getExpWelfare() {
        return expWelfare;
    }

    public void setExpWelfare(BigDecimal expWelfare) {
        this.expWelfare = expWelfare;
    }

    public String getErpParentOrderId() {
        return erpParentOrderId;
    }

    public void setErpParentOrderId(String erpParentOrderId) {
        this.erpParentOrderId = erpParentOrderId;
    }

    public String getErpShopName() {
        return erpShopName;
    }

    public void setErpShopName(String erpShopName) {
        this.erpShopName = erpShopName;
    }

    public String getErpWarehouseName() {
        return erpWarehouseName;
    }

    public void setErpWarehouseName(String erpWarehouseName) {
        this.erpWarehouseName = erpWarehouseName;
    }

    public String getErpCarrierName() {
        return erpCarrierName;
    }

    public void setErpCarrierName(String erpCarrierName) {
        this.erpCarrierName = erpCarrierName;
    }

    public String getModifyAddressOperator() {
        return modifyAddressOperator;
    }

    public void setModifyAddressOperator(String modifyAddressOperator) {
        this.modifyAddressOperator = modifyAddressOperator;
    }

    public long getModifyAddressTime() {
        return modifyAddressTime;
    }

    public void setModifyAddressTime(long modifyAddressTime) {
        this.modifyAddressTime = modifyAddressTime;
    }

    public String getMarkOperator() {
        return markOperator;
    }

    public void setMarkOperator(String markOperator) {
        this.markOperator = markOperator;
    }

    public long getMarkTime() {
        return markTime;
    }

    public void setMarkTime(long markTime) {
        this.markTime = markTime;
    }

    public String getSkuRation() {
        return skuRation;
    }

    public void setSkuRation(String skuRation) {
        this.skuRation = skuRation;
    }
}