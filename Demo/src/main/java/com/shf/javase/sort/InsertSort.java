package com.shf.javase.sort;

import java.util.Arrays;

/**
 * 插入排序  未排序区、已排序区
 *  将数组分为两个区域，排序区域是未排序区域，每一轮从未排序区域中取出第一个元素，插入到排序区域（需保证顺序），
 *  重复上述步骤，直到整个数组有序
 *
 *  优化方式：
 *  1.待插入元素进行比较时，遇到比自己小的元素，就代表找到了插入位置，无需进行后序比较
 *  2.插入时可以直接移动元素，而不是交换元素
 */
public class InsertSort {
    public static void main(String[] args) {
        int[] a = {9, 3, 7, 2, 5, 8, 1, 4};
        insert(a);
        System.out.println(Arrays.toString(a));
    }

    private static void insert(int[] a) {
        for (int i = 1; i < a.length; i++) { // i代表待插入元素的索引
            int t = a[i]; // 代表待插入元素的值
            int j = i - 1;  // 代表已排序区域元素引用
            while (j >= 0) {
                if (t < a[j]) { // 后移
                    a[j + 1] = a[j];
                } else { // 待插入元素大于有序区域的值，退出循环，减少比较次数
                    break;
                }
                j--;
            }
            a[j + 1] = t; // 填补空出来的位置
            System.out.println(Arrays.toString(a));
        }
    }
}
