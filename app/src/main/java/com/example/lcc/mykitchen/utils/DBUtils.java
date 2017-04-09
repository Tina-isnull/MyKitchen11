package com.example.lcc.mykitchen.utils;

import android.content.Context;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Callable;

import com.example.lcc.mykitchen.entity.CollectFood;
import com.example.lcc.mykitchen.entity.CollectKiter;
import com.example.lcc.mykitchen.entity.FootPrint;
import com.example.lcc.mykitchen.entity.TypeFoodsShow;

/**
 * 将足迹和收藏的美食，关注的人实现数据库的增删查
 * Created by lcc on 2017/1/17.
 */
public class DBUtils {
    final int COLLECTION_FOOD=1;
    final int COLLECTION_KITER=2;
    final int FOOD_PRINT=3;
    final int FOOD_TYPE=4;
    private DBHelper dbHelper;
    private Dao<CollectFood,String> dao1;
    private Dao<CollectKiter,String> dao2;
    private Dao<FootPrint,String> dao3;
    private Dao<TypeFoodsShow,String> dao4;
    public DBUtils(Context context,int type){
        try {
            dbHelper=DBHelper.getInstance(context);
            if(type==COLLECTION_FOOD){
                dao1=dbHelper.getDao(CollectFood.class);
            }else if(type==COLLECTION_KITER){
                dao2=dbHelper.getDao(CollectKiter.class);
            }else if(type==FOOD_PRINT){
                dao3=dbHelper.getDao(FootPrint.class);
            }else{
                dao4=dbHelper.getDao(TypeFoodsShow.class);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //对收藏食品进行增删查
    public void addFood(CollectFood ck){
        try {
            dao1.createIfNotExists(ck);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<CollectFood> queryFood(){
        try {
            return dao1.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("数据库查询异常");
        }
    }
    public void delectCollectFood(CollectFood cf){
        try {
            dao1.delete(cf);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //对厨师进行增删查
    public void addKiter(CollectKiter ck){
        try {
            dao2.createIfNotExists(ck);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<CollectKiter> queryKiter(){
        try {
            return dao2.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("数据库查询异常");
        }
    }
    public void delectCollectKiter(CollectKiter ck){
        try {
            dao2.delete(ck);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //对足迹进行增删查
    public void addPrint(FootPrint ck){
        try {
            dao3.createIfNotExists(ck);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<FootPrint> queryPrint(){
        try {
            return dao3.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("数据库查询异常");
        }
    }
    public void delectPrint(FootPrint fp){
        try {
            dao3.delete(fp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //对食品种类进行增删查
    public void addTypeFoodShow(TypeFoodsShow ck){
        try {
            dao4.createIfNotExists(ck);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<TypeFoodsShow> queryTypeFoodShow(){
        try {
            return dao4.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("数据库查询异常");
        }
    }
    public void delectTypeFoodShow(TypeFoodsShow fp){
        try {
            dao4.delete(fp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addTypeFoodBatch(final List<TypeFoodsShow> cbs){
        try {
            dao4.callBatchTasks(new Callable<Void>() {

                @Override
                public Void call() throws Exception {
                    for(TypeFoodsShow cb:cbs){
                        addTypeFoodShow(cb);
                    }
                    return null;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
