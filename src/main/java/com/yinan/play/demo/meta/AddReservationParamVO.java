/**
 * @(#)AddReservationParam.java, 2021/11/15.
 * <p>
 */
package com.yinan.play.demo.meta;

import java.util.List;

/**
 * @author Yinan Zhang (zhangyinan01@corp.netease.com)
 */
public class AddReservationParamVO {
    /**
     * 场次id
     */
    Long sessionId;

    /**
     * 门店id
     */
    String shopId;

    /**
     * 商品信息
     */
    List<ReservationItemInfoVO> itemInfoList;

    /**
     * 加密参数
     */
    String encryptedParam;

    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public List<ReservationItemInfoVO> getItemInfoList() {
        return itemInfoList;
    }

    public void setItemInfoList(List<ReservationItemInfoVO> itemInfoList) {
        this.itemInfoList = itemInfoList;
    }

    public String getEncryptedParam() {
        return encryptedParam;
    }

    public void setEncryptedParam(String encryptedParam) {
        this.encryptedParam = encryptedParam;
    }
}
