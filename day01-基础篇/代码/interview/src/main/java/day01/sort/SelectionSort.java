package day01.sort;

import java.util.Arrays;

import static day01.sort.Utils.swap;

public class SelectionSort {

    public static void main(String[] args) {
        int[] a = {18,23, 19, 9, 23, 15};
        selection(a);
    }

    private static void selection(int[] a) {
        for (int i = 0; i < a.length - 1; i++) {
            // i 代表每轮选择最小元素要交换到的目标索引
            int s = i; // 代表最小元素的索引
            for (int j = s + 1; j < a.length; j++) {
                if (a[s] > a[j]) { // j 元素比 s 元素还要小, 更新 s
                    s = j;
                }
            }
            if (s != i) {
                swap(a, s, i);
            }
            System.out.println(Arrays.toString(a));
        }
    }
}
