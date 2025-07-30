package com.yinan.play.demo;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.listener.PageReadListener;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yinan.excel.TobMallSkuExcelData;
import com.yinan.excel.meta.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class YXExcelTests {

    @Test
    public void testExcel() throws IOException {
        String fileName = "/Users/yinan/Documents/netease/doc/渠道/饭卡/线上正式数据导入/线上严选商品导入20230825/东浩-商品信息模板-8月25日-终版.xlsx";
        String curlShellName = "/Users/yinan/Documents/netease/doc/渠道/饭卡/线上正式数据导入/线上严选商品导入20230825/yx_curl.sh";
        String curlSpecName = "/Users/yinan/Documents/netease/doc/渠道/饭卡/线上正式数据导入/线上严选商品导入20230825/yx_curl_spec.sh";
        FileWriter fileWriter = new FileWriter(curlShellName);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        FileWriter specFileWriter = new FileWriter(curlSpecName);
        BufferedWriter specBufferedWriter = new BufferedWriter(specFileWriter);
        EasyExcel.read(fileName, TobMallSkuExcelData.class, new PageReadListener<TobMallSkuExcelData>(dataList -> {
            Map<String, List<TobMallSkuExcelData>> itemCodeSkuMap = dataList.stream()
                .collect(Collectors.groupingBy(TobMallSkuExcelData::getItemCode));

            for (Map.Entry<String, List<TobMallSkuExcelData>> entry: itemCodeSkuMap.entrySet()) {
                try {
                    TobMallSkuExcelData first = entry.getValue().get(0);
                    if (first == null) {
                        log.info("[op:testExcel] invalid sku data, entry = {}", JSON.toJSONString(entry));
                        continue;
                    }
                    SpuEditParam spuEditParam = new SpuEditParam();
                    spuEditParam.setFirstSaleCategoryId(Long.valueOf(first.getFirstSaleCategoryId()));
                    spuEditParam.setSecondSaleCategoryId(Long.valueOf(first.getSecondSaleCategoryId()));
                    spuEditParam.setSpuId("");
                    spuEditParam.setShopId(10011L);
                    spuEditParam.setSupplierId(10011L);
                    spuEditParam.setName(first.getItemName());
                    spuEditParam.setItemCode(first.getItemCode());
                    spuEditParam.setRecommendReason("");
                    if (StringUtils.isNotBlank(first.getItemPicUrls())) {
                        String[] picUrls = StringUtils.split(first.getItemPicUrls(), ",");
                        spuEditParam.setPrimaryPicUrl(Arrays.asList(picUrls));
                    }

                    spuEditParam.setDetailHtml(first.getDetailHtml());
                    List<SpuSpecEditParam> specList = Lists.newArrayList();
                    Map<String, List<String>> spec1Map = Maps.newHashMap();
                    for (TobMallSkuExcelData sku: entry.getValue()) {
                        String specName = sku.getSpecName1();
                        String specValue = sku.getSpecValue1();
                        if (StringUtils.isNotBlank(specName) && StringUtils.isNotBlank(specValue)) {
                            if (spec1Map.containsKey(specName)) {
                                spec1Map.get(specName).add(specValue);
                            } else {
                                spec1Map.put(specName, Lists.newArrayList(specValue));
                            }
                        }
                    }

                    Map<String, List<String>> spec2Map = Maps.newHashMap();
                    for (TobMallSkuExcelData sku: entry.getValue()) {
                        String specName = sku.getSpecName2();
                        String specValue = sku.getSpecValue2();
                        if (StringUtils.isNotBlank(specName) && StringUtils.isNotBlank(specValue)) {
                            if (spec2Map.containsKey(specName)) {
                                spec2Map.get(specName).add(specValue);
                            } else {
                                spec2Map.put(specName, Lists.newArrayList(specValue));
                            }
                        }
                    }

                    int i = 1;
                    Map<String, Long> spec1NameFrontIdMap = Maps.newHashMap();
                    Map<String, Long> spec1ValueFrontIdMap = Maps.newHashMap();

                    if (MapUtils.isNotEmpty(spec1Map)) {
                        for (Map.Entry<String, List<String>> spec1Entry: spec1Map.entrySet()) {
                            SpuSpecEditParam spec = new SpuSpecEditParam();
                            spec.setId(0L);
                            spec.setFrontId(1);
                            spec1NameFrontIdMap.put(spec1Entry.getKey(), spec.getFrontId());
                            spec.setName(spec1Entry.getKey());
                            spec.setViewOrder(i++);
                            List<SpuSpecValueEditParam> specValueList = Lists.newArrayList();
                            if (CollectionUtils.isNotEmpty(spec1Entry.getValue())) {
                                int frontId = 11;
                                for (String specValue: spec1Entry.getValue()) {
                                    SpuSpecValueEditParam specValueEditParam = new SpuSpecValueEditParam();
                                    specValueEditParam.setId(0L);
                                    specValueEditParam.setFrontId(frontId++);
                                    spec1ValueFrontIdMap.put(specValue, specValueEditParam.getFrontId());
                                    specValueEditParam.setSpuSpecId(0);
                                    specValueEditParam.setViewOrder(1);
                                    specValueEditParam.setValue(specValue);
                                    specValueEditParam.setFrontDesc(specValue);
                                    specValueList.add(specValueEditParam);
                                    spec.setSpecValueList(specValueList);
                                }
                            }
                            specList.add(spec);
                        }
                    }

                    Map<String, Long> spec2NameFrontIdMap = Maps.newHashMap();
                    Map<String, Long> spec2ValueFrontIdMap = Maps.newHashMap();
                    if (MapUtils.isNotEmpty(spec2Map)) {
                        for (Map.Entry<String, List<String>> spec2Entry: spec2Map.entrySet()) {
                            SpuSpecEditParam spec = new SpuSpecEditParam();
                            spec.setId(0L);
                            spec.setFrontId(2);
                            spec2NameFrontIdMap.put(spec2Entry.getKey(), spec.getFrontId());
                            spec.setName(spec2Entry.getKey());
                            spec.setViewOrder(i++);
                            List<SpuSpecValueEditParam> specValueList = Lists.newArrayList();
                            if (CollectionUtils.isNotEmpty(spec2Entry.getValue())) {
                                int frontId = 222;
                                for (String specValue: spec2Entry.getValue()) {
                                    SpuSpecValueEditParam specValueEditParam = new SpuSpecValueEditParam();
                                    specValueEditParam.setId(0L);
                                    specValueEditParam.setFrontId(frontId++);
                                    spec2ValueFrontIdMap.put(specValue, specValueEditParam.getFrontId());
                                    specValueEditParam.setSpuSpecId(0);
                                    specValueEditParam.setViewOrder(1);
                                    specValueEditParam.setValue(specValue);
                                    specValueEditParam.setFrontDesc(specValue);
                                    specValueList.add(specValueEditParam);
                                    spec.setSpecValueList(specValueList);
                                }
                            }
                            specList.add(spec);
                        }
                    }

                    spuEditParam.setSpecList(specList);

                    // 生成规格创建curl
                    if (CollectionUtils.isNotEmpty(specList)) {
                        for (SpuSpecEditParam spec: specList) {
                            if (spec == null) {
                                continue;
                            }
                            WorkBenchSpecModelBean specModelBean = new WorkBenchSpecModelBean();
                            SpuSaleCategoryBean spuSaleCategoryBean = new SpuSaleCategoryBean();
                            spuSaleCategoryBean.setFirstSaleCategoryId(spuEditParam.getFirstSaleCategoryId());
                            spuSaleCategoryBean.setSecondSaleCategoryId(spuEditParam.getSecondSaleCategoryId());

                            specModelBean.setCategory(Lists.newArrayList(spuSaleCategoryBean));
                            specModelBean.setSpecName(spec.getName());
                            List<WorkBenchSpecValueModelBean> specValueList = buildSpecValueList(
                                spec.getSpecValueList());
                            specModelBean.setSpecValueList(specValueList);
                            specModelBean.setSupplierId(10011L);

                            specBufferedWriter.write("curl -H \"Content-Type: application/json\" -X POST -d '"
                                + JSON.toJSONString(specModelBean)
                                + "' 'http://127.0.0.1:8550/proxy/online.tob-item-center.service.mailsaas/workbench/specModel/edit'"
                                + "\n");
                        }

                    }

                    List<SkuEditParam> skuList = Lists.newArrayList();
                    for (TobMallSkuExcelData sku: entry.getValue()) {
                        SkuEditParam skuEditParam = new SkuEditParam();
                        skuEditParam.setSkuCode(sku.getSkuCode());
                        skuEditParam.setMarketPrice(new BigDecimal(sku.getMarketPriceStr()));
                        skuEditParam.setOldCostPrice(BigDecimal.ZERO);
                        skuEditParam.setNewCostPrice(new BigDecimal(sku.getCostPriceStr()));

                        List<SkuSpecCombineEditParam> skuSpecCombineList = Lists.newArrayList();
                        if (StringUtils.isNotBlank(sku.getSpecName1()) && StringUtils.isNotBlank(sku.getSpecValue1())) {
                            SkuSpecCombineEditParam skuSpec1CombineEditParam = new SkuSpecCombineEditParam();
                            skuSpec1CombineEditParam.setSpecId(spec1NameFrontIdMap.get(sku.getSpecName1()));
                            skuSpec1CombineEditParam.setSpecValueId(spec1ValueFrontIdMap.get(sku.getSpecValue1()));

                            skuSpecCombineList.add(skuSpec1CombineEditParam);
                        }

                        if (StringUtils.isNotBlank(sku.getSpecName2()) && StringUtils.isNotBlank(sku.getSpecValue2())) {
                            SkuSpecCombineEditParam skuSpec2CombineEditParam = new SkuSpecCombineEditParam();
                            skuSpec2CombineEditParam.setSpecId(spec2NameFrontIdMap.get(sku.getSpecName2()));
                            skuSpec2CombineEditParam.setSpecValueId(spec2ValueFrontIdMap.get(sku.getSpecValue2()));

                            skuSpecCombineList.add(skuSpec2CombineEditParam);
                        }
                        skuEditParam.setSkuSpecCombineList(skuSpecCombineList);
                        skuEditParam.setInventoryAmount(Long.valueOf(first.getInventory()));

                        skuList.add(skuEditParam);
                    }
                    spuEditParam.setSkuList(skuList);
                    spuEditParam.setPolicyList(Lists.newArrayList());
                    spuEditParam.setReturnAddressId(Long.parseLong(first.getReturnAddressId()));
                    spuEditParam.setShippingTemplateId(52L);
                    spuEditParam.setSupport7DaysReturn("支持七天无忧退换货".equals(first.getReturn7daysSupport()) ? 1 : 0);

                    SpuEditRequestVO spuEditRequestVO = new SpuEditRequestVO();
                    spuEditRequestVO.setSpu(spuEditParam);
                    spuEditRequestVO.setPageType(0);
                    spuEditRequestVO.setOperator("zhangyinan01@corp.netease.com");
                    spuEditRequestVO.setOperatorName("张一楠");

                    bufferedWriter.write("curl -H \"Content-Type: application/json\" -X POST -d '"
                        + JSON.toJSONString(spuEditRequestVO)
                        + "' 'http://127.0.0.1:8550/proxy/online.tob-item-center.service.mailsaas/workbench/spu/edit'"
                        + "\n");
                } catch (Exception e) {
                    log.info("[op:testExcel] entry = {}", JSON.toJSONString(entry), e);
                }
            }
        }, 20000)).sheet().doRead();

        bufferedWriter.close();
        fileWriter.close();
        specBufferedWriter.close();
        specFileWriter.close();

    }

    private List<WorkBenchSpecValueModelBean> buildSpecValueList(List<SpuSpecValueEditParam> specValueList) {
        if (CollectionUtils.isEmpty(specValueList)) {
            return Lists.newArrayList();
        }
        List<WorkBenchSpecValueModelBean> workBenchSpecValueModelBeanList = Lists.newArrayList();
        for (SpuSpecValueEditParam specValue: specValueList) {
            if (specValue == null) {
                continue;
            }
            WorkBenchSpecValueModelBean workBenchSpecValueModelBean = new WorkBenchSpecValueModelBean();
            workBenchSpecValueModelBean.setSpecValue(specValue.getValue());

            workBenchSpecValueModelBeanList.add(workBenchSpecValueModelBean);
        }

        return workBenchSpecValueModelBeanList;
    }
}
