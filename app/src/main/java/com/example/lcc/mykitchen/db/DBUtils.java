package com.example.lcc.mykitchen.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.lcc.mykitchen.utils.dataUtils;

/**
 * 对记事本数据的管理
 * Created by lcc on 2016/12/17.
 */
public class DBUtils {
    public MyDbHelper helper = null;

    public DBUtils(Context context) {
        helper = MyDbHelper.getInstance(context);
    }

    /**
     * 删除数据
     *
     * @param id
     */
    public void delectData(long id) {
        SQLiteDatabase dataBase = helper.getWritableDatabase();
        dataBase.delete("context", "_id=?", new String[]{String.valueOf(id)});
    }

    /**
     * 添加数据
     *
     * @param content
     */
    public void addData(String content) {
        long time = System.currentTimeMillis();
        String formateTime = dataUtils.formateData(time);
        ContentValues values = new ContentValues();
        values.put("content", content);
        values.put("time", formateTime);
        SQLiteDatabase dataBase = helper.getWritableDatabase();
        dataBase.insert("context", null, values);

    }

    /**
     * 更新数据
     *
     * @param id
     * @param content
     */
    public void updateData(long id, String content) {
        long time = System.currentTimeMillis();
        String formateTime = dataUtils.formateData(time);
        ContentValues values = new ContentValues();
        values.put("content", content);
        values.put("time", formateTime);
        SQLiteDatabase dataBase = helper.getWritableDatabase();
        dataBase.update("context", values, "_id=?", new String[]{String.valueOf(id)});
    }

    /**
     * 查找全部的数据
     *
     * @return
     */
    public Cursor queryData() {
        SQLiteDatabase dataBase = helper.getReadableDatabase();
        Cursor cursor = dataBase.query("context", null, null, null, null, null, "time desc");
        return cursor;
    }

    /**
     * 查找有条件的数据
     */
    public Cursor queryDataById(long id) {
        SQLiteDatabase dataBase = helper.getWritableDatabase();
        Cursor cursor = dataBase.query("context", null, "_id=?", new String[]{String.valueOf(id)}, null, null, "time desc");
        return cursor;
    }
}
