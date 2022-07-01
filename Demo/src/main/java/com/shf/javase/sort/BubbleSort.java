package com.shf.javase.sort;

import java.util.Arrays;

/**
 * 冒泡排序
 * 依次比较数组中相邻两个元素大小，若 a[j] > a[j+1]，则交换两个元素，两两都比较一遍称为一轮冒泡，结果是让最大的元素排至最后
 * 重复以上步骤，直到整个数组有序
 */
public class BubbleSort {
    public static void main(String[] args) {
        int[] a = {5, 9, 7, 4, 1, 3, 2, 8};

        bubbleV2(a);
    }

    /**
     * 优化： 每轮冒泡时，最后一次交换索引可以作为下一轮冒泡的比较次数，如果这个值为零，
     *          表示整个数组有序，直接退出外层循环即可
     * @param a
     */
    public static void bubbleV2(int[] a) {
        int n = a.length - 1;
        int last;
        while (true) {
            last = 0; // 表示最后一次交换索引的位置
            for (int i = 0; i < n; i++) {
                if (a[i] > a[i + 1]) {
                    swap(a, i, i + 1);
                    last = i;
                }
            }
            System.out.println(Arrays.toString(a));
            n = last; // 修改下轮冒泡的比较次数
            if (n == 0) {  // 如果最后一次交换的位置索引为0，表示整个数组有序，退出循环
                break;
            }
        }
    }

    public static void bubble(int[] a) {
        boolean swapped;
        for (int j = 0; j < a.length - 1; j++) {
            swapped = false; // 每轮都检测是否发生了交换
            for (int i = 0; i < a.length - 1 - j; i++) {
                if (a[i] > a[i + 1]) {
                    swap(a, i, i + 1);
                    swapped = true;  // 发生交换
                }
            }
            System.out.println("第" + (j + 1) + "轮冒泡" + Arrays.toString(a));

            if (!swapped) { // 未发生交换，已经有序，退出循环
                break;
            }
        }
    }

    public static void swap(int[] a, int i, int j) {
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
    }
}
