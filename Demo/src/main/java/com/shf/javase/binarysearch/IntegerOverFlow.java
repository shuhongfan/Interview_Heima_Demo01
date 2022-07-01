package com.shf.javase.binarysearch;

public class IntegerOverFlow {
    public static void main(String[] args) {
        int l = 0;
        int r = Integer.MAX_VALUE - 1;

//        int m = (l + r) / 2; // l/2 + r/2 ===> l + (-l/2 + r/2 ) ===> l+(r-l)/2
//        int m = l+(r-l)/2; // l/2 + r/2 ===> l + (-l/2 + r/2 ) ===> l+(r-l)/2
        int m = (l+r)>>>1;
        System.out.println(m); // 1073741823

        l = m + 1;
        m = l+(r-l)/2;
        System.out.println(m); // -536870913  溢出
    }
}
