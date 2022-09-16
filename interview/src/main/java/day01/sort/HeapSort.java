package day01.sort;

import java.util.Arrays;

import static day01.sort.Utils.swap;

public class HeapSort {
    public static void main(String[] args) {
        int[] a = {7, 5, 19, 8, 4, 1, 20, 13, 16};
        heap(a);
        System.out.println(Arrays.toString(a));
    }

    private static void heap(int[] a) {
        int count = a.length;
        heapify(a, count);
        System.out.println(Arrays.toString(a));
        int end = count - 1;
        while (end > 0) {
            swap(a, end, 0);
            end--;
            siftDown(a, 0, end);
        }
    }

    private static void heapify(int[] a, int count) {
        int start = getParent(count - 1);
        System.out.println(Arrays.toString(a));
        while (start >= 0) {
            siftDown(a, start, count - 1);
            System.out.println(Arrays.toString(a));
            start--;
        }
    }

    private static void siftDown(int[] a, int start, int end) {
        int root = start;
        while (getLeftChild(root) <= end) {
            int child = getLeftChild(root);
            int swap = root;
            if (a[swap] < a[child]) {
                swap = child;
            }
            if (child + 1 <= end && a[swap] < a[child + 1]) {
                swap = child + 1;
            }
            if (swap == root) {
                return;
            } else {
                swap(a, root, swap);
                root = swap;
            }
        }
    }


    private static int getParent(int i) {
        return (i - 1) / 2;
    }

    private static int getLeftChild(int i) {
        return 2 * i + 1;
    }

    private static int getRightChild(int i) {
        return 2 * i + 2;
    }
}
