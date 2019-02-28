package org.windwant.algorithm.subjects;

import java.util.Arrays;

/**
 * 给定 n 个非负整数表示每个宽度为 1 的柱子的高度图，计算按此排列的柱子，下雨之后能接多少雨水。
 *
 * Created by Administrator on 19-2-28.
 */
public class RainWaterArea {
    /**
     * 分割
     * @param height
     * @return
     */
    public static int trap(int[] height) {
        //最大索引位置
        int maxIndex = findMax(height);

        int lsubMaxindex = maxIndex, rsubMaxIndex = maxIndex;
        int area = 0;

        //左边处理
        while (lsubMaxindex > 0){
            int tmpMax = lsubMaxindex;
            lsubMaxindex = findMax(Arrays.copyOfRange(height, 0, tmpMax));
            area += height[lsubMaxindex] * (tmpMax - lsubMaxindex - 1);
            for (int i = lsubMaxindex + 1; i < tmpMax; i++) {
                area -= height[i] * 1;
            }
        }

        //右边处理
        while (rsubMaxIndex < height.length - 1){
            int tmpMax = rsubMaxIndex;
            rsubMaxIndex = tmpMax + findMax(Arrays.copyOfRange(height, tmpMax + 1, height.length)) + 1;
            area += height[rsubMaxIndex] * (rsubMaxIndex - tmpMax - 1);
            for (int i = tmpMax + 1; i < rsubMaxIndex; i++) {
                area -= height[i] * 1;
            }
        }

        return area;
    }

    public static int findMax(int[] nums){
        int max = 0, maxIndex = 0;
        for (int i = 0; i < nums.length; i++) {
            if(nums[i] > max){
                max = nums[i];
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    public static void main(String[] args) {
        int[] nums = {0,1,0,2,1,0,1,3,2,1,2,1};
        System.out.println(trap(nums));
    }
}
