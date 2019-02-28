package org.windwant.algorithm.subjects;

import java.util.Arrays;

/**
 * 给定一个非负整数数组，你最初位于数组的第一个位置。
 * 数组中的每个元素代表你在该位置可以跳跃的最大长度。
 * 你的目标是使用最少的跳跃次数到达数组的最后一个位置。
 *
 * 注意：假设你总是可以到达数组的最后一个位置。
 * Created by Administrator on 19-2-27.
 */
public class ArrayJump {
    /**
     * 枚举遍历
     * @param nums
     * @return
     */
    public static int jump(int[] nums) {
        if(nums == null || nums.length == 1) return 0;
        if(nums.length == 2) return 1;

        int steps = Integer.MAX_VALUE/2;
        int initStep = 1;
        if(nums[0] >= nums.length - 1) return 1;
        if(nums[0] == 0) return steps;

        while (initStep <= nums[0]){
            int subNeedStep = jump(Arrays.copyOfRange(nums, initStep, nums.length));
            if(subNeedStep + 1 < steps){
                steps = subNeedStep + 1;
            }
            initStep++;
        }
        return steps;
    }

    /**
     * 每次选择 和下一跳（最大跳值）之和最远的=》递归处理
     * @param nums
     * @return
     */
    public static int jump2(int[] nums) {
        if(nums == null || nums.length == 1) return 0;
        if(nums.length == 2) return 1;

        int steps = Integer.MAX_VALUE/2;
        int initStep = nums[0];
        if(nums[0] >= nums.length - 1) return 1;
        if(nums[0] == 0) return steps;

        int maxSum = 0;
        int fromStep = 1;
        while (initStep > 0){
            if(initStep + nums[initStep] > maxSum){
                fromStep = initStep;
                maxSum = initStep + nums[initStep];
            }
            initStep--;
        }
        steps = 1 + jump2(Arrays.copyOfRange(nums, fromStep, nums.length));
        return steps;
    }

    public static void main(String[] args) {
//        int[] nums = {2,3,1,1,4};
//        int[] nums = {2,3,0,1,4};
//        int[] nums = {0};
//        int[] nums = {3,2,1};
//        int[] nums = {2,3,1};
//        int[] nums = {1,1,1,1};
//        int[] nums = {1,1,2,1,1};
//        int[] nums = {2,0,2,0,1};
//        int[] nums = {5,9,3,2,1,0,2,3,3,1,0,0};
        int[] nums = {5,6,4,4,6,9,4,4,7,4,4,8,2,6,8,1,5,9,6,5,2,7,9,7,9,6,9,4,1,6,8,8,4,4,2,0,3,8,5};
        long t = System.nanoTime();
//        System.out.println(jump(nums));
//        System.out.println(System.nanoTime() - t);
//
//        t = System.nanoTime();
        System.out.println(jump2(nums));
        System.out.println(System.nanoTime() - t);
    }
}
