/**
 * @(#)OMSGiftCardDeliveryDetailBean.java, 2020-05-12.
 * <p/>
 * Copyright 2020 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.yinan.play.demo.meta;

/**
 * @author 邵一哲(hzshaoyizhe @ corp.netease.com)
 */
public class OMSGiftCardDeliveryDetailBean {

    /**
     * 卡号
     */
    private String serialNumber;

    /**
     * 卡密
     */
    private String secret;

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
