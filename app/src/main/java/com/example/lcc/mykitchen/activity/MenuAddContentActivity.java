package com.example.lcc.mykitchen.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.example.lcc.mykitchen.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MenuAddContentActivity extends AppCompatActivity {
    @Bind(R.id.et_add_menu_content)
    EditText content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_add_content);
        ButterKnife.bind(this);
        String info = getIntent().getStringExtra("next");
        if (info != null) {
            content.setText(info);
        }
    }

    @OnClick(R.id.tv_addContent_cancel)
    public void cancel(View view) {
        finish();
    }

    @OnClick(R.id.tv_addContent_ok)
    public void finishContect(View view) {
        String minfo = content.getText().toString();
        Intent intent = new Intent();
        intent.putExtra("info", minfo);
        setResult(RESULT_OK, intent);
        finish();
    }
}
