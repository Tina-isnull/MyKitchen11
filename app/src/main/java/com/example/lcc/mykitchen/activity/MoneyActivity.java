package com.example.lcc.mykitchen.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.lcc.mykitchen.MyApp;
import com.example.lcc.mykitchen.R;
import com.example.lcc.mykitchen.entity.UserInfo;
import com.example.lcc.mykitchen.sharemultiphoto.PublishActivity;

public class MoneyActivity extends MyBaseActivity {
private TextView moneyCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money);
        initialUI();
    }

    @Override
    public void initialUI() {
        //初始化ActionBar
        actionBar = (LinearLayout)findViewById(R.id.llActionbarId);
        initActionbar(R.drawable.go_back_normal, "我的钱包", R.drawable.collect_delete);
        moneyCount= (TextView) findViewById(R.id.tv_money_count);
        UserInfo userInfo= MyApp.bmobUser;
        String money=userInfo.getMoney();
        if(TextUtils.isEmpty(money)){
            moneyCount.setText("0");
        }else{
            moneyCount.setText(money);
        }
        RelativeLayout get_one= (RelativeLayout) findViewById(R.id.rl_money_get_one);
        RelativeLayout get_two= (RelativeLayout) findViewById(R.id.rl_money_get_two);
        get_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MoneyActivity.this, PublishActivity.class));
            }
        });
        get_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MoneyActivity.this, UploadMenuActivity.class));
            }
        });
    }


}
