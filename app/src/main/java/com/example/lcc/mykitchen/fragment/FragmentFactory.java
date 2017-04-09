package com.example.lcc.mykitchen.fragment;

import android.support.v4.app.Fragment;
import android.util.Log;

import com.example.lcc.mykitchen.R;

/** 暂时不用
 * @author LCC 定义一个工厂类 来实现根据下标的位置返回相应的Fragment
 */
public class FragmentFactory {
	public static Fragment getInstanceByIndex(int index) {
		Fragment fragment=null;
		switch (index) {
			case R.id.radio0:
				fragment=new ShowFragment();
				break;
			case R.id.radio1:
				fragment=new MethordFragment();
				break;
			case R.id.radio2:
				fragment=new ShareFragment();
				break;
			case R.id.radio3:
				fragment=new MyFragment();
				break;
			default:
				Log.i("TAG", "FragmentFactory.error");
				break;
		}
		return fragment;

	}

}
