package com.yinan.play.demo;

import static com.yinan.enums.Platform.APP;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.remoting.RemoteAccessException;
import org.springframework.retry.RetryException;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.policy.TimeoutRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.rholder.retry.Retryer;
import com.github.rholder.retry.RetryerBuilder;
import com.github.rholder.retry.StopStrategies;
import com.google.common.base.Predicates;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.yinan.enums.Platform;
import com.yinan.play.demo.meta.AbtResourceTO;
import com.yinan.play.demo.meta.AddBuyStepVO;
import com.yinan.play.demo.meta.CacheScheduleStatEvent;
import com.yinan.play.demo.meta.CategoryVO;
import com.yinan.play.demo.meta.ComplexTextColorConsts;
import com.yinan.play.demo.meta.ComplexTextVO;
import com.yinan.play.demo.meta.FlashSaleScreenVO;
import com.yinan.play.demo.meta.FullRefundPolicyBean;
import com.yinan.play.demo.meta.GoodsBasic;
import com.yinan.play.demo.meta.Item;
import com.yinan.play.demo.meta.ItemDetailBaseVO;
import com.yinan.play.demo.meta.ItemSkuParam;
import com.yinan.play.demo.meta.LotteryGiftBean;
import com.yinan.play.demo.meta.NewItemListResultVO;
import com.yinan.play.demo.meta.OMSOrderExpressDetailInfoBean;
import com.yinan.play.demo.meta.OMSOrderOutBean;
import com.yinan.play.demo.meta.OMSOrderPackageBean;
import com.yinan.play.demo.meta.ProcessContext;
import com.yinan.play.demo.meta.enums.CacheOpeTypeEnum;
import com.yinan.play.demo.meta.enums.StatEventTypeEnum;
import com.yinan.play.demo.meta.enums.TriggerStageEnum;
import com.yinan.play.demo.service.RetryService;
import com.yinan.play.demo.util.BeanCopierUtils;
import com.yinan.play.demo.util.CacheExtKeyUtil;
import com.yinan.play.demo.util.DateUtils;
import com.yinan.play.demo.util.MapPartitioner;
import com.yinan.play.demo.util.ParseMD5;
import com.yinan.play.demo.util.SchemeUrlBuilder;
import com.yinan.play.demo.util.UrlUtils;
import com.yinan.play.demo.util.UserInfoUtil;
import com.yinan.play.demo.util.ZipUtils;
import com.yinan.util.DigestUtils;
import com.yinan.util.SkuOperationAtrributeUtil;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class DemoApplicationTests {

    String ZIP_SUFFIX = ".zip";

    private static final String MONTH_CARD_BTN_TITLE_CUSTOM = "%s元购买";

    private static final String OPEN_GIFT_RED_PACKET_DESC_FOR_FREE_POSTAGE = "Pro会员立享免邮，再送%s元红包";

    @Autowired
    private RetryService retryService;

    @Test
    public void contextLoads() {
        System.out.println(NumberUtils.isCreatable("0.5"));
        System.out.println(NumberUtils.isCreatable("600.5"));
        System.out.println(NumberUtils.isCreatable("一"));
    }

    /**
     * 测试注解形式的spring-retry
     */
    @Test
    public void testRetry() {
        // 示例数据
        Map<String, List<String>> spuIdSkuInventoryMap = new HashMap<>();
        for (int i = 1; i <= 53; i++) {
            spuIdSkuInventoryMap.put("key" + i, Arrays.asList("value" + i));
        }

        // 分批
        List<Map<String, List<String>>> partitions = MapPartitioner.partitionMap(spuIdSkuInventoryMap, 20);

        // 打印结果
        for (int i = 0; i < partitions.size(); i++) {
            System.out.println("Partition " + (i + 1) + ": " + partitions.get(i));
        }
    }

    /**
     * 测试spring retry template
     *
     * @throws Throwable
     */
    @Test
    public void testTemplateRetry() throws Throwable {
        SimpleRetryPolicy simpleRetryPolicy = new SimpleRetryPolicy(3, Collections.singletonMap(Exception.class, true));

        TimeoutRetryPolicy timeoutRetryPolicy = new TimeoutRetryPolicy();
        timeoutRetryPolicy.setTimeout(3000L);

        RetryTemplate template = new RetryTemplate();
        template.setRetryPolicy(simpleRetryPolicy);

        template.execute(retryContext -> retryService.retryByTemplate(0.6),
            retryContext -> retryService.recoverByTemplate());
    }

    /**
     * 测试guava retry
     */
    @Test
    public void testGuavaRetry() {
        //lambda
        Callable<Boolean> callable = () -> {
            retryService.guavaRetry(0.6);
            return true;
        };

        Retryer<Boolean> retry = RetryerBuilder.<Boolean>newBuilder().retryIfResult(Predicates.isNull())
            .retryIfExceptionOfType(RemoteAccessException.class).withStopStrategy(StopStrategies.stopAfterAttempt(3))
            .build();

        try {
            retry.call(callable);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (RetryException e) {
            System.out.println("retry failed");
        } catch (com.github.rholder.retry.RetryException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testBigDecimal() {
        BigDecimal totalPrice = new BigDecimal("10");
        BigDecimal division = totalPrice.divide(new BigDecimal("1"), 6, BigDecimal.ROUND_HALF_UP);
        System.out.println(division);
    }

    @Test
    public void testStringContains() {
        String s = "[op:getNoPassSkuProcessList] 获取sku可预约质检量失败, purchaseOrder=YC16050002-20170307-1 ,supplierId=YX0001";

        System.out.println(s.contains("[op:getNoPassSkuProcessList] 获取sku可预约质检量失败, purchaseOrder=YC"));
    }

    @Test
    public void testThreadLocalRandom() {

        List<Integer> list = Lists.newArrayList();
        for (int i = 1; i < 18; i++) {
            list.add(i);
        }
        int shardingTotal = 3;
        int totalSize = list.size();
        int shardingSize = totalSize / shardingTotal;

        int sharingIndex = 2;
        // 业务逻辑
        for (int i = 0; i < shardingTotal; i++) {
            log.info("第 {} 片, shardingVO.getIndex()={}", i, sharingIndex);
            if (shardingTotal - 1 == sharingIndex) {
                System.out.println("命中分片：" + JSON.toJSONString(list.subList(shardingSize * sharingIndex, totalSize)));
            } else if (i == sharingIndex) {
                System.out.println("命中分片："
                    + JSON.toJSONString(list.subList(shardingSize * sharingIndex, shardingSize * (sharingIndex + 1))));
            }
        }
    }

    @Test
    public void outputMapString() {
        Map<String, String> map = Maps.newHashMap();
        map.put("test", "http://127.0.0.1:8550/proxy/test.yanxuan-qc-indicator-core.service.mailsaas");
        map.put("test2", "http://127.0.0.1:8550/proxy/test2.yanxuan-qc-indicator-core.service.mailsaas");

        System.out.println(JSON.toJSONString(map));
    }

    @Test
    public void fastJson() {
        String s = "[{\"firstSaleCategoryId\":6,\"secondSaleCategoryId\":96},{\"firstSaleCategoryId\":6,\"secondSaleCategoryId\":96},{\"firstSaleCategoryId\":6,\"secondSaleCategoryId\":96},{\"firstSaleCategoryId\":6,\"secondSaleCategoryId\":96},{\"firstSaleCategoryId\":6,\"secondSaleCategoryId\":96},{\"firstSaleCategoryId\":6,\"secondSaleCategoryId\":96},{\"firstSaleCategoryId\":6,\"secondSaleCategoryId\":96},{\"firstSaleCategoryId\":6,\"secondSaleCategoryId\":96},{\"firstSaleCategoryId\":6,\"secondSaleCategoryId\":96},{\"firstSaleCategoryId\":6,\"secondSaleCategoryId\":96},{\"firstSaleCategoryId\":6,\"secondSaleCategoryId\":96},{\"firstSaleCategoryId\":6,\"secondSaleCategoryId\":96},{\"firstSaleCategoryId\":6,\"secondSaleCategoryId\":96},{\"firstSaleCategoryId\":6,\"secondSaleCategoryId\":96},{\"firstSaleCategoryId\":6,\"secondSaleCategoryId\":96},{\"firstSaleCategoryId\":6,\"secondSaleCategoryId\":96},{\"firstSaleCategoryId\":6,\"secondSaleCategoryId\":96},{\"firstSaleCategoryId\":6,\"secondSaleCategoryId\":96},{\"firstSaleCategoryId\":6,\"secondSaleCategoryId\":96},{\"firstSaleCategoryId\":6,\"secondSaleCategoryId\":96},{\"firstSaleCategoryId\":6,\"secondSaleCategoryId\":96},{\"firstSaleCategoryId\":6,\"secondSaleCategoryId\":96},{\"firstSaleCategoryId\":6,\"secondSaleCategoryId\":96},{\"firstSaleCategoryId\":6,\"secondSaleCategoryId\":96},{\"firstSaleCategoryId\":6,\"secondSaleCategoryId\":96},{\"firstSaleCategoryId\":6,\"secondSaleCategoryId\":96},{\"firstSaleCategoryId\":6,\"secondSaleCategoryId\":96},{\"firstSaleCategoryId\":6,\"secondSaleCategoryId\":96},{\"firstSaleCategoryId\":6,\"secondSaleCategoryId\":96},{\"firstSaleCategoryId\":6,\"secondSaleCategoryId\":96},{\"firstSaleCategoryId\":6,\"secondSaleCategoryId\":96},{\"firstSaleCategoryId\":6,\"secondSaleCategoryId\":96},{\"firstSaleCategoryId\":6,\"secondSaleCategoryId\":96},{\"firstSaleCategoryId\":6,\"secondSaleCategoryId\":96},{\"firstSaleCategoryId\":6,\"secondSaleCategoryId\":96},{\"firstSaleCategoryId\":6,\"secondSaleCategoryId\":96},{\"firstSaleCategoryId\":6,\"secondSaleCategoryId\":96},{\"firstSaleCategoryId\":6,\"secondSaleCategoryId\":96},{\"firstSaleCategoryId\":6,\"secondSaleCategoryId\":96},{\"firstSaleCategoryId\":6,\"secondSaleCategoryId\":96},{\"firstSaleCategoryId\":6,\"secondSaleCategoryId\":96},{\"firstSaleCategoryId\":6,\"secondSaleCategoryId\":97},{\"firstSaleCategoryId\":6,\"secondSaleCategoryId\":97},{\"firstSaleCategoryId\":6,\"secondSaleCategoryId\":97},{\"firstSaleCategoryId\":6,\"secondSaleCategoryId\":97},{\"firstSaleCategoryId\":6,\"secondSaleCategoryId\":97},{\"firstSaleCategoryId\":6,\"secondSaleCategoryId\":97},{\"firstSaleCategoryId\":6,\"secondSaleCategoryId\":97},{\"firstSaleCategoryId\":6,\"secondSaleCategoryId\":97},{\"firstSaleCategoryId\":6,\"secondSaleCategoryId\":97},{\"firstSaleCategoryId\":6,\"secondSaleCategoryId\":97},{\"firstSaleCategoryId\":6,\"secondSaleCategoryId\":97},{\"firstSaleCategoryId\":6,\"secondSaleCategoryId\":97},{\"firstSaleCategoryId\":6,\"secondSaleCategoryId\":97},{\"firstSaleCategoryId\":6,\"secondSaleCategoryId\":97},{\"firstSaleCategoryId\":6,\"secondSaleCategoryId\":97},{\"firstSaleCategoryId\":6,\"secondSaleCategoryId\":97},{\"firstSaleCategoryId\":6,\"secondSaleCategoryId\":97},{\"firstSaleCategoryId\":6,\"secondSaleCategoryId\":97},{\"firstSaleCategoryId\":6,\"secondSaleCategoryId\":97},{\"firstSaleCategoryId\":6,\"secondSaleCategoryId\":97},{\"firstSaleCategoryId\":6,\"secondSaleCategoryId\":97},{\"firstSaleCategoryId\":6,\"secondSaleCategoryId\":97},{\"firstSaleCategoryId\":6,\"secondSaleCategoryId\":98},{\"firstSaleCategoryId\":6,\"secondSaleCategoryId\":98},{\"firstSaleCategoryId\":6,\"secondSaleCategoryId\":98},{\"firstSaleCategoryId\":6,\"secondSaleCategoryId\":98},{\"firstSaleCategoryId\":6,\"secondSaleCategoryId\":98},{\"firstSaleCategoryId\":6,\"secondSaleCategoryId\":98},{\"firstSaleCategoryId\":6,\"secondSaleCategoryId\":98},{\"firstSaleCategoryId\":6,\"secondSaleCategoryId\":98},{\"firstSaleCategoryId\":6,\"secondSaleCategoryId\":98},{\"firstSaleCategoryId\":6,\"secondSaleCategoryId\":99},{\"firstSaleCategoryId\":6,\"secondSaleCategoryId\":99},{\"firstSaleCategoryId\":6,\"secondSaleCategoryId\":99},{\"firstSaleCategoryId\":6,\"secondSaleCategoryId\":99},{\"firstSaleCategoryId\":6,\"secondSaleCategoryId\":99},{\"firstSaleCategoryId\":6,\"secondSaleCategoryId\":99},{\"firstSaleCategoryId\":6,\"secondSaleCategoryId\":99},{\"firstSaleCategoryId\":6,\"secondSaleCategoryId\":100},{\"firstSaleCategoryId\":6,\"secondSaleCategoryId\":100},{\"firstSaleCategoryId\":6,\"secondSaleCategoryId\":100},{\"firstSaleCategoryId\":6,\"secondSaleCategoryId\":100},{\"firstSaleCategoryId\":6,\"secondSaleCategoryId\":100},{\"firstSaleCategoryId\":6,\"secondSaleCategoryId\":100},{\"firstSaleCategoryId\":6,\"secondSaleCategoryId\":100},{\"firstSaleCategoryId\":6,\"secondSaleCategoryId\":100},{\"firstSaleCategoryId\":6,\"secondSaleCategoryId\":100},{\"firstSaleCategoryId\":6,\"secondSaleCategoryId\":100},{\"firstSaleCategoryId\":6,\"secondSaleCategoryId\":100},{\"firstSaleCategoryId\":6,\"secondSaleCategoryId\":100},{\"firstSaleCategoryId\":6,\"secondSaleCategoryId\":100},{\"firstSaleCategoryId\":12,\"secondSaleCategoryId\":176},{\"firstSaleCategoryId\":12,\"secondSaleCategoryId\":176},{\"firstSaleCategoryId\":12,\"secondSaleCategoryId\":177},{\"firstSaleCategoryId\":12,\"secondSaleCategoryId\":177},{\"firstSaleCategoryId\":12,\"secondSaleCategoryId\":178},{\"firstSaleCategoryId\":12,\"secondSaleCategoryId\":178},{\"firstSaleCategoryId\":12,\"secondSaleCategoryId\":179},{\"firstSaleCategoryId\":12,\"secondSaleCategoryId\":179},{\"firstSaleCategoryId\":12,\"secondSaleCategoryId\":179},{\"firstSaleCategoryId\":12,\"secondSaleCategoryId\":180},{\"firstSaleCategoryId\":12,\"secondSaleCategoryId\":180},{\"firstSaleCategoryId\":12,\"secondSaleCategoryId\":181},{\"firstSaleCategoryId\":12,\"secondSaleCategoryId\":181},{\"firstSaleCategoryId\":12,\"secondSaleCategoryId\":183},{\"firstSaleCategoryId\":12,\"secondSaleCategoryId\":183},{\"firstSaleCategoryId\":12,\"secondSaleCategoryId\":183},{\"firstSaleCategoryId\":12,\"secondSaleCategoryId\":183},{\"firstSaleCategoryId\":12,\"secondSaleCategoryId\":183},{\"firstSaleCategoryId\":12,\"secondSaleCategoryId\":183},{\"firstSaleCategoryId\":12,\"secondSaleCategoryId\":184},{\"firstSaleCategoryId\":12,\"secondSaleCategoryId\":184},{\"firstSaleCategoryId\":12,\"secondSaleCategoryId\":185},{\"firstSaleCategoryId\":12,\"secondSaleCategoryId\":185},{\"firstSaleCategoryId\":12,\"secondSaleCategoryId\":186},{\"firstSaleCategoryId\":12,\"secondSaleCategoryId\":186},{\"firstSaleCategoryId\":12,\"secondSaleCategoryId\":187},{\"firstSaleCategoryId\":12,\"secondSaleCategoryId\":187},{\"firstSaleCategoryId\":12,\"secondSaleCategoryId\":188},{\"firstSaleCategoryId\":12,\"secondSaleCategoryId\":188},{\"firstSaleCategoryId\":12,\"secondSaleCategoryId\":310},{\"firstSaleCategoryId\":12,\"secondSaleCategoryId\":310},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":235},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":235},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":235},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":235},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":235},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":235},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":235},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":235},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":236},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":236},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":236},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":236},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":236},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":237},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":237},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":237},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":237},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":237},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":237},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":238},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":238},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":238},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":238},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":238},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":238},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":238},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":238},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":238},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":239},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":239},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":239},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":239},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":239},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":239},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":239},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":239},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":240},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":240},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":240},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":240},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":240},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":240},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":240},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":240},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":240},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":240},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":240},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":240},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":240},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":240},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":240},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":240},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":240},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":240},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":240},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":240},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":240},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":240},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":240},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":240},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":240},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":240},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":240},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":240},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":240},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":240},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":240},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":240},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":240},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":240},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":240},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":240},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":240},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":240},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":240},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":240},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":240},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":240},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":240},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":240},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":240},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":240},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":240},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":240},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":240},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":240},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":240},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":240},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":241},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":241},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":241},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":241},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":241},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":242},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":242},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":242},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":242},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":242},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":243},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":243},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":243},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":243},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":243},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":244},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":244},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":244},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":244},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":244},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":244},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":244},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":244},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":244},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":244},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":244},{\"firstSaleCategoryId\":16,\"secondSaleCategoryId\":244}]";
        List<CategoryVO> categoryVOList = JSON.parseArray(s, CategoryVO.class);
        Set<String> categoryStrSet = Sets.newHashSet();
        if (org.apache.commons.collections4.CollectionUtils.isNotEmpty(categoryVOList)) {
            for (CategoryVO categoryVO: categoryVOList) {
                categoryStrSet.add(categoryVO.getFirstSaleCategoryId() + "-" + categoryVO.getSecondSaleCategoryId());
            }
        }

        System.out.println(categoryStrSet);
    }

    @Test
    public void dateFormat() {
        System.out.printf(DateUtils.parseLongToString(1672894800000L, "M.d HH:mm") + "开抢");
    }

    @Test
    public void instance() {
        List<String> list = Lists.newArrayList("dft", "SPU", "ALL_SPU_ID", "SPU_ID_TO_SKU_IDS", "ALL_SKU_ID", "SKU",
            "SKU_MARK_UP_RADIO", "ITEM_POOL_SHOP_SKU", "CUSTOMER_SKU", "SPU_SHOP", "SHOP_ITEMS_LIST",
            "INVENTORY_LEFT_AMOUNT", "SPU_SALE_CATEGORY", "SPU_AREA", "SHOP_ITEM_COUNT");
        Collections.sort(list);
        System.out.println(JSON.toJSONString(list));
    }

    private boolean checkAllPackageDelivered(OMSOrderOutBean omsOrderBean) {
        if (omsOrderBean == null || CollectionUtils.isEmpty(omsOrderBean.getOrderPackages())) {
            return false;
        }
        for (OMSOrderPackageBean omsOrderPackage: omsOrderBean.getOrderPackages()) {
            if (omsOrderPackage == null || CollectionUtils.isEmpty(omsOrderPackage.getExpressDetailInfos())) {
                return false;
            }
            for (OMSOrderExpressDetailInfoBean expressDetailInfoBean: omsOrderPackage.getExpressDetailInfos()) {
                if (expressDetailInfoBean == null) {
                    continue;
                }
                if (MapUtils.isEmpty(expressDetailInfoBean.getExpressNoDeliveredMap())) {
                    return false;
                }
                Boolean expressNoDelivered = expressDetailInfoBean.getExpressNoDeliveredMap()
                    .get(expressDetailInfoBean.getExpressNo());
                if (!Boolean.TRUE.equals(expressNoDelivered)) {
                    return false;
                }
                if (org.apache.commons.collections4.CollectionUtils
                    .isNotEmpty(expressDetailInfoBean.getSubExpressNos())) {
                    // 校验子物流单号是否妥投
                    for (String subExpressNo: expressDetailInfoBean.getSubExpressNos()) {
                        Boolean subExpressNoDelivered = expressDetailInfoBean.getExpressNoDeliveredMap()
                            .get(subExpressNo);
                        if (!Boolean.TRUE.equals(subExpressNoDelivered)) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    @Test
    public void collectEmptyListToMap() {

        List<Item> result = Lists.newArrayList();

        Map<Long, Item> itemMap = result.stream().collect(Collectors.toMap(Item::getItemId, i -> i));

        System.out.println(JSON.toJSONString(itemMap));
    }

    @Test
    public void boxing() {

        Boolean check = true;
        System.out.println(check);
        changeBoolean(check);
        System.out.println(check);
    }

    private void changeBoolean(Boolean check) {
        check = false;
    }

    @Test
    public void testRetainList() {
        List<Long> longList = Lists.newArrayList(3L, 1L, 2L);
        List<Long> sortedList = longList.stream().sorted(Comparator.comparing(i -> i)).collect(Collectors.toList());

        //        List<Long> longSecondList = Lists.newArrayList();
        //        longList.retainAll(longSecondList);
        System.out.println(JSON.toJSONString(longList));
        System.out.println(JSON.toJSONString(sortedList));

    }

    @Test
    public void testZip() {
        String path = "/Users/yinan/Documents/misc/git-cheatsheet.pdf";

        String zipName = "git/cheatsheet质量标准";
        //        zipName.replace("/", "-");
        String zipPath = "/Users/yinan/Documents/" + zipName + ZIP_SUFFIX;
        File zipFile = null;
        List<File> fileList = com.google.common.collect.Lists.newArrayList();
        File pdfFile = new File(path);
        fileList.add(pdfFile);
        zipFile = ZipUtils.zipFileList(zipPath, fileList);
    }

    @Test
    public void testTreeMap() {
        Map<Integer, String> map = Maps.newTreeMap();
        System.out.println("123-456".replace("-", ""));
    }

    @Test
    public void testCompareNull() {
        List<String> list = Lists.newArrayList("123", "456");
        list.addAll(null);
        System.out.println(list);
    }

    @Test
    public void generateLuceneSyntax() {
        String s = "[301,303,305,311,102,801,802,803,804,805,807,1006,1022,1027,1028,1031,1033,1035,1037,401,403,404,1201,202,204,703,707,708,601,602,101,301,302,303,305,311,102,103,106,111,114,1006,1011,1022,1027,1028,1029,1030,1031,1032,1033,1034,1036,1037,901,903,904,403,1104,201,202,203,204,108,701,702,703,601,602,806,1006,1028,401,402,403,201,202]";
        List<Integer> integers = JSON.parseArray(s, Integer.class);
        Set<Integer> set = Sets.newHashSet(integers);
        System.out.println(JSON.toJSONString(set));
        System.out.println(set.size());
    }

    @Test
    public void testString() {
        List<Integer> list = Lists.newArrayList(4, 3, 2);
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (Integer i: list) {
            sb.append(",").append(i);
        }
        String str = "";
        int len = sb.length();
        if (len > 0) {
            str = sb.toString().substring(1, len);
        }
        System.out.println(str);
    }

    @Test
    public void testFormatString() {
        Set<String> set1 = Sets.newHashSet("1", "2", "3");
        Set<String> set2 = Sets.newHashSet("1", "2");

        System.out.println(set1.equals(set2));

    }

    @Test
    public void testListRemove() {
        List<Long> passedItemIdList = Lists.newArrayList();
        passedItemIdList.add(678L);
        List<Long> itemIdList = Lists.newArrayList();
        itemIdList.add(123L);
        itemIdList.add(456L);
        itemIdList.add(678L);

        for (Long itemId: passedItemIdList) {
            itemIdList.remove(itemId);
            itemIdList.add(0, itemId);
        }

        System.out.println(JSON.toJSONString(itemIdList));
    }

    @Test
    public void testUrlAppend() {
        Map<String, String> params = new HashMap<>();
        params.put("uid", "40005");
        params.put("name", "张三");
        params.put("mobile", "13800138000");
        params.put("channelId", "6904888");
        params.put("channelName", "电信后付费");
        params.put("orders", "123,456");
        params.put("skuIds", "30001,30002");

        String url = "https://mall.ka-fuli.com/web#/kf/open";
        System.out.println(UrlUtils.appendParamsToUrl(url, params));
    }

    @Test
    public void testGetParamFromUrl() {
        System.out.println(toDateMilliSecond("20250709"));
    }

    public static Long toDateMilliSecond(String dateString) {
        if (StringUtils.isBlank(dateString)) {
            return null;
        }
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
            Date date = df.parse(dateString);
            return date.getTime();
        } catch (ParseException e) {
            log.error("[时间类型转换异常]", e);
        }
        return null;
    }

    @Test
    public void testBoolean() {
        boolean newUserFlag = false;
        Item item = new Item();
        if (item != null) {
            newUserFlag = true;
        }

        log.info("newUserFlag = {}", newUserFlag);
    }

    @Test
    public void testSubList() {
        List<Long> itemIdList = Lists.newArrayList();
        //
        //        itemIdList.add(470036006L);
        //        itemIdList.add(470034004L);
        //        itemIdList.add(469221059L);
        //        itemIdList.add(470017018L);
        //        itemIdList.add(469221062L);

        itemIdList = itemIdList.subList(0, Math.min(itemIdList.size(), 3));

        System.out.println(JSON.toJSONString(itemIdList));
    }

    @Test
    public void testApacheStringJoin() {
        List<String> itemIdList = Lists.newArrayList();
        itemIdList.add("123");
        itemIdList.add("456");
        itemIdList.add("789");

        System.out.println(JSON.toJSONString(itemIdList.subList(0, Math.min(itemIdList.size(), 2))));

        //        System.out.println(StringUtils.join(itemIdList, ","));
    }

    @Test
    public void testSJH() {
        boolean result = false;
        Item item = new Item();
        if (item != null) {
            result = true;
        }

        System.out.println(result);

    }

    @Test
    public void buildSchemeUrl() {
        String schemeUrl = SchemeUrlBuilder.buildWebViewJump("https://act.you.163.com/act/pub/fBNvOlTsvG9b.html", null,
            null);
        System.out.println(schemeUrl);
    }

    @Test
    public void testIntegerCompareInt() {
        Integer i = new Integer("1");

        //        System.out.println(i == 1);

        System.out.println(Boolean.TRUE.equals(null));

    }

    @Test
    public void testBitSet() {
        int[] array = new int[] { 1, 2, 3, 22, 0, 3, 63 };
        BitSet bitSet = new BitSet(1);
        System.out.println(bitSet.size()); //64
        bitSet = new BitSet(65);
        System.out.println(bitSet.size()); //128
        bitSet = new BitSet(23);
        System.out.println(bitSet.size()); //64

        //将数组内容组bitmap
        for (int value: array) {
            bitSet.set(value, true);
        }

        System.out.println(bitSet.get(22));
        System.out.println(bitSet.get(60));

        System.out.println("下面开始遍历BitSet：");
        for (int i = 0; i < bitSet.size(); i++) {
            System.out.println(bitSet.get(i));
        }
    }

    @Test
    public void testSet() {
        String s = "[\"470186662\",\"1106000\",\"470186663\",\"11690003\",\"1262001\",\"1139002\",\"470186664\",\"470186294\",\"470186615\",\"470186612\",\"469220005\",\"10109000\",\"470186613\",\"469253004\",\"470186611\",\"11371000\",\"468876007\",\"468868004\",\"468868005\",\"470186654\",\"469081004\",\"1065002\"]";

        List<String> strings = JSON.parseArray(s, String.class);
        Set<String> stringSet = Sets.newHashSet(strings);
        System.out.println(strings.size());
        System.out.println(stringSet.size());
    }

    @Test
    public void testChangeBoolean() {
        boolean result = false;

        if (true) {
            result = true;
        }

        System.out.println(result);
    }

    @Test
    public void testBigDecimalNull() {
        Item item = new Item();
        item.setPrice(null);
    }

    @Test
    public void testStringContainsABT() {
        String s = "3205_6239_3_oldFlashSale_5.6.0";

        System.out.println(s.contains("oldFlashSale_5.6.0"));
    }

    @Test
    public void testOldComparator() {
        FlashSaleScreenVO flashSaleScreenVO = new FlashSaleScreenVO();
        flashSaleScreenVO.setId(1L);
        flashSaleScreenVO.setStartTime(123);
        flashSaleScreenVO.setStatus(0);

        FlashSaleScreenVO flashSaleScreenVO2 = new FlashSaleScreenVO();
        flashSaleScreenVO2.setId(1L);
        flashSaleScreenVO2.setStartTime(456);
        flashSaleScreenVO2.setStatus(0);

        FlashSaleScreenVO flashSaleScreenVO3 = new FlashSaleScreenVO();
        flashSaleScreenVO3.setId(1L);
        flashSaleScreenVO3.setStartTime(789);
        flashSaleScreenVO3.setStatus(0);

        List<FlashSaleScreenVO> flashSaleScreenVOS = Lists.newArrayList();
        flashSaleScreenVOS.add(flashSaleScreenVO3);
        flashSaleScreenVOS.add(flashSaleScreenVO2);
        flashSaleScreenVOS.add(flashSaleScreenVO);

        System.out.println(JSON.toJSONString(flashSaleScreenVOS));

        flashSaleScreenVOS.sort(flashScreensComparator);

        System.out.println(JSON.toJSONString(flashSaleScreenVOS));
    }

    @Test
    public void testBigDecimalDivide() {
        double numOfTenThousands = new BigDecimal(12500).divide(new BigDecimal("10000"), 1, RoundingMode.HALF_UP)
            .doubleValue();

        System.out.println(numOfTenThousands);

        BigDecimal bd2 = new BigDecimal("99.89");
        BigDecimal descCountPrice = bd2.setScale(1, RoundingMode.DOWN);
        System.out.println("half down: " + descCountPrice.toString());
        BigDecimal descCountPrice2 = bd2.setScale(1, RoundingMode.HALF_UP);
        System.out.println("half UP: " + descCountPrice2.toString());
        BigDecimal descCountPrice3 = bd2.setScale(1, RoundingMode.HALF_DOWN);
        System.out.println("half EVEN: " + descCountPrice3.toString());
    }

    @Test
    public void testBigDecimalMoreThan4DigitAnd1Dot() {
        //        BigDecimal bd = new BigDecimal("0");
        //        System.out.println(checkBigDecimalHasLessThanFourDigitsAndOneDot(bd));
        //
        //        BigDecimal bd2 = new BigDecimal("99.89");
        //        System.out.println(checkBigDecimalHasLessThanFourDigitsAndOneDot(bd2));
        //
        //        BigDecimal bd3 = new BigDecimal("19999.9");
        //        System.out.println(checkBigDecimalHasLessThanFourDigitsAndOneDot(bd3));

        BigDecimal retailPrice = new BigDecimal("2222.00");
        BigDecimal actualPrice = new BigDecimal("643");
        BigDecimal discountPrice = retailPrice.subtract(actualPrice);

        System.out.println(discountPrice.toString());
        System.out.println(checkBigDecimalHasLessThanFourDigitsAndOneDot(discountPrice));

        System.out.println(
            retailPrice.subtract(actualPrice).setScale(1, RoundingMode.DOWN).stripTrailingZeros().toPlainString());

        //        BigDecimal discount = new BigDecimal(180)
        //                .divide(new BigDecimal(62.5), 2, RoundingMode.UP);
        //        System.out.println(discount);
    }

    @Test
    public void testBigDecimalStringFormat() {
        System.out.println(String.format(MONTH_CARD_BTN_TITLE_CUSTOM, new BigDecimal("12")));
    }

    @Test
    public void testFastJsonBlankString() {

        String todayDate = DateUtils.parseLongToString(System.currentTimeMillis(), "yyyy-MM-dd");
        System.out.println(todayDate);
        String lastDayDate = DateUtils.parseLongToString(System.currentTimeMillis() - DateUtils.TIME_OF_DAY,
            "yyyy-MM-dd");
        System.out.printf(lastDayDate);
    }

    @Test
    public void testOperationAttribute() {
        boolean isRedPacketAllowed = false;
        isRedPacketAllowed = !SkuOperationAtrributeUtil.getConflictRedPacket(0L);

        if (!isRedPacketAllowed) {
            System.out.println("not allowed red packet");
        } else {
            System.out.println("red packet allowed");
        }
    }

    @Test
    public void enumSwitch() {
        Platform platform = APP;
        switch (platform) {
            case WAP:
                System.out.println("wap");
                break;
            case WEB:
                System.out.println("web");
                break;
            case APP:
                System.out.println("app");
                break;
            default:
                System.out.println("null");
        }
    }

    @Test
    public void processDeviceIdMd5() {
        System.out.println(DigestUtils.md5("5b3e4f51e0e69d69fbc0da59e1b631c9"));
    }

    private final static Comparator<FlashSaleScreenVO> flashScreensComparator = new Comparator<FlashSaleScreenVO>() {
        @Override
        public int compare(FlashSaleScreenVO o1, FlashSaleScreenVO o2) {
            if (o1.getStartTime() > o2.getStartTime()) {
                return -1;
            } else if (o1.getStartTime() < o2.getStartTime()) {
                return 1;
            }
            return 0;
        }
    };

    @Test
    public void testRandom() {

        List<String> allItemIdList = Lists.newArrayList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
        List<String> topItemIdList = Lists.newArrayList("5", "4");
        List<String> sortedItemIdList = allItemIdList.stream().sorted(Comparator.comparingInt(topItemIdList::indexOf))
            .collect(Collectors.toList());

        System.out.println(JSON.toJSONString(sortedItemIdList));
    }

    @Test
    public void testStringFormat() {
        String s = "支付%s天后返2张红包";
        System.out.println(String.format(s, 2));
    }

    @Test
    public void fullRefundPolicy() {
        FullRefundPolicyBean fullRefundPolicyBean = new FullRefundPolicyBean();
        int i = 7;
        fullRefundPolicyBean.setTitles(Lists.newArrayList("商品下单", "付款后%s天", "红包到账（系统发放）"));
        for (String title: fullRefundPolicyBean.getTitles()) {
            title = String.format(title, i);
        }
        fullRefundPolicyBean.setDetailTitle("全额返活动规则");
        fullRefundPolicyBean.setContent(
            Lists.newArrayList("全额返仅限指定用户参加", "用户购买指定商品完成支付后，商品支付金额将以2张红包形式返还（成功付款3天后到账），红包直接当现金使用，您可以用红包在商城购买其他商品",
                "红包满足使用门槛、除商品详情页标识不可使用红包商品外均可使用。红包不可提现，红包有效期为14天，请尽早在有效期内使用，具体红包您可以在【个人】-【我的资产】-【红包】查看；",
                "首单全额返活动商品在支付时不支持使用立减金/红包/优惠券 /积分/回馈金/礼品卡等所有优惠；",
                "若用户在收到返还的红包后取消交易或申请退款等，已返还的红包将自动失效，已使用的全额返红包将在退款中扣除。", "在法律允许的范围内，网易严选拥有对该活动的最终解释权。"));

        System.out.println(JSON.toJSONString(fullRefundPolicyBean));

    }

    @Test
    public void beanCopy() {
        ProcessContext originProcess = new ProcessContext();
        ItemDetailBaseVO originBaseVo = new ItemDetailBaseVO();
        originBaseVo.setPurchaseType(3);

        originProcess.setItemDetail(originBaseVo);
        ProcessContext copiedContext = new ProcessContext();
        BeanCopierUtils.copyProperties(copiedContext, originProcess);

        System.out.println(JSON.toJSONString(originProcess));
        System.out.println(JSON.toJSONString(copiedContext));
    }

    private boolean checkBigDecimalHasLessThanFourDigitsAndOneDot(BigDecimal discountPrice) {
        if (discountPrice == null) {
            return false;
        }
        if (BigDecimal.ZERO.compareTo(discountPrice) >= 0) {
            return false;
        }
        String discountPriceStr = discountPrice.stripTrailingZeros().toPlainString();
        System.out.println(discountPriceStr);
        if (discountPriceStr.length() <= 4) {
            return true;
        }
        int digitCount = 0;
        int dotCount = 0;
        for (int i = 0; i < discountPriceStr.length(); i++) {
            char c = discountPriceStr.charAt(i);
            if (Character.isDigit(c)) {
                digitCount += 1;
            }
            if ('.' == c) {
                dotCount += 1;
            }
        }

        return digitCount <= 4 && dotCount <= 1;
    }

    @Test
    public void skuModKey() {
        String s = "472270377";
        String[] array = s.split(",");
        System.out.println(JSON.toJSONString(array));
        for (String skuId: array) {
            System.out.println("get YANXUAN_COMMON_SKU_INVENTORY_CACHE\\|SKU_INVENTORY_V1_"
                + CacheExtKeyUtil.getSkuInventoryModKey(Long.parseLong(skuId)));
        }
    }

    @Test
    public void skuBacisModKey() {
        String s = "472299805,472299804";
        String[] array = s.split(",");
        System.out.println(JSON.toJSONString(array));
        for (String skuId: array) {
            System.out.println("get YANXUAN_COMMON_IC_SKU_CACHE\\|SKU_CACHE_V1_"
                + CacheExtKeyUtil.getSkuBasisModKey(Long.parseLong(skuId)));
        }
    }

    @Test
    public void jsonTest() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nextMinute = now.plusMinutes(1).truncatedTo(ChronoUnit.MINUTES);
        Duration duration = Duration.between(now, nextMinute);
        System.out.println(duration.toMillis() / 1000L);
    }

    @Test
    public void numeric() {
        System.out.println(org.apache.commons.lang3.StringUtils.isNumeric(""));
    }

    @Test
    public void testMapPut() {
        Map<Long, Set<String>> itemPlanIdMap = Maps.newHashMap();
        itemPlanIdMap.put(123L, Sets.newHashSet("123String"));
        Set<Long> itemIdSet = Sets.newHashSet(123L, 456L);
        for (Long itemId: itemIdSet) {
            Set<String> strings = itemPlanIdMap.get(itemId);
            if (strings == null) {
                itemPlanIdMap.put(itemId, Sets.newHashSet(itemId + "String"));
            } else {
                strings.add("forString");
            }
        }

        System.out.println(JSON.toJSONString(itemPlanIdMap));
    }

    @Test
    public void bigDecimalAddition() {
        BigDecimal bigDecimal = BigDecimal.ZERO;
        bigDecimal = bigDecimal.add(new BigDecimal("2.2"));
        bigDecimal = bigDecimal.add(new BigDecimal("2.23"));

        System.out.println(bigDecimal.toPlainString());
    }

    @Test
    public void testProcessorsCount() {
        Map<String, String> map = Maps.newHashMap();
        map.put("1", "1");
        map.put("2", "1");
        map.put("3", "1");
        System.out.println(JSON.toJSONString(map));

        map.remove("1");
        System.out.println(JSON.toJSONString(map));

        map.remove("4");
        System.out.println(JSON.toJSONString(map));
    }

    @Test
    public void testListSet() {
        List<String> list = Lists.newArrayList();
        list.add("主图一");
        list.add("主图二");
        list.add("主图三");
        list.add("主图四");
        list.add("主图五");

        if (org.apache.commons.collections4.CollectionUtils.isNotEmpty(list) && list.size() > 1) {
            list.add(list.size() - 1, "评价图");
        }
        System.out.println(JSON.toJSONString(list));
    }

    @Test
    public void substring() {
        String s = "丁老板";
        if (org.apache.commons.lang3.StringUtils.isNotBlank(s) && s.length() > 7) {
            System.out.println(s.substring(0, 7) + "\n" + s.substring(7));
        } else {
            System.out.println(s);
        }
    }

    @Test
    public void testNpe() {
        List<Long> itemIdList = Lists.newArrayList(456L, 789L);
        List<Item> itemList = Lists.newArrayList();
        Item item1 = new Item();
        item1.setItemId(123L);
        item1.setName("");
        item1.setSkuList(Lists.newArrayList());
        item1.setPrice(new BigDecimal("0"));

        Item item2 = new Item();
        item2.setItemId(456L);
        item2.setName("");
        item2.setSkuList(Lists.newArrayList());
        item2.setPrice(new BigDecimal("0"));

        Item item3 = new Item();
        item3.setItemId(789L);
        item3.setName("");
        item3.setSkuList(Lists.newArrayList());
        item3.setPrice(new BigDecimal("0"));

        Item item4 = new Item();
        item4.setItemId(101112L);
        item4.setName("");
        item4.setSkuList(Lists.newArrayList());
        item4.setPrice(new BigDecimal("0"));

        itemList.add(item1);
        itemList.add(item2);
        itemList.add(item3);
        itemList.add(item4);

        Map<Long, Integer> itemIdSortMap = IntStream.range(0, itemIdList.size()).boxed()
            .collect(Collectors.toMap(itemIdList::get, Function.identity()));
        System.out.println(JSON.toJSONString(itemIdSortMap));
        System.out.println(JSON.toJSONString(itemList));
        itemList.sort(Comparator
            .comparingInt(vo -> itemIdSortMap.get(vo.getItemId()) != null ? itemIdSortMap.get(vo.getItemId()) : 9999));
        System.out.println(JSON.toJSONString(itemList));
    }

    @Test
    public void testIntStream() {
        List<Integer> list = IntStream.range(0, 10).boxed().collect(Collectors.toList());
        System.out.println(JSON.toJSONString(list));
    }

    @Test
    public void testBigKey() {
        String s = "10.131.68.170,10.131.68.162,10.131.68.173,10.131.68.155,10.131.68.171,10.131.68.177,10.131.68.158,10.131.68.190,10.131.68.191,10.131.68.156,10.131.68.185,10.131.68.180,10.131.68.179,10.131.68.189,10.131.68.165,10.131.68.181,10.131.68.157,10.131.68.184,10.131.68.183,10.131.68.152,10.131.68.186,10.131.68.174,10.131.68.160,10.131.68.188,10.131.68.176,10.131.68.153,10.131.68.182,10.131.68.178,10.131.68.169,10.131.68.172,10.131.68.175,10.131.68.166,10.131.68.168,10.131.68.154,10.131.68.163,10.131.66.129";
        String[] strings = s.split(",");
        List<String> ipList = Arrays.asList(strings);
        Collections.sort(ipList);

        System.out.println(JSON.toJSONString(ipList));
        Set<String> set = Sets.newHashSet(ipList);
        System.out.println(set.size());

        for (String string: ipList) {
            System.out.println(string);
        }
    }

    @Test
    public void testArrays() {
        String s = "123,456,789,5555";
        long lastItemId = 5555;
        NewItemListResultVO resultVO = new NewItemListResultVO();
        resultVO.setHasMore(true);
        //非第一页，直接根据透传商品id列表返回商品列表信息
        List<Long> itemIdList = Arrays.stream(s.split(",")).map(Long::valueOf).collect(Collectors.toList());
        List<Long> resultItemIdList = new ArrayList<>(20);
        for (int i = itemIdList.indexOf(lastItemId) + 1; i < itemIdList.size(); i++) {
            if (resultItemIdList.size() >= 2) {
                break;
            }
            resultItemIdList.add(itemIdList.get(i));
        }
        if (CollectionUtils.isEmpty(resultItemIdList)) {
            resultVO.setHasMore(false);
        } else if (itemIdList.get(itemIdList.size() - 1).equals(resultItemIdList.get(resultItemIdList.size() - 1))) {
            resultVO.setHasMore(false);
        }

        System.out.println(JSON.toJSONString(resultItemIdList));
        System.out.println(JSON.toJSONString(resultVO));
    }

    @Test
    public void testTimeUnit() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime past23Days = now.minus(23, ChronoUnit.DAYS);
        LocalDateTime future7Days = now.plus(7, ChronoUnit.DAYS);
        past23Days = LocalDateTime.of(past23Days.toLocalDate(), LocalTime.MIN);
        future7Days = LocalDateTime.of(future7Days.toLocalDate(), LocalTime.MIN);
        System.out.println(now.toInstant(ZoneOffset.of("+8")).toEpochMilli());
        System.out.println(past23Days.toInstant(ZoneOffset.of("+8")).toEpochMilli());
        System.out.println(future7Days.toInstant(ZoneOffset.of("+8")).toEpochMilli());

        now = LocalDateTime.of(now.toLocalDate(), LocalTime.MIN);
        System.out.println(now.toInstant(ZoneOffset.of("+8")).toEpochMilli());
    }

    @Test
    public void testAddAll() {
        Map<Long, Long> itemAutoOnSaleTimeMap = Maps.newHashMap();
        itemAutoOnSaleTimeMap.put(470004005L, 1611280800000L);

        System.out.println(JSON.toJSONString(getFutureTimeOnSaleItemMap(itemAutoOnSaleTimeMap)));
    }

    @Test
    public void testInstant() {
        Instant instant = Instant.ofEpochMilli(1610531789000L);
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime selectDate = LocalDateTime.ofInstant(instant, zone);
        long onSaleStartTime = LocalDateTime.of(selectDate.toLocalDate(), LocalTime.MIN).toInstant(ZoneOffset.of("+8"))
            .toEpochMilli();
        long onSaleEndTime = LocalDateTime.of(selectDate.toLocalDate(), LocalTime.MAX).toInstant(ZoneOffset.of("+8"))
            .toEpochMilli();

        System.out.println(onSaleStartTime);
        System.out.println(onSaleEndTime);
    }

    /**
     * 获取未来X天内自动上架的商品与当天日期0点的映射
     */
    private Map<Long, List<Long>> getFutureTimeOnSaleItemMap(Map<Long, Long> itemAutoOnSaleTimeMap) {
        if (MapUtils.isEmpty(itemAutoOnSaleTimeMap)) {
            return Maps.newHashMap();
        }

        Map<Long, List<Long>> futureSaleTimeItemMap = Maps.newHashMap();
        LocalDateTime initialTime = LocalDateTime.now();
        for (int i = 0; i <= 6; i++) {
            initialTime = initialTime.plus(1, ChronoUnit.DAYS);
            LocalDateTime futureDaysStartTime = LocalDateTime.of(initialTime.toLocalDate(), LocalTime.MIN);
            long futureStartTime = futureDaysStartTime.toInstant(ZoneOffset.of("+8")).toEpochMilli();
            LocalDateTime futureDayEndTime = LocalDateTime.of(initialTime.toLocalDate(), LocalTime.MAX);
            long futureEndTime = futureDayEndTime.toInstant(ZoneOffset.of("+8")).toEpochMilli();

            for (Map.Entry<Long, Long> itemAutoOnSaleTimeEntry: itemAutoOnSaleTimeMap.entrySet()) {
                if (itemAutoOnSaleTimeEntry.getValue() >= futureStartTime
                    && itemAutoOnSaleTimeEntry.getValue() <= futureEndTime) {
                    if (futureSaleTimeItemMap.get(futureStartTime) == null) {
                        futureSaleTimeItemMap.put(futureStartTime,
                            Lists.newArrayList(itemAutoOnSaleTimeEntry.getKey()));
                    } else {
                        futureSaleTimeItemMap.get(futureStartTime).add(itemAutoOnSaleTimeEntry.getKey());
                    }
                }
            }
        }
        return futureSaleTimeItemMap;
    }

    @Test
    public void testDailyCalendar() {
        LocalDateTime now = LocalDateTime.now();
        now = LocalDateTime.of(now.toLocalDate(), LocalTime.MIN);
        LocalDateTime pastDays = now.minus(7, ChronoUnit.DAYS);
        long startTime = pastDays.toInstant(ZoneOffset.of("+8")).toEpochMilli();
        System.out.println(startTime);

    }

    @Test
    public void testFastJson() {
        LotteryGiftBean lotteryGiftBean = new LotteryGiftBean();
        System.out.println(JSON.toJSONString(lotteryGiftBean));
    }

    @Test
    public void testListAddIndex() {
        List<Integer> integerList = Lists.newArrayList(1, 2, 3);
        integerList.add(1, 4);
        System.out.println(JSON.toJSONString(integerList));
    }

    @Test
    public void testLottery() {
        String config = "[\n" + "  {\n" + "    \"id\":1,\n" + "    \"couponCode\":\"FXYL2020021101\",\n"
            + "    \"cash\":30,\n" + "    \"validDate\":7,\n" + "    \"state\":2,\n" + "    \"probability\":0.15,\n"
            + "    \"desc\":\"恭喜！你被满299减30元券砸中啦！7天有效，记得使用哦～\"\n" + "  },\n" + "  {\n" + "    \"id\":2,\n"
            + "    \"couponCode\":\"FXYL2020021102\",\n" + "    \"cash\":10,\n" + "    \"validDate\":7,\n"
            + "    \"state\":2,\n" + "    \"probability\":0.30,\n" + "    \"desc\":\"恭喜！你被满189减10元券砸中啦！7天有效，记得使用哦～\"\n"
            + "  },\n" + "  {\n" + "    \"id\":3,\n" + "    \"couponCode\":\"FXYL2020021103\",\n" + "    \"cash\":5,\n"
            + "    \"validDate\":7,\n" + "    \"state\":2,\n" + "    \"probability\":0.4499,\n"
            + "    \"desc\":\"恭喜！你被满99减5元券砸中啦！7天有效，记得使用哦～\"\n" + "  }\n" + "]";
        List<LotteryGiftBean> lotteryGiftList = JSON.parseArray(config, LotteryGiftBean.class);

        System.out.println(JSON.toJSONString(getLotteryGiftVoMap(lotteryGiftList)));
        System.out.println(JSON.toJSONString(getLotteryProbabilityMap(lotteryGiftList)));
        System.out.println(JSON.toJSONString(getLuckThreshold(lotteryGiftList)));
    }

    @Test
    public void testTop() {
        List<GoodsBasic> goodsBasics = Lists.newArrayList();
        GoodsBasic goodsBasic1 = new GoodsBasic();
        goodsBasic1.setItemId(123);
        goodsBasic1.setStickAtTop(true);

        GoodsBasic goodsBasic2 = new GoodsBasic();
        goodsBasic2.setItemId(456);
        goodsBasic2.setStickAtTop(false);

        GoodsBasic goodsBasic3 = new GoodsBasic();
        goodsBasic3.setItemId(789);
        goodsBasic3.setStickAtTop(true);

        goodsBasics.add(goodsBasic1);
        goodsBasics.add(goodsBasic2);
        goodsBasics.add(goodsBasic3);

        System.out.println(checkTopItemRank(goodsBasics));

    }

    @Test
    public void testSort() {
        List<Long> sortedItemIdList = Lists.newArrayList(1L, 2L, 3L, 4L);

        //置顶商品处理
        List<Long> topItemIdList = Lists.newArrayList(1L, 2L, 3L, 4L);
        if (!CollectionUtils.isEmpty(topItemIdList)) {
            sortedItemIdList.removeIf(topItemIdList::contains);
            sortedItemIdList.addAll(0, topItemIdList);
        }

        System.out.println(JSON.toJSONString(sortedItemIdList));
    }

    @Test
    public void testDuplicate() {
        String s = "[{\"itemId\":1108028,\"stickAtTop\":false},{\"itemId\":3995415,\"stickAtTop\":false},{\"itemId\":3990150,\"stickAtTop\":false},{\"itemId\":1306027,\"stickAtTop\":false},{\"itemId\":1085019,\"stickAtTop\":false},{\"itemId\":3379044,\"stickAtTop\":false},{\"itemId\":3997518,\"stickAtTop\":false},{\"itemId\":3997047,\"stickAtTop\":false},{\"itemId\":3445007,\"stickAtTop\":false},{\"itemId\":4003238,\"stickAtTop\":false},{\"itemId\":3990127,\"stickAtTop\":false},{\"itemId\":3998411,\"stickAtTop\":false},{\"itemId\":1436033,\"stickAtTop\":false},{\"itemId\":3998493,\"stickAtTop\":false},{\"itemId\":3989107,\"stickAtTop\":false},{\"itemId\":3829027,\"stickAtTop\":false},{\"itemId\":1113019,\"stickAtTop\":false},{\"itemId\":1686093,\"stickAtTop\":false},{\"itemId\":4003237,\"stickAtTop\":false},{\"itemId\":3996993,\"stickAtTop\":false},{\"itemId\":4002695,\"stickAtTop\":false},{\"itemId\":3994257,\"stickAtTop\":false},{\"itemId\":3998662,\"stickAtTop\":false},{\"itemId\":3993385,\"stickAtTop\":false},{\"itemId\":1624001,\"stickAtTop\":false},{\"itemId\":3998196,\"stickAtTop\":false},{\"itemId\":3989796,\"stickAtTop\":false},{\"itemId\":3409060,\"stickAtTop\":false},{\"itemId\":3990698,\"stickAtTop\":false},{\"itemId\":1370002,\"stickAtTop\":false},{\"itemId\":1027014,\"stickAtTop\":false},{\"itemId\":1138000,\"stickAtTop\":false},{\"itemId\":3994457,\"stickAtTop\":false},{\"itemId\":1621020,\"stickAtTop\":false},{\"itemId\":3987025,\"stickAtTop\":false},{\"itemId\":3463000,\"stickAtTop\":false},{\"itemId\":1565068,\"stickAtTop\":false},{\"itemId\":3987805,\"stickAtTop\":false},{\"itemId\":3989105,\"stickAtTop\":false},{\"itemId\":3992762,\"stickAtTop\":false},{\"itemId\":1369004,\"stickAtTop\":false},{\"itemId\":3998391,\"stickAtTop\":false},{\"itemId\":3992597,\"stickAtTop\":false},{\"itemId\":1565069,\"stickAtTop\":false},{\"itemId\":3805013,\"stickAtTop\":false},{\"itemId\":1306026,\"stickAtTop\":false},{\"itemId\":1487030,\"stickAtTop\":false},{\"itemId\":3988838,\"stickAtTop\":false},{\"itemId\":3993448,\"stickAtTop\":false},{\"itemId\":3828099,\"stickAtTop\":false},{\"itemId\":3413004,\"stickAtTop\":false},{\"itemId\":3827023,\"stickAtTop\":false},{\"itemId\":3428015,\"stickAtTop\":false},{\"itemId\":3850013,\"stickAtTop\":false},{\"itemId\":3420060,\"stickAtTop\":false},{\"itemId\":1414001,\"stickAtTop\":false},{\"itemId\":3998695,\"stickAtTop\":false},{\"itemId\":1116034,\"stickAtTop\":false},{\"itemId\":1283013,\"stickAtTop\":false},{\"itemId\":3995068,\"stickAtTop\":false},{\"itemId\":3997604,\"stickAtTop\":false},{\"itemId\":3990582,\"stickAtTop\":false},{\"itemId\":1586006,\"stickAtTop\":false},{\"itemId\":4005124,\"stickAtTop\":false},{\"itemId\":1382000,\"stickAtTop\":false},{\"itemId\":3995534,\"stickAtTop\":false},{\"itemId\":3465038,\"stickAtTop\":false},{\"itemId\":1435024,\"stickAtTop\":false},{\"itemId\":3413006,\"stickAtTop\":false},{\"itemId\":1623004,\"stickAtTop\":false},{\"itemId\":1009024,\"stickAtTop\":false},{\"itemId\":1110003,\"stickAtTop\":false},{\"itemId\":3998372,\"stickAtTop\":false},{\"itemId\":3989106,\"stickAtTop\":false},{\"itemId\":3998211,\"stickAtTop\":false},{\"itemId\":1116033,\"stickAtTop\":false},{\"itemId\":3990721,\"stickAtTop\":false},{\"itemId\":1127003,\"stickAtTop\":false}]";
        List<ItemSkuParam> itemSkuParams = JSON.parseArray(s, ItemSkuParam.class);
        System.out.println("list size: " + itemSkuParams.size());

        List<Long> itemIdList = itemSkuParams.stream().map(ItemSkuParam::getItemId).collect(Collectors.toList());
        List<Long> duplicates = itemIdList.stream().collect(Collectors.groupingBy(Function.identity())).entrySet()
            .stream().filter(e -> e.getValue().size() > 1).map(Map.Entry::getKey).collect(Collectors.toList());

        System.out.println(JSON.toJSONString(duplicates));

    }

    @Test
    public void testCalBigDecimal() {
        BigDecimal subsidySavePriceThreshold = new BigDecimal("500");
        BigDecimal savePriceThresholdInYuan = subsidySavePriceThreshold.divide(new BigDecimal("100"), 2,
            RoundingMode.HALF_UP);
        System.out.println(savePriceThresholdInYuan);
        BigDecimal savePrice = new BigDecimal("6");
        if (savePrice.compareTo(savePriceThresholdInYuan) < 0) {
            System.out.println("less than threshold");
        } else {
            System.out.println("greater than threshold");
        }
    }

    public Map<Long, LotteryGiftBean> getLotteryGiftVoMap(List<LotteryGiftBean> lotteryGiftList) {
        if (org.apache.commons.collections4.CollectionUtils.isEmpty(lotteryGiftList)) {
            return Maps.newHashMap();
        }
        return lotteryGiftList.stream().collect(Collectors.toMap(LotteryGiftBean::getId, i -> i));
    }

    /**
     * 获取奖品概率映射表
     */
    public Map<Long, Double> getLotteryProbabilityMap(List<LotteryGiftBean> lotteryGiftList) {
        Map<Long, Double> lotteryProbabilityMap = new HashMap<>();
        double offset = 0;

        for (LotteryGiftBean lotteryGiftVO: lotteryGiftList) {
            offset += lotteryGiftVO.getProbability();
            if (offset <= 1) {
                lotteryProbabilityMap.put(lotteryGiftVO.getId(), offset);
            } else {
                lotteryProbabilityMap.put(lotteryGiftVO.getId(), (double) 0);
            }
        }

        return lotteryProbabilityMap;
    }

    /**
     * 获取获奖总概率
     */
    public double getLuckThreshold(List<LotteryGiftBean> lotteryGiftList) {
        if (org.apache.commons.collections4.CollectionUtils.isEmpty(lotteryGiftList)) {
            return 0;
        }
        return lotteryGiftList.stream().mapToDouble(LotteryGiftBean::getProbability).sum();
    }

    private boolean checkTopItemRank(List<GoodsBasic> goodsRankList) {
        if (org.apache.commons.collections4.CollectionUtils.isEmpty(goodsRankList)) {
            return true;
        }
        int lastStickAtTopItemIndex = 0;
        for (int i = 0; i <= goodsRankList.size() - 1; i++) {
            GoodsBasic goodsBasic = goodsRankList.get(i);
            if (goodsBasic != null && goodsBasic.isStickAtTop()) {
                lastStickAtTopItemIndex = i;
            }
        }

        List<GoodsBasic> topItemList = goodsRankList.stream()
            .filter(goodsBasic -> goodsBasic != null && goodsBasic.isStickAtTop()).collect(Collectors.toList());

        if (topItemList.size() > 0 && lastStickAtTopItemIndex + 1 > topItemList.size()) {
            return false;
        }
        return true;
    }

    @Test
    public void testDateUtil() {
        List<Integer> sortedItemList = Lists.newArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        sortedItemList = sortedItemList.subList(0, Math.min(sortedItemList.size(), 20));
        System.out.println(JSON.toJSONString(sortedItemList));
    }

    @Test
    public void testAllowCount() {
        int configAllowCount = 5;
        int stepNo = 0;
        AddBuyStepVO addBuyStepVO1 = new AddBuyStepVO();
        addBuyStepVO1.setStepNo(0);
        addBuyStepVO1.setSatisfy(true);
        addBuyStepVO1.setConfigCount(3);

        AddBuyStepVO addBuyStepVO2 = new AddBuyStepVO();
        addBuyStepVO2.setStepNo(1);
        addBuyStepVO2.setSatisfy(true);
        addBuyStepVO2.setConfigCount(5);

        for (AddBuyStepVO addBuyStepVO: Lists.newArrayList(addBuyStepVO1, addBuyStepVO2)) {
            if (!addBuyStepVO.isSatisfy() && stepNo <= addBuyStepVO.getStepNo()) {
                configAllowCount = addBuyStepVO.getConfigCount();
                break;
            }
        }

        System.out.println(configAllowCount);
    }

    @Test
    public void testRegex() {
        List<AbtResourceTO> abtResourceTOS = Lists.newArrayList();
        AbtResourceTO abtResourceTO = new AbtResourceTO();
        abtResourceTO.setSpuId(470195255L);
        abtResourceTO.setPlanIds(Lists.newArrayList(String.valueOf(17674)));
        abtResourceTOS.add(abtResourceTO);
        System.out.println(JSON.toJSON(abtResourceTOS).toString());
    }

    @Test
    public void testPage() {
        Long itemId = 333L;
        int pageSize = 3;
        List<Long> itemIdList = Lists.newArrayList(111L, 222L, 333L, 444L, 555L);
        int index = 0;
        if (itemId != null) {
            for (int i = 0; i < itemIdList.size(); i++) {
                if (itemIdList.get(i).longValue() == itemId.longValue()) {
                    index = i + 1;
                    break;
                }
            }
        } else if (itemId == null || itemId == 0) {
            index = 0;
        }

        List<Long> subList = Lists.newArrayList();

        for (int j = 0; index < itemIdList.size() && j < pageSize; index++, j++) {
            subList.add(itemIdList.get(index));
        }
        System.out.println(JSON.toJSONString(subList));

    }

    private boolean validCharacter(char c) {
        if (c >= 0x4e00 && c <= 0x9fa5) {
            return true;
        }
        if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z')) {
            return true;
        }
        if (c >= '0' && c <= '9') {
            return true;
        }
        if (c == '-') {
            return true;
        }
        if (c == ' ') {
            return true;
        }
        return false;
    }

    @Test
    public void testGetNextList() {
        System.out.println(extractUndeliverableProductIds(
            "方法:tob.mall.callback.order.prebind的依赖服务错误,相关信息如下:收货地址不可配送:300871782、300871783"));
    }

    public List<String> extractUndeliverableProductIds(String errorMessage) {
        String keyword = "收货地址不可配送" + ":";
        int keywordIndex = errorMessage.indexOf(keyword);

        if (keywordIndex == -1) {
            return Collections.emptyList();
        }

        // 截取关键词后的内容并去除首尾空格
        String idsSection = errorMessage.substring(keywordIndex + keyword.length()).trim();

        // 处理空内容的情况
        if (idsSection.isEmpty()) {
            return Collections.emptyList();
        }

        // 分割并处理可能的空格
        return Arrays.stream(idsSection.split("、")).map(String::trim).filter(id -> !id.isEmpty())
            .collect(Collectors.toList());
    }

    @Test
    public void calMilkYield() {
        int monthOld = 3;
        double weightInKilo = 5;
        int mealCountInOneDay = 8;

        Map<Integer, List<Integer>> monthOldCaloriePerKiloMap = new HashMap<>();
        List<Integer> caloriePerKilForOneMonth = new ArrayList<>();
        caloriePerKilForOneMonth.add(110);
        caloriePerKilForOneMonth.add(130);
        List<Integer> caloriePerKilForThreeMonth = new ArrayList<>();
        caloriePerKilForThreeMonth.add(100);
        List<Integer> caloriePerKilForSixMonth = new ArrayList<>();
        caloriePerKilForSixMonth.add(85);
        List<Integer> caloriePerKilForTwelveMonth = new ArrayList<>();
        caloriePerKilForTwelveMonth.add(80);
        monthOldCaloriePerKiloMap.put(1, caloriePerKilForOneMonth);
        monthOldCaloriePerKiloMap.put(3, caloriePerKilForThreeMonth);
        monthOldCaloriePerKiloMap.put(6, caloriePerKilForSixMonth);
        monthOldCaloriePerKiloMap.put(12, caloriePerKilForTwelveMonth);

        Set<Integer> monthOldSet = monthOldCaloriePerKiloMap.keySet();
        Integer matchedMonthKey = null;
        for (Integer targetMonthOld: monthOldSet) {
            if (targetMonthOld > monthOld) {
                break;
            }
            matchedMonthKey = targetMonthOld;
        }
        if (matchedMonthKey == null) {
            System.out.println("没有找到对应月份的体重卡路里比，请检查体重卡路里配置");
        }
        List<Integer> calorieList = monthOldCaloriePerKiloMap.get(matchedMonthKey);
        if (org.apache.commons.collections4.CollectionUtils.isEmpty(calorieList)) {
            System.out.println("对应月份的体重卡路里比没有设置，请检查");
        }

        if (calorieList.size() == 1) {
            BigDecimal milkYield = new BigDecimal(String.valueOf(weightInKilo)).setScale(2, RoundingMode.HALF_UP)
                .multiply(new BigDecimal(String.valueOf(calorieList.get(0))))
                .divide(new BigDecimal("0.67"), RoundingMode.HALF_UP)
                .divide(new BigDecimal(String.valueOf(mealCountInOneDay)), RoundingMode.HALF_UP);
            System.out.println("宝宝年龄：" + monthOld + "个月，" + "体重：" + weightInKilo + "kg。" + "如果一天" + mealCountInOneDay
                + "餐，每餐需要喝" + milkYield.toPlainString() + "ml母乳");
        }

        if (calorieList.size() == 2) {
            BigDecimal minMilkYield = new BigDecimal(String.valueOf(weightInKilo)).setScale(2, RoundingMode.HALF_UP)
                .multiply(new BigDecimal(String.valueOf(calorieList.get(0))))
                .divide(new BigDecimal("0.67"), RoundingMode.HALF_UP)
                .divide(new BigDecimal(String.valueOf(mealCountInOneDay)), RoundingMode.HALF_UP);
            BigDecimal maxMilkYield = new BigDecimal(String.valueOf(weightInKilo)).setScale(2, RoundingMode.HALF_UP)
                .multiply(new BigDecimal(String.valueOf(calorieList.get(1))))
                .divide(new BigDecimal("0.67"), RoundingMode.HALF_UP)
                .divide(new BigDecimal(String.valueOf(mealCountInOneDay)), RoundingMode.HALF_UP);;
            System.out.println("宝宝年龄：" + monthOld + "个月，" + "体重：" + weightInKilo + "kg。" + "如果一天" + mealCountInOneDay
                + "餐，每餐需要喝" + minMilkYield.toPlainString() + "ml-" + maxMilkYield.toPlainString() + "ml母乳");
        }

    }

    @Test
    public void testDaysBetween() {
        long currentTime = System.currentTimeMillis();
        LocalDateTime pastLowestPriceTime = LocalDateTime
            .ofInstant(Instant.ofEpochMilli(currentTime), ZoneId.systemDefault()).truncatedTo(ChronoUnit.DAYS);

        LocalDateTime now = LocalDateTime.ofInstant(Instant.ofEpochMilli(currentTime), ZoneId.systemDefault())
            .truncatedTo(ChronoUnit.DAYS);

        Duration duration = Duration.between(pastLowestPriceTime, now);
        long dayDifference = duration.toDays();

        long mod = dayDifference % 10L;

        if (mod < 5) {
            dayDifference += (5 - mod);
        } else if (mod > 5) {
            dayDifference += (10 - mod);
        }

        System.out.println(dayDifference);
    }

    @Test
    public void testSimpleFormat() throws ParseException {

        String appSecret = "ef068514-5ad5-4041-801d-05ac930071f4";
        Map<String, String> treeMap = new TreeMap<>();
        treeMap.put("appKey", "c8d5b76e-59c9-4a7c-bccc-735642c23dcd");
        treeMap.put("method", "yicai.channel.order.pre.create");
        treeMap.put("timestamp", "2024-10-21 10:59:36");
        treeMap.put("orderInfo",
            "{\"expFee\":0,\"expFeeCompanyPay\":0,\"expFeeThirdPay\":0,\"orderId\":\"(FLSC)POSD20241021105915640\",\"orderItems\":[{\"count\":1,\"name\":\"秋冬女士100%羊毛衫套头内搭毛衣针织打底衫\",\"originPrice\":284.76,\"skuId\":\"301066713\",\"subtotalPrice\":284.76}],\"realPrice\":284.76,\"receiverAddressDetail\":\"城区洪家楼街道黄台南路五号康和东苑4-2-1904\",\"receiverCityName\":\"济南市\",\"receiverDistrictName\":\"历城区\",\"receiverMobile\":\"15615315338\",\"receiverName\":\"岳彤\",\"receiverProvinceName\":\"山东\",\"receiverStreetName\":\"城区\",\"submitTime\":1729479576000,\"zipCode\":\"250100\"}");
        //遍历treeMap，将其value取出添加进拼接字符串
        StringBuilder sb = new StringBuilder();

        Iterator<String> it = treeMap.keySet().iterator();

        String key;
        while (it.hasNext()) {
            key = it.next();
            sb.append(key).append("=").append(treeMap.get(key));
        }

        //等待验签的字符串收尾添加appSecret，准备加密验签
        String paramsStr = appSecret + sb + appSecret;

        //获取加密后的字符串
        String paramsSignStr = ParseMD5.parseStrToMd5U32(paramsStr);

        System.out.println(paramsSignStr);

    }

    //从给定数组中选择k个最大的数，保持原有顺序
    private List<Integer> prepare(int[] nums, int k) {
        //使用了单调栈，每个元素最多入栈一次，出栈一次
        Stack<Integer> stack = new Stack<>();
        //需要丢弃的元素数量
        int drop = nums.length - k;
        for (int num: nums) {
            while (drop > 0 && !stack.isEmpty() && num > stack.peek()) {
                stack.pop();
                drop--;
            }
            stack.push(num);
        }
        //返回栈中的前k个元素
        return stack.subList(0, k);
    }

    private int[] maxSubsequence(int[] nums, int k) {
        int length = nums.length;
        int[] stack = new int[k]; // 结果数组，存储选取的k个数
        int top = -1; // 栈顶指针，指向结果数组的最后一个元素
        int remain = length - k; // 剩余的元素数量，等于原数组的长度减去k
        for (int i = 0; i < length; i++) { // i是当前遍历到的nums中的元素的下标
            int num = nums[i]; // 当前元素
            // 如果栈不为空，且栈顶元素小于当前元素，且剩余的元素数量大于0，就将栈顶元素删除，即将top减1，remain减1
            while (top >= 0 && stack[top] < num && remain > 0) {
                top--;
                remain--;
            }
            // 如果栈的元素数量小于k，就将当前元素添加到栈中，即将num赋值给stack[++top]
            if (top < k - 1) {
                stack[++top] = num;
            } else {
                remain--; // 如果栈的元素数量等于k，就将remain减1
            }
        }
        return stack; // 返回结果数组
    }

    @Test
    public void testCat() {
        BigDecimal balanceAllocateRatio = new BigDecimal("102.59").divide(new BigDecimal("102.6"), 4,
            RoundingMode.DOWN);
        System.out.println("item1 balanceAllocateRatio: " + balanceAllocateRatio);
        BigDecimal item1BalancePrice = balanceAllocateRatio.multiply(new BigDecimal("49.90")).setScale(2,
            RoundingMode.HALF_UP);
        System.out.println("item1 balance price: " + item1BalancePrice);
        BigDecimal item1CashPrice = new BigDecimal("49.90").subtract(item1BalancePrice);
        System.out.println("item1 cash price: " + item1CashPrice);

        BigDecimal item2Balance = balanceAllocateRatio.multiply(new BigDecimal("44.70")).setScale(2,
            RoundingMode.HALF_UP);
        System.out.println("item2 balance price: " + item2Balance);
        BigDecimal item2CashPrice = new BigDecimal("44.70").subtract(item2Balance);
        System.out.println("item2 cash price: " + item2CashPrice);

        BigDecimal expBalance = new BigDecimal("102.59").subtract(item1BalancePrice).subtract(item2Balance);
        System.out.println("exp balance: " + expBalance);
        BigDecimal expCash = new BigDecimal("0.01").subtract(item1CashPrice).subtract(item2CashPrice);
        System.out.println("exp cash: " + expCash);

        //        if (expCash.compareTo(BigDecimal.ZERO) < 0) {
        //            // 最后一项为最小金额项，如果发生现金分摊金额为负数的情况，将当前项现金支付比例默认设置为0.01，将差额补偿到最大金额商品项
        //            BigDecimal deficit = new BigDecimal("0.00").subtract(expCash);
        //            System.out.println("deficit: " + deficit);
        //            expCash = new BigDecimal("0.00");
        //            System.out.println("new exp cash: " + expCash);
        //            expBalance = new BigDecimal("8.00").subtract(expCash);
        //            System.out.println("new exp balance: " + expBalance);
        //            item1CashPrice = item1CashPrice.subtract(deficit);
        //            System.out.println("new item1 cash price: " + item1CashPrice);
        //            item1BalancePrice = new BigDecimal("49.90").subtract(item1CashPrice);
        //            System.out.println("new item1 balance price: " + item1BalancePrice);
        //
        //        }

        //
        //        BigDecimal item2BalancePrice = new BigDecimal("102.59")
        //                .subtract(item2Balance);
        //        System.out.println("item2 balance price: " + item2BalancePrice);
        //        BigDecimal item2CashPrice = new BigDecimal("0.01").subtract(new BigDecimal("0.01"));
        //        System.out.println("item2 cash price: " + item2CashPrice);

    }

    @Test
    public void testBooleanNull() {
        System.out.println(null instanceof Boolean);
    }

    private List<ComplexTextVO> genComplexTip(String originPromTip) {
        if (null == originPromTip || "".equals(originPromTip)) {
            return Lists.newArrayList();
        }
        List<ComplexTextVO> complexTextList = Lists.newArrayList();

        //获取数字的内容
        String integerOrDecimalRegex = "(-)?[0-9]+([.][0-9]+)?";
        Pattern pattern = Pattern.compile(integerOrDecimalRegex);
        Matcher m = pattern.matcher(originPromTip);
        List<String> numberList = new ArrayList<>();
        while (m.find()) {
            numberList.add(m.group());
        }

        //获取非数字的内容
        originPromTip = originPromTip.replaceAll(integerOrDecimalRegex, "_");
        List<String> stringList = Lists.newArrayList(Arrays.asList(originPromTip.split("_")));
        for (String notNumber: stringList) {
            ComplexTextVO complexTextVO = new ComplexTextVO();
            complexTextVO.setValue(notNumber);
            complexTextVO.setType(ComplexTextColorConsts.STAMP_BLACK);
            complexTextList.add(complexTextVO);
        }

        //合并数字到结果中
        if (org.apache.commons.collections4.CollectionUtils.isNotEmpty(numberList)) {
            int index = 1;
            for (String number: numberList) {
                ComplexTextVO complexTextVO = new ComplexTextVO();
                complexTextVO.setValue(number);
                complexTextVO.setType(ComplexTextColorConsts.STAMP_RED);
                complexTextList.add(index, complexTextVO);
                index = index + 2;
            }
        }

        return complexTextList;
    }

    @Test
    public void testObjectMap() {
        List<GoodsBasic> goodsBasicList = Lists.newArrayList();
        GoodsBasic goodsBasic1 = new GoodsBasic();
        goodsBasic1.setItemId(123L);
        goodsBasicList.add(goodsBasic1);

        GoodsBasic goodsBasic2 = new GoodsBasic();
        goodsBasic2.setItemId(123L);
        goodsBasicList.add(goodsBasic2);

        GoodsBasic goodsBasic3 = new GoodsBasic();
        goodsBasic3.setItemId(456L);
        goodsBasicList.add(goodsBasic3);

        Map<Long, Long> issuedSchemePrizeCountMap = goodsBasicList.stream()
            .collect(Collectors.groupingBy(GoodsBasic::getItemId, Collectors.counting()));
        System.out.println(JSON.toJSONString(issuedSchemePrizeCountMap));
    }

    @Test
    public void testFields() {
        CacheScheduleStatEvent completeScheduleStatEvent = CacheScheduleStatEvent.builder()
            .time(System.currentTimeMillis()).type(StatEventTypeEnum.CACHE_SCHEDULE_EVENT)
            .ope(CacheOpeTypeEnum.SCHEDULE).cacheName("ITEM_CACHE").triggerStage(TriggerStageEnum.TRIGGER_COMPLETE)
            .result("SUCCESS").trigger("ITEM_CACHE" + "." + "DSCHEDULE").job("REMOTE" + "." + "ITEM_CACHE")
            .elapsed(200L).build();

        System.out.println(genFields(completeScheduleStatEvent));
    }

    @Test
    public void stringManage() {
        // 获取当前北京时间
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Asia/Shanghai"));

        // 计算下个月的第一天
        ZonedDateTime nextMonthFirstDay = now.with(TemporalAdjusters.firstDayOfNextMonth()).with(LocalTime.MIDNIGHT);

        // 将给定的时间戳转换为ZonedDateTime
        Instant givenInstant = Instant.ofEpochMilli(1685548800000L);
        ZonedDateTime givenTime = ZonedDateTime.ofInstant(givenInstant, ZoneId.of("Asia/Shanghai"));

        // 判断下个月第一天是否早于给定的时间
        System.out.println(nextMonthFirstDay.isBefore(givenTime));
    }

    @Test
    public void testAnd() {
        SimpleDateFormat mydate = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        System.out.println(mydate.format(new Date(System.currentTimeMillis())));
    }

    @Test
    public void testToString() {
        List<Integer> list = Lists.newArrayList(2, 3, 1);
        System.out.println(
            JSON.toJSONString(list.stream().sorted(Comparator.comparing(i -> i)).collect(Collectors.toList())));
        System.out.println(UUID.randomUUID());
    }

    @Test
    public void testJackson() throws JsonProcessingException {
        Map<Integer, String> map = Maps.newHashMap();
        map.put(1, "已满199元，可选择5件商品低价换购");
        // 使用fastjson序列化
        String fastjsonSerialization = JSON.toJSONString(map);
        System.out.println("fastjson序列化结果: " + fastjsonSerialization);
        // 使用fastjson反序列化，与initSuccessResult中保持一致
        Map<Integer, String> fastjsonDeserialization = JSON.parseObject(JSON.toJSONString(map), HashMap.class);

        // 使用jackson序列化
        ObjectMapper jacksonObjectMapper = new ObjectMapper();
        String jacksonSerialization = jacksonObjectMapper.writeValueAsString(fastjsonDeserialization);
        System.out.println("jackson序列化结果: " + jacksonSerialization);
    }

    @Test
    public void insertGenerate() {
        String s = " INSERT INTO `mt_reservation`.`TB_MOUTAI_RESERVATION_ITEM` (`itemCode`, `itemName`, `shortCode`, `shortName`, `itemKind`, `itemKindName`, `itemType`, `itemTypeName`, `itemSubType`, `itemSubTypeName`, `barcode`, `unitId`, `unitName`, `outprice`, `tSize`, `pSize`, `createTime`, `updateTime`) VALUES (";
        int startItemCode = 750;
        for (int i = 0; i < 43; i++) {
            System.out.println(s + "'" + (startItemCode + i) + "'," + "'茅台酒" + i + "',"
                + "'0', '0', '01', 'mt', '50', '低度', 'MTDDJ001', '普通低度', '6902952880195', '10', '瓶', 0.01, 1062, 12, 1639924724000, 1639924724000);");
        }
    }

    @Test
    public void getDayOfWeek() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, -4);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (dayOfWeek == 0) {
            dayOfWeek = 7;
        }
        System.out.println(dayOfWeek);
    }

    @Test
    public void listSub() {
        System.out.println("123".equals(null));
    }

    @Test
    public void parseDate() {
        System.out.println(UserInfoUtil.tokenValidate("1649301352915",
            UserInfoUtil.getOriginalStr("8220c2a8c148d6dad1f625b4cbb6ebfd"), "f5f32b04d0cea756222c4d01134dcb"));
    }

    @Test
    public void schemeUrl() {
        String listSuccessString = "[4001574,4001592,4001593,4001677,4005641,4005782,4005807,4005808,4005851,4005857,4007632,4007652,4007655,4007806,4007851,4007855,4007885,4009671,4009810,4009834,4009847,4009855,4009904,4009937,4009946,4011724,4011795,4013850,4021975,4028187,4028237,4028244,4032427,4032463,1417026,1667019,1667027,1667068,1673035,3420004,3420074,3424020,3459005,3459033,3465011,3469024,3469025,3551207,3551208,3803003,3803007,3803017,3805058,3807045,3811008,3987141,3987203,3987235,3987247,3987288,3987429,3991234,3991239,3991240,3991245,3991254,3991256,3991260,3991361,3991363,3991364,3991417,3991421,3991429,3991431,3991432,3991433,3991435,3991439,3991441,3991442,3991443,3991444,3991452,3991481,3991482,3991488,3991492,3991503,3991530,3991538,3991543,3991548,3993489,3993545,3993556,3993597,3995401,3997474,3997512,3997517,3997518,3997519,3997555,3997557,3997563,3997565,3997568,3997570,3997571,3997572,3997574,3997575,3997577,3997580,3997584,3997589,3997591,3997593,3997595,3997596,3997598,3997599,3997600,3997601,3997607,3997613,3997614,3997616,3997618,3997620,3997631,3999441,3999736,3997078,3997097,3997204,3997223,3997224,3997226,3997258,3998994,3998995,3998996,3999150,3999180,3999204,3999270,3999271,3999305,3999317,3999327,4001126,4001238,4001243,4001285,4003167,4003173,4003192,4003204,4003224,4003238,4003296,4003420,4003421,4003422,4003430,4003431,4003432,4003433,4003438,4003439,4003447,4003448,4003449,4005155,4005185,4005188,4005189,4005190,4005191,4005251,4005255,4005280,4005286,4005289,4005291,4005295,4005298,4005300,4005628,4005631,4007343,4007414,4007442,4007454,4007505,4007526,4007548,4007549,4007615,4009230,4009253,4009451,4009483,4009491,4009529,4009536,4009651,4009653,4009711,4009714,4011465,4011496,4013485,4013574,4013575,4013579,4013621,4021801,4021806,4027803,4029775,4031885,4031924,4031970,4031990,4032031,4033930,4033932,4033941,4033944,4033958,4036061,1169002,1220001,1251000,1251001,1251002,1306026,1306027,1435022,1476005,1519011,1519013,1550015,1564039,1593000,1636000,1646000,1652031,1683058,1687060,3430052,3430065,3430078,3436036,3438016,3440059,3440179,3471012,3471016,3481045,3522104,3524007,3811001,3815034,3815050,3823020,3823034,3827017,3827031,3827042,3829015,3829027,3986730,3986818,3986820,3986846,3986854,3986883,3986888,3986891,3986894,3986895,3986896,3986899,3986909,3986912,3986929,3986985,3986986,3986993,3986997,3987019,3987071,3987114,3987115,3987118,3987119,3987135,3989175,3989176,3989181,3989234,3990826,3990880,3990950,3991037,3991077,3991123,3991126,3991127,3991161,3991164,3991205,3992962,3993038,3993057,3993058,3993083,3993098,3995046,3995048,3995055,3995092,3995133,3995151,3995207,3996956,3996960,3996993,3997045,3997051,3996989,3996991,3998620,3998774,3998777,3998783,3998784,3998793,3998794,3998798,3998835,3998946,3998947,4000914,4000997,4002705,4002790,4002848,4002864,4002866,4002879,4002905,4003042,4003045,4003046,4003055,4003059,4003062,4003064,4003066,4003067,4004794,4004804,4004807,4004808,4004846,4004847,4004848,4005005,4005006,4005008,4005046,4005047,4005048,4005050,4005051,4005052,4005056,4005057,4005060,4005083,4005084,4005086,4005096,4005179,4005181,4005183,4006800,4006812,4006840,4006856,4006868,4007111,4007122,4007123,4007124,4007126,4007127,4007128,4008836,4008842,4008921,4008933,4008956,4008959,4008966,4009260,4011017,4011041,4013244,4013261,4017296,4023203,4023226,4023364,4029353,4029355,4029365,4029585,4029647,4031427,4031453,4031462,4031481,4031490,4033413,4033485,4033671,4035678,4035741,1055023,1147023,1151017,1436030,1649019,1651000,1651001,1667080,3408050,3410005,3410052,3410057,3410072,3441064,3445019,3451015,3496012,3533000,3533006,3533007,3551275,3551315,3822010,3828009,3828037,3834015,3838016,3840002,3875024,3879025,3987526,3987579,3987744,3987749,3987768,3987769,3987770,3987772,3989504,3989734,3991601,3991603,3991639,3991640,3991645,3991646,3991660,3991678,3991680,3991681,3991688,3991723,3991729,3991800,3991803,3991805,3991825,3991974,3991975,3993605,3993640,3993922,3994008,3995649,3995734,3995757,3995758,3995836,3996015,3996029,3997765,3997766,3997767,3997768,3997808,3997810,3997834,3997835,3997861,3997876,3997893,3997894,3997897,3997899,3997962,3997967,3997984,3997995,3998001,3998003,3998012,3998021,3998039,3998048,3998050,3998078,3998101,3998104,3999799,3999814,3999890,3999898,3999899,3999945,3999957,3999958,3999959,3999968,4000022,4000023,4000024,4000026,4000070,4000071,4000098,4000100,4000102,4001805,4001829,4001830,4001831,4001833,4001853,4001854,4001855,4001924,4001975,4001985,4001987,4001991,4001996,4001997,4002002,4003908,4004117,4004142,4004143,4005991,4005996,4005998,4006001,4006004,4006005,4006011,4006015,4006024,4006029,4006030,4006031,4006033,4006056,4006058,4006059,4006061,4006066,4006070,4006072,4006073,4006074,4006096,4006099,4006128,4006168,4006169,4006193,4006202,4006220,4006221,4006222,4006223,4006224,4006250,4006256,4006264,4006306,4006308,4006310,4006312,4006314,4008071,4008073,4008195,4008201,4008208,4012132,4012138,4012243,4012247,4014207,4014280,4014432,4024421,4026392,4026431,4026447,4026448,4026570,4028466,4028647,4030571,1006006,1086013,1092026,1246029,1246031,1459005,1551015,1551072,1553015,1586017,1672050,1672057,1686032,1686087,3423012,3425005,3429050,3429054,3429056,3431030,3437007,3441105,3441107,3441109,3441172,3441174,3441189,3468000,3468023,3468056,3523095,3523097,3810010,3814113,3816012,3818014,3818015,3822033,3826060,3987849,3988081,3988346,3989902,3990023,3990039,3992059,3992061,3992068,3992090,3992105,3992106,3992113,3992116,3992118,3992237,3992307,3994055,3994260,3994374,3994393,3996217,3996228,3996346,3996347,3996502,3996507,3998084,3998090,3998092,3998093,3998100,3998102,3998147,3998153,3998161,3998286,3998292,3998296,3998299,3998301,3998344,3998363,3998515,3998573,3998580,3998655,4000283,4000464,4000615,4000620,4002263,4002265,4002290,4002414,4002437,4002556,4002564,4002581,4002583,4002597,4002600,4002601,4002603,4002604,4002605,4002651,4002659,4002662,4002667,4002668,4002672,4004542,4004546,4004550,4004557,4004619,4004632,4004633,4004635,4004638,4004640,4004641,4004643,4004647,4004649,4004651,4004662,4004683,4004685,4004686,4004688,4004690,4004693,4004695,4004698,4004701,4004718,4004719,4004730,4004731,4004733,4004796,4006372,4006415,4006474,4006691,4006695,4006696,4006710,4006727,4006732,4006738,4006740,4006741,4006743,4006760,4006783,4008454,4008465,4008560,4008626,4008627,4008628,4008629,4008630,4008631,4008632,4008633,4008648,4008659,4008683,4008685,4008702,4008765,4010522,4010523,4010534,4010541,4010568,4010869,4016615,4016637,4016727,4016863,4022749,4026862,4026938,4026942,4028820,4029043,4029070,4029196,4029210,4031101,4031315,4031320,4033041,4035213,4037277,4039192,1023019,1027017,1109030,1113019,1156006,1240001,1447022,1541020,1541027,1541029,1621018,1621020,1621034,1621035,1654005,1662047,1662049,1664016,1666023,1666066,3409009,3409060,3409084,3411002,3419070,3446002,3452044,3452062,3458008,3458012,3458061,3495016,3497017,3505045,3538014,3540012,3544002,3550283,3802011,3802012,3802015,3804050,3804075,3841028,3986420,3986493,3986494,3986515,3986520,3986521,3986524,3986525,3986614,3986615,3986618,3986653,3986737,3986741,3988363,3988461,3988468,3988522,3990550,3990632,3992495,3992502,3992510,3992551,3992591,3992600,3992603,3992604,3992748,3992752,3994625,3994757,3994758,3994931,3996635,3996636,3996637,3996639,3996640,3996645,3996646,3996660,3996697,3996698,3996699,3996700,3996703,3996722,3996724,3996741,3996746,3996747,3996749,3996773,3996781,3996782]";
        String listTotalString = "[4035678,4033671,4031490,4028187,4026942,4026938,4026448,4026447,4023203,4022749,4013579,4013244,4010869,4010534,4009451,4008073,4008071,4007655,4006168,4006128,4005857,4005851,4005086,4005084,4005083,4004550,4004546,4004542,4003296,4003238,4003224,4003192,4003173,4003167,4003059,4003055,4003046,4003045,4002667,4002605,4002604,4002603,4002601,4002600,4002597,4002583,4002581,4002290,4001924,4001833,4001831,4001574,3999959,3999958,3999957,3999945,3999899,3999898,3999441,3999204,3999180,3998947,3998655,3998620,3998580,3998039,3998003,3997893,3997876,3997768,3997767,3997765,3997620,3997580,3997097,3997078,3996781,3996773,3996502,3996029,3996015,3995092,3994260,3993556,3993545,3992510,3992502,3992059,3991800,3991723,3991688,3991482,3991481,3991256,3991254,3991164,3991127,3991126,3991077,3990950,3990880,3988522,3987749,3987235,3986909,3986818,3986741,3551207,3540012,3496012,3452062,3409084,1541029,1541027,1147023,1023019,3986820,3803003,1654005,1092026,3992061,4036061,4028466,4027803,4026862,4026570,4017296,4014432,4014280,4013261,4012243,4011465,4010568,4009904,4009536,4009483,4008966,4008842,4008683,4008659,4008648,4007615,4007526,4007442,4006695,4005998,4005991,4005280,4004117,4002905,4002556,4001975,4001805,4000914,3999890,3999814,3998573,3998344,3998161,3998153,3998050,3997995,3997631,3997618,3997616,3997593,3997591,3997589,3997577,3997572,3997571,3997568,3997565,3997563,3997258,3997045,3996993,3996991,3996989,3995151,3994931,3994625,3994008,3993922,3993605,3993038,3992495,3991729,3989234,3987429,3986618,3986420,3841028,3838016,3829027,3828037,3828009,3827017,3818015,3816012,3815050,3810010,3807045,3804050,3551208,3505045,3497017,3495016,3471016,3469025,3459033,3458061,3451015,3441064,3440059,3420074,1687060,1672057,1667080,1667068,1667027,1666066,1666023,1662047,1652031,1651001,1651000,1636000,1621035,1621034,1621018,1551015,1519011,1476005,1459005,1436030,1417026,1240001,1220001,1109030,1055023,1006006,4032031,4031970,4031462,4030571,4008685,4003908,4031453,4033485,4032427,4031481,3436036,4004557,1435022,1447022,1649019,3424020,3429054,3429056,3465011,3538014,3811008,3986737,3987114,3987118,3987119,3987203,3991240,3991245,3991503,3991538,3991803,3991805,3991825,3992106,3992113,3992116,3992118,3992962,3994374,3995649,3995836,3996217,3996228,3996635,3996636,3996637,3996639,3996640,3996645,3996646,3996660,3996697,3996699,3996700,3996703,3996782,3996956,3997474,3997517,3997518,3997519,3997596,3997598,3997599,3997600,3997601,3997607,3997835,3998078,3998084,3998100,3998102,3998104,3998292,3998363,3998515,3998835,3998994,3998995,3999150,3999327,4000070,4000071,4000100,4001126,4001238,4001243,4001677,4001985,4001987,4001997,4002651,4002662,4002668,4002790,4002864,4002866,4003447,4003449,4004619,4004647,4004796,4004807,4004846,4004848,4005155,4005179,4005188,4005286,4005291,4005628,4005641,4006074,4006099,4006202,4006250,4006314,4006840,4007122,4007124,4007505,4009260,4014207,4016615,4024421,4026392,4026431,4028647,4029355,4029365,4033958,4035741,1169002,1672050,1686032,1686087,3419070,3423012,3429050,3458012,3551275,3551315,3805058,3811001,3827042,3986846,3986854,3986888,3986891,3986899,3986912,3986986,3986993,3986997,3987019,3987115,3987247,3987288,3987769,3988346,3988363,3988461,3988468,3989175,3989181,3989902,3991123,3991432,3991433,3991442,3991444,3991603,3991640,3991680,3992752,3995133,3996347,3996507,3996722,3996724,3996741,3996747,3996749,3997861,3997897,3997899,3998774,3998777,3998783,3998793,3999270,3999271,3999736,4001855,4002002,4002848,4003431,4003433,4003439,4004640,4004662,4004733,4004794,4005047,4005048,4005056,4005189,4005251,4005300,4006011,4006030,4006031,4006033,4006058,4006059,4006072,4006073,4006264,4006306,4006308,4006310,4006415,4006696,4006760,4006783,4006868,4007851,4007885,4008201,4008465,4008560,4008956,4008959,4009529,4009651,4009653,4009671,4009711,4009714,4009834,4009847,4011496,4012132,4012138,4012247,4013574,4013575,4029353,4032463,4033930,4039192,1246029,1246031,1541020,1550015,1551072,1564039,1586017,1593000,3408050,3409009,3409060,3410005,3410052,3410072,3430052,3430065,3430078,3437007,3440179,3441105,3441107,3441109,3441172,3441174,3441189,3445019,3452044,3458008,3459005,3468056,3522104,3523095,3523097,3524007,3533000,3533006,3533007,3550283,3802011,3802012,3802015,3803007,3803017,3815034,3822010,3823020,3826060,3986521,3986730,3986883,3986894,3986895,3986896,3986929,3986985,3987071,3987135,3987141,3987526,3987579,3987768,3987770,3987772,3987849,3988081,3989176,3990023,3990039,3990550,3990632,3990826,3991037,3991205,3991234,3991239,3991260,3991361,3991363,3991364,3991417,3991421,3991429,3991431,3991435,3991439,3991441,3991443,3991452,3991488,3991492,3991530,3991543,3991548,3991601,3991639,3991645,3991646,3991660,3991678,3991681,3991974,3991975,3992068,3992090,3992105,3992237,3992307,3992551,3992591,3992600,3992603,3992604,3992748,3993057,3993058,3993083,3993098,3993489,3993597,3993640,3994055,3994393,3994757,3994758,3995046,3995048,3995055,3995207,3995734,3995757,3995758,3996346,3996698,3996746,3996960,3997512,3997570,3997574,3997584,3997595,3997613,3997614,3997808,3997810,3997834,3997962,3997967,3998012,3998090,3998092,3998093,3998101,3998286,3998296,3998299,3998301,3998784,3998794,3999317,3999799,4000022,4000023,4000024,4000026,4000098,4000102,4000615,4000620,4000997,4001592,4001593,4001829,4001830,4001853,4001854,4001991,4001996,4002263,4002265,4002437,4002659,4002672,4002705,4003204,4003420,4003421,4003422,4003430,4003432,4003438,4003448,4004142,4004143,4004632,4004633,4004635,4004638,4004641,4004643,4004649,4004651,4004683,4004685,4004686,4004688,4004690,4004693,4004695,4004698,4004701,4004718,4004719,4004730,4004731,4004804,4004808,4004847,4005005,4005006,4005008,4005046,4005050,4005051,4005052,4005057,4005060,4005096,4005181,4005183,4005185,4005190,4005191,4005255,4005289,4005295,4005298,4005631,4005782,4005807,4005808,4006004,4006005,4006029,4006056,4006058,4006059,4006061,4006066,4006070,4006096,4006193,4006220,4006221,4006222,4006223,4006224,4006312,4006372,4006474,4006691,4006710,4006727,4006732,4006738,4006740,4006741,4006743,4006800,4006812,4006856,4007111,4007123,4007126,4007127,4007128,4007454,4007548,4007549,4007632,4007652,4007806,4007855,4008454,4008626,4008627,4008628,4008629,4008630,4008631,4008632,4008633,4008702,4008765,4009230,4009253,4009855,4009937,4009946,4010522,4010523,4010541,4011017,4011795,4013621,4016637,4016727,4021801,4021806,4021975,4023226,4023364,4028244,4029043,4029070,4029196,4029210,4029585,4029647,4031924,4033413,4033932,4033941,4033944,4035213,4033041,4032427,4031990,4031970,4031885,4031481,4031462,4031427,4031320,4031315,4031101,4030571,4029775,4028820,4028237,4013485,4010568,4009810,4009491,4008933,4008921,4008842,4008836,4008685,4008208,4008195,4007615,4007343,4006001,4005998,4003042,4002879,4002564,4001285,4000464,4000283,3999968,3999305,3998996,3998946,3998798,3998021,3998001,3997984,3997894,3997766,3997575,3997226,3997224,3997223,3997204,3997051,3995401,3879025,3827031,3471012,1621020,1113019,4037277,4016863,4013850,4011724,4011041,4007414,4006256,4006169,4006024,4006015,4005996,4003067,4003066,4003064,4003062,4002414,3998147,3998048,3997557,3997555,3991161,3989734,3989504,3987744,3986653,3986615,3986614,3986525,3986524,3986520,3986515,3986494,3986493,3875024,3840002,3834015,3829015,3823034,3822033,3818014,3814113,3804075,3544002,3496012,3481045,3469024,3468023,3468000,3446002,3438016,3436036,3431030,3425005,3420004,3411002,3410057,1683058,1673035,1667019,1664016,1662049,1646000,1553015,1519013,1417026,1306027,1306026,1251002,1251001,1251000,1156006,1151017,1092026,1086013,1027017,3995401]";

        List<Long> listSuccess = JSON.parseArray(listSuccessString, Long.class);
        List<Long> listTotal = JSON.parseArray(listTotalString, Long.class);

    }

    private void changeString(String s) {
        s = "123";
    }

    private Map<String, Object> genFields(CacheScheduleStatEvent scheduleInfo) {
        if (scheduleInfo == null) {
            return Maps.newHashMap();
        }
        Method[] methods = scheduleInfo.getClass().getMethods();
        Map<String, Object> map = new HashMap<>();
        for (Method m: methods) {
            if (m.getName().startsWith("get")) {
                Object value = null;
                try {
                    value = m.invoke(scheduleInfo);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
                if (value != null) {
                    if (value instanceof Long && (Long) value <= 0L) {
                        continue;
                    }
                    String fieldName = m.getName().substring(3);
                    if ("Class".equals(fieldName)) {
                        continue;
                    }
                    map.put(fieldName.toLowerCase(), value);
                }

            }
        }

        return map;
    }

    private List<Long> getNextCoupleItemIds(List<Long> allItemIdList, long currentItemId, int count) {
        if (CollectionUtils.isEmpty(allItemIdList)) {
            return Lists.newArrayList();
        }
        if (!allItemIdList.contains(currentItemId)) {
            return Lists.newArrayList();
        }
        List<Long> copyAllItemList = Lists.newArrayList(allItemIdList);
        List<Long> itemIdsAfterCurrentItemId = copyAllItemList.subList(copyAllItemList.indexOf(currentItemId) + 1,
            copyAllItemList.size());
        return itemIdsAfterCurrentItemId.subList(0, Math.min(itemIdsAfterCurrentItemId.size(), count));
    }

    private Set<Long> getMoutaiProxyUserIdByItemId(long itemId) {
        if (itemId <= 0) {
            return Sets.newHashSet();
        }
        Set<Long> userIdSet = Sets.newHashSet();
        int i = 0;
        List<String> strings = Lists.newArrayList("123,456", "666,777", "789,123123");
        while (i < strings.size()) {
            String s = strings.get(i);
            String[] proxyMoutaiUserIds = org.apache.commons.lang3.StringUtils.split(s, ',');

            if (proxyMoutaiUserIds == null || proxyMoutaiUserIds.length == 0) {
                return userIdSet;
            }

            for (String userId: proxyMoutaiUserIds) {
                userIdSet.add(Long.valueOf(userId));
            }
            i++;
        }
        return userIdSet;
    }

}
