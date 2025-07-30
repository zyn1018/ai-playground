/**
 * @(#)OrderExceptionLogBean.java, 2025/7/17.
 * <p/>
 * Copyright 2025 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.yinan.order.admin;

import lombok.Data;

/**
 * @author 吴文铠 (wuwenkai@corp.netease.com)
 */
@Data
public class OrderExceptionLogBean {

    private long channelId;

    private String typeName;

    private String extraInfo;

}