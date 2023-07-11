/**
 * @(#)ItemDotModelVO.java, 2017/12/7.
 * Copyright 2017 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.yinan.play.demo.meta;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * VO对象：列表页商品通用服务端埋点数据结构
 * <p>
 * 从资源配置中心接入开始，启用了服务端埋点方案，移动端透传该数据
 * 
 * @author 洪汉金(honghanjin@corp.netease.com)
 */
public class ItemDotModelVO {

    // 新版资源配置中心
    public final static int MODEL_TYPE_RESOURCES = 1;

    // 推荐系统
    public final static int MODEL_TYPE_RECOMMEND = 2;

    // 兜底商品
    public final static int MODEL_TYPE_DOUDI = 3;

    // 旧版资源管理平台（yanxuan-admin）
    public final static int MODEL_TYPE_ADMIN_RESOURCES = 4;

    // 运营工作台配置
    public final static int MODEL_TYPE_OPERATION_RESOURCES = 5;

    //搜索结果页app上报字段
    public final static int MODEL_TYPE_SEARCH_RESULT_POP_WINDOW = 6;

    //商详abt
    public final static int MODEL_TYPE_ITEMDETAIL_ABT = 7;

    //算法统一埋点extra
    public final static int MODEL_TYPE_ALGORITHM_EXTRA = 8;

    // 埋点数据模型类型：0-无效，1-资源配置中心，2-推荐系统，3-兜底商品，4-旧版资源管理平台（yanxuan-admin），5-运营工作台配置
    private int modelType = 0;

    // modelType = 6时该结构有效
    private Object searchExtraData;

    // modelType = 8时该结构有效
    private Object alg_personalized;

    /**
     * abt数据埋点 实验ID_方案ID "abtest_dis":'10_11|12_13' #实验ID_方案ID（rdc或者rcmd来源的多个实验
     * 以 "|" 分隔）
     *
     */
    @JSONField(name = "abtest_dis")
    private String abtestDis;

    private String rcmdVer;

    private String type;

    public String getRcmdVer() {
        return rcmdVer;
    }

    public void setRcmdVer(String rcmdVer) {
        this.rcmdVer = rcmdVer;
    }

    public int getModelType() {
        return modelType;
    }

    public void setModelType(int modelType) {
        this.modelType = modelType;
    }

    public Object getSearchExtraData() {
        return searchExtraData;
    }

    public void setSearchExtraData(Object searchExtraData) {
        this.searchExtraData = searchExtraData;
    }

    public String getAbtestDis() {
        return abtestDis;
    }

    public void setAbtestDis(String abtestDis) {
        this.abtestDis = abtestDis;
    }

    public Object getAlg_personalized() {
        return alg_personalized;
    }

    public void setAlg_personalized(Object alg_personalized) {
        this.alg_personalized = alg_personalized;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
