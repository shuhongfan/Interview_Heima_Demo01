package com.shf.javase.binarysearch;

/**
 * 二分查找
 *
 * 前提：有已排序数组 A（假设已经做好）
 *
 * 定义左边界 L、右边界 R，确定搜索范围，循环执行二分查找（3、4两步）
 *
 * 获取中间索引 M = Floor((L+R) /2)
 *
 * 中间索引的值 A[M] 与待搜索的值 T 进行比较
 *
 * ① A[M] == T 表示找到，返回中间索引
 *
 * ② A[M] > T，中间值右侧的其它元素都大于 T，无需比较，中间索引左边去找，M - 1 设置为右边界，重新查找
 *
 * ③ A[M] < T，中间值左侧的其它元素都小于 T，无需比较，中间索引右边去找， M + 1 设置为左边界，重新查找
 *
 * 当 L > R 时，表示没有找到，应结束循环
 */
public class BinarySearch {
    public static void main(String[] args) {
//        1. 前提： 有已排序数组A
        int[] array = {1, 5, 8, 11, 19, 22, 31, 35, 40, 45, 48, 49, 50};
        int target = 48;
        int idx = binarySearch(array, target);
        System.out.println(idx);
    }

    /**
     * 二分查找，找到返回元素索引，找不到返回 -1
     * @param array
     * @param target
     * @return
     */
    private static int binarySearch(int[] array, int target) {
//        2. 定义左边界l，有边界r，确定搜索范围，循环执行二分查找
        int l = 0, r = array.length - 1, m;
        while (l<=r){  // 5. 当l>r时，表示没有找到，应该结束循环
            m = (l + r) / 2; // 3. 获取中间索引m
//            4. 中间索引值与待搜索的值进行比较
            if (array[m]==target){ // 相等，表示找到，返回中间索引
                return m;
            } else if (array[m]>target){ // 中间值右侧的其他元素都大于target，无需比较，中间索引左边去找，m-1设置右边界，重新检查
                r = m - 1;
            } else {// 中间值左侧的其他元素都小于target，无需比较，中间索引右边去找，m+1设置左边界，重新检查
                l = m + 1;
            }
        }
        return -1;
    }
}
