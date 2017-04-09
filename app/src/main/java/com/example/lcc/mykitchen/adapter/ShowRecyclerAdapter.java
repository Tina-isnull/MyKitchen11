package com.example.lcc.mykitchen.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lcc.mykitchen.R;
import com.example.lcc.mykitchen.entity.UserInfo;
import com.example.lcc.mykitchen.manager.HttpRequestManager;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lcc on 2017/4/3.
 */
public class ShowRecyclerAdapter extends RecyclerView.Adapter<ShowRecyclerAdapter.MyViewHolder> {
    Context mContext;
    List<UserInfo> mData;
    OnItemClickLitener mOnItemClickListener;

    public ShowRecyclerAdapter(Context context, List<UserInfo> data) {
        this.mContext = context;
        this.mData = data;

    }
    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }
    // 添加数据
    public void addDate(List<UserInfo> list, boolean isClear) {
        if (isClear) {
            this.mData.clear();
            this.mData.addAll(list);
        } else {
            this.mData.addAll(list);
        }
        this.notifyDataSetChanged();
    }
    public UserInfo getItem(int position){
        return mData.get(position);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        MyViewHolder myViewHolder = new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.inflater_show_reyclerview, viewGroup, false));
        return myViewHolder;
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public void onBindViewHolder(final MyViewHolder myViewHolder, final int i) {
        myViewHolder.kitterName.setText(mData.get(i).getUsername());
        myViewHolder.kitterInfo.setText(mData.get(i).getIntro());
        if(mData.get(i).getHeaderUrl()==null){
            myViewHolder.kitterHeader.setImageResource(R.drawable.header0);
        }else{
            HttpRequestManager.displayImage(mData.get(i).getHeaderUrl(),myViewHolder.kitterHeader);
        }
        myViewHolder.kitterHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.OnClickItem(myViewHolder.itemView,i);
            }
        });

    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView kitterInfo;
        ImageView kitterHeader;
        TextView kitterName;

        public MyViewHolder(View itemView) {
            super(itemView);
            kitterInfo = (TextView) itemView.findViewById(R.id.tv_item_info);
            kitterName = (TextView) itemView.findViewById(R.id.tv_item_name);
            kitterHeader = (ImageView) itemView.findViewById(R.id.circle_item_show);
        }
    }
    public interface OnItemClickLitener {
        void OnClickItem(View view, int position);
    }
}


