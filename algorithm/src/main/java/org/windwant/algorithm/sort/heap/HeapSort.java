package org.windwant.algorithm.sort.heap;

public class HeapSort {

    /**
     * 最大堆维护
     *
     * @param arr 堆数组
     * @param i 维护元素下标
     * @param offSet 原址偏移量
     */
    public static void maxHeapify(int[] arr, int i, int offSet) {
        int size = arr.length - offSet; //堆大小
        int maxIndex = i; //记录当前节点及其子节点的最大值节点索引
        int left = 2 * i + 1; //左子节点索引
        int right = 2 * i + 2; //右子节点索引

        //对比节点及其左子节点
        if (left < size && arr[left] > arr[maxIndex]) {
            maxIndex = left;
        }

        //对比节点及其右子节点
        if (right < size && arr[right] > arr[maxIndex]) {
            maxIndex = right;
        }

        //不满足最大堆性质，则进行下沉节点i，递归处理
        if (maxIndex != i) {
            int tmp = arr[i];
            arr[i] = arr[maxIndex];
            arr[maxIndex] = tmp;
            //因为交换了子节点的值，则以子节点为根节点的子堆特性可能发生变化，需要维护
            maxHeapify(arr, maxIndex, offSet);
        }
    }

    /**
     * 构建最大堆
     *
     * @param arr
     */
    public static void buildHeap(int[] arr) {
        for (int i = (arr.length - 1) / 2; i >= 0; i--) {
            maxHeapify(arr, i, 0);
        }
    }

    /**
     * 交换最大值
     *
     * @param arr 堆数组
     * @param maxIndex 最大值元素待交换位置
     */
    public static void swapMax(int[] arr, int maxIndex) {
        int tmp = arr[maxIndex];
        arr[maxIndex] = arr[0];
        arr[0] = tmp;
    }

    /**
     * 堆排序
     *
     * @param arr
     */
    public static void heapSort(int[] arr) {
        buildHeap(arr); //构建堆
        swapMax(arr, arr.length - 1); //交换最大值
        for (int i = 0; i < arr.length - 2 ; i++) {
            maxHeapify(arr, 0, i + 1); //根节点堆维护 offset 偏移元素个数
            swapMax(arr, arr.length - 1 - (i + 1)); //交换最大值
        }
    }

    public static void main(String[] args) {
        int[] arr = new int[]{27,17,3,16,13,10,1,5,7,12,4,8,9,0};
        heapSort(arr);
        StringBuilder ab = new StringBuilder();
        for (int i : arr) {
            ab.append(i);
        }
        System.out.println(ab);
    }
}
