package day01.sort;

import java.util.Arrays;

public class InsertSort {
    public static void main(String[] args) {
        int[] a = {7, 5, 19, 8, 4, 1};
        insert(a);
    }

    // 修改了代码与希尔排序一致
    public static void insert(int[] a) {
        // i 代表待插入元素的索引
        for (int i = 1; i < a.length; i++) {
            int t = a[i]; // 代表待插入的元素值
            int j = i;
            System.out.println(j);
            while (j >= 1) {
                if (t < a[j - 1]) { // j-1 是上一个元素索引，如果 > t，后移
                    a[j] = a[j - 1];
                    j--;
                } else { // 如果 j-1 已经 <= t, 则 j 就是插入位置
                    break;
                }
            }
            a[j] = t;
            System.out.println(Arrays.toString(a) + " " + j);
        }
    }
}
