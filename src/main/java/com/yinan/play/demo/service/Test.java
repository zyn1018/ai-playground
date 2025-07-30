/**
 * @(#)Test.java, 2024/8/14.
 *
 * Copyright 2024 NetEase, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.yinan.play.demo.service;

/**
 * @author Yinan Zhang (zhangyinan01@corp.netease.com)
 */
public class Test {

    public static void main(String[] args) {
        try (AutoCloseableImpl autoCloseable = new AutoCloseableImpl()) {
            System.out.println("Finish process");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class AutoCloseableImpl implements AutoCloseable {
        @Override
        public void close() throws Exception {
            System.out.println("Close resource");
        }
    }
}
