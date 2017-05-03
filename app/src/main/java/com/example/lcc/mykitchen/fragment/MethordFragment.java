package com.example.lcc.mykitchen.fragment;


import com.example.lcc.mykitchen.MyApp;
import com.example.lcc.mykitchen.adapter.SharePagerAdapter;
import com.example.lcc.mykitchen.entity.UserInfo;
import com.example.lcc.mykitchen.view.TabGroup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.lcc.mykitchen.R;
import com.example.lcc.mykitchen.zhibo.LivePublisherActivity;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 *
 */
public class MethordFragment extends BaseFragment {

    private ViewPager vPager;
    private TabGroup tgroup;
    private SharePagerAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contentView=inflater.inflate(R.layout.fragment_methord, container, false);
        initUI();

        return contentView;
    }

    @Override
    public void initUI() {
        actionBar=(LinearLayout) contentView.findViewById(R.id.llActionbarId);
        initActionbar(-1,"美食直播",R.drawable.frag_meth_zhibo);
        ImageView zhibo= (ImageView) actionBar.findViewById(R.id.imgRightActionbarId);

        zhibo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserInfo userInfo=MyApp.bmobUser;
                if(userInfo==null){
                    Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
                }else{
                    startActivity(new Intent(getActivity(),LivePublisherActivity.class));
                }

            }
        });

        initRadioButton();
        initViewPager();

    }

    private void initRadioButton() {
        tgroup = (TabGroup) contentView.findViewById(R.id.radioGroup1);
        tgroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //
                RadioButton rBtn = (RadioButton) group.findViewById(checkedId);
                int index = group.indexOfChild(rBtn);
                tgroup.update(index);
                if (checkedId == R.id.radio0) {
                    vPager.setCurrentItem(0);

                } else {
                    vPager.setCurrentItem(1);
                }

            }
        });
    }

    private void initViewPager() {
        vPager = (ViewPager) contentView.findViewById(R.id.viewPagerVideoFID);
        FragmentManager fManager = getChildFragmentManager();
        adapter = new SharePagerAdapter(fManager);
        VideoFragment1 vfrag1 = new VideoFragment1();
        VideoFragment2 vfrag2 = new VideoFragment2();
        adapter.addFragment(vfrag1);
        adapter.addFragment(vfrag2);
        vPager.setAdapter(adapter);
        //让第一个ViewPager页面显示
        vPager.setCurrentItem(0);
        vPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int arg0) {
                if (arg0 == 0) {
                    tgroup.check(R.id.radio0);
                } else {
                    tgroup.check(R.id.radio1);
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
    }

}
