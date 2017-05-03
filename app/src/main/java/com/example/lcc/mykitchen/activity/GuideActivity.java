package com.example.lcc.mykitchen.activity;

        import android.os.Bundle;
        import android.support.v4.view.PagerAdapter;
        import android.support.v4.view.ViewPager;
        import android.support.v4.view.ViewPager.OnPageChangeListener;


        import android.app.Activity;
        import android.content.Intent;
        import android.view.View;
        import android.view.View.OnClickListener;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.ImageView.ScaleType;
        import android.widget.LinearLayout;
        import android.widget.TextView;

        import com.example.lcc.mykitchen.R;

public class GuideActivity extends Activity {
    private int[] imgs = new int[] { R.drawable.guide01, R.drawable.guide02, R.drawable.guide03,R.drawable.guide04 };
    private ImageView[] pointImgs;
    private TextView tvInput;
    private LinearLayout guideLLpoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        tvInput=(TextView) findViewById(R.id.tvInputId);
        // 加载viewPager添加监听
        loadViewPager();
        // 初始化小圆点的
        InitPoint();
    }

    private void loadViewPager() {
        ViewPager guideVpager = (ViewPager) findViewById(R.id.guideViewpagerId);
        //为滑动页添加动画
        guideVpager.setPageTransformer(true,new DepthPageTransformer());
        // 创建adapter
        GuideVpagerAdapter adapter = new GuideVpagerAdapter();
        // 添加适配器
        guideVpager.setAdapter(adapter);

        guideVpager.setOnPageChangeListener(new OnPageChangeListener() {
            // 滑动完成后的状态
            @Override
            public void onPageSelected(int arg0) {
                for (int i = 0; i < pointImgs.length; i++) {
                    if (i == arg0) {
                        pointImgs[i].setEnabled(true);
                    } else {
                        pointImgs[i].setEnabled(false);
                    }
                }
                if(arg0==3){
                    guideLLpoint.setVisibility(View.INVISIBLE);
                    tvInput.setVisibility(View.VISIBLE);
                    tvInput.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(GuideActivity.this,MainPagerActivity.class));
                            finish();
                        }
                    });
                }
            }

            // 滑动的时候的状态
            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            // 点击的时候和按下，松开调用的状态
            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }

    private void InitPoint() {
        // 设置小圆点的位置距离，注意必须加LinearLayout.
        guideLLpoint = (LinearLayout) findViewById(R.id.guideLLId);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(20,
               20);
        layoutParams.setMargins(10, 0, 10, 0);
        // 实例化小圆点数组
        pointImgs = new ImageView[imgs.length];
        // 遍历
        for (int i = 0; i < pointImgs.length; i++) {
            ImageView imagePoint = new ImageView(this);
            imagePoint.setLayoutParams(layoutParams);
            imagePoint.setBackgroundResource(R.drawable.guide_point_selector);
            if (i == 0) {
                imagePoint.setEnabled(true);
            } else {
                imagePoint.setEnabled(false);
            }
            // 将小圆点添加到数组中
            pointImgs[i] = imagePoint;
            // 将小圆点添加到布局中
            guideLLpoint.addView(imagePoint);
        }

    }

    class GuideVpagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imgs.length;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageview = new ImageView(GuideActivity.this);
            int img = imgs[position];
            imageview.setImageResource(img);
            imageview.setScaleType(ScaleType.FIT_XY);
            container.addView(imageview);
            return imageview;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

    }

}

