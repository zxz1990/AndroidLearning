package com.example.zxz.androidtest.java;

public class Solution {
    public static int minNumberInRotateArray(int[] array) {
        if (array == null || array.length == 0) {
            return 0;
        }
        int low = 0;
        int high = array.length - 1;
        int mid;
        int ans = array[0];
        while (low <= high) {
            if (array[high] >= array[low]) {
                return array[low];
            }
            mid = (low + high) >> 1;
            if (array[mid] >= array[low]) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        int[] a = {10, 11, 12, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        System.out.println(minNumberInRotateArray(a));
    }
}