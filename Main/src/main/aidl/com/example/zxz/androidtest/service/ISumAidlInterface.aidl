// ISumAidlInterface.aidl
package com.example.zxz.androidtest.service;

// Declare any non-default types here with import statements

interface ISumAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

            int getSum(int a, int b);
}
