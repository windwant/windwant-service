package org.windwant.algorithm.subjects;

import java.util.Arrays;

/**
 * 给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。
 * 你可以假设每种输入只会对应一个答案。但是，你不能重复利用这个数组中同样的元素。
 * Created by Administrator on 19-2-25.
 */
public class TwoSum {
    /**
     * 暴力法
     * @param nums
     * @param target
     * @return
     */
    public static int[] twoSum(int[] nums, int target) {
        int lgn = nums.length;
        for(int i = 0; i < lgn; i++){
            for(int j = i + 1; j < lgn; j++){
                if(nums[i] + nums[j] == target){
                    return new int[]{i, j};
                }
            }
        }
        return new int[0];
    }

    public static void main(String[] args) {
        int[] nums = {2, 7, 11, 15};
        int target = 9;
        System.out.println(Arrays.toString(TwoSum.twoSum(nums, target)));
    }
}
