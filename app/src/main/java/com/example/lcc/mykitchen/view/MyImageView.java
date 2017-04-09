package com.example.lcc.mykitchen.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by lcc on 2017/4/8.
 */
public class MyImageView extends ImageView{


    public MyImageView(Context context) {
        super(context);
    }

    public MyImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, widthMeasureSpec);
        }

}
