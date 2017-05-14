package com.example.lcc.mykitchen.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lcc.mykitchen.R;
import com.example.lcc.mykitchen.entity.FoodDetails;
import com.example.lcc.mykitchen.manager.HttpRequestManager;

/**
 * Created by lcc on 2017/4/2.
 */
public class ShowFragmentAdapter1 extends MyBaseAdapter<FoodDetails> {
    public ShowFragmentAdapter1(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.inflater_showf_listview2, null);
            vHolder = new ViewHolder();
            vHolder.foodName = (TextView) convertView.findViewById(R.id.tv_item2_foodName);
            vHolder.kiterName = (TextView) convertView.findViewById(R.id.tv_item2_name);
            vHolder.foodContent = (TextView) convertView.findViewById(R.id.tv_item2_intro);
            vHolder.foodHeader= (ImageView) convertView.findViewById(R.id.img_item2);
            vHolder.kiterHeader= (ImageView) convertView.findViewById(R.id.img_item2_touxiang);
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

        if(!TextUtils.isEmpty(food.getKitterImgUrl())){
            HttpRequestManager.displayImage(food.getKitterImgUrl(),vHolder.kiterHeader);
        }else{
            vHolder.kiterHeader.setImageResource(R.drawable.logo);
        }
        //vHolder.foodBackground.setBackgroundResource(R.drawable.show_list);
        return convertView;
    }

    class ViewHolder {
        ImageView kiterHeader;
        TextView foodName;
        TextView kiterName;
        TextView foodContent;
        LinearLayout foodBackground;
        ImageView foodHeader;
    }
}
