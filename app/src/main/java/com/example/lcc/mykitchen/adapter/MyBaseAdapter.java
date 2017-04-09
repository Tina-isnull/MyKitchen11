package com.example.lcc.mykitchen.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class MyBaseAdapter<T> extends BaseAdapter {
    protected Context context = null;
    protected LayoutInflater inflater = null;
    protected List<T> listData = new ArrayList<T>();

    public MyBaseAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    // 添加数据
    public void addDate(List<T> list, boolean isClear) {
        if (isClear) {
            this.listData.clear();
            this.listData.addAll(list);
        } else {
            this.listData.addAll(list);
        }
        this.notifyDataSetChanged();
    }

    // 删除数据
    public void removeData(T t) {
        if (this.listData.contains(t)) {
            this.listData.remove(t);
        }
        this.notifyDataSetChanged();

    }

    //得到所有的数据
    public List<T> getData() {
        return this.listData;
    }

    @Override
    public int getCount() {
        return this.listData.size();
    }

    @Override
    public T getItem(int position) {
        return this.listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public abstract View getView(int position, View convertView, ViewGroup parent);

}
