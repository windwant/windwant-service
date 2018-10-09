package org.windwant.algorithm.sort;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 两两对比相邻的
 * Created by windwant on 2016/12/5.
 */
public class BubbleSort {

    /**
     * 大数后移 升序排序
     * @param arr
     */
    public static void bubbleSortAsc(int[] arr){
        int lgn = arr.length;
        for (int i = 0; i < lgn - 1; i++) {
            for (int j = 0; j < lgn - 1 - i; j++) {
                if(arr[j] > arr[j + 1]){
                    int temp = arr[j + 1];
                    arr[j + 1] = arr[j];
                    arr[j] = temp;
                }
            }
        }
    }

    /**
     * 大数前移 降序排序
     * @param arr
     */
    public static void bubbleSortDesc(int[] arr){
        int lgn = arr.length;
        for (int i = 0; i < lgn; i++) {
            for (int j = i; j < arr.length; j++) {
                if(arr[j] > arr[i]){
                    int tmp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = tmp;
                }
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("Hello BubbleSort");
        int[] arr = new int[20];
        for (int i = 0; i < 20; i++) {
            arr[i] = ThreadLocalRandom.current().nextInt(100);
        }
        System.out.println("before: " + Arrays.toString(arr));
        bubbleSortAsc(arr);
        System.out.println("ASC: " + Arrays.toString(arr));
        bubbleSortDesc(arr);
        System.out.println("DESC: " + Arrays.toString(arr));
    }
}
