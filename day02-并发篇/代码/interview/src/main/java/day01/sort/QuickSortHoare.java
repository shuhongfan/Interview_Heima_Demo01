package day01.sort;

import java.util.Arrays;

import static day01.sort.Utils.swap;

public class QuickSortHoare {
    public static void main(String[] args) {
//        int[] a = {1,2,3};
//        int[] a = {9, 3, 2, 1};
//        int[] a = {9, 3, 7, 2, 5, 8, 1, 4};
        int[] a = {1, 2, 3, 4, 5, 7, 8, 9};
        System.out.println(Arrays.toString(a));
        quick0(a, 0, a.length - 1);
    }

    private static void quick0(int[] a, int l, int h) {
        if (l >= h) {
            return;
        }
        int p = partition(a, l, h);
        colorPrint(a, l, p, p + 1, h);
        // 注意如果左边界选择了 p-1, 则会因为返回的 p 可能不是基准点位置导致出错
        quick0(a, l, p);
        quick0(a, p + 1, h);
    }

    private static void colorPrint(int[] a, int r1l, int r1h, int r2l, int r2h) {
        System.out.print("[");
        for (int i = 0; i < a.length; i++) {
            if (i >= r1l && i <= r1h) {
                System.out.print("\033[31m" + a[i] + "\033[0m");
            } else if (i >= r2l && i <= r2h) {
                System.out.print("\033[34m" + a[i] + "\033[0m");
            } else {
                System.out.print("_");
            }
            if (i < a.length - 1) {
                System.out.print(" ");
            }
        }
        System.out.println("]");

    }

    private static int partition(int[] a, int l, int h) {
//        int pv = a[l];
        int pv = a[(l + h) >>> 1];
        System.out.println("pv=" + pv);
        int i = l - 1;
        int j = h + 1;
        while (true) {
            while (a[++i] < pv) {
            }
            while (a[--j] > pv) {
            }
            if (i >= j) {
                return j;
            }
            swap(a, i, j);
        }
    }
}
