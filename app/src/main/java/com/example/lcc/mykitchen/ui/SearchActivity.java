package com.example.lcc.mykitchen.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.example.lcc.mykitchen.R;

import java.util.ArrayList;
import java.util.List;

import com.example.lcc.mykitchen.adapter.SearchAdapter;
import com.example.lcc.mykitchen.entity.TypeFoodsFromWeb;
import com.example.lcc.mykitchen.entity.TypeFoodsShow;
import com.example.lcc.mykitchen.manager.HttpRequestManager;
import com.example.lcc.mykitchen.utils.DBUtils;

/**
 * Created by lcc on 2017/1/19.
 */
public class SearchActivity extends Activity {
    ListView listView;
    SearchAdapter adapter;
    List<TypeFoodsShow> datas=new ArrayList<>();
    DBUtils dbUtils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initUI();
    }

    private void initUI() {
        dbUtils=new DBUtils(this,4);
        listView= (ListView) findViewById(R.id.list_search);
        adapter=new SearchAdapter(this);
        listView.setAdapter(adapter);
        //点击菜品的种类，请求相应的数据
        adapter.setOnItemClickGridViewListener(new SearchAdapter.OnItemClickGridViewListener() {

            @Override
            public void getFoodType(String type) {
                Intent intent=new Intent();
                intent.putExtra("type",type);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<TypeFoodsShow> datas=dbUtils.queryTypeFoodShow();
        if(datas!=null&&datas.size()!=0){
            adapter.addDate(datas,true);
        }else{
            refresh();
        }
    }

    private void refresh() {
        HttpRequestManager.requestFoodType(new HttpRequestManager.loadFoodTypeListener() {
            @Override
            public void foodTypeShow(List<TypeFoodsFromWeb.FoodResult> bean) {
                for(int i=0;i<bean.size();i++){
                    TypeFoodsShow typeFood=new TypeFoodsShow();
                    List<TypeFoodsFromWeb.Menus> menu=bean.get(i).getList();
                    typeFood.setName(bean.get(i).getName());
                    typeFood.setId(bean.get(i).getParentId());
                    StringBuilder builder=new StringBuilder();
                    for (int j=0;j<menu.size();j++){
                        builder.append(menu.get(j).getName()).append(",");
                    }
                    builder.deleteCharAt(builder.length()-1);
                    Log.i("TAG","builder"+builder.toString());
                    typeFood.setList(builder.toString());
                    datas.add(typeFood);
                }
                adapter.addDate(datas,true);
                dbUtils.addTypeFoodBatch(datas);

            }

        });
    }
}
