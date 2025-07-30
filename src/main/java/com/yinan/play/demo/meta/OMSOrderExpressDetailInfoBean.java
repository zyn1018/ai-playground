/**
 * @(#)OMSOrderExpressDetailInfoBean.java, 2018/10/17.
 * <p>
 * Copyright 2018 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.yinan.play.demo.meta;

import java.util.List;
import java.util.Map;

/**
 * https://wiki.mail.netease.com/display/yxfx/ChannelOrderExpressDetailInfo
 *
 * @author 雷琨(hzleikun @ corp.netease.com)
 */
public class OMSOrderExpressDetailInfoBean {

    private String expressCompany;

    private String expressCode;

    private String expressNo;

    private List<String> subExpressNos;

    private List<OMSOrderSkuBean> skus;

    private Long storeHouseId;

    private String storeHouseName;

    /**
     * key为物流单号（包括上述的expressNo和subExpressNos）
     * value为该物流是否妥投
     */
    private Map<String, Boolean> expressNoDeliveredMap;

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

    public List<String> getSubExpressNos() {
        return subExpressNos;
    }

    public void setSubExpressNos(List<String> subExpressNos) {
        this.subExpressNos = subExpressNos;
    }

    public List<OMSOrderSkuBean> getSkus() {
        return skus;
    }

    public void setSkus(List<OMSOrderSkuBean> skus) {
        this.skus = skus;
    }

    public Long getStoreHouseId() {
        return storeHouseId;
    }

    public void setStoreHouseId(Long storeHouseId) {
        this.storeHouseId = storeHouseId;
    }

    public String getStoreHouseName() {
        return storeHouseName;
    }

    public void setStoreHouseName(String storeHouseName) {
        this.storeHouseName = storeHouseName;
    }

    public Map<String, Boolean> getExpressNoDeliveredMap() {
        return expressNoDeliveredMap;
    }

    public void setExpressNoDeliveredMap(Map<String, Boolean> expressNoDeliveredMap) {
        this.expressNoDeliveredMap = expressNoDeliveredMap;
    }
}