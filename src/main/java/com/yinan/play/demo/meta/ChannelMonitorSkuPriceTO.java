package com.yinan.play.demo.meta;

import lombok.Data;

/**
 * @author Yinan Zhang (zhangyinan01@corp.netease.com)
 */
@Data
public class ChannelMonitorSkuPriceTO {


    // 严选skuId
    private Long yxSkuId;

    // 价格
    private String price;

    //拉取日 年月日，格式： yyyy-MM-dd  比如 2024-01-01
    private String graspDay;
}
