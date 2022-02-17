package day01.sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static day01.sort.Utils.swap;

public class LomutoVsHoare {

    public static void main(String[] args) {
        List<int[]> all1 = new ArrayList<>();
        List<int[]> all2 = new ArrayList<>();
        List<int[]> all3 = new ArrayList<>();
        List<int[]> all4 = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            int[] array = Utils.randomArray(10000);
            all1.add(array);
            all2.add(Arrays.copyOf(array, array.length));
            all3.add(Arrays.copyOf(array, array.length));
            all4.add(Arrays.copyOf(array, array.length));
        }
        System.out.println("hoarePartition");
        testPartition(all1, LomutoVsHoare::hoarePartition);
        System.out.println("LomutoPartition");
        testPartition(all2, LomutoVsHoare::LomutoPartition);
        System.out.println("otherPartition");
        testPartition(all3, LomutoVsHoare::otherPartition);
        System.out.println("moveInsteadSwapPartition");
        testPartition(all3, LomutoVsHoare::moveInsteadSwapPartition);
    }

    private static void testPartition(List<int[]> all, FourConsumer consumer) {
        List<AtomicInteger> cs = new ArrayList<>();
        for (int[] array : all) {
            AtomicInteger c = new AtomicInteger();
            consumer.apply(array, 0, array.length - 1, c);
            cs.add(c);
        }
        // 打印的是平均移动次数，而非交换次数，一次交换有3次移动
        System.out.println(cs + " avg:" + cs.stream().mapToLong(AtomicInteger::get).average().orElse(0));
    }

    interface FourConsumer {
        void apply(int[] a, int b, int c, AtomicInteger d);
    }

    private static int hoarePartition(int[] a, int l, int h, AtomicInteger c) {
//        int pv = a[l];
        int pv = a[(l + h) >>> 1];
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
            c.accumulateAndGet(3, Integer::sum);
            swap(a, i, j);
        }
    }

    private static int LomutoPartition(int[] a, int l, int h, AtomicInteger c) {
        int pv = a[h];
        int i = l;
        for (int j = l; j < h; j++) {
            if (a[j] < pv) {
                if (i != j) {
                    c.accumulateAndGet(3, Integer::sum);
                    swap(a, i, j);
                }
                i++;
            }
        }
        if (i != h) {
            c.accumulateAndGet(3, Integer::sum);
            swap(a, h, i);
        }
        return i;
    }

    private static int otherPartition(int[] a, int l, int h, AtomicInteger c) {
        int pv = a[l];
        int i = l;
        int j = h;
        while (i < j) {
            while (i < j && a[j] > pv) {
                j--;
            }
            while (i < j && a[i] <= pv) {
                i++;
            }
            c.accumulateAndGet(3, Integer::sum);
            swap(a, i, j);
        }
        c.accumulateAndGet(3, Integer::sum);
        swap(a, l, i);
        return i;
    }

    private static int moveInsteadSwapPartition(int[] a, int l, int h, AtomicInteger c) {
        int pv = a[l];
        int i = l;
        int j = h;
        while (i < j) {
            // j 从右找小的
            while (i < j && a[j] > pv) {
                j--;
            }
            if (i < j) {
                c.incrementAndGet();
                a[i] = a[j];
                i++;
            }
            // i 从左找大的
            while (i < j && a[i] <= pv) {
                i++;
            }
            if (i < j) {
                c.incrementAndGet();
                a[j] = a[i];
                j--;
            }
        }
        c.incrementAndGet();
        a[j] = pv;
        return j;
    }


}
