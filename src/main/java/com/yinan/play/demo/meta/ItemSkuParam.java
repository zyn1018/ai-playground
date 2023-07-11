package com.yinan.play.demo.meta;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author swh
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemSkuParam {
    /**
     * 商品id
     */
    private Long itemId;

    /**
     * 指定显示的skuId
     */
    private Long skuId;

    /**
     * 指定显示的sku列表，与skuId互斥
     */
    private List<Long> skuIdList;

    /**
     * 是否是置顶商品
     */
    private boolean stickAtTop;
}
