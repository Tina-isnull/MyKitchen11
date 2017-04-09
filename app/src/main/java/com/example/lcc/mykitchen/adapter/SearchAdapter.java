package com.example.lcc.mykitchen.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lcc.mykitchen.R;
import com.example.lcc.mykitchen.entity.TypeFoodsShow;
import com.example.lcc.mykitchen.view.MyGridView;

/**
 * Created by lcc on 2017/1/19.
 */
public class SearchAdapter extends MyBaseAdapter<TypeFoodsShow> {
    OnItemClickGridViewListener listener;
    public SearchAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if(convertView==null){
            convertView=inflater.inflate(R.layout.inflater_search_listview,null);
            vh=new ViewHolder();
            vh.title= (TextView) convertView.findViewById(R.id.tv_search_title);
            vh.gridView= (MyGridView) convertView.findViewById(R.id.gv_search_foodType);
            convertView.setTag(vh);
        }else{
            vh= (ViewHolder) convertView.getTag();
        }
        TypeFoodsShow typeFoods=getItem(position);
        if(!typeFoods.getName().equals("")){
            vh.title.setText(typeFoods.getName());
        }
        //将字符串转化为集合
        String typeBuilder=typeFoods.getList();
        String[] typeArray=typeBuilder.split(",");
        final ArrayAdapter<String> adapter=new ArrayAdapter<String>(context,R.layout.inflater_searchadapter_textview,R.id.text_search,typeArray);
        vh.gridView.setAdapter(adapter);
        vh.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item=adapter.getItem(position);
                Toast.makeText(context, item, Toast.LENGTH_SHORT).show();
                listener.getFoodType(item);

            }
        });
        return convertView;
    }
    public void setOnItemClickGridViewListener(OnItemClickGridViewListener listener){
        this.listener=listener;
    }
    class ViewHolder{
        TextView title;
        MyGridView gridView;
    }
    public interface OnItemClickGridViewListener{
        void getFoodType(String type);
    }
}
