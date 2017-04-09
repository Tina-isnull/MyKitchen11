package com.example.lcc.mykitchen.ui;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.lcc.mykitchen.R;
import com.tencent.rtmp.TXLivePusher;

import com.example.lcc.mykitchen.utils.SpUtils;

public class SplashActivity extends Activity {
    SpUtils spUtils;
    TextView skip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        int[] sdkver = TXLivePusher.getSDKVersion();
        if (sdkver != null && sdkver.length >= 3) {
            Log.d("rtmpsdk","rtmp sdk version is:" + sdkver[0] + "." + sdkver[1] + "." + sdkver[2]);
        }




        skip= (TextView) findViewById(R.id.mainCountDownId);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SplashActivity.this,MainPagerActivity.class));
                finish();
            }
        });
        spUtils=new SpUtils(this);
        // 倒计时并跳转到主页面
       new Handler().postDelayed(new Runnable() {
           @Override
           public void run() {
               if(spUtils.isFirstRun()){
                   startActivity(new Intent(SplashActivity.this,GuideActivity.class));
                   spUtils.setFirstRun(false);
               }else{
                   startActivity(new Intent(SplashActivity.this,MainPagerActivity.class));
               }
               finish();
           }
       },3000);
    }

}
