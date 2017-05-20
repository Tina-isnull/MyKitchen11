package com.example.lcc.mykitchen.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lcc.mykitchen.R;
import com.example.lcc.mykitchen.entity.FoodDetails;
import com.example.lcc.mykitchen.entity.FoodFromWeb;
import com.example.lcc.mykitchen.entity.UploadMenuBean;
import com.example.lcc.mykitchen.entity.UserInfo;
import com.example.lcc.mykitchen.manager.HttpRequestManager;
import com.example.lcc.mykitchen.view.MyImageView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;

public class PersonDetialsActivity extends MyBaseActivity {
    private ImageView headerImg;
    private TextView name;
    private TextView intro;
    private GridView works;
    private UserInfo userInfo;
    private List<FoodDetails> detialsList;
    private GridViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_detials);
        userInfo = (UserInfo) getIntent().getSerializableExtra("intro");
        detialsList = new ArrayList<>();
        adapter = new GridViewAdapter();
        initialUI();
    }

    @Override
    public void initialUI() {
        //初始化ActionBar
        actionBar = (LinearLayout) findViewById(R.id.llActionbarId);
        initActionbar(R.drawable.go_back_normal, "个人信息", -1);
        headerImg = (ImageView) findViewById(R.id.img_person_header);
        name = (TextView) findViewById(R.id.tv_person_name);
        intro = (TextView) findViewById(R.id.tv_person_intro);
        works = (GridView) findViewById(R.id.Grid_person_zuo);
        HttpRequestManager.displayImage(userInfo.getHeaderUrl(), headerImg);
        name.setText(userInfo.getUsername());
        if (userInfo.getIntro() != null) {
            intro.setText(userInfo.getIntro());
        }
        works.setAdapter(adapter);

        works.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FoodDetails steps = (FoodDetails) adapter.getItem(position);
                if(userInfo.getObjectId().equals(BmobUser.getCurrentUser(PersonDetialsActivity.this,UserInfo.class).getObjectId())){
                    Intent intent = new Intent(PersonDetialsActivity.this, EditMenuActivity.class);
                    intent.putExtra("details", steps);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(PersonDetialsActivity.this, FoodDetailsActivity.class);
                    intent.putExtra("step", steps);
                    startActivity(intent);
                }


            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getFromBmobFood();
    }

    public void getFromBmobFood() {
        BmobQuery<UploadMenuBean> query = new BmobQuery<>();
        query.addWhereEqualTo("user", userInfo.getObjectId());
        query.findObjects(this, new FindListener<UploadMenuBean>() {
            @Override
            public void onSuccess(List<UploadMenuBean> list) {
                if (list.size() >= 0 && list != null) {
                    detialsList.clear();
                    for (int i = 0; i < list.size(); i++) {
                        FoodFromWeb.Detials detials = new FoodFromWeb().new Detials();
                        detials.setBurden(list.get(i).getMenuHeader());
                        detials.setTitle(list.get(i).getMenuName());
                        detials.setSteps(list.get(i).getMenuStepList());
                        detials.setImtro(list.get(i).getMenuIntro());
                        detials.setIngredients(list.get(i).getMenuMaterial());
                        FoodDetails food = new FoodDetails(list.get(i).getObjectId(),list.get(i).getUser().getHeaderUrl(), list.get(i).getUser().getUsername(), detials);
                        detialsList.add(food);
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    public class GridViewAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return detialsList.size();
        }

        @Override
        public Object getItem(int position) {
            return detialsList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        public View getView(int position, View convertView, ViewGroup parent) {
            viewHolder vh ;
            if (convertView == null) {
                vh=new viewHolder();
                convertView= LayoutInflater.from(PersonDetialsActivity.this).inflate(R.layout.item_gridview,null);
                vh.img= (MyImageView) convertView.findViewById(R.id.img_picture);
                convertView.setTag(vh);
            } else {
                vh = (viewHolder) convertView.getTag();
            }
            HttpRequestManager.displayImage(detialsList.get(position).getDetial().getBurden(), vh.img);
            return convertView;
        }
       class viewHolder{
           MyImageView img;
       }

    }
}
