package com.example.lcc.mykitchen.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lcc.mykitchen.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import com.example.lcc.mykitchen.entity.FoodFromWeb;
import com.example.lcc.mykitchen.manager.HttpRequestManager;

/**
 * Created by lcc on 2017/1/17.
 */
public class FoodDetailAdapter extends MyBaseAdapter<FoodFromWeb.Steps> {
    public FoodDetailAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vHolder;
        if(convertView==null){
            convertView=inflater.inflate(R.layout.inflater_list_food_detials,null);
            vHolder=new ViewHolder(convertView);
            convertView.setTag(vHolder);
        }else{
            vHolder= (ViewHolder) convertView.getTag();
        }
        FoodFromWeb.Steps steps=getItem(position);
        HttpRequestManager.displayImage(steps.getImg(),vHolder.img);
        vHolder.step.setText(steps.getStep());
        return convertView;
    }
    class ViewHolder{
        @Bind(R.id.img_food_detail_intro)
        ImageView img;
        @Bind(R.id.tv_food_detail_intro)
        TextView step;
        public ViewHolder(View convertView){
            ButterKnife.bind(this,convertView);
        }
    }
}
