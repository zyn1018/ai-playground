package com.yinan.play.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BubbleSortTests {

    @Test
    public void contextLoads() {
    }

    @Test
    public void testBubbleSort() {
        int[] nums = {1, 2, 3, 4, 5, 6};
        System.out.println(Arrays.toString(nums));
        bubbleSort(nums);
        System.out.println(Arrays.toString(nums));
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
