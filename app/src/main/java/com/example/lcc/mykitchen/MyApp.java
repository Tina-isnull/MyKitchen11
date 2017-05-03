package com.example.lcc.mykitchen;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.lcc.mykitchen.entity.RelatedPartner;
import com.example.lcc.mykitchen.entity.UserInfo;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;
import cn.sharesdk.framework.ShareSDK;

import com.example.lcc.mykitchen.constant.Constant;
import com.example.lcc.mykitchen.utils.sharemultiphoto.CustomConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lcc on 2017/1/9.
 */
public class MyApp extends Application {
    public static MyApp context;
    private  UserInfo bmobUser;
    public static List<String> relatedName;

    @Override
    public void onCreate() {
        super.onCreate();
        relatedName = new ArrayList<>();
        context = this;
        bmobUser = BmobUser.getCurrentUser(context, UserInfo.class);
        removeTempFromPref();
        Bmob.initialize(this, Constant.BMOB_KEY);
        ImageLoader.getInstance().init(
                ImageLoaderConfiguration.createDefault(this));
        ShareSDK.initSDK(this);
//获得关注的人的信息
        if (bmobUser != null) {
            BmobQuery<RelatedPartner> query = new BmobQuery<>();
            query.include("relatedName");
            query.addWhereEqualTo("userName", bmobUser.getObjectId());
            query.findObjects(context, new FindListener<RelatedPartner>() {
                @Override
                public void onSuccess(List<RelatedPartner> list) {
                    for (RelatedPartner data : list) {
                        relatedName.add(data.getRelatedName().getObjectId());
                        Log.i("TAG", data.getRelatedName().getObjectId());
                    }
                }

                @Override
                public void onError(int i, String s) {
                    //TODO
                    Log.i("TAG", "错误代码" + i + "," + s);
                }
            });
        }

    }

    private void removeTempFromPref() {
        SharedPreferences sp = getSharedPreferences(
                CustomConstants.APPLICATION_NAME, MODE_PRIVATE);
        sp.edit().remove(CustomConstants.PREF_TEMP_IMAGES).commit();
    }
}
