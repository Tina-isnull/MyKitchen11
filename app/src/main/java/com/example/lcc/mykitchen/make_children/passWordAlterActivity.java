package com.example.lcc.mykitchen.make_children;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.lcc.mykitchen.ui.MyBaseActivity;
import com.example.lcc.mykitchen.R;

public class passWordAlterActivity extends MyBaseActivity {

    private EditText old;
    private EditText newP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_word_alter);
        initialUI();
    }

    @Override
    public void initialUI() {
        //设置ActionBar
        actionBar = (LinearLayout)findViewById(R.id.llActionbarId);
        initActionbar(R.drawable.go_back_normal,"修改密码",-1);
        //获得控件
        old = (EditText) findViewById(R.id.et_oldPassId);
        newP = (EditText) findViewById(R.id.et_newPassId);
        Button commit= (Button) findViewById(R.id.btn_commitId);

        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPass= old.getText().toString();
                String newPass= newP.getText().toString();
            }
        });
    }
}
