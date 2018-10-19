package com.example.zxz.androidtest.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.zxz.androidtest.TestFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuezhi.zxz on 2017/6/21.
 */

public class MyFragmentStatePagerAdapter extends FragmentStatePagerAdapter {

    private List<TestFragment> mFragments = new ArrayList<>();

    public void setFragments(List<TestFragment> fragments) {
        mFragments = fragments;
    }

    public MyFragmentStatePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "Fragment:" + mFragments.get(position).getTitle();
    }
}
