package com.example.lcc.mykitchen.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.lcc.mykitchen.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.lcc.mykitchen.entity.MenuMaterial;

public class AddMaterialActivity extends AppCompatActivity {
    @Bind(R.id.ll_addMaterial)
    LinearLayout addMaterial;
    @Bind(R.id.ll_add_material_item1)
    LinearLayout material1;
    private ImageView delete;
    //创建集合管理动态布局
    HashMap<Integer, LinearLayout> conditions = new HashMap<>();
    int itemId = 1;
    //默认加入的位置
    int position = 1;
    private List<MenuMaterial> lists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_material);
        ButterKnife.bind(this);
        lists = new ArrayList<>();
        //把当前对象保存到集合中
        conditions.put(0, material1);
    }

    private void createConditon() {
        //每次创建一个view
        LinearLayout ll_limit = (LinearLayout) View.inflate(this, R.layout.item_add_material, null);
        //删除按钮
        ImageView iv_del_item = (ImageView) ll_limit.findViewById(R.id.img_add_material_delete);
        //给每个删除条目绑定一个id
        iv_del_item.setTag(itemId);
        iv_del_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取绑定的id
                int itemId = (int) v.getTag();
                //布局中删除指定位置的view
                addMaterial.removeView(conditions.get(itemId));
                //集合中删除指定的item对象
                conditions.remove(itemId);
                //记录position最新位置
                position--;
            }
        });
        //把当前对象保存到集合中
        conditions.put(itemId, ll_limit);
        addMaterial.addView(ll_limit, position);
        //每次添加以后，位置会变动
        position++;
        //每调用一次id++，防止重复
        itemId++;

    }

    public void finishMaterial() {
        Set<Map.Entry<Integer, LinearLayout>> items = conditions.entrySet();
        //转换为迭代器
        Iterator iter = items.iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            LinearLayout val = (LinearLayout) entry.getValue();
            EditText ed1 = (EditText) val.findViewById(R.id.et_add_material1);
            EditText ed2 = (EditText) val.findViewById(R.id.et_add_material2);
            String s1 = ed1.getText().toString().trim();
            String s2 = ed2.getText().toString().trim();
            if (TextUtils.isEmpty(s1) && TextUtils.isEmpty(s2)) {
                Toast.makeText(this, "限制条件填写不完整", Toast.LENGTH_SHORT).show();
                return;
            }
            MenuMaterial material = new MenuMaterial();
            material.setMaterial(s1);
            material.setCount(s2);
            Log.i("TAG", "menuStep=" + material.toString());
            lists.add(material);
        }
    }

    @OnClick(R.id.btn_addMaterial_finish)
    public void finishMaterial(View view) {
        finishMaterial();
        Intent intent = new Intent();
        intent.putExtra("materials", (ArrayList) lists);
        setResult(RESULT_OK, intent);
        finish();
    }

    @OnClick(R.id.ll_add_material)
    public void addMaterial(View view) {
        createConditon();
    }
}
