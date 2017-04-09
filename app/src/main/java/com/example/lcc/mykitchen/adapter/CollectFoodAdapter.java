package com.example.lcc.mykitchen.adapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lcc.mykitchen.R;

import com.example.lcc.mykitchen.entity.CollectFood;
import com.example.lcc.mykitchen.entity.CollectionFood;
import com.example.lcc.mykitchen.manager.HttpRequestManager;

public class CollectFoodAdapter extends MyBaseAdapter<CollectionFood> {

	public CollectFoodAdapter(Context context) {
		super(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.inflate_collect_foodman_listview, null);
			viewHolder = new ViewHolder();
			viewHolder.imgHeader = (ImageView) convertView.findViewById(R.id.img_collecaty_headerId);
			viewHolder.name = (TextView) convertView.findViewById(R.id.tv_collecaty_nameId);
			viewHolder.intro = (TextView) convertView.findViewById(R.id.tv_collecaty_introId);
			convertView.setTag(viewHolder);
		}else{
			viewHolder=(ViewHolder) convertView.getTag();
		}
		CollectionFood collect=getItem(position);
		if(collect.getFoodDetails().getDetial().getAlbums()!=null&&collect.getFoodDetails().getDetial().getAlbums().size()>0){
			HttpRequestManager.displayImage(collect.getFoodDetails().getDetial().getAlbums().get(0),viewHolder.imgHeader);
		}else{
			HttpRequestManager.displayImage(collect.getFoodDetails().getDetial().getBurden(),viewHolder.imgHeader);
		}
		viewHolder.name.setText(collect.getFoodDetails().getDetial().getTitle());
		viewHolder.intro.setText(collect.getFoodDetails().getDetial().getImtro());
		return convertView;
	}

	class ViewHolder {
		ImageView imgHeader;
		TextView name;
		TextView intro;
	}

}
