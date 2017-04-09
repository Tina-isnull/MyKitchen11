package com.example.lcc.mykitchen.adapter;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.lcc.mykitchen.R;
import com.example.lcc.mykitchen.video.CommonVideoView;

import com.example.lcc.mykitchen.entity.FoodVideo;
import com.example.lcc.mykitchen.manager.HttpRequestManager;

public class MethFragmentAdapter extends MyBaseAdapter<FoodVideo> {

    public MethFragmentAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vHolder = null;
        if (convertView == null) {
            vHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.inflater_methord_listview, null);
            vHolder.kiterImg = (ImageView) convertView.findViewById(R.id.imgListHeadId);
            vHolder.KiterName = (TextView) convertView.findViewById(R.id.tvListNameId);
            vHolder.foodVideo = (CommonVideoView) convertView.findViewById(R.id.videoListId);
            vHolder.foodVideoName = (TextView) convertView.findViewById(R.id.tvListVideoNameId);
            convertView.setTag(vHolder);
        } else {
            vHolder = (ViewHolder) convertView.getTag();
        }
        //初始化ListView数据
        FoodVideo foodVideo = (FoodVideo) getItem(position);
        HttpRequestManager.displayImage(foodVideo.getUserInfo().getHeaderUrl(), vHolder.kiterImg);
        vHolder.KiterName.setText(foodVideo.getUserInfo().getUsername());
        vHolder.foodVideo.showTthumbnail(foodVideo.getVideoUrl());
        vHolder.foodVideo.getURL(foodVideo.getVideoUrl());
        vHolder.foodVideoName.setText(foodVideo.getVideoName());

        return convertView;
    }

    class ViewHolder {
        ImageView kiterImg;//厨师的头像
        TextView KiterName;//厨师的名字
        CommonVideoView foodVideo;//美食视频
        TextView foodVideoName;//视频的名字

    }
}
