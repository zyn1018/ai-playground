package com.yinan.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author Yinan Zhang (zhangyinan01@corp.netease.com)
 */
@Data
public class TobMallSkuExcelData {

    /**
     * 一级类目id
     */
    @ExcelProperty(index = 0)
    private String firstSaleCategoryId;


    /**
     * 二级类目id
     */
    @ExcelProperty(index = 2)
    private String secondSaleCategoryId;

    /**
     * 商品名称
     */
    @ExcelProperty(index = 4)
    private String itemName;

    /**
     * 商品货号
     */
    @ExcelProperty(index = 5)
    private String itemCode;

    /**
     * 运费模板id
     */
    @ExcelProperty(index = 7)
    private String shippingTemplateId;

    /**
     * 退货地址id
     */
    @ExcelProperty(index = 8)
    private String returnAddressId;

    /**
     * 规格名称1
     */
    @ExcelProperty(index = 9)
    private String specName1 = "规格";

    /**
     * 规格值1
     */
    @ExcelProperty(index = 10)
    private String specValue1;

    /**
     * 规格名称2
     */
    @ExcelProperty(index = 11)
    private String specName2;

    /**
     * 规格值2
     */
    @ExcelProperty(index = 12)
    private String specValue2;


    /**
     * 商品图片，中间用英文逗号隔开
     */
    @ExcelProperty(index = 13)
    private String itemPicUrls;

    /**
     * 商品详情html地址
     */
    @ExcelProperty(index = 14)
    private String detailHtml;

    /**
     * 7天无理由退款
     */
    @ExcelProperty(index = 15)
    private String return7daysSupport;

    /**
     * 供应商id
     */
    @ExcelProperty(index = 16)
    private String supplierId;

    /**
     * sku货号
     */
    @ExcelProperty(index = 17)
    private String skuCode;

    /**
     * 成本价
     */
    @ExcelProperty(index = 18)
    private String costPriceStr;

    /**
     * 市场价
     */
    @ExcelProperty(index = 19)
    private String marketPriceStr;

    /**
     * 库存
     */
    @ExcelProperty(index = 20)
    private String inventory;

}
