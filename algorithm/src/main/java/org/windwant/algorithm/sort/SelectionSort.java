package org.windwant.algorithm.sort;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 每次找出剩下集合中最小的值 交换位置
 * Created by windwant on 2016/12/5.
 */
public class SelectionSort {

    public static void SelectionSort(int[] arr){
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i + 1; j < arr.length ; j++) {
                if(arr[j] < arr[i]){
                    int temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("Hello SelectionSort");
        int[] arr = new int[20];
        for (int i = 0; i < 20; i++) {
            arr[i] = ThreadLocalRandom.current().nextInt(100);
        }
        System.out.println("before: " + Arrays.toString(arr));
        SelectionSort(arr);
        System.out.println(" after: " + Arrays.toString(arr));
    }
}
