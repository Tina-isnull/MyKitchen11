package com.example.lcc.mykitchen.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.example.lcc.mykitchen.adapter.BannerShowAdapter;
import com.example.lcc.mykitchen.adapter.ShowFragmentAdapter;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.example.lcc.mykitchen.R;

import com.example.lcc.mykitchen.manager.HideSoftKeyboard;
import com.example.lcc.mykitchen.view.ListviewForScrollview;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
public class ShowFragment extends BaseFragment{

    private ListviewForScrollview listview;
    private ViewPager mViewPager;
    private LinearLayout mLinearLayout;
    private int[] imgs;//轮播背景图数组
    private ImageView[] points;//小圆点数组
    private boolean isContinue = true;
    private AtomicInteger what = new AtomicInteger(0);
    private BannerShowAdapter pagerDdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contentView = inflater.inflate(R.layout.fragment_show, container, false);
        //获得组件的根布局
        LinearLayout ll= (LinearLayout) contentView.findViewById(R.id.ll_show_id);
        //点击组件隐藏软键盘
        UpdateUI(ll);
        initUI();
        return contentView;
    }

    @Override
    public void initUI() {
        // 初始化基本组件（findViewById）
        initView();
        // 初始化ListView
        initListView();
        // 初始化显示在广告栏中的图片和小圆点，以及添加适配器，监听器
        initViewPager();

    }

    private void initView() {
        listview = (ListviewForScrollview) contentView.findViewById(R.id.listviewShowFID);
        mViewPager = (ViewPager) contentView.findViewById(R.id.viewPagerShowFID);
        mLinearLayout = (LinearLayout) contentView.findViewById(R.id.points);
    }

    private void initViewPager() {
        //广告业背景数组
        imgs = new int[]{R.drawable.adverpager01, R.drawable.adverpager02, R.drawable.adverpager03};
        //实例化小圆点的数组
        points=new ImageView[imgs.length];
        // 实例化并封装广告页ImageView对象的的list
        List<ImageView> imgList = new ArrayList<ImageView>();
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        for (int i = 0; i < imgs.length; i++) {
            ImageView img = new ImageView(getActivity());
            img.setBackgroundResource(imgs[i]);
            img.setLayoutParams(params);
            imgList.add(img);
            // 实例化广告业的小圆点并添加到数组中
            ImageView point = new ImageView(getActivity());
            params = new LayoutParams(20, 20);
            params.leftMargin = 10;
            point.setBackgroundResource(R.drawable.point_selector);
            point.setLayoutParams(params);
            //默认第一个为选中状态
            if (i == 0) {
                point.setEnabled(true);
            } else {
                point.setEnabled(false);
            }
            points[i] = point;
            mLinearLayout.addView(point);
        }
        // 实例化并添加ViewPager的适配器
        pagerDdapter = new BannerShowAdapter(imgList);
        mViewPager.setAdapter(pagerDdapter);
        //添加监听方法
        mViewPager.setOnPageChangeListener(new BannerShowListener());
        mViewPager.setOnTouchListener(new BannerTouchListener());
        //定时滑动工作线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (isContinue) {
                        viewHandler.sendEmptyMessage(what.get());
                        whatOption();
                    }
                }
            }
        }).start();
    }
    /**
     * 操作圆点轮换变背景
     */
    private void whatOption() {
        what.incrementAndGet();
        if (what.get() > imgs.length - 1) {
            what.getAndAdd(-4);
        }
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {

        }
    }

    /**
     * 处理定时切换广告栏图片的句柄
     */
    private final Handler viewHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            mViewPager.setCurrentItem(msg.what);
            super.handleMessage(msg);
        }

    };
    /** 广告业ViewPager的监听方法*/
    class BannerShowListener implements OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageSelected(int position) {
            for (int i = 0; i < points.length; i++) {
                if(i==position){
                    points[i].setEnabled(false);

                }else{
                    points[i].setEnabled(true);

                }
            }
        }

    }

    class BannerTouchListener implements OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_MOVE:
                    isContinue = true;
                    break;
                case MotionEvent.ACTION_UP:
                    isContinue = false;
                    break;

                default:
                    isContinue = false;
                    break;
            }
            return false;
        }

    }


    /** 初始化ListView*/
    private void initListView() {
        // 设置每个item间的距离
        listview.setDividerHeight(10);
        ShowFragmentAdapter adapter = new ShowFragmentAdapter(getActivity());
        // 初始化测试数据
        // 向adapter中添加数据
        listview.setAdapter(adapter);
    }


    /**
         * 点击页面收起小键盘*/
    public void UpdateUI(View view){

        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View arg0, MotionEvent arg1) {
                    // TODO Auto-generated method stub
                    HideSoftKeyboard.hideSoftKeyboard(getActivity());
                    return false;
                }
            });
        }
        if (view instanceof ViewGroup) {

            for(int i = 0; i < ((ViewGroup) view).getChildCount(); i++){
                View innerView = ((ViewGroup) view).getChildAt(i);
                UpdateUI(innerView);
            }
        }
    }




    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}
