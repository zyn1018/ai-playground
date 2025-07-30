/**
 * @(#)OMSOrderOutBean.java, 2018/10/16.
 * <p>
 * Copyright 2018 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.yinan.play.demo.meta;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * https://wiki.mail.netease.com/display/yxfx/OmsToChannelOrder
 *
 * @author 雷琨(hzleikun @ corp.netease.com)
 */
public class OMSOrderOutBean {

    private String orderId;

    private String submitTime;

    private String payTime;

    private String buyerAccount;

    private String receiverName;

    private String receiverMobile;

    private String receiverPhone;

    private String receiverPostcode;

    private String receiverStateName;

    private String receiverProvinceName;

    private String receiverCityName;

    private String receiverDistrictName;

    private String receiverTownName;

    private String receiverAddressDetail;

    private String receiverFullAddress;

    private BigDecimal realPrice;

    private BigDecimal expFee;

    private String payMethod;

    private String invoiceTitle;

    private BigDecimal invoiceAmount;

    private String orderStatus;

    private List<OMSOrderPackageBean> orderPackages;

    private OMSOrderInvoiceInfoBean invoiceInfo;

    private Map<String, String> extInfo;

    /**
     * 扩展信息
     */
    private OrderAttributeBean orderAttribute;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(String submitTime) {
        this.submitTime = submitTime;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getBuyerAccount() {
        return buyerAccount;
    }

    public void setBuyerAccount(String buyerAccount) {
        this.buyerAccount = buyerAccount;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverMobile() {
        return receiverMobile;
    }

    public void setReceiverMobile(String receiverMobile) {
        this.receiverMobile = receiverMobile;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    public String getReceiverPostcode() {
        return receiverPostcode;
    }

    public void setReceiverPostcode(String receiverPostcode) {
        this.receiverPostcode = receiverPostcode;
    }

    public String getReceiverStateName() {
        return receiverStateName;
    }

    public void setReceiverStateName(String receiverStateName) {
        this.receiverStateName = receiverStateName;
    }

    public String getReceiverProvinceName() {
        return receiverProvinceName;
    }

    public void setReceiverProvinceName(String receiverProvinceName) {
        this.receiverProvinceName = receiverProvinceName;
    }

    public String getReceiverCityName() {
        return receiverCityName;
    }

    public void setReceiverCityName(String receiverCityName) {
        this.receiverCityName = receiverCityName;
    }

    public String getReceiverDistrictName() {
        return receiverDistrictName;
    }

    public void setReceiverDistrictName(String receiverDistrictName) {
        this.receiverDistrictName = receiverDistrictName;
    }

    public String getReceiverTownName() {
        return receiverTownName;
    }

    public void setReceiverTownName(String receiverTownName) {
        this.receiverTownName = receiverTownName;
    }

    public String getReceiverAddressDetail() {
        return receiverAddressDetail;
    }

    public void setReceiverAddressDetail(String receiverAddressDetail) {
        this.receiverAddressDetail = receiverAddressDetail;
    }

    public String getReceiverFullAddress() {
        return receiverFullAddress;
    }

    public void setReceiverFullAddress(String receiverFullAddress) {
        this.receiverFullAddress = receiverFullAddress;
    }

    public BigDecimal getRealPrice() {
        return realPrice;
    }

    public void setRealPrice(BigDecimal realPrice) {
        this.realPrice = realPrice;
    }

    public BigDecimal getExpFee() {
        return expFee;
    }

    public void setExpFee(BigDecimal expFee) {
        this.expFee = expFee;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    public String getInvoiceTitle() {
        return invoiceTitle;
    }

    public void setInvoiceTitle(String invoiceTitle) {
        this.invoiceTitle = invoiceTitle;
    }

    public BigDecimal getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(BigDecimal invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public List<OMSOrderPackageBean> getOrderPackages() {
        return orderPackages;
    }

    public void setOrderPackages(List<OMSOrderPackageBean> orderPackages) {
        this.orderPackages = orderPackages;
    }

    public OMSOrderInvoiceInfoBean getInvoiceInfo() {
        return invoiceInfo;
    }

    public void setInvoiceInfo(OMSOrderInvoiceInfoBean invoiceInfo) {
        this.invoiceInfo = invoiceInfo;
    }

    public Map<String, String> getExtInfo() {
        return extInfo;
    }

    public void setExtInfo(Map<String, String> extInfo) {
        this.extInfo = extInfo;
    }

    public OrderAttributeBean getOrderAttribute() {
        return orderAttribute;
    }

    public void setOrderAttribute(OrderAttributeBean orderAttribute) {
        this.orderAttribute = orderAttribute;
    }
}