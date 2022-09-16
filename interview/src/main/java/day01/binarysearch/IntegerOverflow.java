package day01.binarysearch;

public class IntegerOverflow {
    public static void main(String[] args) {
        int l = 0;
        int r = Integer.MAX_VALUE - 1;

        int m = (l + r) >>> 1;
        System.out.println(m);

        // 在右侧
        l = m + 1;
        m = (l + r) >>> 1;
        System.out.println(m);

        System.out.println(126 + 63);
        System.out.println((126 + 63)/2);
        System.out.println((126 + 63)>>>1);
    }

    private static void method1() {
        int l = 0;
        int r = Integer.MAX_VALUE - 1;

        int m = l + (r - l) / 2;  //   l/2  +  r/2 ==>  l + (-l/2 + r/2) ==> l + (r-l)/2
        System.out.println(m);

        // 在右侧
        l = m + 1;
        m = l + (r - l) / 2;
        System.out.println(m);

        System.out.println(126 + 63);
    }
}
