package com.yinan.play.demo;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSON;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BubbleSortTests {

    @Test
    public void contextLoads() {

        String needAddSpecValue = "[\"1kg\",\"150g\",\"400g\",\"2kg\",\"300g\",\"85g\",\"500g\",\"25kg\",\"5kg\",\"5L\",\"1L\",\"200g\",\"380g\",\"420g\",\"1只\",\"320g\",\"80g\",\"10kg\",\"1.9L\",\"630ml\",\"640ml\",\"300ml\",\"500ml\",\"218ml\",\"397g\",\"850g\",\"410g\",\"3kg\",\"3.5kg\",\"750g\",\"100g\",\"250g\",\"1块\",\"250ml\",\"350g\",\"435g\",\"22.7kg\",\"10L\",\"450g\",\"907g\",\"400ml\",\"15kg\",\"70g\",\"1枚\",\"75g\",\"1个\",\"360g\",\"395ml\",\"440g\",\"450ml\",\"1.6kg\",\"288g\",\"250ml*24\",\"110g\",\"600ml\",\"335g\",\"2.7KG\",\"240ml\",\"750ml\",\"245ml\"]";

        String existedSpecValue = "[\"1.6kg*6\",\"1.9L*6\",\"100g\",\"100g*48\",\"100g*72\",\"100g*96\",\"10kg\",\"10L*2\",\"10盒\",\"110g\",\"150g\",\"150g*60包\",\"15kg\",\"1kg\",\"1kg*10\",\"1kg*20\",\"1L*12\",\"2.5kg\",\"2.7KG\",\"200g\",\"200g*30\",\"200g*40\",\"200只/箱\",\"20包\",\"218ml*12\",\"22.7kg\",\"240ml*12\",\"245ml*60\",\"250g\",\"250ml*24\",\"25kg\",\"25枚\",\"288g*20\",\"2kg\",\"3.5kg\",\"300g\",\"300g*20\",\"300g*32\",\"30块\",\"320g\",\"335g*36\",\"350g\",\"360g\",\"380g*24\",\"397g*24\",\"3kg\",\"3kg*4\",\"4.5kg\",\"400g\",\"400g*15\",\"400g*40\",\"400g*50\",\"400ml*40包\",\"410g*12\",\"420g*24\",\"435g\",\"440g*12\",\"450g\",\"450g*40\",\"450ml*12\",\"500g\",\"500g*12\",\"500g*20\",\"500g*24\",\"500g*40\",\"500g*40包\",\"500ml*12\",\"50kg\",\"50块\",\"5kg\",\"5L*4桶\",\"600ml*12\",\"630ml*12\",\"640ml*12\",\"7.5kg\",\"750g*20\",\"750ml\",\"75g\",\"7kg\",\"8.5kg\",\"80g\",\"850g*12\",\"85g\",\"8包\",\"9.5kg\",\"907g*12\",\"9kg\",\"包\",\"只\",\"支\",\"整个\",\"整只\",\"整箱\",\"斤\",\"盒\",\"箱\",\"袋\"]";

        List<String> needAddSpecValueList = JSON.parseArray(needAddSpecValue, String.class);

        List<String> existedSpecValueList = JSON.parseArray(existedSpecValue, String.class);

        needAddSpecValueList.removeIf(existedSpecValueList::contains);

        System.out.println(JSON.toJSONString(needAddSpecValueList));

    }

    @Test
    public void testBubbleSort() {
        String n1 = "70,23,1,45,1000,975,7,89,34,45";
        System.out.println(solution(n1));
    }


    /**
     * @param n1:String
     * @return:String
     */
    public String solution(String n1){
        if(n1 == null || n1.length()==0){
            return "0";
        }
        int max = 0;
        // 第二大的数

        String[] arr = n1.split(",");
        sort(arr);
        return arr[1];
    }

    public void sort(String []arr){

        for(int i = 0;i<arr.length;i++){

            for(int j = 1;j<arr.length;j++){
                int current = Integer.parseInt(arr[j-1]);
                int currentLast = Integer.parseInt(arr[j]);
                if(current < currentLast){
                    swap(arr,j-1,j);
                }

            }

        }
    }

    public void swap(String arr[],int source,int target){
        String temp = arr[source];
        arr[source] = arr[target];
        arr[target] = temp;
    }

    /**
     * 冒泡排序
     *
     * @param nums
     */
    private void bubbleSort(int[] nums) {
        if (nums.length == 0) {
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < nums.length - 1; j++) {
                if (nums[j] > nums[j + 1]) {
                    int temp = nums[j + 1];
                    nums[j + 1] = nums[j];
                    nums[j] = temp;
                }
            }
        }
    }






}
