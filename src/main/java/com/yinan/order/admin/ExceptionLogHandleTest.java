/**
 * @(#)ExceptionLogHandleTest.java, 2025/7/21.
 * <p/>
 * Copyright 2025 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.yinan.order.admin;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @author 吴文铠 (wuwenkai@corp.netease.com)
 */
public class ExceptionLogHandleTest {

    public static void main(String[] args) {
        // 将运维文件放置在指定目录下
        String filePath = "/Users/yinan/Downloads/";
        String fileName = filePath + "6375_logDownload.txt";
        List<String> logList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                logList.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<OrderExceptionLogBean> orderExceptionLogBeanList = new ArrayList<>();
        for (String log: logList) {
            int messageStart = log.indexOf("message=");
            String message = log.substring(messageStart + 8);
            orderExceptionLogBeanList.add(JSON.parseObject(message, OrderExceptionLogBean.class));
        }
        Map<String, String> channelOwnerMap = generateChannelOwnerMap();
        List<String> result = new ArrayList<>();
        for (OrderExceptionLogBean orderExceptionLogBean: orderExceptionLogBeanList) {
            Long channelId = orderExceptionLogBean.getChannelId();
            String typeName = orderExceptionLogBean.getTypeName();
            String extraInfo = orderExceptionLogBean.getExtraInfo();
            OrderExceptionType orderExceptionType = OrderExceptionType.of(typeName);
            if (null == orderExceptionType) {
                continue;
            }
            Map<String, String> resultMap = parseExtraInfo(channelId, orderExceptionType, extraInfo);
            if (MapUtils.isEmpty(resultMap)) {
                continue;
            }
            String orderId = resultMap.get("orderId");
            String curl = resultMap.get("curl");
            String applyId = resultMap.get("applyId");
            if (null == curl || "".equals(curl)) {
                continue;
            }
            if (typeName.equals(OrderExceptionType.REFUND_CALLBACK.getTypeName())) {
                orderId = applyId;
            }
            if (null == orderId || "".equals(orderId)) {
                continue;
            }
            curl = curl.replaceAll("\t", "");
            String ownerName = channelOwnerMap.get(String.valueOf(channelId));
            if (null == ownerName || "".equals(ownerName)) {
                ownerName = "刘帅";
            }
            result
                .add(ownerName + "\t" + orderExceptionType.getDesc() + "\t" + channelId + "\t" + orderId + "\t" + curl);
        }
        System.out.println("异常数量：" + result.size());
        writeListToFile(result, filePath + "orderExceptionResult.txt");
    }

