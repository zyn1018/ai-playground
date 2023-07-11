/**
 * @(#)TestController.java, 2020/6/10.
 * <p>
 * Copyright 2020 NetEase, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.yinan.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Yinan Zhang (zhangyinan01@corp.netease.com)
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping(value = "/rebel")
    public String getName() {
        return "Yinan Zhang";
    }
}
