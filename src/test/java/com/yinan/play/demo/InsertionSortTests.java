package com.yinan.play.demo;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.yinan.play.demo.meta.GoodsBasic;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InsertionSortTests {

    @Test
    public void contextLoads() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        // 获取Class对象的三种方式
        System.out.println("根据类名:  \t" + GoodsBasic.class);
        System.out.println("根据对象:  \t" + new GoodsBasic().getClass());
        Class goodsClass = Class.forName("com.yinan.play.demo.meta.GoodsBasic");
        System.out.println("根据全限定类名:\t" + goodsClass);
        // 常用的方法
        System.out.println("获取全限定类名:\t" + goodsClass.getName());
        System.out.println("获取类名:\t" + goodsClass.getSimpleName());
        System.out.println("实例化:\t" + goodsClass.newInstance());
    }

    public static void main(String[] args) {
        String[] nums="70,23,1,45,1000,975,7,89,34,45".split(",");
        List<Integer> list=Arrays.stream(nums).map(Integer::parseInt).sorted().collect(Collectors.toList());
        System.out.println(String.valueOf(list.get(list.size() - 2)));
    }

    /**
     * 冒泡排序
     *
     * @param nums
     */
    private void insertionSort(int[] nums) {
        if (nums.length == 0) {
            return;
        }
        for (int i = 1; i < nums.length; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[j] > nums[i]) {
                    int temp = nums[j];
                    nums[j] = nums[i];
                    nums[i] = temp;
                }
            }
        }
    }
}
