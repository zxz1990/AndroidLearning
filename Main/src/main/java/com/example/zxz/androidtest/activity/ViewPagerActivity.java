package com.example.zxz.androidtest.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.zxz.androidtest.R;
import com.example.zxz.androidtest.TestFragment;
import com.example.zxz.androidtest.adapter.MyFragmentStatePagerAdapter;
import com.example.zxz.androidtest.adapter.MyPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuezhi.zxz on 2017/6/21.
 */

public class ViewPagerActivity extends FragmentActivity {

    private ViewPager mViewPager;
    private MyPagerAdapter mAdapter;
    private MyFragmentStatePagerAdapter mFragmentStatePagerAdapter;
    private List<TestFragment> mFragments;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager);
        mViewPager = (ViewPager)findViewById(R.id.view_pager);
        mAdapter = new MyPagerAdapter();
        FragmentManager fm = getSupportFragmentManager();
        mFragmentStatePagerAdapter = new MyFragmentStatePagerAdapter(fm);
//        initAdapter();
        mFragments = new ArrayList<>();

        initFragmentStateAdapter();

        findViewById(R.id.btn_change_fragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment();
            }
        });
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
        mFragments.clear();
        for (int i = 0; i < 10; ++i) {
            TestFragment fragment = new TestFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("id", i);
            bundle.putInt("position", i);
            bundle.putString("text", "位置"+i);
            fragment.setArguments(bundle);
            mFragments.add(fragment);
        }
        mFragmentStatePagerAdapter.setFragments(mFragments);

        mViewPager.setAdapter(mFragmentStatePagerAdapter);

        mViewPager.setCurrentItem(6, true);
    }

    void changeFragment() {
        mFragments.remove(2);

        TestFragment fragment = new TestFragment();
        Bundle bundle = new Bundle();
        int id = 100;
        bundle.putInt("id", id);
        bundle.putInt("position", id);
        bundle.putString("text", "位置"+id);
        fragment.setArguments(bundle);
        mFragments.add(5, fragment);
        mFragmentStatePagerAdapter.notifyDataSetChanged();
    }

}
