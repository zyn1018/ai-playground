package com.yinan.play.demo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.yinan.play.demo.meta.CategoryDTO;
import com.yinan.play.demo.meta.CategoryTO;
import com.yinan.play.demo.meta.ChannelMonitorSkuPriceTO;
import com.yinan.play.demo.meta.ChannelMonitorSkuRecordSortPO;
import com.yinan.play.demo.util.DateUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CombinationBuyTests {

    @Test
    public void contextLoads() {
        String[] array = "70,23,1,45,1000,975,7,89,34,45".split(",");
        if (array.length == 0) {
            return;
        }
        if (array.length < 2) {
            System.out.println(array[array.length - 1]);
        }
        // 将数组转化成int数组
        int[] intArray = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            intArray[i] = Integer.parseInt(array[i]);
        }
        int[] temp = new int[intArray.length];
        mergeSort(intArray, 0, intArray.length - 1, temp);
        System.out.println(intArray[intArray.length - 2]);
    }
    
    public void quickSortIntegers(int[] A) {
        // Write your code here
        if (A == null || A.length == 0) {
            return;
        }
        quickSort(A, 0, A.length - 1);
    }

    private void quickSort(int[] A, int start, int end) {
        if (start >= end) {
            return;
        }
        int left = start;
        int right = end;
        //1.pivot,A[start], A[end]
        //get value not index
        int pivot = A[(start + end) / 2];
        //2.left <=right not left<right
        while (left <= right) {
            while (left <= right && A[left] < pivot) {
                left++;
            }
            while (left <= right && A[right] > pivot)
                right--;
            if (left <= right) {
                int temp = A[left];
                A[left] = A[right];
                A[right] = temp;
                left++;
                right--;
            }
        }
        quickSort(A, start, right);
        quickSort(A, left, end);
    }
    
    public void mergeSortIntegers(int[] A) {
        // Write your code here
        if (A == null || A.length == 0) {
            return;
        }
        int[] temp = new int[A.length];
        mergeSort(A, 0, A.length - 1, temp);
    }

    private void mergeSort(int[] A, int start, int end, int[] temp) {
        if (start >= end) {
            return;
        }

        mergeSort(A, start, (start + end) / 2, temp);
        mergeSort(A, (start + end) / 2 + 1, end, temp);
        merge(A, start, end, temp);
    }

    private void merge(int[] A, int start, int end, int[] temp) {
        int middle = (start + end) / 2;
        int leftIndex = start;
        int rightIndex = middle + 1;
        int index = leftIndex;

        while (leftIndex <= middle && rightIndex <= end) {
            if (A[leftIndex] < A[rightIndex]) {
                temp[index++] = A[leftIndex++];
            } else {
                temp[index++] = A[rightIndex++];
            }
        }
        while (leftIndex <= middle) {
            temp[index++] = A[leftIndex++];
        }

        while (rightIndex <= end) {
            temp[index++] = A[rightIndex++];
        }

        for (int i = start; i < end; i++) {
            A[i] = temp[i];
        }
        for (int i = start; i <= end; i++) {
            A[i] = temp[i];
        }
    }

    @Test
    public void mapSize() {
        getOrderCompanyPayProportion();
    }

    private void getOrderCompanyPayProportion() {
        Long personalPayPrice = 2337L;
        Long companyPayPrice = 2663L;

        //企业支付比例 企业支付金额/(个人+企业)
        BigDecimal companyProportion = new BigDecimal(companyPayPrice)
            .divide(new BigDecimal(personalPayPrice).add(new BigDecimal(companyPayPrice)), 6, RoundingMode.HALF_EVEN);
        System.out.println("companyProportion: " + companyProportion);
        BigDecimal skuOriginPrice = new BigDecimal("25.00");
        BigDecimal refundSkuPrice = skuOriginPrice.multiply(new BigDecimal(1));
        BigDecimal companyRefundAmount = refundSkuPrice.multiply(companyProportion).setScale(2,
                RoundingMode.HALF_EVEN);
        System.out.println("companyRefundAmount: " + companyRefundAmount);
        BigDecimal userRefundAmount = refundSkuPrice.subtract(companyRefundAmount);
        System.out.println("userRefundAmount: " + userRefundAmount);
    }

    private BigDecimal getLowestPrice(ChannelMonitorSkuRecordSortPO channelMonitorSkuRecordSortPO) {
        BigDecimal lowestPrice = new BigDecimal(-1);
        String priceLowestArray1 = channelMonitorSkuRecordSortPO.getPriceLowestArray1();
        CopyOnWriteArrayList<ChannelMonitorSkuPriceTO> channelMonitorSkuPriceTOS = new CopyOnWriteArrayList(
            JSON.parseArray(priceLowestArray1, ChannelMonitorSkuPriceTO.class));
        while (!channelMonitorSkuPriceTOS.isEmpty() && daysBetween(channelMonitorSkuPriceTOS.get(0).getGraspDay(),
            DateUtils.long2String(System.currentTimeMillis(), "yyyy-MM-dd")) >= 30) {
            channelMonitorSkuPriceTOS.remove(0);
        }
        if (!channelMonitorSkuPriceTOS.isEmpty()) {
            if (lowestPrice.compareTo(new BigDecimal(channelMonitorSkuPriceTOS.get(0).getPrice())) < 0) {
                lowestPrice = new BigDecimal(channelMonitorSkuPriceTOS.get(0).getPrice());
            }
        }

        String priceLowestArray2 = channelMonitorSkuRecordSortPO.getPriceLowestArray2();
        List<ChannelMonitorSkuPriceTO> channelMonitorSkuPriceTO2S = JSON.parseArray(priceLowestArray2,
            ChannelMonitorSkuPriceTO.class);
        while (!channelMonitorSkuPriceTO2S.isEmpty() && daysBetween(channelMonitorSkuPriceTO2S.get(0).getGraspDay(),
            DateUtils.long2String(System.currentTimeMillis(), "yyyy-MM-dd")) >= 30) {
            channelMonitorSkuPriceTO2S.remove(0);
        }
        if (!channelMonitorSkuPriceTO2S.isEmpty()) {
            if (lowestPrice.compareTo(new BigDecimal(channelMonitorSkuPriceTO2S.get(0).getPrice())) < 0) {
                lowestPrice = new BigDecimal(channelMonitorSkuPriceTO2S.get(0).getPrice());
            }
        }

        return lowestPrice;
    }

    private long daysBetween(String startDate, String endDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date startDateSdf = sdf.parse(startDate);
            Date endDateSdf = sdf.parse(endDate);

            long diffInMillies = Math.abs(startDateSdf.getTime() - endDateSdf.getTime());
            long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
            return diff;
        } catch (Exception e) {
            throw new RuntimeException("日期转换错误");
        }
    }

    private CategoryDTO categoryConvert(CategoryTO materialTO) {
        if (materialTO == null) {
            return null;
        }
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(materialTO.getId());
        categoryDTO.setName(materialTO.getName());
        categoryDTO.setLevel(materialTO.getLevel());
        categoryDTO.setParentId(materialTO.getParentId());
        categoryDTO.setEnable(materialTO.getEnable());
        categoryDTO.setSubCategoryList(categoryListConvert(materialTO.getSubCategoryList()));

        return categoryDTO;
    }

    private List<CategoryDTO> categoryListConvert(List<CategoryTO> materialTOList) {
        if (CollectionUtils.isEmpty(materialTOList)) {
            return Lists.newArrayList();
        }
        return materialTOList.stream().map(this::categoryConvert).collect(Collectors.toList());
    }

}
