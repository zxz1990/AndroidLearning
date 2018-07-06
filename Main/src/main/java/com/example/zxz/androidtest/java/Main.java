package com.example.zxz.androidtest.java;

import java.math.BigInteger;

class SuperClass {

    static int count = 1;

    static{

        System.out.println("Initialize class SuperClass");

    }

    public class Inner {}

}



class SubClass extends SuperClass{

    static{

        System.out.println("Initialize class SubClass");

    }

}



public class Main {

    public static void main(String[] args) {

        int x = SubClass.count;


        SuperClass sc = new SuperClass();
        SuperClass.Inner inner = sc.new Inner();


        BigInteger[][]a = new BigInteger[2][];
        for(int i=0;i<a.length;++i) {
            a[i] = new BigInteger[4];
            for(int j=0;j<a[i].length;++j) {
                System.out.println(a[i][j]);
                //a[i][j]=i+j;
            }
        }

        //for(int i=0;i<a.length;++i) {
        //    for(int j=0;j<a[i].length;++j) {
        //        System.out.println(a[i][j]);
        //        //a[i][j]=10;
        //    }
        //}
    }

}