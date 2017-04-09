package com.example.lcc.mykitchen.utils;

import java.lang.ref.WeakReference;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

public class LoadImageUtils {

	public static void loadImage(String path,int height,int width){
		new BitmapWorkerTask().execute(path,height,width);
	}

	static class BitmapWorkerTask extends AsyncTask<Object, Void, Bitmap>{
		private WeakReference<ImageView> wReference;

		public BitmapWorkerTask(){

		}
		@Override
		protected Bitmap doInBackground(Object... params) {
			return null;
		}

	}

}
