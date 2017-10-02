package com.example.zxz.androidtest.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.zxz.androidtest.R;
import com.example.zxz.androidtest.TestFragment;
import com.example.zxz.androidtest.adapter.MyFragmentStatePagerAdapter;
import com.example.zxz.androidtest.adapter.MyPagerAdapter;

/**
 * Created by xuezhi.zxz on 2017/6/21.
 */

public class ViewPagerActivity extends FragmentActivity {

    private ViewPager mViewPager;
    private MyPagerAdapter mAdapter;
    private MyFragmentStatePagerAdapter mFragmentStatePagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager);
        mViewPager = (ViewPager)findViewById(R.id.view_pager);
        mAdapter = new MyPagerAdapter();
        FragmentManager fm = getSupportFragmentManager();
        mFragmentStatePagerAdapter = new MyFragmentStatePagerAdapter(fm);
//        initAdapter();
        initFragmentStateAdapter();
    }

    void initAdapter() {
        View v = new View(this);
        ViewPager.LayoutParams layoutParams = new ViewPager.LayoutParams();
        layoutParams.height = ViewPager.LayoutParams.WRAP_CONTENT;
        layoutParams.width = ViewPager.LayoutParams.WRAP_CONTENT;
        v.setLayoutParams(layoutParams);
        v.setBackgroundColor(Color.GREEN);
        mAdapter.addView(v);

        v = new View(this);
        v.setLayoutParams(layoutParams);
        v.setBackgroundColor(Color.RED);
        mAdapter.addView(v);
        v = new View(this);
        v.setLayoutParams(layoutParams);
        v.setBackgroundColor(Color.YELLOW);
        mAdapter.addView(v);
        mViewPager.setAdapter(mAdapter);

//        mViewPager.setRotation(45);
    }

    void initFragmentAdapter() {



//        mViewPager.setAdapter(mFragmentPagerAdapter);
    }

    void initFragmentStateAdapter() {
        for (int i = 0; i < 10; ++i) {
            TestFragment fragment = new TestFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("position", i);
            fragment.setArguments(bundle);
            mFragmentStatePagerAdapter.addFragment(fragment);
        }
        mViewPager.setAdapter(mFragmentStatePagerAdapter);

        mViewPager.setCurrentItem(6, true);
    }

}
