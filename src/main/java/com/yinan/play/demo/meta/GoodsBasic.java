package com.yinan.play.demo.meta;

import java.util.List;

import lombok.Data;

/**
 * 商品在GOODS中的形态
 *
 * @author swh
 */
@Data
public class GoodsBasic {

    /**
     * 商品id
     */
    private long itemId;

    /**
     * sku id 列表
     */
    private List<Long> skuIds;

    /**
     * 是否备用
     */
    private boolean standByFlag;

    /**
     * 商品在goods系统中的id
     */
    private long goodsId;

    /**
     * 促销Id(秒杀)
     */
    private long promotionId;

    /**
     * 是否为置顶商品，多个置顶商品之间按录入顺序排序
     */
    private boolean stickAtTop;
}
