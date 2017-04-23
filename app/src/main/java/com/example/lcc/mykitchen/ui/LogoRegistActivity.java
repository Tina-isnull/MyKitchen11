package com.example.lcc.mykitchen.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.lcc.mykitchen.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class LogoRegistActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo_rgist);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.btn_lore_loId)
    public void login(View view) {
        startActivity(new Intent(this,LoginActivity.class));
        finish();
    }

    @OnClick(R.id.btn_lore_reId)
    public void register(View view) {
        startActivity(new Intent(this,RegisterActivity.class));
        finish();
    }
}