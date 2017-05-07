package com.example.lcc.mykitchen.activity;

import android.content.Context;
import android.util.Log;

import com.example.lcc.mykitchen.entity.ChatInfo;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by lcc on 2017/5/6.
 */
public class FlusMessagePlay extends Thread{

    public static boolean isStop=false;
    Context mContext;
    String msg = "", msg1 = "";
    private messageShow message;
    public FlusMessagePlay(Context context,messageShow message) {
        mContext = context;
        this.message=message;
    }

    @Override
    public void run() {
        BmobQuery<ChatInfo> query = new BmobQuery<>();
        while (!isStop) {
            query.findObjects(mContext, new FindListener<ChatInfo>() {
                @Override
                public void onSuccess(List<ChatInfo> list) {
                    if (list != null && list.size() != 0) {
                        for (int i = 0; i < list.size(); i++) {
                            msg = msg + list.get(i).getChatContent() + "\n";
                        }
                        Log.d("TAG","msg="+msg);
                        message.setInfos(msg);
                        msg = "";
                    } else {

                    }

                }

                @Override
                public void onError(int i, String s) {

                }


            });
            try {
                Thread.sleep(5000);//每隔5s刷新一次数据
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }
    }

    public interface messageShow {
        public void setInfos(String message);
    }
}
