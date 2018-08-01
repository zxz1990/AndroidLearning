package com.example.zxz.androidtest.activity;

import android.app.Activity;

/**
 * Created by xuezhi.zxz on 2015/8/31.
 */
public class BaseActivity extends Activity{

    public final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onStart() {
        super.onStart();
    }
}
