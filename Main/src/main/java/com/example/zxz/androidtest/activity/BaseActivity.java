package com.example.zxz.androidtest.activity;

import android.app.Activity;

import com.yunos.viewinject.ViewUtils;

/**
 * Created by xuezhi.zxz on 2015/8/31.
 */
public class BaseActivity extends Activity{

    @Override
    protected void onStart() {
        super.onStart();
        ViewUtils.inject(this);
    }
}
