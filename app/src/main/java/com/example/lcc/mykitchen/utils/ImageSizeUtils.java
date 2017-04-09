package com.example.lcc.mykitchen.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;

public class ImageSizeUtils {
	public static Bitmap decodeSampledBitmapFromResource(String path, int reqWidth, int reqHeight) {
		// 读取图片的宽高
		Options option = new Options();
		option.inJustDecodeBounds = true;// 只读取边界
		// 读取宽高，将数据封装到option中
		BitmapFactory.decodeFile(path, option);
		// 计算压缩比例
		option.inSampleSize = calculateInSampleSize(option, reqWidth, reqHeight);
		// 开始压缩
		option.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(path, option);
	}

	public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
		int height=options.outHeight;
		int width=options.outWidth;
		int inSampleSize=1;
		if(height>reqHeight||width>reqWidth){
			if(height>width){
				inSampleSize=Math.round((float)height/(float)reqHeight);
			}else{
				inSampleSize=Math.round((float)width/(float)reqWidth);
			}

		}
		return inSampleSize;

	}
}
