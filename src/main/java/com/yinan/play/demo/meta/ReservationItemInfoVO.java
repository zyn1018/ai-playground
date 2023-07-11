/**
 * @(#)ItemInfoVO.java, 2021/11/15.
 * <p>
 *
 */
package com.yinan.play.demo.meta;

/**
 * @author Yinan Zhang (zhangyinan01@corp.netease.com)
 */
public class ReservationItemInfoVO {
    /**
     * 商品id
     */
    String itemId;

    /**
     * 预约件数
     */
    Integer count;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "ItemInfoVO{" + "itemId=" + itemId + ", count=" + count + '}';
    }
}
