package com.example.lcc.mykitchen.adapter;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class SharePagerAdapter extends FragmentPagerAdapter {
    List<Fragment> fragments = new ArrayList<Fragment>();

    public SharePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    //提供外界添加fragment的方法
    public void addFragment(Fragment fragment) {
        this.fragments.add(fragment);
    }

    @Override
    public Fragment getItem(int arg0) {
        return fragments.get(arg0);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }


}
