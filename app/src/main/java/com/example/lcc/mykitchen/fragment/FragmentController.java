package com.example.lcc.mykitchen.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.ArrayList;

/**
 * Created by lcc on 2016/12/23.
 */
public class FragmentController {
    int containerId;
    ArrayList<Fragment> fragments;
    private FragmentManager fragmentManager;
    private static FragmentController fragmentController;


    private FragmentController(int containerId, FragmentActivity activity) {
        this.containerId = containerId;
        fragmentManager = activity.getSupportFragmentManager();
        initFragment();
    }

    public static FragmentController getInstance(int containerId, FragmentActivity activity) {
        if (fragmentController == null) {
            fragmentController = new FragmentController(containerId, activity);
        }
        return fragmentController;
    }

    private void initFragment() {
        fragments = new ArrayList<>();
        fragments.add(new ShowFragment2());
        fragments.add(new MethordFragment());
        fragments.add(new ShareFragment());
        fragments.add(new MyFragment());
        FragmentTransaction ft = fragmentManager.beginTransaction();
        for (Fragment fragment : fragments) {
            ft.add(containerId, fragment);
        }
        ft.commitAllowingStateLoss();
    }

    public void showFragment(int position) {
        hideFragment();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.show(fragments.get(position));
        ft.commitAllowingStateLoss();

    }

    private void hideFragment() {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        for (Fragment fragment : fragments) {
            if (fragment != null) {
                ft.hide(fragment);
            }
        }
        ft.commitAllowingStateLoss();
    }


}
