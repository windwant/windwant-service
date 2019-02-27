package org.windwant.algorithm.subjects;

/**
 * 给定 n 个非负整数 a1，a2，...，an，每个数代表坐标中的一个点 (i, ai) 。在坐标内画 n 条垂直线，垂直线 i 的两个端点分别为 (i, ai) 和 (i, 0)。
 * 找出其中的两条线，使得它们与 x 轴共同构成的容器可以容纳最多的水。
 * 说明：你不能倾斜容器，且 n 的值至少为 2。
 * Created by Administrator on 19-2-25.
 */
public class MaxAreaOfArray {

    public static int maxArea(int[] height) {
        int area = 0, lgn = height.length;
        if(lgn < 2) return 0;

        for (int i = 0; i < lgn; i++) {
            for (int i1 = i + 1; i1 < lgn; i1++) {
                int tmpArea = (height[i]>height[i1]?height[i1]:height[i]) * (i1 - i);
                if(tmpArea > area){
                    area = tmpArea;
                }
            }
        }
        return area;
    }

    public static int maxArea2(int[] height) {
        int area = 0, lgn = height.length;
        if(lgn < 2) return 0;

        for (int i = 0, j = lgn - 1; i < j; ) {
            int tmpArea = (height[i]>height[j]?height[j]:height[i]) * Math.abs(j - i);
            if(tmpArea > area){
                area = tmpArea;
                i++;//正方向前进一步，避免反方向遍历时，重复比较
            }else {
                j--;//反方向前进一步，避免正方向遍历时，重复比较
            }
        }
        return area;
    }

    public static void main(String[] args) {
        int[] arr = {1,8,6,2,5,4,8,3,7};
        long t = System.nanoTime();
        System.out.println(MaxAreaOfArray.maxArea(arr));
        System.out.println("maxArea use time: " + (System.nanoTime() - t));
        t = System.nanoTime();
        System.out.println(MaxAreaOfArray.maxArea2(arr));
        System.out.println("maxArea: use time: " + (System.nanoTime() - t));
    }
}
