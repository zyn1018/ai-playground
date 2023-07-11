package com.yinan.play.demo.meta;

import java.math.BigDecimal;

/**
 * 分享有礼奖品信息解析
 *
 * @author hzyangming1
 */
public class LotteryGiftBean {

    /**
     * 激活码id
     */
    private long id;

    /**
     * 优惠券码
     */
    private String couponCode;

    /**
     * 优惠金额
     */
    BigDecimal cash;

    /**
     * 有效期，天数
     */
    private int validDate;

    /**
     * 状态
     */
    private int state;

    /**
     * 抽奖概率
     */
    private double probability;

    /**
     * 中奖提示文案
     */
    private String desc;

    @Override
    public String toString() {
        return "LotteryGiftVO [id=" + id + ", couponCode=" + couponCode
            + ", cash=" + cash + ", validDate=" + validDate + ", state=" + state
            + ", probability=" + probability + ", desc=" + desc + "]";
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public BigDecimal getCash() {
        return cash;
    }

    public void setCash(BigDecimal cash) {
        this.cash = cash;
    }

    public int getValidDate() {
        return validDate;
    }

    public void setValidDate(int validDate) {
        this.validDate = validDate;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
