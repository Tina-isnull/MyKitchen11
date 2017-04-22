package com.example.lcc.mykitchen.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lcc.mykitchen.R;
import com.example.lcc.mykitchen.entity.FootprintBean;
import com.example.lcc.mykitchen.manager.HttpRequestManager;

/**
 * Created by lcc on 2017/4/22.
 */
public class FootprintAdapter extends MyBaseAdapter<FootprintBean>  {
    public FootprintAdapter(Context context) {
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
        FootprintBean footprint=getItem(position);
        if(footprint.getFoodDetails().getDetial().getAlbums()!=null&&footprint.getFoodDetails().getDetial().getAlbums().size()>0){
            HttpRequestManager.displayImage(footprint.getFoodDetails().getDetial().getAlbums().get(0),viewHolder.imgHeader);
        }else{
            HttpRequestManager.displayImage(footprint.getFoodDetails().getDetial().getBurden(),viewHolder.imgHeader);
        }
        viewHolder.name.setText(footprint.getFoodDetails().getDetial().getTitle());
        viewHolder.intro.setText(footprint.getFoodDetails().getDetial().getImtro());
        return convertView;
    }
    class ViewHolder {
        ImageView imgHeader;
        TextView name;
        TextView intro;
    }
}
