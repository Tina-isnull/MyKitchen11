package com.example.lcc.mykitchen.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by lcc on 2017/4/9.
 */
public class MyListVIew extends ListView {
    public MyListVIew(Context context) {
        super(context);
    }

    public MyListVIew(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyListVIew(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);

        super.onMeasure(widthMeasureSpec, expandSpec);

    }

}
