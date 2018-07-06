package org.windwant.algorithm.sort;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 堆排序
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
        System.out.println("after: " + Arrays.toString(heapSort(arr)));
    }

    /**
     * 构建n次堆，每次取第一个值保存，然后构建余下元素数组
     * @param arr
     */
    public static int[] heapSort(int[] arr){
        int[] temp = new int[arr.length];
        int lgn = arr.length;
        for (int i = 0; i < lgn; i++) {
            maxHeapify(arr);//从大到小排序
            //minHeapify(arr);//从小到大排序
            temp[i] = arr[0];
            arr = Arrays.copyOfRange(arr, 1, arr.length);
        }
        return temp;
    }

    /**
     * 构建小顶堆
     * @param arr
     * @param arr
     */
    public static void minHeapify(int[] arr){
        //假定当前数组构成堆，则需要从最高堆高度位置的父节点（以此向前至首位置）开始调整，
        //从最后一个叶子节点的父节点开始调整，size = arr.length; 末节点 arr[size - 1], 父节点 arr[(size >>> 1)-1],
        //为什么不使用 (size - 1)>>1，因为反向计算子节点时，child = (i<<1) + 1可能>=size,
        for(int i = ((arr.length >>> 1) - 1); i>=0; i--){ //
            siftDownComparable(i, arr[i], arr);
        }
    }

    /**
     * 下移大值
     * @param i
     * @param key
     * @param arr
     */
    public static void siftDownComparable(int i, int key, int[] arr){
        int half = arr.length>>>1;
        while (i < half){
            int child = (i<<1) + 1;//计算左子节点位置
            int right = child + 1;//计算右子节点位置 可能不存在
            int value = arr[child];//保存左子节点值
            if(right < arr.length && arr[child] > arr[right]){ //判断较小的子节点
                value = arr[child = right];
            }
            if(key <= value){//判断和子节点较小值的大小，小于则直接退出
                break;
            }
            arr[i] = value;//大于子节点值，则将较小的子节点赋值当前位置，
            i = child;//将较小子节点位置当作当前位置，继续循环
        }
        arr[i] = key;//将key放到最终的位置
    }

    /**
     * 构建大顶堆
     * @param arr
     * @param arr
     */
    public static void maxHeapify(int[] arr){
        //假定当前数组构成堆，则需要从最高堆高度位置的父节点（以此向前至首位置）开始调整，
        //从最后一个叶子节点的父节点开始调整，size = arr.length; 末节点 arr[size - 1], 父节点 arr[(size >>> 1)-1],
        //为什么不使用 (size - 1)>>1，因为反向计算子节点时，child = (i<<1) + 1可能>=size,
        for(int i = arr.length - 1; i>=0; i--){ //
            siftUpComparable(i, arr[i], arr);
        }
    }
    private static void siftUpComparable(int k, int x, int[] arr) {
        while (k > 0) {
            int parent = (k - 1) >>> 1;
            int e = arr[parent];
            if (x <= e)
                break;
            arr[k] = e;
            k = parent;
        }
        arr[k] = x;
    }
}
