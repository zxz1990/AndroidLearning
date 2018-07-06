package com.example.zxz.androidtest.java;

public class VolatileSynchronized {
    private int inc = 0;

    private synchronized void increase() {
        inc++;
    }

    public static void main(String[] args) {
        final VolatileSynchronized test = new VolatileSynchronized();
        int initCount = Thread.activeCount();
        System.out.println("init:" + initCount);
        for (int i = 0; i < 10; i++) {
            new Thread() {
                public void run() {
                    for (int j = 0; j < 1000; j++) { test.increase(); }
                }

            }.start();
        }

        //保证前面的线程都执行完
        while (Thread.activeCount() > initCount) { Thread.yield(); }
        ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
        Thread[] threads = new Thread[threadGroup.activeCount()];
        threadGroup.enumerate(threads);
        System.out.println(threadGroup.toString());
        System.out.println(test.inc);
        for (Thread thread :
            threads) {
            System.out.println(thread.getName());
        }
    }
}
