package com.yinan.play.demo.meta;

import lombok.Data;

/**
 * @author Yinan Zhang (zhangyinan01@corp.netease.com)
 */
@Data
public class ChannelMonitorSkuRecordSortPO {

    // 平台code(淘宝tb、京东jd)
    private String platformCode;

    // 严选skuId
    private Long yxSkuId;

    // 渠道skuId
    private String channelSkuId;

    // 渠道spuId
    private String channelSpuId;

    // 渠道价格1(淘宝直降价,京东红字价)
    private String price1;

    // 渠道价格2(淘宝折扣/券后价,京东到手价)
    private String price2;

    // 维护价格1近30天价格滑动单调递增队列，O（1）时间复杂度拿到最低值
    private String priceLowestArray1;

    // 维护价格2近30天价格滑动单调递增队列，O（1）时间复杂度拿到最低值
    private String priceLowestArray2;

    // 价格1近30天价格（记录可能小于30天）
    private String price30Array1;

    // 价格1近30天价格（记录可能小于30天）
    private String price30Array2;

}
