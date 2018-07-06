package com.example.zxz.androidtest.java;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadTest {

    public static String getString(int id) {
        try {
            Thread.sleep((long)(Math.random()*1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "string_"+id;
    }
    static int pos = 0;

    static Object obj = new Object();


    public static void test() {

        ExecutorService threadPool = Executors.newFixedThreadPool(10);

        final List<Integer> ids = new ArrayList<>();
        for(int i=0;i<20;++i) {
            ids.add(i);
        }


        //work
        final String[] strings = new String[ids.size()];
        for(int i=0;i<ids.size();++i) {
            final int index = i;
            final int id = ids.get(i);
            //System.out.println("start:"+id);

            threadPool.execute(new Runnable() {
                @Override
                public void run() {



                    strings[index] = getString(id);


                    //System.out.println("end:" + id+" index:"+index+" pos:"+pos);
                    synchronized (ThreadTest.class) {
                        if (index == pos) {

                            int j = index;
                            for (; j < strings.length && strings[j] != null; ++j) {
                                System.out.println("ans:"+strings[j]);
                                strings[j] = null;
                            }
                            pos = j;
                        }

                    }
                }
            });
        }

    }


    public static void main(String[] args) {
        test();
    }
}
