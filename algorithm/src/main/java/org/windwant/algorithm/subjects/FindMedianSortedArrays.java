package org.windwant.algorithm.subjects;

/**
 * 给定两个大小为 m 和 n 的有序数组 nums1 和 nums2。
 * 请你找出这两个有序数组的中位数，并且要求算法的时间复杂度为 O(log(m + n))。
 * 你可以假设 nums1 和 nums2 不会同时为空。
 * Created by Administrator on 19-2-25.
 */
public class FindMedianSortedArrays {
    /**
     * 使用两个排序数据组的归并过程
     * 分别定义两个数组的遍历索引，每次对比提取相应数组的元素
     * 不实际存储归并后的数据，
     * 处理前半数元素即可
     * @param nums1
     * @param nums2
     * @return
     */
    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int lgn1 = nums1.length;
        int lgn2 = nums2.length;
        int allLgn = lgn1 + lgn2;
        int middleIndex = allLgn/2;

        int middleLeft = 0,middleRight = 0;

        int index1 = 0;
        int index2 = 0;
        int curr = 0;
        for (int i = 0; i < middleIndex + 1; i++) {
            if(index1 < lgn1 && index2 < lgn2) {
                if (nums1[index1] > nums2[index2]) {
                    curr = nums2[index2];
                    index2++;
                } else {
                    curr = nums1[index1];
                    index1++;
                }
            }else if(index1 < lgn1){
                curr = nums1[index1];
                index1++;
            }else if(index2 < lgn2){
                curr = nums2[index2];
                index2++;
            }
            if(i == middleIndex - 1){
                middleLeft = curr;
            }
            if(i == middleIndex){
                middleRight = curr;
            }
        }
        if(allLgn%2 == 0){
            return (middleLeft + middleRight)/2.0;
        }else {
            return middleRight;
        }
    }

    public static void main(String[] args) {
        int[] nums1 = {1, 3};
        int[] nums2 = {2};
        System.out.println(FindMedianSortedArrays.findMedianSortedArrays(nums1, nums2));
        nums1 = new int[]{1, 2};
        nums2 = new int[]{3, 4};
        System.out.println(FindMedianSortedArrays.findMedianSortedArrays(nums1, nums2));
    }
}
