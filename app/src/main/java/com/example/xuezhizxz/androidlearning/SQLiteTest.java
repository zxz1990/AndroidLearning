package com.example.xuezhizxz.androidlearning;

import android.os.Bundle;

import com.yunos.viewinject.view.annotation.ContentView;

/**
 * Created by xuezhi.zxz on 2015/8/31.
 */

@ContentView(R.layout.activity_sqlitetest)
public class SQLiteTest extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Thread(new Runnable() {
            @Override
            public void run() {

            }
        }).start();
    }


}
