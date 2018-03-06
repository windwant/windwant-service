package org.windwant.algorithm.sort;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by windwant on 2016/12/6.
 */
public class HeapSort {

    public static void main(String[] args) {
        System.out.println("Hello HeapSort");
        int[] arr = new int[20];
        for (int i = 0; i < 20; i++) {
            arr[i] = ThreadLocalRandom.current().nextInt(100);
        }
        System.out.println("before: " + Arrays.toString(arr));
        heapSort(arr);
        System.out.println(" after: " + Arrays.toString(arr));
    }

    public static void heapSort(int[] arr){

    }
}
