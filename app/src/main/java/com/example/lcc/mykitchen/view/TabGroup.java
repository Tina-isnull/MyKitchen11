package com.example.lcc.mykitchen.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.util.AttributeSet;
import android.widget.RadioGroup;

import com.example.lcc.mykitchen.R;

public class TabGroup extends RadioGroup {

	public TabGroup(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public TabGroup(Context context) {
		super(context);
	}

	int width, height, redStartPos,starPos;

	// 状态改变的时候会调用此方法
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		width = w;
		height = h;
		starPos=width/(getChildCount()*4);
	}

	// 绘制RadioButton下划线
	@Override
	protected void dispatchDraw(Canvas canvas) {
		super.dispatchDraw(canvas);
		canvas.save();
		Paint paint=new Paint();
		paint.setColor(getResources().getColor(R.color.themeColor));
		paint.setStyle(Style.STROKE);
		paint.setStrokeWidth(5);
		Path path=new Path();
		path.moveTo(redStartPos+starPos,height);
		path.lineTo(redStartPos+starPos+width/(getChildCount()*2), height);
		canvas.drawPath(path, paint);
		canvas.restore();
	}
	public void update(int index){
		redStartPos=(width/getChildCount())*index;
		invalidate();

	}
}
