package org.windwant.algorithm.subjects;

/**
 * 给定 n 个非负整数 a1，a2，...，an，每个数代表坐标中的一个点 (i, ai) 。在坐标内画 n 条垂直线，垂直线 i 的两个端点分别为 (i, ai) 和 (i, 0)。
 * 找出其中的两条线，使得它们与 x 轴共同构成的容器可以容纳最多的水。
 * 说明：你不能倾斜容器，且 n 的值至少为 2。
 * Created by Administrator on 19-2-25.
 */
public class MaxAreaOfArray {

    public int maxArea(int[] height) {
        int area = 0, lgn = height.length;
        if(lgn < 2) return 0;

        for (int i = 0; i < lgn; i++) {
            for (int i1 = i + 1; i1 < lgn; i1++) {
                int h = height[i]>height[i1]?height[i1]:height[i];
                if(h*(i1 - i) > area){
                    area = h*(i1 - i);
                }
            }
        }
        return area;
    }

    public static void main(String[] args) {
        int[] arr = {1,8,6,2,5,4,8,3,7};
        System.out.println(new MaxAreaOfArray().maxArea(arr));
    }
}
