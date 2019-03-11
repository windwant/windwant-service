package org.windwant.algorithm.sort.selection;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 每次找出剩下集合中最小的值 交换位置
 *
 * 降序和升序
 *
 * 外层需要遍历 n -  1 次 对应的每次内层遍历次数 n - 1 n - 2 ... 1
 * 总的次数 (1 + n)*(n -1）/2) = n^2/2 - 1/2 所以时间复杂度为O(n^2)
 *
 * Created by windwant on 2016/12/5.
 */
public class SelectionSort {

    public static void SelectionSortAsc(int[] arr){
        int min = 0;
        for (int i = 0; i < arr.length - 1; i++) {
            min = i;
            for (int j = i + 1; j < arr.length ; j++) {
                if(arr[j] < arr[min]){
                    min = j;
                }
            }
            if(min != i){
                int tmp = arr[i];
                arr[i] = arr[min];
                arr[min] = tmp;
            }
        }
    }

    public static void SelectionSortDesc(int[] arr){
        int max = 0;
        for (int i = 0; i < arr.length - 1; i++) {
            max = i;
            for (int j = i + 1; j < arr.length ; j++) {
                if(arr[j] > arr[max]){
                    max = j;
                }
            }
            if(max != i){
                int tmp = arr[i];
                arr[i] = arr[max];
                arr[max] = tmp;
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
        SelectionSortDesc(arr);
        System.out.println("DESC after: " + Arrays.toString(arr));
        SelectionSortAsc(arr);
        System.out.println("ASC after: " + Arrays.toString(arr));
    }
}
