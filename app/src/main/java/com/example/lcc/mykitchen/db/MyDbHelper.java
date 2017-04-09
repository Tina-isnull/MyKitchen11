package com.example.lcc.mykitchen.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lcc on 2016/12/17.
 */
public class MyDbHelper extends SQLiteOpenHelper {
    private static MyDbHelper helper = null;

    public MyDbHelper(Context context) {
        super(context, "kitchen.db", null, 1);
    }
//单例模式
    public synchronized static MyDbHelper getInstance(Context context) {
        if (helper == null) {
            helper = new MyDbHelper(context);
        }
        return helper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建表
        String sql = "create table context(" +
                "_id Integer primary key autoincrement," +
                "content text not null,time long not null)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //更新数据库
        String sql="drop table context";
        db.execSQL(sql);
        onCreate(db);

    }
}
