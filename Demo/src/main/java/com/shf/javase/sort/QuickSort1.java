package com.shf.javase.sort;

import java.util.Arrays;

/**
 * 快速排序
 * 每一轮排序选择一个基准点（pivot）进行分区
 * 让小于基准点的元素的进入一个分区，大于基准点的元素的进入另一个分区
 * 当分区完成时，基准点元素的位置就是其最终位置
 * 在子分区内重复以上过程，直至子分区元素个数少于等于 1，这体现的是分而治之的思想 （divide-and-conquer）
 * 从以上描述可以看出，一个关键在于分区算法，常见的有洛穆托分区方案、双边循环分区方案、霍尔分区方案
 *
 *
 * 选择最右元素作为基准点元素
 *
 * j 指针负责找到比基准点小的元素，一旦找到则与 i 进行交换
 *
 * i 指针维护小于基准点元素的边界，也是每次交换的目标索引
 *
 * 最后基准点与 i 交换，i 即为分区位置
 */
public class QuickSort1 {
    public static void main(String[] args) {
        int[] a = {5, 3, 7, 2, 9, 8, 1, 4};
        quick(a, 0, a.length - 1);
    }

    public static void quick(int[] a,int l ,int h){
        if (l>=h){
            return;
        }
        int p = partition(a, l, h); // 索引值p
        quick(a, l, p - 1); // 左边分区的范围确定
        quick(a, p + 1, h); // 右边分区的范围确定
    }

    private static int partition(int[] a, int l, int h) {
        int pv = a[h]; // 基准点元素(选择最右侧作为基准点元素)
        int i = l; // i指针维护小于基准点元素的边界，也是每次交换的目标索引
        for (int j = l; j < h; j++) { // j指针负责找到比基准点小的元素，一旦找到则与i进行交换
            if (a[j] < pv) {
                if (i!=j){
                    swap(a, i, j);
                }
                i++;
            }
        }
        if (i!=h){
            swap(a, h, i); // 最后基准点与i交换，i即为分区位置
        }
        System.out.println(Arrays.toString(a) + " i = " + i);
//        返回值代表了基准点元素所在的正确索引，用它确定下一轮分区的边界
        return i;
    }

    public static void swap(int[] a, int i, int j) {
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
    }
}
