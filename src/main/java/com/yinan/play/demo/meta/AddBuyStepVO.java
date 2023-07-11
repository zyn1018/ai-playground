/**
 * @(#)AddBuyStepVO.java, 2021/3/8.
 * <p>
 * Copyright 2021 NetEase, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.yinan.play.demo.meta;

/**
 * @author Yinan Zhang (zhangyinan01@corp.netease.com)
 */
public class AddBuyStepVO {
    /**
     * 阶梯编号
     */
    private int stepNo;

    /**
     * 是否满足该阶梯
     */
    private boolean satisfy;

    /**
     * 阶梯配置的换购商品数量
     */
    private int configCount;

    public int getStepNo() {
        return stepNo;
    }

    public void setStepNo(int stepNo) {
        this.stepNo = stepNo;
    }

    public boolean isSatisfy() {
        return satisfy;
    }

    public void setSatisfy(boolean satisfy) {
        this.satisfy = satisfy;
    }

    public int getConfigCount() {
        return configCount;
    }

    public void setConfigCount(int configCount) {
        this.configCount = configCount;
    }
}
