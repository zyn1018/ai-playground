/**
 * @(#)ComplexTextVO.java, 2017/5/19.
 * 
 * Copyright 2017 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.yinan.play.demo.meta;

/**
 * 复杂文本数据结构，用于将复杂文本切片，便于前端装配
 * 
 * @author 陈新超(hzchenxinchao@corp.netease.com)
 */
public class ComplexTextVO {
    //    文字描述
    private String value;

    /**
     * 颜色类型：0-黑色，1-蓝色，2-红色 3-灰色 4-白色 5-浅灰色
     */
    private int type;

    //    跳转链接，为空表示无需跳转
    private String schemeUrl;

    /**
     * 是否加粗
     */
    private boolean bold = false;

    /**
     * 是否划线
     */
    private boolean crossLine = false;

    /**
     * 是否下划线
     */
    private boolean underLine = false;

    /**
     * 字号大小
     */
    private int fontSize;

    public ComplexTextVO() {}

    public ComplexTextVO(String value) {
        this.value = value;
        this.type = ComplexTextColorConsts.STAMP_BLACK;
    }

    public boolean isBold() {
        return bold;
    }

    public void setBold(boolean bold) {
        this.bold = bold;
    }

    public ComplexTextVO(String value, int type) {
        this.value = value;
        this.type = type;
    }

    public ComplexTextVO(String value, int type, boolean bold,
                         boolean crossLine) {
        this.value = value;
        this.type = type;
        this.bold = bold;
        this.crossLine = crossLine;
    }

    public ComplexTextVO(String value, int type, boolean bold) {
        this.value = value;
        this.type = type;
        this.bold = bold;
    }

    public ComplexTextVO(String value, int type, boolean bold, int fontSize) {
        this.value = value;
        this.type = type;
        this.bold = bold;
        this.fontSize = fontSize;
    }

    public ComplexTextVO(String value, int type, String shemeUrl) {
        this.value = value;
        this.type = type;
        this.schemeUrl = shemeUrl;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getSchemeUrl() {
        return schemeUrl;
    }

    public void setSchemeUrl(String schemeUrl) {
        this.schemeUrl = schemeUrl;
    }

    public boolean isCrossLine() {
        return crossLine;
    }

    public void setCrossLine(boolean crossLine) {
        this.crossLine = crossLine;
    }

    public boolean isUnderLine() {
        return underLine;
    }

    public void setUnderLine(boolean underLine) {
        this.underLine = underLine;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }
}
