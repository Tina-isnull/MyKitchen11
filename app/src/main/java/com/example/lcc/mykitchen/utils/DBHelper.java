package com.example.lcc.mykitchen.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import com.example.lcc.mykitchen.entity.CollectFood;
import com.example.lcc.mykitchen.entity.CollectKiter;
import com.example.lcc.mykitchen.entity.FootPrint;
import com.example.lcc.mykitchen.entity.TypeFoodsShow;

/**
 * Created by lcc on 2017/1/17.
 */
public class DBHelper extends OrmLiteSqliteOpenHelper {
    private static DBHelper dbhelper;

    public DBHelper(Context context) {
        super(context, "collection.db", null, 1);
    }

    public static DBHelper getInstance(Context context) {
        if(dbhelper == null){
            synchronized (DBHelper.class) {
                if (dbhelper == null) {
                    dbhelper = new DBHelper(context);
                }
            }
        }

        return dbhelper;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTableIfNotExists(connectionSource, CollectKiter.class);
            TableUtils.createTableIfNotExists(connectionSource, CollectFood.class);
            TableUtils.createTableIfNotExists(connectionSource, FootPrint.class);
            TableUtils.createTableIfNotExists(connectionSource, TypeFoodsShow.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {
        try {
            TableUtils.dropTable(connectionSource, CollectKiter.class, true);
            TableUtils.dropTable(connectionSource, CollectFood.class, true);
            TableUtils.dropTable(connectionSource, FootPrint.class, true);
            TableUtils.dropTable(connectionSource, TypeFoodsShow.class, true);
            onCreate(sqLiteDatabase, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
