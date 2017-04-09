package com.example.lcc.mykitchen.adapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lcc.mykitchen.R;

import com.example.lcc.mykitchen.entity.CollectKiter;
import com.example.lcc.mykitchen.entity.UserInfo;
import com.example.lcc.mykitchen.manager.HttpRequestManager;

public class ForceManAdapter extends MyBaseAdapter<UserInfo> {

	public ForceManAdapter(Context context) {
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
		UserInfo collect=getItem(position);
		HttpRequestManager.displayImage(collect.getHeaderUrl(),viewHolder.imgHeader);
		viewHolder.name.setText(collect.getUsername());
		viewHolder.intro.setText(collect.getIntro());
		return convertView;
	}

	class ViewHolder {
		ImageView imgHeader;
		TextView name;
		TextView intro;
	}

}
