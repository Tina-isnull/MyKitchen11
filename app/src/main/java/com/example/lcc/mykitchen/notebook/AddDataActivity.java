package com.example.lcc.mykitchen.notebook;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.lcc.mykitchen.R;

import com.example.lcc.mykitchen.db.DBUtils;

public class AddDataActivity extends Activity {
    private DBUtils dataUtils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //创建数据操作类
        dataUtils = new DBUtils(this);
        setContentView(R.layout.activity_add_data);
        initialUI();
    }


    public void initialUI() {
        Button save= (Button) findViewById(R.id.btn_node_saveId);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et= (EditText) findViewById(R.id.et_note_editId);
                String editText=et.getText().toString();
                if(TextUtils.isEmpty(editText)){
                    et.setError("文本不可为空");
                    return;
                }
                dataUtils.addData(editText);
                setResult(200);
                finish();
            }
        });


    }
}
