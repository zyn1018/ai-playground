/**
 * @(#)OMSOrderInvoiceInfoBean.java, 2018/10/17.
 * <p>
 * Copyright 2018 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.yinan.play.demo.meta;

import java.math.BigDecimal;

/**
 * https://wiki.mail.netease.com/display/yxfx/InvoiceInfo
 *
 * @author 雷琨(hzleikun @ corp.netease.com)
 */
public class OMSOrderInvoiceInfoBean {

    private String invoiceTitle;

    private BigDecimal invoiceAmount;

    private String invoiceContent;

    private String taxNum;

    private String registeredAddress;

    private String depositBank;

    private String rankAccount;

    private String registeredMobile;

    private Integer type;

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

    public String getInvoiceContent() {
        return invoiceContent;
    }

    public void setInvoiceContent(String invoiceContent) {
        this.invoiceContent = invoiceContent;
    }

    public String getTaxNum() {
        return taxNum;
    }

    public void setTaxNum(String taxNum) {
        this.taxNum = taxNum;
    }

    public String getRegisteredAddress() {
        return registeredAddress;
    }

    public void setRegisteredAddress(String registeredAddress) {
        this.registeredAddress = registeredAddress;
    }

    public String getDepositBank() {
        return depositBank;
    }

    public void setDepositBank(String depositBank) {
        this.depositBank = depositBank;
    }

    public String getRankAccount() {
        return rankAccount;
    }

    public void setRankAccount(String rankAccount) {
        this.rankAccount = rankAccount;
    }

    public String getRegisteredMobile() {
        return registeredMobile;
    }

    public void setRegisteredMobile(String registeredMobile) {
        this.registeredMobile = registeredMobile;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}