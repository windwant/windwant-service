package org.windwant.algorithm.subjects;


import java.util.HashMap;
import java.util.Map;

/**
 * 给定一个未排序的整数数组，找出其中没有出现的最小的正整数。
 * 你的算法的时间复杂度应为O(n)，并且只能使用常数级别的空间。
 *
 * Created by Administrator on 19-2-28.
 */
public class FirstMissingPositive {

    /**
     * 数组操作
     * @param nums
     * @return
     */
    public static int firstMissingPositive(int[] nums) {
        int max = 0;
        for (int i = 0; i < nums.length; i++) {
            if(nums[i] < 0) continue;
            if(nums[i] > max){
                max = nums[i];
            }
        }

        max = max == Integer.MAX_VALUE?max:max + 2;
        for (int i = 1; i < max; i++) {
            if(contains(nums, i)) continue;
            return i;
        }
        return max + 1;
    }

    public static boolean contains(int[] nums, int ele){
        for (int i = 0; i < nums.length; i++) {
            if(nums[i] == ele) return true;
        }
        return false;
    }

    /**
     * map操作
     * @param nums
     * @return
     */
    public static int firstMissingPositive1(int[] nums) {
        Map<Integer, Integer> vs = new HashMap<>();
        int max = 0;
        for (int i = 0; i < nums.length; i++) {
            if(nums[i] < 0) continue;
            if(nums[i] > max){
                max = nums[i];
            }
            vs.put(nums[i], i);
        }

        max = max == Integer.MAX_VALUE?max:max + 2;
        for (int i = 1; i < max; i++) {
            if(vs.get(i) != null) continue;
            return i;
        }
        return max + 1;
    }

    public static void main(String[] args) {
//        int[] nums = {1,2,0};
//        int[] nums = {3,4,-1,1};
//        int[] nums = {7,8,9,11,12};
        int[] nums = {2147483647};
        long t = System.nanoTime();
        System.out.println(firstMissingPositive(nums));
        System.out.println("1: " + (System.nanoTime() - t));
        t = System.nanoTime();
        System.out.println(firstMissingPositive1(nums));
        System.out.println("2: " + (System.nanoTime() - t));
    }
}
