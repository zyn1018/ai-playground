/**
 * @(#)AbtExperimentKeyVO.java, 2019年07月22日.
 * <p>
 * Copyright 2019 Netease, Inc. All rights reserved. NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.yinan.play.demo.meta;

/**
 * 移动端传递到AppServer后台的实验信息
 *
 * @author lxl
 */
public class AbtExperimentKeyVO {

    /**
     * 组装的groupId信息:"1_HOMEPAGE_BANNER_2_TAC_002_001", //实验ID，
     * $(ABT协议版本号)_$(页面)_$(模块)_$(位置)_$(服务标识)_$(实验ID)_$(方案ID) ：需要回传
     * <p>
     *
     */
    private String groupId;

    /**
     * 扩展信息：需要回传
     */
    private String extend;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getExtend() {
        return extend;
    }

    public void setExtend(String extend) {
        this.extend = extend;
    }
}