    private static Map<String, String> parseExtraInfo(Long channelId, OrderExceptionType orderExceptionType,
        String extraInfo) {
        Map<String, String> resultMap = new HashMap<>();
        JSONObject extraInfoObject = JSON.parseObject(extraInfo);
        String orderId = null;
        String curl = null;
        String order;
        JSONObject orderObject;
        switch (orderExceptionType) {
            case ORDER_PRE_CREATE:
                order = extraInfoObject.getString("order");
                orderObject = JSON.parseObject(order);
                orderId = orderObject.getString("orderId");
                curl = "curl -X POST -d 'channelId=" + channelId + "&order=" + order + "' "
                    + "'http://127.0.0.1:8550/proxy/online.yanxuan-distribution-wolverine.service.mailsaas/wolverine/api/openapi/op/v1/order/api/preCreate'";
                break;
            case ORDER_PRE_CONFIRM:
                orderId = extraInfoObject.getString("orderId");
                curl = "curl 'http://127.0.0.1:8550/proxy/online.yanxuan-distribution-wolverine.service.mailsaas/wolverine/api/openapi/op/v1/order/api/preConfirm?channelId="
                    + channelId + "&orderId=" + orderId + "'";
                break;
            case ORDER_CREATE:
                order = extraInfoObject.getString("order");
                orderObject = JSON.parseObject(order);
                orderId = orderObject.getString("orderId");
                curl = "curl -X POST -d 'channelId=" + channelId + "&order=" + order + "' "
                    + "'http://127.0.0.1:8550/proxy/online.yanxuan-distribution-wolverine.service.mailsaas/wolverine/api/openapi/op/v1/order/api/create'";
                break;
            case ORDER_CANCEL:
                orderId = extraInfoObject.getString("orderId");
                Boolean intercept = extraInfoObject.getBoolean("intercept");
                String skuIds = extraInfoObject.getString("skuIds");
                String skuInfo = extraInfoObject.getString("skuInfo");
                String requestId = extraInfoObject.getString("requestId");
                Boolean supplement = extraInfoObject.getBoolean("supplement");
                String version = extraInfoObject.getString("version");
                curl = "curl -X POST -d 'channelId=" + channelId + "&orderId=" + orderId;
                if (Boolean.TRUE.equals(intercept)) {
                    curl = curl + "&intercept=true";
                }
                if (null != skuIds && !"".equals(skuIds)) {
                    curl = curl + "&skuIds=" + skuIds;
                }
                if (null != skuInfo && !"".equals(skuInfo)) {
                    curl = curl + "&skuInfo=" + skuInfo;
                }
                if (null != requestId && !"".equals(requestId)) {
                    curl = curl + "&requestId=" + requestId;
                }
                if (Boolean.TRUE.equals(supplement)) {
                    curl = curl + "&supplement=true";
                }
                curl = curl + "' "
                    + "'http://127.0.0.1:8550/proxy/online.yanxuan-distribution-wolverine.service.mailsaas/wolverine/api/openapi/op/"
                    + version + "/order/api/cancel'";
                break;
            case ORDER_CONFIRM_PACKAGE_RECEIPT:
                orderId = extraInfoObject.getString("orderId");
                String packageId = extraInfoObject.getString("packageId");
                String confirmTime = extraInfoObject.getString("confirmTime");
                try {
                    confirmTime = URLEncoder.encode(confirmTime, StandardCharsets.UTF_8.name());
                } catch (UnsupportedEncodingException e) {
                    break;
                }
                curl = "curl 'http://127.0.0.1:8550/proxy/online.yanxuan-distribution-wolverine.service.mailsaas/wolverine/api/openapi/op/v1/order/api/confirmPackageReceipt?channelId="
                    + channelId + "&orderId=" + orderId + "&packageId=" + packageId + "&confirmTime=" + confirmTime
                    + "'";
                break;
            case ORDER_CANCEL_NOTIFY:
                String cancelNotify = extraInfoObject.getString("payload");
                JSONObject cancelNotifyObject = JSON.parseObject(cancelNotify);
                JSONObject dataObject = JSON.parseObject(cancelNotifyObject.getString("data"));
                orderId = dataObject.getString("orderId");
                curl = "curl -H Content-Type:application/json -X POST 'http://127.0.0.1:8550/proxy/online.yanxuan-distribution-wolverine.service.mailsaas/wolverine/bd/api/v1/order/cancel/oms/callback' -d "
                    + "'" + cancelNotify + "'";
                break;
            case ORDER_CHANGE_ADDRESS:
                order = extraInfoObject.getString("order");
                orderObject = JSON.parseObject(order);
                orderId = orderObject.getString("orderId");
                curl = "curl -X POST -d 'channelId=" + channelId + "&order=" + order + "' "
                    + "'http://127.0.0.1:8550/proxy/online.yanxuan-distribution-wolverine.service.mailsaas/wolverine/api/openapi/op/v1/order/api/address/change'";
                break;
            case ORDER_THIRD_PARTY_CHANGE_ADDRESS:
                String orderAddress = extraInfoObject.getString("orderAddress");
                JSONObject orderAddressObject = JSON.parseObject(orderAddress);
                orderId = orderAddressObject.getString("orderId");
                curl = "curl -X POST -d 'channelId=" + channelId + "&orderAddress=" + orderAddress + "' "
                    + "'http://127.0.0.1:8550/proxy/online.yanxuan-distribution-wolverine.service.mailsaas/wolverine/api/openapi/op/v1/order/api/thirdparty/address/change'";
                break;
            case RETURN_GOODS_REFUND_CREATE:
                String applyInfo = extraInfoObject.getString("applyInfo");
                JSONObject applyInfoObject = JSON.parseObject(applyInfo);
                orderId = applyInfoObject.getString("orderId");
                curl = "curl -X POST -d 'channelId=" + channelId + "&applyInfo=" + applyInfo + "' "
                    + "'http://127.0.0.1:8550/proxy/online.yanxuan-distribution-wolverine.service.mailsaas/wolverine/api/openapi/op/v1/refund/api/create'";
                break;
            case REFUND_CALLBACK:
                String refundCallback = extraInfoObject.getString("payload");
                String applyId = extraInfoObject.getString("applyId");
                resultMap.put("applyId", applyId);
                curl = "curl -H Content-Type:application/json -X POST 'http://127.0.0.1:8550/proxy/online.yanxuan-distribution-wolverine.service.mailsaas/wolverine/bd/api/v1/order/refund/callback' -d "
                    + "'" + refundCallback + "'";
                break;
            case OMS_ORDER_CALLBACK:
                String omsOrderCallBack = extraInfoObject.getString("payload");
                String method = extraInfoObject.getString("method");
                String paramName = getOmsCallBackParamName(method);
                if (null == paramName) {
                    break;
                }
                JSONObject omsOrderCallBackObject = JSON.parseObject(omsOrderCallBack);
                orderId = omsOrderCallBackObject.getString("orderId");
                try {
                    omsOrderCallBack = URLEncoder.encode(omsOrderCallBack, StandardCharsets.UTF_8.name());
                } catch (UnsupportedEncodingException e) {
                    break;
                }
                curl = "curl -d " + "\"method=" + method + "&channelId=" + channelId + "&" + paramName + "="
                    + omsOrderCallBack + "\" "
                    + "'http://127.0.0.1:8550/proxy/online.yanxuan-distribution-wolverine.service.mailsaas/inner/api'";
            default:
                break;
        }
        resultMap.put("orderId", orderId);
        resultMap.put("curl", curl);
        return resultMap;
    }

