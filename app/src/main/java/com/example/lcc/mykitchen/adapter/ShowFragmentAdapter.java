package com.example.lcc.mykitchen.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lcc.mykitchen.R;

import com.example.lcc.mykitchen.entity.FoodDetails;
import com.example.lcc.mykitchen.manager.HttpRequestManager;

public class ShowFragmentAdapter extends MyBaseAdapter<FoodDetails> {

	public ShowFragmentAdapter(Context context) {
		super(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder vHolder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.inflater_showf_listview, null);
			vHolder = new ViewHolder();
			vHolder.foodName = (TextView) convertView.findViewById(R.id.tvShowFoodNameId);
			vHolder.kiterName = (TextView) convertView.findViewById(R.id.tvShowKiterNameId);
			vHolder.foodContent = (TextView) convertView.findViewById(R.id.tvShowFoodContentId);
			vHolder.foodBackground = (LinearLayout) convertView.findViewById(R.id.llShowFId);
            vHolder.foodHeader= (ImageView) convertView.findViewById(R.id.img_show_foodHeader);
			convertView.setTag(vHolder);
		} else {
			vHolder = (ViewHolder) convertView.getTag();
		}
		FoodDetails food = getItem(position);
		vHolder.foodName.setText(food.getDetial().getTitle());
		vHolder.kiterName.setText(food.getName());
		vHolder.foodContent.setText(food.getDetial().getImtro());
		if(food.getDetial().getAlbums()!=null&&food.getDetial().getAlbums().size()>0){
			HttpRequestManager.displayImage(food.getDetial().getAlbums().get(0),vHolder.foodHeader);
		}else{
			HttpRequestManager.displayImage(food.getDetial().getBurden(),vHolder.foodHeader);
		}
		//vHolder.foodBackground.setBackgroundResource(R.drawable.show_list);
		return convertView;
	}

	class ViewHolder {
		TextView foodName;
		TextView kiterName;
		TextView foodContent;
		LinearLayout foodBackground;
		ImageView foodHeader;
	}

}
