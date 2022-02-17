package day01.sort;

import java.util.Arrays;

import static day01.sort.Utils.swap;

// 双边循环法
@SuppressWarnings("all")
public class QuickSort2 {
    public static void main(String[] args) {
        int[] a = {5, 3, 7, 2, 9, 8, 1, 4};
        System.out.println(Arrays.toString(a));
        quick(a, 0, a.length - 1);
    }

    private static void quick(int[] a, int l, int h) {
        if (l >= h) {
            return;
        }
        int p = partition(a, l, h);
        quick(a, l, p - 1);
        quick(a, p + 1, h);
    }

    private static int partition(int[] a, int l, int h) {
        int pv = a[l];
        int i = l;
        int j = h;
        while (i < j) {
            // j 从右找小的
            while (i < j && a[j] > pv) {
                j--;
            }
            // i 从左找大的
            while (i < j && a[i] <= pv) {
                i++;
            }
            swap(a, i, j);
        }
        swap(a, l, j);
        System.out.println(Arrays.toString(a) + " j=" + j);
        return j;
    }
}
