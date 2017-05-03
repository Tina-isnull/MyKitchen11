package com.example.lcc.mykitchen.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.lcc.mykitchen.MyApp;
import com.example.lcc.mykitchen.R;
import com.example.lcc.mykitchen.adapter.FootprintAdapter;
import com.example.lcc.mykitchen.entity.FoodDetails;
import com.example.lcc.mykitchen.entity.FootprintBean;
import com.example.lcc.mykitchen.entity.UserInfo;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;

public class FootprintActivity extends MyBaseActivity {
    private  FootprintAdapter adapter;
    private List<FootprintBean> footprints;
    private UserInfo bmobUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_footprint);
        initialUI();
    }

    @Override
    public void initialUI() {
        //初始化ActionBar
        actionBar = (LinearLayout)findViewById(R.id.llActionbarId);
        initActionbar(R.drawable.go_back_normal, "我的足迹", R.drawable.collect_delete);
        bmobUser = BmobUser.getCurrentUser(this, UserInfo.class);
        footprints=new ArrayList<>();
        ListView FootListView = (ListView) findViewById(R.id.listview_myf_collectId);
        adapter = new FootprintAdapter(this);
        FootListView.setAdapter(adapter);
        FootListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FoodDetails steps = adapter.getItem(position).getFoodDetails();
                Intent intent = new Intent(FootprintActivity.this, FoodDetailsActivity.class);
                intent.putExtra("step", steps);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        BmobQuery<FootprintBean> query=new BmobQuery<>();
        query.addWhereEqualTo("userId", bmobUser.getObjectId());
        query.findObjects(FootprintActivity.this, new FindListener<FootprintBean>() {
            @Override
            public void onSuccess(List<FootprintBean> list) {
                footprints.clear();
                if(list!=null&&list.size()!=0){
                    for(int i=0;i<list.size();i++){
                        String id=list.get(i).getFoodDetails().getDetial().getId();
                        String title=list.get(i).getFoodDetails().getDetial().getTitle();
                        if(footprints==null||footprints.size()==0){
                            footprints.add(list.get(i));

                        }
                       for(int j=0;j<footprints.size();j++){
                           String footId=footprints.get(j).getFoodDetails().getDetial().getId();
                           String footTitle=footprints.get(j).getFoodDetails().getDetial().getTitle();
                           if(footId==null){
                               if(!footTitle.equals(title)&&j==footprints.size()-1){
                                   footprints.add(list.get(i));
                               }if(footTitle.equals(title)){
                                   break;
                               }
                           }else{
                               if(!footId.equals(id)&&j==footprints.size()-1){
                                   footprints.add(list.get(i));
                               }if(footId.equals(id)){
                                   break;
                               }
                           }

                       }

                    }
                    adapter.addDate(footprints, true);


                }
            }

            @Override
            public void onError(int i, String s) {

            }
        });

    }
}
