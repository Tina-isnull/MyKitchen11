package com.example.lcc.mykitchen.notebook;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.lcc.mykitchen.R;

import com.example.lcc.mykitchen.db.DBUtils;

public class NoteMainActivity extends Activity {

    private DBUtils dataUtils;
    private SimpleCursorAdapter adapter;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_main);
        //创建数据操作类
        dataUtils = new DBUtils(this);
        initialUI();
    }

    /**
     * 初始化界面
     */
    public void initialUI() {

        //跳转到添加记录的界面
        Button img= (Button) findViewById(R.id.btn_node_addId);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(NoteMainActivity.this,AddDataActivity.class);
                startActivityForResult(intent,100);
            }
        });
        //获得ListView
        ListView listView = (ListView) findViewById(R.id.listView_note_Id);
        //从数据库中取数据
        cursor = dataUtils.queryData();
        //创建适配器
        adapter = new SimpleCursorAdapter(this, R.layout.inflater_notelist, cursor,
                new String[]{"content", "time"}, new int[]{R.id.tv_note_contentId, R.id.tv_note_timeId},
                SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        //添加适配器
        listView.setAdapter(adapter);
        //添加监听方法
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(NoteMainActivity.this,UpdateDataActivity.class);
                intent.putExtra("index",id);
                startActivityForResult(intent,300);
            }
        });
        registerForContextMenu(listView);
    }

    /**
     * 创建上下文菜单
     */

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(0, 100, 0, "您确定要删除吗？");
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    /**
     * 上下文菜单选择
     */
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == 100) {
            //回调函数提供的额外的菜单信息
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            long id = info.id;
            dataUtils.delectData(id);
            //刷新界面
            Cursor cursor = dataUtils.queryData();
            adapter.changeCursor(cursor);
        }
        return super.onContextItemSelected(item);
    }
    /**
     * 处理返回来的数据
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            // 刷新界面
        if(requestCode==100&&resultCode==200){
            Cursor cursor = dataUtils.queryData();
            adapter.changeCursor(cursor);
        }
        if(requestCode==300&&resultCode==400){
            Cursor cursor = dataUtils.queryData();
            adapter.changeCursor(cursor);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        if(cursor!=null){
            cursor.close();
        }
        super.onDestroy();
    }
}
