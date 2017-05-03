package com.example.lcc.mykitchen.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lcc.mykitchen.MyApp;
import com.example.lcc.mykitchen.R;
import com.example.lcc.mykitchen.make_children.CountAlterActivity;
import com.example.lcc.mykitchen.make_children.passWordAlterActivity;

import cn.bmob.v3.BmobUser;

import com.example.lcc.mykitchen.constant.Constant;
import com.example.lcc.mykitchen.utils.DataCleanManager;
import com.example.lcc.mykitchen.utils.SpUtils;

import java.io.File;

public class MakeActivity extends MyBaseActivity implements View.OnClickListener {
    private SpUtils sp;
    private TextView tvClear;
    private  File cacheFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make);
        initialUI();
    }

    @Override
    public void initialUI() {
        sp = new SpUtils(this, Constant.USER_INFO);
        //初始化ActionBar
        actionBar = (LinearLayout) findViewById(R.id.llActionbarId);
        initActionbar(R.drawable.go_back_normal, "设置", -1);
        //获得控件
        RelativeLayout makeCount = (RelativeLayout) findViewById(R.id.rl_make_countId);
        RelativeLayout makePassWord = (RelativeLayout) findViewById(R.id.rl_make_passWordId);
        RelativeLayout makeCache = (RelativeLayout) findViewById(R.id.rl_make_cacheId);
        RelativeLayout makeAboutMe = (RelativeLayout) findViewById(R.id.rl_make_aboutWeId);
        tvClear= (TextView) findViewById(R.id.tv_make_clear);
        Button quitBtn = (Button) findViewById(R.id.btn_make_logo);
        makeCount.setOnClickListener(this);
        makePassWord.setOnClickListener(this);
        makeCache.setOnClickListener(this);
        makeAboutMe.setOnClickListener(this);
        tvClear.setOnClickListener(this);
        quitBtn.setOnClickListener(this);

        getCacheSize();
    }

    private void getCacheSize() {
        cacheFile=this.getCacheDir();
        try {
            String size= DataCleanManager.getCacheSize(cacheFile);
            tvClear.setText(size);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_make_countId:
                String phone= MyApp.bmobUser.getMobilePhoneNumber();
                if(TextUtils.isEmpty(phone)){
                    startActivity(new Intent(this, CountAlterActivity.class));
                }else{
                    Toast.makeText(MakeActivity.this, "您已经绑定手机号", Toast.LENGTH_SHORT).show();;
                }
                break;

            case R.id.rl_make_passWordId:
                startActivity(new Intent(this, passWordAlterActivity.class));
                break;
            case R.id.rl_make_cacheId:
                DataCleanManager.deleteFolderFile(cacheFile.getAbsolutePath(),true);
                getCacheSize();
                break;
            case R.id.rl_make_aboutWeId:
                break;
            case R.id.btn_make_logo:
                BmobUser.logOut(this);
               // DataCleanManager.cleanApplicationData();
                sp.setLoginState(false);
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
            default:
                break;
        }

    }
}
