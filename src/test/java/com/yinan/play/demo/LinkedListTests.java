package com.yinan.play.demo;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSON;
import com.yinan.play.demo.meta.algorithm.Node;
import com.yinan.play.demo.util.ParseMD5;

/**
 * 链表相关算法题
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class LinkedListTests {

    @Test
    public void contextLoads() {
        Map<String, Object> innerRequestParam = new TreeMap<>();
        innerRequestParam.put("paramJson",
            "{\"order\":\"{\\\"orderAddressInfo\\\":{\\\"address\\\":\\\"会展湾东城小区1栋5单元2012\\\",\\\"cityId\\\":\\\"440300\\\",\\\"cityName\\\":\\\"深圳市\\\",\\\"districtId\\\":\\\"440306\\\",\\\"districtName\\\":\\\"宝安区\\\",\\\"mobile\\\":\\\"19925253062\\\",\\\"name\\\":\\\"糖糖女士\\\",\\\"provinceId\\\":\\\"440000\\\",\\\"provinceName\\\":\\\"广东省\\\",\\\"townId\\\":\\\"440306021000\\\",\\\"townName\\\":\\\"福海街道\\\"},\\\"orderId\\\":200085780,\\\"orderItemList\\\":[{\\\"costPrice\\\":74.90,\\\"count\\\":1,\\\"skuCode\\\":\\\"300261955\\\",\\\"skuId\\\":\\\"5144437\\\",\\\"spuName\\\":\\\"加厚加大 干湿两用抽取式洗脸巾 72抽/包\\\"},{\\\"costPrice\\\":14.90,\\\"count\\\":1,\\\"skuCode\\\":\\\"300252200\\\",\\\"skuId\\\":\\\"5434857\\\",\\\"spuName\\\":\\\"新增口味装 牙线棒50支/盒 圆线&扁线可选\\\"},{\\\"costPrice\\\":55.10,\\\"count\\\":1,\\\"skuCode\\\":\\\"301091279\\\",\\\"skuId\\\":\\\"5240287\\\",\\\"spuName\\\":\\\"新品/100%新疆棉，显瘦不变形双面针织T恤女\\\"},{\\\"costPrice\\\":43.90,\\\"count\\\":1,\\\"skuCode\\\":\\\"300260882\\\",\\\"skuId\\\":\\\"5440383\\\",\\\"spuName\\\":\\\"99.9%抑菌率 氨基酸泡沫抑菌洗手液\\\"},{\\\"costPrice\\\":14.90,\\\"count\\\":1,\\\"skuCode\\\":\\\"300252199\\\",\\\"skuId\\\":\\\"5234868\\\",\\\"spuName\\\":\\\"新增口味装 牙线棒50支/盒 圆线&扁线可选\\\"}],\\\"orderPriceInfo\\\":{\\\"freightPrice\\\":0},\\\"orderStatus\\\":0,\\\"orderStatusDesc\\\":\\\"未付款\\\",\\\"submitTime\\\":1717552293552,\\\"updateTime\\\":1717552293552,\\\"userInfo\\\":{\\\"userMobile\\\":\\\"19925253062\\\",\\\"userName\\\":\\\"唐思\\\"}}\"}");

        innerRequestParam.put("appKey", "f1f719f5f84842e7ab907be52659380d");
        innerRequestParam.put("method", "tob.mall.callback.order.prebind");
        innerRequestParam.put("timestamp", "2024-06-05+09:51:33");
        //遍历treeMap，将其value取出添加进拼接字符串
        StringBuilder sb = new StringBuilder();
        Iterator<String> it = innerRequestParam.keySet().iterator();
        String key;
        while (it.hasNext()) {
            key = it.next();
            sb.append(key).append("=").append(innerRequestParam.get(key));
        }
        System.out.println(genSign(sb.toString(), "505eed91440d4ace85238eb815c9e54d"));
    }

    @Test
    public void contextLoads1() {
        String requestBody = "sign=343F9F22B8FC7692B898FF4B4F7F77DF&appKey=f1f719f5f84842e7ab907be52659380d&method=tob.mall.callback.order.prebind&paramJson=%7B%22order%22%3A%22%7B%5C%22orderAddressInfo%5C%22%3A%7B%5C%22address%5C%22%3A%5C%22%E4%BC%9A%E5%B1%95%E6%B9%BE%E4%B8%9C%E5%9F%8E%E5%B0%8F%E5%8C%BA1%E6%A0%8B5%E5%8D%95%E5%85%832012%5C%22%2C%5C%22cityId%5C%22%3A%5C%22440300%5C%22%2C%5C%22cityName%5C%22%3A%5C%22%E6%B7%B1%E5%9C%B3%E5%B8%82%5C%22%2C%5C%22districtId%5C%22%3A%5C%22440306%5C%22%2C%5C%22districtName%5C%22%3A%5C%22%E5%AE%9D%E5%AE%89%E5%8C%BA%5C%22%2C%5C%22mobile%5C%22%3A%5C%2219925253062%5C%22%2C%5C%22name%5C%22%3A%5C%22%E7%B3%96%E7%B3%96%E5%A5%B3%E5%A3%AB%5C%22%2C%5C%22provinceId%5C%22%3A%5C%22440000%5C%22%2C%5C%22provinceName%5C%22%3A%5C%22%E5%B9%BF%E4%B8%9C%E7%9C%81%5C%22%2C%5C%22townId%5C%22%3A%5C%22440306021000%5C%22%2C%5C%22townName%5C%22%3A%5C%22%E7%A6%8F%E6%B5%B7%E8%A1%97%E9%81%93%5C%22%7D%2C%5C%22orderId%5C%22%3A200085780%2C%5C%22orderItemList%5C%22%3A%5B%7B%5C%22costPrice%5C%22%3A74.90%2C%5C%22count%5C%22%3A1%2C%5C%22skuCode%5C%22%3A%5C%22300261955%5C%22%2C%5C%22skuId%5C%22%3A%5C%225144437%5C%22%2C%5C%22spuName%5C%22%3A%5C%22%E5%8A%A0%E5%8E%9A%E5%8A%A0%E5%A4%A7+%E5%B9%B2%E6%B9%BF%E4%B8%A4%E7%94%A8%E6%8A%BD%E5%8F%96%E5%BC%8F%E6%B4%97%E8%84%B8%E5%B7%BE+72%E6%8A%BD%2F%E5%8C%85%5C%22%7D%2C%7B%5C%22costPrice%5C%22%3A14.90%2C%5C%22count%5C%22%3A1%2C%5C%22skuCode%5C%22%3A%5C%22300252200%5C%22%2C%5C%22skuId%5C%22%3A%5C%225434857%5C%22%2C%5C%22spuName%5C%22%3A%5C%22%E6%96%B0%E5%A2%9E%E5%8F%A3%E5%91%B3%E8%A3%85+%E7%89%99%E7%BA%BF%E6%A3%9250%E6%94%AF%2F%E7%9B%92+%E5%9C%86%E7%BA%BF%26%E6%89%81%E7%BA%BF%E5%8F%AF%E9%80%89%5C%22%7D%2C%7B%5C%22costPrice%5C%22%3A55.10%2C%5C%22count%5C%22%3A1%2C%5C%22skuCode%5C%22%3A%5C%22301091279%5C%22%2C%5C%22skuId%5C%22%3A%5C%225240287%5C%22%2C%5C%22spuName%5C%22%3A%5C%22%E6%96%B0%E5%93%81%2F100%25%E6%96%B0%E7%96%86%E6%A3%89%EF%BC%8C%E6%98%BE%E7%98%A6%E4%B8%8D%E5%8F%98%E5%BD%A2%E5%8F%8C%E9%9D%A2%E9%92%88%E7%BB%87T%E6%81%A4%E5%A5%B3%5C%22%7D%2C%7B%5C%22costPrice%5C%22%3A43.90%2C%5C%22count%5C%22%3A1%2C%5C%22skuCode%5C%22%3A%5C%22300260882%5C%22%2C%5C%22skuId%5C%22%3A%5C%225440383%5C%22%2C%5C%22spuName%5C%22%3A%5C%2299.9%25%E6%8A%91%E8%8F%8C%E7%8E%87+%E6%B0%A8%E5%9F%BA%E9%85%B8%E6%B3%A1%E6%B2%AB%E6%8A%91%E8%8F%8C%E6%B4%97%E6%89%8B%E6%B6%B2%5C%22%7D%2C%7B%5C%22costPrice%5C%22%3A14.90%2C%5C%22count%5C%22%3A1%2C%5C%22skuCode%5C%22%3A%5C%22300252199%5C%22%2C%5C%22skuId%5C%22%3A%5C%225234868%5C%22%2C%5C%22spuName%5C%22%3A%5C%22%E6%96%B0%E5%A2%9E%E5%8F%A3%E5%91%B3%E8%A3%85+%E7%89%99%E7%BA%BF%E6%A3%9250%E6%94%AF%2F%E7%9B%92+%E5%9C%86%E7%BA%BF%26%E6%89%81%E7%BA%BF%E5%8F%AF%E9%80%89%5C%22%7D%5D%2C%5C%22orderPriceInfo%5C%22%3A%7B%5C%22freightPrice%5C%22%3A0%7D%2C%5C%22orderStatus%5C%22%3A0%2C%5C%22orderStatusDesc%5C%22%3A%5C%22%E6%9C%AA%E4%BB%98%E6%AC%BE%5C%22%2C%5C%22submitTime%5C%22%3A1717552293552%2C%5C%22updateTime%5C%22%3A1717552293552%2C%5C%22userInfo%5C%22%3A%7B%5C%22userMobile%5C%22%3A%5C%2219925253062%5C%22%2C%5C%22userName%5C%22%3A%5C%22%E5%94%90%E6%80%9D%5C%22%7D%7D%22%7D&timestamp=2024-06-05+09%3A51%3A33";
        try {
            TreeMap<String, String> paramMap = parseFormRequestBody(requestBody);
            System.out.println(JSON.toJSONString(paramMap));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    protected String genSign(String data, String appSecret) {
        //参数值拼接的字符串收尾添加appSecret值
        String waitSignStr = appSecret + data + appSecret;
        System.out.println("genSign source string= " + waitSignStr);
        String sign = ParseMD5.parseStrToMd5U32(waitSignStr);
        System.out.println("genSign success, sign = " + sign);
        return sign;
    }

    private TreeMap<String, String> parseFormRequestBody(String requestBody) throws UnsupportedEncodingException {
        TreeMap<String, String> map = new TreeMap<>();
        try {
            String decodedFormData = URLDecoder.decode(requestBody, StandardCharsets.UTF_8.name());
            System.out.println("decodedFormData = " + decodedFormData);
            // 用正则表达式找到所有的 key=value 对
            Pattern pattern = Pattern.compile("([^&=]+)=((?:(?!&(?:\\w+=|$)).)*)");
            Matcher matcher = pattern.matcher(decodedFormData);

            while (matcher.find()) {
                String key = matcher.group(1);
                String value = matcher.group(2);
                map.put(key, value);
            }

        } catch (UnsupportedEncodingException e) {
            // 处理解码异常
            throw e;
        }
        return map;
    }

    @Test
    public void linkedListReverse() {
        System.out.println(new BigDecimal("0.01").divide(new BigDecimal("10"), 4, RoundingMode.DOWN)
                .multiply(BigDecimal.valueOf(100)).setScale(2, RoundingMode.DOWN));
    }

    /**
     * 单链表反转(遍历反转)
     *
     * @param head
     *            链表头
     * @retur 反转后的链表头
     */
    private Node reverseLinkedList(Node head) {
        if (head == null) {
            return null;
        }
        //如果列表只有一个元素
        if (head.getNext() == null) {
            return head;
        }
        Node pre = head;
        Node currentNode = head.getNext();
        while (currentNode != null) {
            Node temp = currentNode.getNext();
            currentNode.setNext(pre);
            pre = currentNode;
            currentNode = temp;
        }
        head.setNext(null);
        return pre;
    }

    /**
     * 单链表反转（递归）
     *
     * @param head
     *            链表头
     * @return 反转后的链表头
     */
    private Node recursionReverseLinkedList(Node head) {
        if (head == null || head.getNext() == null) {
            return head;
        }
        Node currentNode = recursionReverseLinkedList(head.getNext());
        head.getNext().setNext(head);
        head.setNext(null);

        return currentNode;
    }
}
