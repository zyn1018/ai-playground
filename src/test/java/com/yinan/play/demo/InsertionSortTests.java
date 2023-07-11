package com.yinan.play.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InsertionSortTests {

    @Test
    public void contextLoads() {
    }

    @Test
    public void testBubbleSort() {
        int[] nums = {1,3,4,2,5,6};
        System.out.println(Arrays.toString(nums));
        insertionSort(nums);
        System.out.println(Arrays.toString(nums));
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
