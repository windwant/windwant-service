package org.windwant.algorithm.sort;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 希尔排序
 * 插入排序的改进 不断缩小排序间隔 减少移动项消耗
 * Created by windwant on 2016/12/6.
 */
public class ShellSort {

    public static void shellSort(int[] arr){
        int step = arr.length/2;
        while (step >= 1) { //步长为1时 包含所有数组序列
            for (int i = 0; i < step; i++) { //步长为n则数组将分为n组分别排序
                for (int j = step + i; j < arr.length; j += step) { //对跨步长每组元素进行插入排序
                    int temp = arr[j];
                    int preIndex = j - step;
                    while (preIndex > -1 && temp < arr[preIndex]) {
                        arr[preIndex + step] = arr[preIndex];
                        preIndex -= step;
                    }
                    arr[preIndex + step] = temp;
                    System.out.println(" middle: " + Arrays.toString(arr));
                }
            }
            step /= 2; //每次步长处理
        }
    }

    public static void main(String[] args) {
        System.out.println("Hello ShellSort");
        int[] arr = new int[20];
        for (int i = 0; i < 20; i++) {
            arr[i] = ThreadLocalRandom.current().nextInt(100);
        }
        System.out.println("before: " + Arrays.toString(arr));
        shellSort(arr);
        System.out.println(" after: " + Arrays.toString(arr));
    }
}
