package com.shf.javase.sort;

import java.util.Arrays;

/**
 * 快速排序
 *
 * 选择最左元素作为基准点元素
 * j 指针负责从右向左找比基准点小的元素，i 指针负责从左向右找比基准点大的元素，一旦找到二者交换，直至 i，j 相交
 * 最后基准点与 i（此时 i 与 j 相等）交换，i 即为分区位置
 *
 * 基准点在左边，并且要先 j 后 i
 *
 * while( i < j && a[j] > pv ) j--
 *
 * while ( i < j && a[i] <= pv ) i++
 */
public class QuickSort2 {
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
        int pv = a[l]; // 选择最左元素作为基准点元素
        int i = l;  // i 指针负责从左向右找比基准点大的元素，一旦找到二者交换，直到i，j相交
        int j = h; // j 指针负责从右向左找比基准点小的元素，i指针负责从左向右找比基准点大的元素，一旦找到二者交换，直到i，j相交
        while (i < j) {
            while (i < j && a[j] > pv) { // j 从右向左找小的
                j--;
            }
            while (i < j && a[i] <= pv) { // i 从左向右找小的
                i++;
            }
            swap(a, i, j);
        }
        System.out.println(Arrays.toString(a)+" j="+j);
        return j;
    }

    public static void swap(int[] a, int i, int j) {
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
    }
}
