package com.example.lcc.mykitchen.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.lcc.mykitchen.constant.Constant;

/**
 * Created by lcc on 2017/1/9.
 * <p/>
 * 是操作偏好设置文件的工具类
 * <p/>
 * 获取偏好设置文件的几种方式：
 * 1. 通过调用Context中的getSharedPreferences(文件名称，存储位置)方法获取偏好设置文件
 * 2. 通过调用Activity中的getPreferences(存储位置)获取偏好设置文件
 * 偏好设置文件的名称是固定的：activity对像名_perferences.xml
 * 3. 通过调用PerferenceManager工具类的getDefaultPerferences(上下文)方法获取偏好设置文件
 * 偏好设置文件的名称是固定的：包名_perference.xml，存储位置固定为Context.Mode_Private
 *
 * @author pjy
 */
public class SpUtils {

    SharedPreferences sp;
    SharedPreferences.Editor editor;

    public SpUtils(Context context, String name) {
        sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        editor = sp.edit();
    }

    public SpUtils(Context context) {
        sp = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sp.edit();
    }

    public boolean isPause() {
        return sp.getBoolean(Constant.VIDEO_PAUSE,false);
    }

    public void setPause(boolean flag) {
        editor.putBoolean(Constant.VIDEO_PAUSE, flag);
        editor.commit();
    }
    public boolean isFirstRun() {
        return sp.getBoolean(Constant.FIRSTRUN, true);
    }

    public void setFirstRun(boolean flag) {
        editor.putBoolean(Constant.FIRSTRUN, flag);
        editor.commit();
    }

    public boolean isCloseBanner() {
        return sp.getBoolean(Constant.CLOSEBANNER, false);
    }

    public void setCloseBanner(boolean flag) {
        editor.putBoolean(Constant.CLOSEBANNER, flag);
        editor.commit();
    }


    //记录登录状态
    public void setLoginState(boolean flag) {
        editor.putBoolean(Constant.LOGIN_STATE, flag);
        editor.commit();
    }

    public boolean getLoginState() {
        return sp.getBoolean(Constant.LOGIN_STATE, false);
    }

}