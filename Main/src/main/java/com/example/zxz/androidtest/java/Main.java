package com.example.zxz.androidtest.java;

import java.math.BigInteger;

class SuperClass {

    final static int count = 1;

    final int countfield = 1;

    static {

        System.out.println("Initialize class SuperClass");

    }

    class Inner$Inner2{}

    public static class Inner {
        public Inner() {
            //count = 2;
            //countfield = 3;
        }


        class $1 implements Runnable {
            @Override
            public void run() {

            }
        }

        Object sb1 = null;
        Object sb2 = null;
        Object sb3 = null;

        void test(Object sb) {
            sb3 = sb;
        }

        final public void hello() {
            final Object sb = new Object();
            sb1 = sb;
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    //System.out.println(sb.hashCode());
                    sb2 = sb;
                }
            };
            runnable.run();
            test(sb);

            System.out.println(sb1+","+sb2+","+sb3);

            System.out.println(sb1==sb2);
            System.out.println(sb1==sb3);
            System.out.println(sb2==sb3);
            //sb.append(0);
        }

        class Inner12 {

        }
        class dd$ee{

        }
    }

}

class SubClass extends SuperClass {

    static {

        System.out.println("Initialize class SubClass");

    }

}

public class Main {

    public static void main(String[] args) {

        int x = SubClass.count;

        SuperClass sc = new SuperClass();
        //SuperClass.Inner inner = sc.new Inner();

        SuperClass.Inner inner1 = new SuperClass.Inner();
        inner1.hello();

        BigInteger[][] a = new BigInteger[2][];
        for (int i = 0; i < a.length; ++i) {
            a[i] = new BigInteger[4];
            for (int j = 0; j < a[i].length; ++j) {
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