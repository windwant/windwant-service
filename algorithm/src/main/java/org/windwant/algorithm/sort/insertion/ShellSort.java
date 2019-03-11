package org.windwant.algorithm.sort.insertion;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 希尔排序
 * 插入排序的改进 不断缩小排序间隔 减少移动项消耗
 * 间隔使用希尔间隔 时间复杂度 n/2 n/4 ... n/2^n===1 = O(n^2)
 *
 * 使用不同的间隔方案，时间复杂度不同
 *
 * Frank & Lazarus, 和 	Hibbard, 时间复杂度 O(n^3/2)   Sedgewick O(n^4/3) O(n^1.3)
 *
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

    /**
     * 改进形式
     * @param arr
     */
    public static void shellSort1(int[] arr){
        int step = arr.length/2;
        while (step >= 1) { //步长为1时 包含所有数组序列
            for (int i = step; i < arr.length; i += step) { //对跨步长每组元素进行插入排序
                int temp = arr[i];
                int j = i;
                while (j > 0 && temp < arr[j - step]) {
                    arr[j] = arr[j - step];
                    j -= step;
                }
                arr[j] = temp;
            }
            System.out.println("step: " + step + " middle: " + Arrays.toString(arr));
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
        shellSort1(arr);
        System.out.println("after: " + Arrays.toString(arr));
    }
}
