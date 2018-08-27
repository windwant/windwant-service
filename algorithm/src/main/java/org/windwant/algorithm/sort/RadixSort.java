package org.windwant.algorithm.sort;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 基数排序
 * 十个桶
 * 从个位开始，每个数字个位数字对应每个桶数字存放
 * 收集分配，排序好的数字
 * 继续处理
 * 十位
 * 百位
 * ...
 * 重复
 * Created by windwant on 2016/12/6.
 */
public class RadixSort {

    //LSD
    public static void radixLSDSort(int[] arr){
        //最高位数
        int maxBit = getMaxBit(arr);
        //十个bulk 分别存放 每个位数 数字 相应的元素 如个位为3 则放入bulk[3]
        Queue<Integer>[] bulk = new Queue[10];
        for (int i = 0; i < bulk.length; i++) {
            bulk[i] = new ArrayDeque();
        }
        //分别处理不同位数 存放 处理
        for (int i = 0; i < maxBit; i++) {
            for (int j = 0; j < arr.length; j++) {
                int ivalue = (int) (arr[j] % Math.pow(10, i + 1) / Math.pow(10, i)); //去相应位数上的数字作为 存入bulk index
                bulk[ivalue].offer(arr[j]);
            }

            //取出bulk中的元素 重新放入数组 并清除bulk中的元素。
            int arrIndex = 0;
            for (int j = 0; j < bulk.length; j++) {
                while (bulk[j].size()>0){
                    arr[arrIndex++] = bulk[j].poll();
                }
            }
        }
    }

    public static int getMaxBit(int[] arr){
        int max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if(arr[i] > max){
                max = arr[i];
            }
        }

        int b = 0;
        while (max > 0){
            max /= 10;
            b++;
        }
        return b;
    }

    public static void radixMSDSort(int[] arr){
        msdSort(arr, 0, arr.length, getMaxBit(arr));
    }

    //MSD
    public static void msdSort(int[] arr, int left, int right, int maxBit){
        //十个bulk 分别存放 每个位数 数字 相应的元素 如个位为3 则放入bulk[3]
        Queue<Integer>[] bulk = new Queue[10];
        for (int i = 0; i < bulk.length; i++) {
            bulk[i] = new ArrayDeque();
        }
        //依据最高位分别放入不同的bulk
        for (int j = left; j < right; j++) {
            int ivalue = (int) (arr[j] % Math.pow(10, maxBit) / Math.pow(10, maxBit - 1)); //去相应位数上的数字作为 存入bulk index
            bulk[ivalue].offer(arr[j]);
        }

        //取出bulk中的元素 重新放入数组 并清除bulk中的元素。记录bulk中queue大小大于1的数组索引 递归使用
        List<Integer> count = new ArrayList<Integer>();
        int arrIndex = left;
        for (int j = 0; j < bulk.length; j++) {
            int start = arrIndex;
            while (bulk[j].size()>0){
                arr[arrIndex++] = bulk[j].poll();
            }
            if(arrIndex - start > 1){
                count.add(start);
                count.add(arrIndex);
            }
        }
        //递归最小位判断
        int nextBit = maxBit - 1;
        if(nextBit > 0) {
            for (int i = 1; i < count.size(); i += 2) {
                msdSort(arr, count.get(i - 1), count.get(i), nextBit);
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("Hello RadixSort");
        int[] lsdArr = new int[20];
        for (int i = 0; i < 20; i++) {
            lsdArr[i] = ThreadLocalRandom.current().nextInt(100);
        }
        int[] msdArr = Arrays.copyOf(lsdArr, lsdArr.length);
        System.out.println("LSD before: " + Arrays.toString(lsdArr));
        radixLSDSort(lsdArr);
        System.out.println("LSD  after: " + Arrays.toString(lsdArr));
        System.out.println("MSD before: " + Arrays.toString(msdArr));
        radixMSDSort(msdArr);
        System.out.println("MSD  after: " + Arrays.toString(msdArr));
    }
}
