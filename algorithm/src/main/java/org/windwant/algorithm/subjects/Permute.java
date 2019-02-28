package org.windwant.algorithm.subjects;

import java.util.*;

/**
 * 1. 给定一个没有重复数字的序列，返回其所有可能的全排列。
 * 2. 给定一个可包含重复数字的序列，返回所有不重复的全排列。
 * Created by Administrator on 19-2-27.
 */
public class Permute {

    /**
     * 递归处理
     * @param nums
     * @return
     */
    public static List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList();
        if(nums.length == 1) {
            result.add(new ArrayList(){{add(nums[0]);}});
            return result;
        }
        for (int num : nums) {
            int[] tmp = new int[nums.length - 1];
            int index = 0;
            for (int i = 0; i < nums.length; i++) {
                if(num == nums[i]) continue;
                tmp[index] = nums[i];
                index++;
            }
            List<List<Integer>> sub = permute(tmp);
            sub.stream().forEach(item->item.add(num));
            result.addAll(sub);
        }
        return result;
    }

    /**
     * 递归处理
     * Set 处理重复
     * @param nums
     * @return
     */
    public static List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> result = new ArrayList();
        if(nums.length == 1) {
            result.add(new ArrayList(){{add(nums[0]);}});
            return result;
        }
        for (int num : nums) {
            int[] tmp = new int[nums.length - 1];
            int index = 0;
            for (int i = 0; i < nums.length; i++) {
                if(num == nums[i] && index == i) continue;
                tmp[index] = nums[i];
                index++;
            }
            List<List<Integer>> sub = permuteUnique(tmp);
            sub.stream().forEach(item->item.add(num));
            result.addAll(sub);
        }
        Set<List<Integer>> sets = new HashSet();
        result.stream().forEach(item->sets.add(item));
        return new ArrayList(sets);
    }

    public static void main(String[] args) {
        int[] nums = {1,2,3};
        List<List<Integer>> result = permute(nums);
        for (List<Integer> list : result) {
            System.out.println(Arrays.toString(list.toArray()));
        }

        nums = new int[]{1,1,2};
        result = permuteUnique(nums);
        for (List<Integer> list : result) {
            System.out.println(Arrays.toString(list.toArray()));
        }
    }
}
