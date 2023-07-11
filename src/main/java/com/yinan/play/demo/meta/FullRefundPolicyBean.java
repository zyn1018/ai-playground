/**
 * @(#)ReturnFirstPolicyVO.java, 2020/9/14.
 * <p>
 * Copyright 2020 NetEase, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.yinan.play.demo.meta;

import java.util.List;

/**
 * @author Yinan Zhang (zhangyinan01@corp.netease.com)
 */
public class FullRefundPolicyBean {
    /**
     * 规则标题列表
     */
    private List<String> titles;

    /**
     * 规则明细标题
     */
    private String detailTitle;

    /**
     * 规则明晰
     */
    private List<String> content;

    public List<String> getTitles() {
        return titles;
    }

    public void setTitles(List<String> titles) {
        this.titles = titles;
    }

    public String getDetailTitle() {
        return detailTitle;
    }

    public void setDetailTitle(String detailTitle) {
        this.detailTitle = detailTitle;
    }

    public List<String> getContent() {
        return content;
    }

    public void setContent(List<String> content) {
        this.content = content;
    }
}
