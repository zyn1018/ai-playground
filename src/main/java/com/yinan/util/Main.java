/**
 * @(#)Main.java, 2024/10/14.
 *
 * Copyright 2024 NetEase, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.yinan.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Yinan Zhang (zhangyinan01@corp.netease.com)
 */
public class Main {
    public static void main(String args[]) {
        System.out.println(gcd(15,35));
    }

    public static void mergeAndPrint(List<Integer> longerList, List<Integer> shorterList, int totalNodeCount,
        Map<Integer, String> nodeDataAddressMap) {
        int j = 0;
        int k = shorterList.size() - 1;
        List<Integer> mergedList = new ArrayList<>(totalNodeCount);
        for (int i = 0; i < totalNodeCount; i++) {
            if (i % 3 == 2 && k >= 0) {
                mergedList.add(shorterList.get(k--));
            } else {
                mergedList.add(longerList.get(j++));
            }
        }

        for (int i = 0; i < totalNodeCount; i++) {
            Integer data = mergedList.get(i);
            if (i == totalNodeCount - 1) {
                System.out.println(nodeDataAddressMap.get(data) + " " + data + " " + "-1");
            } else {
                System.out.println(
                    nodeDataAddressMap.get(data) + " " + data + " " + nodeDataAddressMap.get(mergedList.get(i + 1)));
            }
        }
    }

    public static int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    public static long reverse(long num, boolean isEven) {
        long res = num;
        if (!isEven) {
            num /= 10;
        }
        while (num != 0) {
            res = res * 10 + num % 10;
            num /= 10;
        }
        return res;
    }
}
