package day01.sort;

import java.util.Arrays;

public class ShellSort {
    public static void main(String[] args) {
        int[] a = {7, 5, 19, 8, 4, 1};
        shell(a);
    }

    private static void shell(int[] a) {
        int n = a.length;
        for (int gap = n / 2; gap > 0; gap /= 2) {
            // i 代表待插入元素的索引
            for (int i = gap; i < n; i++) {
                int t = a[i]; // 代表待插入的元素值
                int j = i;
                while (j >= gap) {
                    // 每次与上一个间隙为 gap 的元素进行插入排序
                    if (t < a[j - gap]) { // j-gap 是上一个元素索引，如果 > t，后移
                        a[j] = a[j - gap];
                        j -= gap;
                    } else { // 如果 j-1 已经 <= t, 则 j 就是插入位置
                        break;
                    }
                }
                a[j] = t;
                System.out.println(Arrays.toString(a) + " gap:" + gap);
            }
        }
    }


}
