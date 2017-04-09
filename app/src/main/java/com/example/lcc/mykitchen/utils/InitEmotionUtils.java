package com.example.lcc.mykitchen.utils;

import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.example.lcc.mykitchen.R;
import com.example.lcc.mykitchen.adapter.EmotionGvAdapter;
import com.example.lcc.mykitchen.adapter.EmotionPagerAdapter;
import com.example.lcc.mykitchen.utils.EmotionUtils;
import com.example.lcc.mykitchen.utils.sharemultiphoto.DisplayUtils;


import java.util.ArrayList;
import java.util.List;


/**
 * Created by lcc on 16/7/14.
 */
public class InitEmotionUtils {
    /**
     * @param mActivity            上下文
     * @param vp_emotion_dashboard viewpager  用于承载表情
     * @param et_emotion           EditText编辑框
     * @param views                需要的表情布局view
     */
    public static void initEmotion(final Activity mActivity, ViewPager vp_emotion_dashboard, EditText et_emotion, final ArrayList<View> views) {

        // 获取屏幕宽度
        int gvWidth = DisplayUtils.getScreenWidthPixels(mActivity);
        // 表情边距
        int spacing = DisplayUtils.dp2px(mActivity, 18);
        int verSpacing = DisplayUtils.dp2px(mActivity, 21);
        // GridView中item的宽度
        int itemWidth = (gvWidth - spacing * 8) / 7;
        int gvHeight = itemWidth * 3 + verSpacing * 4;
        int gv_pading = DisplayUtils.dp2px(mActivity, 0);

        List<GridView> gvs = new ArrayList<GridView>();
        List<String> emotionNames = new ArrayList<String>();
        // 遍历所有的表情名字
        for (String emojiName : EmotionUtils.emojiMap.keySet()) {
            if (!emotionNames.equals("[ht]")) {
                emotionNames.add(emojiName);
            }
            // 每20个表情作为一组,同时添加到ViewPager对应的view集合中
            if (emotionNames.size() == 20) {
                GridView gv = createEmotionGridView(mActivity, emotionNames, gvWidth, spacing, itemWidth, gvHeight, et_emotion);
                gvs.add(gv);
                // 添加完一组表情,重新创建一个表情名字集合
                emotionNames = new ArrayList<String>();
            }
        }

        //ToDo 苏艳改过emotionNames.size() > 0，处理热门的添加
        // 检查最后是否有不足20个表情的剩余情况
        if (emotionNames.size() > 1) {
            GridView gv = createEmotionGridView(mActivity, emotionNames, gvWidth, spacing, itemWidth, gvHeight, et_emotion);
            gvs.add(gv);
        }

        // 将多个GridView添加显示到ViewPager中
        EmotionPagerAdapter emotionPagerGvAdapter = new EmotionPagerAdapter(gvs);
        vp_emotion_dashboard.setAdapter(emotionPagerGvAdapter);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(gvWidth, gvHeight);
        vp_emotion_dashboard.setLayoutParams(params);
        vp_emotion_dashboard.setCurrentItem(0);
        vp_emotion_dashboard.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                if (position == 0) {
                    views.get(0).setBackgroundResource(R.drawable.shape_emotion_black);
                    views.get(1).setBackgroundResource(R.drawable.shape_emotion_gray);
                    views.get(2).setBackgroundResource(R.drawable.shape_emotion_gray);
                    views.get(3).setBackgroundResource(R.drawable.shape_emotion_gray);
                    views.get(4).setBackgroundResource(R.drawable.shape_emotion_gray);
                    views.get(5).setBackgroundResource(R.drawable.shape_emotion_gray);
                }
                if (position == 1) {
                    views.get(1).setBackgroundResource(R.drawable.shape_emotion_black);
                    views.get(0).setBackgroundResource(R.drawable.shape_emotion_gray);
                    views.get(2).setBackgroundResource(R.drawable.shape_emotion_gray);
                    views.get(3).setBackgroundResource(R.drawable.shape_emotion_gray);
                    views.get(4).setBackgroundResource(R.drawable.shape_emotion_gray);
                    views.get(5).setBackgroundResource(R.drawable.shape_emotion_gray);
                }
                if (position == 2) {
                    views.get(2).setBackgroundResource(R.drawable.shape_emotion_black);
                    views.get(0).setBackgroundResource(R.drawable.shape_emotion_gray);
                    views.get(1).setBackgroundResource(R.drawable.shape_emotion_gray);
                    views.get(3).setBackgroundResource(R.drawable.shape_emotion_gray);
                    views.get(4).setBackgroundResource(R.drawable.shape_emotion_gray);
                    views.get(5).setBackgroundResource(R.drawable.shape_emotion_gray);
                }
                if (position == 3) {
                    views.get(3).setBackgroundResource(R.drawable.shape_emotion_black);
                    views.get(0).setBackgroundResource(R.drawable.shape_emotion_gray);
                    views.get(1).setBackgroundResource(R.drawable.shape_emotion_gray);
                    views.get(2).setBackgroundResource(R.drawable.shape_emotion_gray);
                    views.get(4).setBackgroundResource(R.drawable.shape_emotion_gray);
                    views.get(5).setBackgroundResource(R.drawable.shape_emotion_gray);
                }
                if (position == 4) {
                    views.get(4).setBackgroundResource(R.drawable.shape_emotion_black);
                    views.get(0).setBackgroundResource(R.drawable.shape_emotion_gray);
                    views.get(2).setBackgroundResource(R.drawable.shape_emotion_gray);
                    views.get(3).setBackgroundResource(R.drawable.shape_emotion_gray);
                    views.get(1).setBackgroundResource(R.drawable.shape_emotion_gray);
                    views.get(5).setBackgroundResource(R.drawable.shape_emotion_gray);
                }
                if (position == 5) {
                    views.get(5).setBackgroundResource(R.drawable.shape_emotion_black);
                    views.get(0).setBackgroundResource(R.drawable.shape_emotion_gray);
                    views.get(2).setBackgroundResource(R.drawable.shape_emotion_gray);
                    views.get(3).setBackgroundResource(R.drawable.shape_emotion_gray);
                    views.get(1).setBackgroundResource(R.drawable.shape_emotion_gray);
                    views.get(4).setBackgroundResource(R.drawable.shape_emotion_gray);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    /**
     * 创建显示表情的GridView
     */
    public static GridView createEmotionGridView(final Activity mActivity, List<String> emotionNames, int gvWidth, int padding, int itemWidth, int gvHeight, final EditText et_emotion) {
        // 创建GridView
        GridView gv = new GridView(mActivity);
        gv.setBackgroundColor(android.graphics.Color.parseColor("#f4f4f4"));
        gv.setSelector(android.R.color.transparent);
        gv.setNumColumns(7);
        gv.setPadding(padding, padding, padding, padding);
        gv.setHorizontalSpacing(padding);
        gv.setVerticalSpacing(padding);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(gvWidth, gvHeight);
        gv.setLayoutParams(params);
        // 给GridView设置表情图片
        EmotionGvAdapter adapter = new EmotionGvAdapter(mActivity, emotionNames, itemWidth);
        gv.setAdapter(adapter);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object itemAdapter = parent.getAdapter();

                if (itemAdapter instanceof EmotionGvAdapter) {
                    // 点击的是表情
                    EmotionGvAdapter emotionGvAdapter = (EmotionGvAdapter) itemAdapter;

                    if (position == emotionGvAdapter.getCount() - 1) {
                        // 如果点击了最后一个回退按钮,则调用删除键事件
                        et_emotion.dispatchKeyEvent(new KeyEvent(
                                KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
                    } else {
                        // 如果点击了表情,则添加到输入框中
                        String emotionName = emotionGvAdapter.getItem(position);

                        // 获取当前光标位置,在指定位置上添加表情图片文本
                        int curPosition = et_emotion.getSelectionStart();
                        StringBuilder sb = new StringBuilder(et_emotion.getText().toString());
                        sb.insert(curPosition, emotionName);

                        // 特殊文字处理,将表情等转换一下
                        et_emotion.setText(StringUtils.getEmotionContent(
                                mActivity, et_emotion, sb.toString()));

                        // 将光标设置到新增完表情的右侧
                        et_emotion.setSelection(curPosition + emotionName.length());
                    }

                }
            }
        });
        return gv;
    }


}
