package org.windwant.algorithm.sort;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 不稳定的排序算法 =交换步骤
 * 快速排序法
 * Created by windwant on 2016/12/5.
 */
public class QuickSort {

    public static void quikeSort(int[] arr, int low, int high) {
        int start = low;
        int end = high;
        int anchor = arr[low];

        while (end > start) {
            //比较 anchor---end
            while (end > start && arr[end] >= anchor) {  //从末尾向前寻找小于等于anchor的值
                end--;
            }

            //交换位置
            if (arr[end] <= anchor) {
                int temp = arr[end];
                arr[end] = arr[start];
                arr[start] = temp;
            }

            //比较start---anchor
            while (end > start && arr[start] <= anchor) {//从起始位置向后寻找大于等于anchor的值
                start++;
            }

            //交换位置
            if (arr[start] >= anchor) {
                int temp = arr[start];
                arr[start] = arr[end];
                arr[end] = temp;
            }
        }
        //anchor位置确定。左边的元素值都小于anchor值，右边的值都大于anchor值，递归排序左右两侧排序
        //左边元素。low---anchor值索引-1
        if (start > low) {
            quikeSort(arr, low, start - 1);
        }

        //右边元素。从anchor值索引+1---high
        if (end < high) {
            quikeSort(arr, end + 1, high);
        }
    }

    public static void main(String[] args) {
        System.out.println("Hello QuickSort");
        int[] arr = new int[20];
        for (int i = 0; i < 20; i++) {
            arr[i] = ThreadLocalRandom.current().nextInt(100);
        }
        System.out.println("before: " + Arrays.toString(arr));
        quikeSort(arr, 0, arr.length - 1);
        System.out.println(" after: " + Arrays.toString(arr));
    }
}