/**
 * @(#)OMSSkuDetailBean.java, 2020-11-19.
 * <p/>
 * Copyright 2020 Netease, Inc. All rights reserved. NETEASE PROPRIETARY/CONFIDENTIAL. Use is
 * subject to license terms.
 */
package com.yinan.play.demo.meta;

/**
 * @author 张欢(zhanghuan04 @ corp.netease.com)
 */
public class OMSSkuDetailBean {

  private String skuId;

  private int saleCount;

  public String getSkuId() {
    return skuId;
  }

  public void setSkuId(String skuId) {
    this.skuId = skuId;
  }

  public int getSaleCount() {
    return saleCount;
  }

  public void setSaleCount(int saleCount) {
    this.saleCount = saleCount;
  }
}