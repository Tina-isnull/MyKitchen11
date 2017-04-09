package com.example.lcc.mykitchen.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lcc.mykitchen.R;
import com.example.lcc.mykitchen.entity.UserInfo;
import com.example.lcc.mykitchen.manager.HttpRequestManager;

/**
 * Created by lcc on 2017/4/9.
 */
public class ArrayCommentAdapter extends MyBaseAdapter<String> {
    public ArrayCommentAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_comment, null);
            viewHolder = new ViewHolder();
            viewHolder.comment = (TextView) convertView.findViewById(R.id.item_tv_comment);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder) convertView.getTag();
        }
        viewHolder.comment.setText(listData.get(position));
        return convertView;
    }

    class ViewHolder {
        TextView comment;
    }

}
