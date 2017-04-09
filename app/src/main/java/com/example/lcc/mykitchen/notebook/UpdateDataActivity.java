
package com.example.lcc.mykitchen.notebook;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.lcc.mykitchen.R;

import com.example.lcc.mykitchen.db.DBUtils;

public class UpdateDataActivity extends Activity {
    private DBUtils dataUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_data);
        //创建数据操作类
        dataUtils = new DBUtils(this);
        initialUI();
    }

    public void initialUI() {

        final EditText text = (EditText) findViewById(R.id.et_note_updateId);
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text.setFocusable(true);
                text.setFocusableInTouchMode(true);
            }
        });


        //获得从主页面传过来的id值
        Intent intent = getIntent();
        final long id = intent.getLongExtra("index", -1);

        //将数据显示在面板上
        Cursor cursor = dataUtils.queryDataById(id);
        cursor.moveToFirst();
        String oldData = cursor.getString(cursor.getColumnIndex("content"));
        text.setText(oldData);
        //添加保存按钮
        Button save = (Button) findViewById(R.id.btn_node_saveId);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //得到面板的数据
                String updateData = text.getText().toString();
                dataUtils.updateData(id, updateData);
                setResult(400);
                finish();
            }
        });
        //添加删除按钮
        Button delect= (Button) findViewById(R.id.btn_node_deleteId);
        delect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataUtils.delectData(id);
                setResult(400);
                finish();
            }
        });

    }
}
