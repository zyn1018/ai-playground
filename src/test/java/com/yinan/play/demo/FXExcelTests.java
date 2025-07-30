package com.yinan.play.demo;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.listener.PageReadListener;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.yinan.excel.TobMallSkuExcelData;
import com.yinan.excel.meta.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
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

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class FXExcelTests {

    @Test
    public void testExcel() throws IOException {
        String fileName = "/Users/yinan/Desktop/演示数据/商品信息模板-东浩0712.xlsx";
        String curlShellName = "/Users/yinan/Desktop/jd_curl.sh";
        String curlSpecName = "/Users/yinan/Desktop/jd_curl_spec.sh";
        FileWriter fileWriter = new FileWriter(curlShellName);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        FileWriter specFileWriter = new FileWriter(curlSpecName);
        BufferedWriter specBufferedWriter = new BufferedWriter(specFileWriter);

        EasyExcel.read(fileName, TobMallSkuExcelData.class, new PageReadListener<TobMallSkuExcelData>(dataList -> {
            for (TobMallSkuExcelData sku : dataList) {
                try {
                    sku.setItemName(sku.getItemName().replace("'", ""));
                    SpuEditParam spuEditParam = new SpuEditParam();
                    spuEditParam.setFirstSaleCategoryId(Long.valueOf(sku.getFirstSaleCategoryId()));
                    spuEditParam.setSecondSaleCategoryId(Long.valueOf(sku.getSecondSaleCategoryId()));
                    spuEditParam.setSpuId("");
                    spuEditParam.setShopId(10002L);
                    spuEditParam.setSupplierId(10002L);
                    spuEditParam.setName(sku.getItemName());
                    spuEditParam.setItemCode(sku.getItemCode());
                    spuEditParam.setRecommendReason("");
                    if (StringUtils.isNotBlank(sku.getItemPicUrls())) {
                        String[] picUrls = StringUtils.split(sku.getItemPicUrls(), "`");
                        spuEditParam.setPrimaryPicUrl(Arrays.asList(picUrls));
                    }

                    if (StringUtils.isNotBlank(sku.getDetailHtml())) {
                        spuEditParam.setDetailHtml(sku.getDetailHtml());

                    }

                    List<SpuSpecEditParam> specList = Lists.newArrayList();

                    SpuSpecEditParam spec = new SpuSpecEditParam();
                    spec.setId(0L);
                    spec.setFrontId(1);
                    spec.setName("规格");
                    spec.setViewOrder(1);
                    List<SpuSpecValueEditParam> specValueList = Lists.newArrayList();
                    SpuSpecValueEditParam specValueEditParam = new SpuSpecValueEditParam();
                    specValueEditParam.setId(0L);
                    specValueEditParam.setFrontId(11);
                    specValueEditParam.setSpuSpecId(0);
                    specValueEditParam.setViewOrder(1);
                    specValueEditParam.setValue(sku.getItemName());
                    specValueEditParam.setFrontDesc(sku.getItemName());
                    specValueList.add(specValueEditParam);
                    spec.setSpecValueList(specValueList);

                    specList.add(spec);
                    spuEditParam.setSpecList(specList);
                    // 生成规格创建curl
                    if (CollectionUtils.isNotEmpty(specList)) {
                        for (SpuSpecEditParam specEdit : specList) {
                            if (specEdit == null) {
                                continue;
                            }
                            WorkBenchSpecModelBean specModelBean = new WorkBenchSpecModelBean();
                            SpuSaleCategoryBean spuSaleCategoryBean = new SpuSaleCategoryBean();
                            spuSaleCategoryBean.setFirstSaleCategoryId(spuEditParam.getFirstSaleCategoryId());
                            spuSaleCategoryBean.setSecondSaleCategoryId(spuEditParam.getSecondSaleCategoryId());

                            specModelBean.setCategory(Lists.newArrayList(spuSaleCategoryBean));
                            specModelBean.setSpecName(specEdit.getName());
                            List<WorkBenchSpecValueModelBean> specEditValueList = buildSpecValueList(specEdit.getSpecValueList());
                            specModelBean.setSpecValueList(specEditValueList);
                            specModelBean.setSupplierId(28L);

                            specBufferedWriter.write("curl -H \"Content-Type: application/json\" -X POST -d '" + JSON.toJSONString(specModelBean) + "' 'http://127.0.0.1:8550/proxy/online.tob-item-center.service.mailsaas/workbench/specModel/edit'" + "\n");
                        }

                    }

                    List<SkuEditParam> skuList = Lists.newArrayList();
                    SkuEditParam skuEditParam = new SkuEditParam();
                    skuEditParam.setSkuCode(sku.getSkuCode());
                    skuEditParam.setMarketPrice(new BigDecimal(sku.getMarketPriceStr()));
                    skuEditParam.setOldCostPrice(BigDecimal.ZERO);
                    skuEditParam.setNewCostPrice(new BigDecimal(sku.getCostPriceStr()));

                    List<SkuSpecCombineEditParam> skuSpecCombineList = Lists.newArrayList();
                    SkuSpecCombineEditParam skuSpec1CombineEditParam = new SkuSpecCombineEditParam();
                    skuSpec1CombineEditParam.setSpecId(1);
                    skuSpec1CombineEditParam.setSpecValueId(11);
                    skuSpecCombineList.add(skuSpec1CombineEditParam);

                    skuEditParam.setSkuSpecCombineList(skuSpecCombineList);
                    skuEditParam.setInventoryAmount(500L);

                    skuList.add(skuEditParam);

                    spuEditParam.setSkuList(skuList);
                    spuEditParam.setPolicyList(Lists.newArrayList());
                    spuEditParam.setReturnAddressId(0L);
                    spuEditParam.setShippingTemplateId(51L);
                    spuEditParam.setSupport7DaysReturn(0);

                    SpuEditRequestVO spuEditRequestVO = new SpuEditRequestVO();
                    spuEditRequestVO.setSpu(spuEditParam);
                    spuEditRequestVO.setPageType(0);
                    spuEditRequestVO.setOperator("zhangyinan01@corp.netease.com");
                    spuEditRequestVO.setOperatorName("张一楠");

                    bufferedWriter.write("curl -H \"Content-Type: application/json\" -X POST -d '" + JSON.toJSONString(spuEditRequestVO) + "' 'http://127.0.0.1:8550/proxy/online.tob-item-center.service.mailsaas/workbench/spu/edit'" + "\n");
                } catch (Exception e) {
                    log.info("[op:testExcel] sku = {}", JSON.toJSONString(sku), e);
                }
            }
        }, 5000)).sheet().doRead();

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
        for (SpuSpecValueEditParam specValue : specValueList) {
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
