package com.example.zxz.androidtest.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.example.zxz.androidtest.widget.SlideBackFrameLayout;

/**
 * Created by xuezhi.zxz on 2015/8/31.
 */
public class BaseActivity extends AppCompatActivity {

    public final String TAG = this.getClass().getSimpleName();
    private SlideBackFrameLayout mLayout = null;


    @Override
    public void setContentView(int layoutResID) {
        Log.d(TAG, "setContentView() called with: layoutResID = [" + layoutResID + "]");
        setContentView(LayoutInflater.from(this).inflate(layoutResID, null));
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(wrapSlideContentView(view));
    }

    View wrapSlideContentView(View view) {
        Log.d(TAG, "wrapSlideContentView() called with: view = [" + view + "]");
        if (!enableSlideBack()) {
            return view;
        }
        mLayout = new SlideBackFrameLayout(this);
        mLayout.addView(view);
        mLayout.setCallBack(new SlideBackFrameLayout.CallBack() {
            @Override
            public void onFinish() {
                finish();
            }
        });
        return mLayout;
    }

    protected boolean enableSlideBack() {
        return true;
    }

}
