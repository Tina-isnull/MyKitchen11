package com.example.lcc.mykitchen.activity;

import android.content.Intent;
import android.os.Bundle;

import com.example.lcc.mykitchen.MyApp;
import com.example.lcc.mykitchen.adapter.CollectFoodAdapter;
import com.example.lcc.mykitchen.entity.CollectionFood;
import com.example.lcc.mykitchen.entity.FoodDetails;

import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.lcc.mykitchen.R;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

public class CollectActivity extends MyBaseActivity {
private   CollectFoodAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        //初始化界面
        initialUI();
    }

    @Override
    public void initialUI() {
        //初始化ActionBar
        actionBar = (LinearLayout)findViewById(R.id.llActionbarId);
        initActionbar(R.drawable.go_back_normal, "您的收藏", R.drawable.collect_delete);
        //初始化ListView
        initListView();
    }

    private void initListView() {
        ListView collectListview = (ListView) findViewById(R.id.listview_myf_collectId);
        adapter = new CollectFoodAdapter(this);
        collectListview.setAdapter(adapter);
        collectListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FoodDetails steps = adapter.getItem(position).getFoodDetails();
                Intent intent = new Intent(CollectActivity.this, FoodDetailsActivity.class);
                intent.putExtra("step", steps);
                startActivity(intent);
            }
        });
       /* DBUtils dbUtils=new DBUtils(this,1);
        List<CollectFood> list = dbUtils.queryFood();*/

    }

    @Override
    protected void onResume() {
        super.onResume();
        BmobQuery<CollectionFood> query=new BmobQuery<>();
        query.addWhereEqualTo("userId",MyApp.bmobUser.getObjectId());
        query.findObjects(CollectActivity.this, new FindListener<CollectionFood>() {
            @Override
            public void onSuccess(List<CollectionFood> list) {
                if(list!=null&&list.size()!=0){
                    adapter.addDate(list, true);

                }
            }

            @Override
            public void onError(int i, String s) {

            }
        });

    }
}