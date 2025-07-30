/**
 * @(#)MapPartitioner.java, 2025/1/16.
 *
 * Copyright 2025 NetEase, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.yinan.play.demo.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.collections4.MapUtils;

/**
 * @author Yinan Zhang (zhangyinan01@corp.netease.com)
 */
public class MapPartitioner {
    public static <K, V> List<Map<K, V>> partitionMap(Map<K, V> originalMap, int batchSize) {
        if (MapUtils.isEmpty(originalMap) || batchSize <= 0) {
            return new ArrayList<>();
        }
        // 将原始 Map 的 entrySet 转换为 List
        List<Map.Entry<K, V>> entries = new ArrayList<>(originalMap.entrySet());

        // 使用 IntStream 分批
        return IntStream.range(0, (entries.size() + batchSize - 1) / batchSize)
            .mapToObj(i -> entries.stream().skip((long) i * batchSize).limit(batchSize)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)))
            .collect(Collectors.toList());
    }
}
