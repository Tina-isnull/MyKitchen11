package com.example.lcc.mykitchen.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.lcc.mykitchen.R;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

public class RegisterActivity extends MyBaseActivity {
    @Bind(R.id.et_register_phoneId)
    EditText identification;
    @Bind(R.id.et_register_password)
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        //初始化控件
        initialUI();
    }

    @Override
    public void initialUI() {
        //设置actionBar
        actionBar = (LinearLayout) findViewById(R.id.include_register_id);
        initActionbar(R.drawable.go_back_normal, "注册", -1);
    }

    @OnClick(R.id.btn_register_Id)
    public void register(View view) {
        String mIdentification = identification.getText().toString();
        String mPassword = password.getText().toString();
        if (TextUtils.isEmpty(mIdentification) || TextUtils.isEmpty(mPassword)) {
            Toast.makeText(this, "用户名或密码不可为空", Toast.LENGTH_SHORT).show();
        } else {
            BmobUser bu = new BmobUser();
            String md5=new String(Hex.encodeHex(DigestUtils.sha(mPassword)));
            bu.setUsername(mIdentification);
            bu.setPassword(md5);
            bu.signUp(this, new SaveListener() {
                @Override
                public void onSuccess() {
                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                    finish();
                }

                @Override
                public void onFailure(int code, String msg) {
                    Toast.makeText(RegisterActivity.this, "注册失败"+code, Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}