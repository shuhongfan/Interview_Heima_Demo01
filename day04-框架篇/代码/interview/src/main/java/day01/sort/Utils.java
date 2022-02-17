package day01.sort;

import java.util.Random;

public class Utils {
    public static void swap(int[] array, int i, int j) {
        int t = array[i];
        array[i] = array[j];
        array[j] = t;
    }

    public static void shuffle(int[] array) {
        Random rnd = new Random();
        int size = array.length;
        for (int i = size; i > 1; i--) {
            swap(array, i - 1, rnd.nextInt(i));
        }
    }

    public static int[] randomArray(int n) {
        int lastVal = 1;
        Random r = new Random();
        int[] array = new int[n];
        for (int i = 0; i < n; i++) {
            int v = lastVal + Math.max(r.nextInt(10), 1);
            array[i] = v;
            lastVal = v;
        }
        shuffle(array);
        return array;
    }

    public static int[] evenArray(int n) {
        int[] array = new int[n];
        for (int i = 0; i < n; i++) {
            array[i] = i * 2;
        }
        return array;
    }

    public static int[] sixteenArray(int n) {
        int[] array = new int[n];
        for (int i = 0; i < n; i++) {
            array[i] = i * 16;
        }
        return array;
    }

    public static int[] lowSameArray(int n) {
        int[] array = new int[n];
        Random r = new Random();
        for (int i = 0; i < n; i++) {
            array[i] = r.nextInt() & 0x7FFF0002;
        }
        return array;
    }
}