    public static String getOmsCallBackParamName(String method) {
        if ("yanxuan.callback.order.cancel".equals(method)) {
            return "orderCancelResult";
        }
        if ("yanxuan.callback.package.cancel".equals(method)) {
            return "orderPackageCancelResult";
        }
        if ("yanxuan.notification.order.exceptional".equals(method)) {
            return "exceptionInfo";
        }
        if ("yanxuan.notification.order.delivered".equals(method)) {
            return "orderPackage";
        }
        return null;
    }

    public static Map<String, String> generateChannelOwnerMap() {
        // 创建一个 Map 来存储渠道 ID 和负责人
        Map<String, String> channelMap = new HashMap<>();

        // 将数据添加到 Map 中
        addChannels(channelMap, "沈月东",
            new String[] { "5995821", "6900998", "6901147", "6901299", "6901508", "6901731", "6901857", "6902335",
                "6902778", "6902881", "6902880", "6903282", "6903313", "6903388", "6903256", "6903435", "6903556",
                "6903643", "6903890", "6801062", "6901590", "6902859", "6902882", "6903283", "6904081", "6800980",
                "6903277", "6800980", "6903277" });

        addChannels(channelMap, "吴文铠",
            new String[] { "1990361", "235524", "6800331", "6800357", "6801219", "6801308", "6800255", "6901026",
                "6901511", "6902578", "6902685", "6902833", "6902523", "1007", "5080689", "2231542", "6904373",
                "6902403", "6901506", "6900786", "6902620", "6904556" });

        addChannels(channelMap, "李国雍", new String[] { "6900818", "6903138", "6801369" });

        addChannels(channelMap, "张广臣", new String[] { "6904012", "6902636", "6902637", "6902625", "6902815", "6903018",
            "6903017", "6903981", "6903990" });

        addChannels(channelMap, "张广臣", new String[] { "6902366", "6801297", "6903891", "6903892", "6903893" });

        addChannels(channelMap, "张一楠", new String[] { "95094" });

        addChannels(channelMap, "蒲玉杰", new String[] { "6900885", "6903384", "6902587", "6903198" });
        return channelMap;
    }

    // 辅助方法，用于将渠道 ID 和负责人添加到 Map 中
    private static void addChannels(Map<String, String> map, String manager, String[] ids) {
        for (String id: ids) {
            map.put(id, manager);
        }
    }

    public static void writeListToFile(List<String> list, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String line: list) {
                writer.write(line);
                writer.newLine(); // 写入新行
            }
            System.out.println("写入成功！");
        } catch (IOException e) {
            System.err.println("写入文件时发生错误: " + e.getMessage());
        }
    }

}
