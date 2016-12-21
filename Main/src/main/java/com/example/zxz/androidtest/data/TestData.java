package com.example.zxz.androidtest.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 学智 on 2016-01-02.
 */
public class TestData {
    public static List<String> getListString() {
        List<String> list = new ArrayList<>();
        for(int i=1; i <= 100; ++i) {
            list.add("TextView Data:"+i);
        }
        return list;
    }
}
