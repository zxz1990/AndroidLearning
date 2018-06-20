package com.example.zxz.androidtest.java;

import java.util.Arrays;
import java.util.Date;

public class QSortTest {

    static int partion(int[] arr, int low, int high) {

        if (low >= high) {
            return low;
        }

        int start = low;
        int end = high;

        int t = arr[start];

        //System.out.println("partion:" + start + "," + end);
        while (start < end) {
            while (start < end && arr[end] >= t) {
                --end;
            }
            while (start < end && arr[start] <= t) {
                ++start;
            }
            if (start < end) {
                int tmp = arr[end];
                arr[end] = arr[start];
                arr[start] = tmp;
            }
            //System.out.print(start + "," + end);
            //output("        xunhuan:", arr);

        }

        arr[low] = arr[start];
        arr[start] = t;
        //output("        !!!!", arr);
        return start;
    }

    static void qsort(int[] arr, int low, int high) {
        if (arr == null) {
            return;
        }
        if (low >= high) {
            return;
        }
        //System.out.println("qsort:" + low + "," + high);
        int position = partion(arr, low, high);
        qsort(arr, low, position - 1);
        qsort(arr, position + 1, high);
    }

    static void output(String title, int[] arr) {
        //System.out.print(title + ":---- ");
        //for (int i = 0; i < arr.length; ++i) {
        //    System.out.print(arr[i] + " ");
        //}
        //System.out.println();
    }

    static long timeMill = 0;

    static void outputTime() {
        long oldTime = timeMill;
        timeMill = new Date().getTime();
        if(oldTime == 0) {
            return;
        }
        System.out.println("time:"+(timeMill-oldTime));
    }

    public static void main(String[] args) {
        int n = 4000000;
        int low = 0;
        int high = 10000000;
        int[] a = new int[n];
        int[] b = new int[n];
        for (int i = 0; i < n; ++i) {
            int t = (int)(Math.random() * (high - low) + low);
            a[i] = t;
            b[i] = t;
        }

        output("a1", a);
        output("b1", b);

        System.out.println("equals:"+ Arrays.equals(a,b));

        outputTime();
        Arrays.sort(b);

        outputTime();
        qsort(a, 0, a.length - 1);


        outputTime();
        output("@@@@a2", a);
        output("@@@@b2", b);

        System.out.println("equals:"+ Arrays.equals(a,b));

    }
}
