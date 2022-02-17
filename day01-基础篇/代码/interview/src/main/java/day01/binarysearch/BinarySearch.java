package day01.binarysearch;

public class BinarySearch {
    public static void main(String[] args) {
        int[] array = {1, 5, 8, 11, 19, 22, 31, 35, 40, 45, 48, 49, 50};
        int target = 48;
//        int idx = binarySearch(array, target);
        int idx = myBinarySearch(array, target);
        System.out.println(idx);
    }

    public static int myBinarySearch(int[] a,int t){
        int l=0,r=a.length-1,m;
        while (l<=r){
            m = (l+r)/2;
            if (a[m]==t){
                return m;
            } else if (a[m]>t){
                r=m-1;
            } else {
                l=m+1;
            }
        }
        return -1;
    }

    // 二分查找, 找到返回元素索引，找不到返回 -1
    public static int binarySearch(int[] a, int t) {
        int l = 0, r = a.length - 1, m;
        while (l <= r) {
            m = (l + r) / 2;
            if (a[m] == t) {
                return m;
            } else if (a[m] > t) {
                r = m - 1;
            } else {
                l = m + 1;
            }
        }
        return -1;
    }
}
