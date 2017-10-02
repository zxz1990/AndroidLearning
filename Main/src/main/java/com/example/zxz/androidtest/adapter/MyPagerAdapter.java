package com.example.zxz.androidtest.adapter;

import android.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuezhi.zxz on 2017/6/21.
 */

public class MyPagerAdapter extends PagerAdapter {

    private List<Fragment> mFragments = new ArrayList<>();
    private List<View> mViews = new ArrayList<>();

    public void addFragmet(Fragment fragment) {
        mFragments.add(fragment);
    }

    public void addView(View view) {
        mViews.add(view);
    }

    @Override
    public int getCount() {
        return mViews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mViews.get(position));
        return mViews.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViews.get(position));
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "标题"+position;
    }
}
