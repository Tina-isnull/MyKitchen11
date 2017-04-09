package com.example.lcc.mykitchen.fragment;


import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.lcc.mykitchen.adapter.ShowFragmentAdapter1;
import com.example.lcc.mykitchen.adapter.ShowRecyclerAdapter;
import com.example.lcc.mykitchen.entity.UploadMenuBean;
import com.example.lcc.mykitchen.entity.UserInfo;
import com.example.lcc.mykitchen.ui.FoodDetailsActivity;
import com.example.lcc.mykitchen.R;
import com.example.lcc.mykitchen.ui.PersonDetialsActivity;
import com.example.lcc.mykitchen.ui.SearchActivity;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.example.lcc.mykitchen.adapter.BannerShowAdapter;
import com.example.lcc.mykitchen.adapter.ShowFragmentAdapter;
import com.example.lcc.mykitchen.entity.FoodFromWeb;
import com.example.lcc.mykitchen.entity.FoodDetails;
import com.example.lcc.mykitchen.manager.HideSoftKeyboard;
import com.example.lcc.mykitchen.manager.HttpRequestManager;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

public class ShowFragment2 extends BaseFragment {
    PullToRefreshListView ptrListView;
    ListView listView;
    HorizontalScrollView scrollView;
    private ViewPager mViewPager;
    private LinearLayout mLinearLayout;
    private int[] imgs;//轮播背景图数组
    private ImageView[] points;//小圆点数组
    private boolean isContinue = true;
    private AtomicInteger what = new AtomicInteger(0);
    private BannerShowAdapter pagerAdapter;
    private ShowFragmentAdapter1 adapter;
    TextView tvFoodName;
    String food = null;
    ImageView addCookBook;
    RadioGroup radioGroup;
    List<String> lists = new ArrayList<>();
    Handler mHandler = new Handler();
    List<UserInfo> kitterLists;
    private ShowRecyclerAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contentView = inflater.inflate(R.layout.fragment_show2, container, false);
        //获得组件的根布局
        LinearLayout ll = (LinearLayout) contentView.findViewById(R.id.ll_show_id);
        kitterLists=new ArrayList<>();
        //点击组件隐藏软键盘
        UpdateUI(ll);
        initUI();
        return contentView;
    }

    @Override
    public void initUI() {
        scrollView = (HorizontalScrollView) contentView.findViewById(R.id.hscrollview_show);
        addCookBook = (ImageView) contentView.findViewById(R.id.img_show_add);
        tvFoodName = (TextView) contentView.findViewById(R.id.etActionbarId);
        tvFoodName.setVisibility(View.GONE);
        radioGroup = (RadioGroup) contentView.findViewById(R.id.ll_show_select);
        //初始化listView
        initListView();
        tvFoodName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivityForResult(intent, 200);
                getActivity().overridePendingTransition(R.anim.input_anim, R.anim.output_anim);
            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int id = radioGroup.getCheckedRadioButtonId();
                Log.i("TAG", id + "");
                RadioButton radioButton = (RadioButton) contentView.findViewById(id);
                food = radioButton.getText().toString();
                // refresh(food);
            }
        });
        lists.add("家常菜");
        lists.add("烘焙");
        lists.add("素菜");
        lists.add("面食");
        lists.add("汤");
        lists.add("川菜");
        lists.add("京菜");
        lists.add("西餐");
        for (String data : lists) {
            addRadioButton(data);
        }
    }

    /**
     * 初始化ListView
     */
    private void initListView() {
        ptrListView = (PullToRefreshListView) contentView.findViewById(R.id.refresh_list_show);
        listView = ptrListView.getRefreshableView();
        listView.setDividerHeight(0);
        adapter = new ShowFragmentAdapter1(getActivity());
        listView.setAdapter(adapter);
        //为listview添加头部信息
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        View header_viewpager = inflater.inflate(R.layout.header_listview_show, listView, false);
        View header_selects = inflater.inflate(R.layout.include_showf_menu, listView, false);
        RecyclerView mRecycler = (RecyclerView) header_selects.findViewById(R.id.recycler_include);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecycler.setLayoutManager(linearLayoutManager);
        mAdapter = new ShowRecyclerAdapter(getActivity(),kitterLists );
        mRecycler.setAdapter(mAdapter);
        mAdapter.setOnItemClickLitener(new ShowRecyclerAdapter.OnItemClickLitener() {
            @Override
            public void OnClickItem(View view, int position) {
                UserInfo info=mAdapter.getItem(position);
                Intent intent=new Intent(getActivity(), PersonDetialsActivity.class);
                intent.putExtra("intro",info);
                startActivity(intent);

            }
        });
        listView.addHeaderView(header_viewpager);
        listView.addHeaderView(header_selects);
        //初始化广告栏
        initViewPager(header_viewpager);
        //为listview添加监听事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FoodDetails steps = adapter.getItem(position - 3);
                Intent intent = new Intent(getActivity(), FoodDetailsActivity.class);
                intent.putExtra("step", steps);
                startActivity(intent);
            }
        });

        ptrListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                tvFoodName.setVisibility(View.VISIBLE);
                if (food == null) {
                    food = "红烧肉";
                }
                refresh(food);
            }
        });

    }

    /**
     * 初始化viewPager
     */
    private void initViewPager(View header_viewpager) {
        mLinearLayout = (LinearLayout) header_viewpager.findViewById(R.id.ll_header_points);
        mViewPager = (ViewPager) header_viewpager.findViewById(R.id.viewPager_header_show);
        //广告业背景数组
        imgs = new int[]{R.drawable.adverpager01, R.drawable.adverpager02, R.drawable.adverpager03};
        //实例化小圆点的数组
        points = new ImageView[imgs.length];
        // 实例化并封装广告页ImageView对象的的list
        List<ImageView> imgList = new ArrayList<ImageView>();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        for (int i = 0; i < imgs.length; i++) {
            ImageView img = new ImageView(getActivity());
            img.setBackgroundResource(imgs[i]);
            img.setLayoutParams(params);
            imgList.add(img);
            // 实例化广告业的小圆点并添加到数组中
            ImageView point = new ImageView(getActivity());
            params = new LinearLayout.LayoutParams(15, 15);
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
        pagerAdapter = new BannerShowAdapter(imgList);
        mViewPager.setAdapter(pagerAdapter);
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

    /**
     * 广告业ViewPager的监听方法
     */
    class BannerShowListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            //请求ListView不要抢已经分配给ViewPager的动作(事件)
            listView.requestDisallowInterceptTouchEvent(true);
        }

        @Override
        public void onPageSelected(int position) {
            listView.requestDisallowInterceptTouchEvent(false);
            for (int i = 0; i < points.length; i++) {
                if (i == position) {
                    points[i].setEnabled(false);

                } else {
                    points[i].setEnabled(true);

                }
            }
        }

    }

    class BannerTouchListener implements View.OnTouchListener {
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

    //动态添加选择栏的选项
    public void addRadioButton(String text) {
        RadioButton radioButton = new RadioButton(getActivity());
        RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(10, 10, 10, 10);
        radioButton.setLayoutParams(layoutParams);
        radioButton.setTextSize(14);
        //radioButton.setTextColor(getResources().getColor(R.color.category_tab_text));
        radioButton.setButtonDrawable(android.R.color.transparent);//隐藏单选圆形按钮
        radioButton.setGravity(Gravity.CENTER);
        radioButton.setPadding(10, 10, 10, 10);
        radioButton.setText(text);
        radioButton.setChecked(false);
        ColorStateList csl=getResources().getColorStateList(R.color.lr_font_color_selector);
        radioButton.setTextColor(csl);//设置选中/未选中的文字颜色
       // radioButton.setBackgroundResource(R.drawable.selector_show_bg);//设置按钮选中/未选中的背景
        radioGroup.addView(radioButton);//将单选按钮添加到RadioGroup中
    }

    //动态添加选择栏的选项,来源是从搜索界面传回来的数据
    public void addRadioButtonFromSearch(String text) {
        lists.add(text);
        RadioButton radioButton = new RadioButton(getActivity());
        RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, 50);
        layoutParams.setMargins(10, 10, 10, 10);
        radioButton.setLayoutParams(layoutParams);
        radioButton.setTextSize(12);
        radioButton.setButtonDrawable(android.R.color.transparent);//隐藏单选圆形按钮
        radioButton.setGravity(Gravity.CENTER);
        radioButton.setPadding(10, 10, 10, 10);
        radioButton.setText(text);
        radioButton.setTextColor(getResources().getColor(R.color.lr_font_color_selector));//设置选中/未选中的文字颜色
        radioButton.setBackgroundResource(R.drawable.selector_show_bg);//设置按钮选中/未选中的背景
        radioGroup.addView(radioButton);//将单选按钮添加到RadioGroup中
        radioButton.setChecked(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (food == null) {
            food = "家常菜";
        }
        refresh(food);
        getKitterInfo();
    }

    private void refresh(String food) {
      /*  HttpRequestManager.requestFood(food, new HttpRequestManager.loadFoodListener() {
           @Override
            public void foodShow(List<FoodFromWeb.Detials> bean) {*/
        List<FoodDetails> lists = new ArrayList<FoodDetails>();
               /* for (FoodFromWeb.Detials foodData : bean) {
                    FoodDetails food = new FoodDetails("","商家", foodData);
                   lists.add(food);
                }*/
        getFromBmobFood(lists);
    }
    /*    });
    }*/

    public void getFromBmobFood(final List<FoodDetails> detialsList) {
        BmobQuery<UploadMenuBean> query = new BmobQuery<>();
        query.include("user");
        query.findObjects(getActivity(), new FindListener<UploadMenuBean>() {
            @Override
            public void onSuccess(List<UploadMenuBean> list) {
                if (list.size() >= 0 && list != null) {
                    ptrListView.onRefreshComplete();
                    for (int i = 0; i < list.size(); i++) {
                        Log.i("TAG", "user=" + list.get(i).getUser().getUsername());
                        FoodFromWeb.Detials detials = new FoodFromWeb().new Detials();
                        detials.setBurden(list.get(i).getMenuHeader());
                        detials.setTitle(list.get(i).getMenuName());
                        detials.setSteps(list.get(i).getMenuStepList());
                        detials.setImtro(list.get(i).getMenuIntro());
                        detials.setIngredients(list.get(i).getMenuMaterial());
                        FoodDetails food = new FoodDetails(list.get(i).getUser().getHeaderUrl(), list.get(i).getUser().getUsername(), detials);
                        detialsList.add(food);
                    }
                    adapter.addDate(detialsList, true);

                }
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 200 && resultCode == getActivity().RESULT_OK) {
            food = data.getStringExtra("type");
            for (int i = 0; i < lists.size(); i++) {
                if (lists.get(i).equals(food)) {
                    RadioButton button = (RadioButton) radioGroup.findViewById(i + 1);
                    button.setChecked(true);
                    scrollView.scrollTo(i + 1, 0);
                    return;
                }
            }
            addRadioButtonFromSearch(food);
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                }
            });
        }
    }

    /**
     * 点击页面收起小键盘
     */
    public void UpdateUI(View view) {

        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View arg0, MotionEvent arg1) {
                    HideSoftKeyboard.hideSoftKeyboard(getActivity());
                    return false;
                }
            });
        }
        if (view instanceof ViewGroup) {

            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                UpdateUI(innerView);
            }
        }
    }
/**
 * 得到kitter的基本信息
 */
    public void getKitterInfo(){
        BmobQuery<UserInfo> bmobQuery=new BmobQuery<>();
        bmobQuery.findObjects(getActivity(), new FindListener<UserInfo>() {
            @Override
            public void onSuccess(List<UserInfo> list) {
                if(list!=null&&list.size()!=0){
                    kitterLists=list;
                    mAdapter.addDate(kitterLists,true);
                }
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }
}
