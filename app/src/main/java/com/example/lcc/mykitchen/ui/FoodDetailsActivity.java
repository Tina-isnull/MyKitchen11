package com.example.lcc.mykitchen.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.lcc.mykitchen.R;

import com.example.lcc.mykitchen.adapter.FoodDetailAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.example.lcc.mykitchen.entity.FoodDetails;
import com.example.lcc.mykitchen.manager.HttpRequestManager;

public class FoodDetailsActivity extends MyBaseActivity {
    @Bind(R.id.list_food_detail)
    ListView listview;
    String material;
    FoodDetailAdapter adapter;
    private FoodDetails detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detials);
        detail = (FoodDetails) getIntent().getSerializableExtra("step");
        ButterKnife.bind(this);
        initialUI();
    }

    @Override
    public void initialUI() {
        actionBar = (LinearLayout) findViewById(R.id.llActionbarId);
        initActionbar(R.drawable.go_back_normal, "详情", R.drawable.like);
        initListView();
    }

    private void initListView() {
        View view=getLayoutInflater().inflate(R.layout.header_list_food_detials,listview,false);
        ImageView foodImg= (ImageView) view.findViewById(R.id.img_list_header);
        final TextView foodIntro= (TextView) view.findViewById(R.id.tv_list_header_content);
        TextView foodMaterial= (TextView) view.findViewById(R.id.tv_list_header_material);
        foodIntro.setOnClickListener(new View.OnClickListener() {
            Boolean flag = true;
            @Override
            public void onClick(View v) {
                if(flag){
                    flag = false;
                    foodIntro.setEllipsize(null); // 展开
                    foodIntro.setSingleLine(flag);
                }else{
                    flag = true;
                    foodIntro.setEllipsize(TextUtils.TruncateAt.END); // 收缩
                    foodIntro.setSingleLine(flag);
                }
            }
        });





        //初始化数据
        if(detail.getDetial().getAlbums()!=null&&detail.getDetial().getAlbums().size()>0){
            HttpRequestManager.displayImage(detail.getDetial().getAlbums().get(0),foodImg);
            material=detail.getDetial().getIngredients()+detail.getDetial().getBurden();
        }else{
            HttpRequestManager.displayImage(detail.getDetial().getBurden(),foodImg);
            material=detail.getDetial().getIngredients();
        }
        //对材料进行裁剪
        String [] temp=null;
        temp=material.split(";");
        StringBuilder builder=new StringBuilder();
        for (int i=0;i<temp.length;i++){
            builder.append(temp[i]).append('\n');
        }
        builder.deleteCharAt(builder.length()-1);
        material=builder.toString();

        foodIntro.setText(detail.getDetial().getImtro());
        foodMaterial.setText(material);
        listview.addHeaderView(view);
        adapter=new FoodDetailAdapter(this);
        adapter.addDate(detail.getDetial().getSteps(),false);
        listview.setAdapter(adapter);
    }
}
