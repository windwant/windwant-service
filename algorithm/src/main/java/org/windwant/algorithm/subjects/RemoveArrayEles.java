package org.windwant.algorithm.subjects;

import java.util.*;

/**
 * 给定一个排序数组，你需要在原地删除重复出现的元素，使得每个元素只出现一次，返回移除后数组的新长度。

 * 不要使用额外的数组空间，你必须在原地修改输入数组并在使用 O(1) 额外空间的条件下完成。

 * Created by Administrator on 19-3-5.
 */
public class RemoveArrayEles {

    public static int removeDuplicates(int[] nums) {
        if(nums == null || nums.length == 0) return 0;
        if(nums.length == 1) return 1;

        int index = 0;
        int lgn = nums.length;
        while (index < lgn){
            if(Arrays.binarySearch(nums, index + 1, lgn, nums[index]) > -1){
                for (int i = index; i < lgn - 1 ; i++) {
                    nums[i] = nums[i + 1];
                }
                nums[lgn - 1] = Integer.MIN_VALUE;
                lgn--;
            }else {
                index++;
            }
        }
        return lgn;
    }

    public static int removeDuplicates1(int[] nums) {
        if(nums == null || nums.length == 0) return 0;
        if(nums.length == 1) return 1;

        int index = 0;
        int lgn = nums.length;
        Set<Integer> sets = new LinkedHashSet<>();
        while (index < lgn){
            if(sets.contains(nums[index])) {
                index++;
                continue;
            }
            sets.add(nums[index]);
            index++;
        }
        Iterator<Integer> it = sets.iterator();
        index = 0;
        while (it.hasNext()){
            nums[index] = it.next();
            index++;
        }
        return sets.size();
    }

    public static int removeElement(int[] nums, int val) {
        if(nums == null || nums.length == 0) return 0;

        int index = 0;
        int lgn = nums.length;
        while (index < lgn){
            if(nums[index] == val){
                for (int i = index; i < lgn - 1 ; i++) {
                    nums[i] = nums[i + 1];
                }
                nums[lgn - 1] = Integer.MIN_VALUE;
                lgn--;
            }else {
                index++;
            }
        }
        return lgn;
    }

    public static void main(String[] args) {
        int[] nums = {0,0,1,1,1,2,2,3,3,4};
//        System.out.println(removeDuplicates(nums));
//        nums = new int[]{1, 1, 2};
//        System.out.println(removeDuplicates(nums));
//        nums = new int[]{-3,-1,0,0,0,3,3};
//        System.out.println(removeDuplicates1(nums));
//        nums = new int[]{3,2,2,3};
//        System.out.println(removeElement(nums, 3));
        nums = new int[]{1};
        System.out.println(removeElement(nums, 1));
    }
}
