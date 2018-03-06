package org.windwant.algorithm.sort;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 将剩余未排序的逐个插入到已排序的集合中
 * Created by windwant on 2016/12/5.
 */
public class InsertionSort {

    public static void insertionSort(int [] array){
        for(int i = 1; i < array.length; i++){
            int temp = array[i];//被标记的值或者说是当前需要插入的值
            int j = i;
            //如果轮循值大于被标记值则往后移
            while( j > 0 && temp < array[j - 1]){
                array[j] = array[j - 1];
                j-- ;
            }
            //将被标记值插入最终移出的空位置
            array[j] = temp;
        }
    }

    public static void main(String[] args) {
        System.out.println("Hello InsertionSort");
        int[] arr = new int[20];
        for (int i = 0; i < 20; i++) {
            arr[i] = ThreadLocalRandom.current().nextInt(100);
        }
        System.out.println("before: " + Arrays.toString(arr));
        insertionSort(arr);
        System.out.println(" after: " + Arrays.toString(arr));
    }

    public static void insertionSortDeprected(int[] arr){
        int max = arr.length - 1;
        int j = 0;//先选取最左边一个元素为最小有序数列
        while (j < max){ //当序列大小等于length - 1时完成
            //每次将数列最后一个元素与最小序列元素比较
            boolean need = false; //最小序列是否增加
            for (int i = 0; i < j + 1; i++) {
                if (arr[max] >= arr[i]) {
                    if(i == j){ //当大于最小序列最大值时处理替换
                        int temp = arr[i + 1];
                        arr[i + 1] = arr[max];
                        arr[max] = temp;
                        need = true; //当数列最后一个值比较最小序列最大值后 大于并替换序列后一个元素值是，最小序列长度+1
                        System.out.println(" times: " + Arrays.toString(arr));
                    }else {
                        continue;
                    }
                }else
                { //当大于前一个值，小于当前值时，替换位置
                    int temp = arr[i];
                    arr[i] = arr[max];
                    arr[max] = temp;
                    System.out.println(" times: " + Arrays.toString(arr));
                }
            }
            if(need) {
                j++;
            }
        }
    }
}
