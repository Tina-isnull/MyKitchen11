package com.example.lcc.mykitchen.ui;


import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lcc.mykitchen.R;

import butterknife.ButterKnife;

public abstract class MyBaseActivity extends Activity {
    public LinearLayout actionBar;
    public static String TAG = "bmob";
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        ButterKnife.bind(this);
        initialUI();
    }

    public abstract void initialUI();

    public void initActionbar(int leftImg, String title, int rightImg) {
        if (actionBar == null) {
            return;
        }

        ImageView imgL = (ImageView) actionBar.findViewById(R.id.imgLeftActionbarId);
        ImageView imgR = (ImageView) actionBar.findViewById(R.id.imgRightActionbarId);
        TextView acText = (TextView) actionBar.findViewById(R.id.tvActionbarId);
        if (leftImg == -1) {
            imgL.setVisibility(View.INVISIBLE);
        } else {
            imgL.setVisibility(View.VISIBLE);
            imgL.setImageResource(leftImg);
        }
        if (rightImg == -1) {
            imgR.setVisibility(View.INVISIBLE);
        } else {
            imgR.setVisibility(View.VISIBLE);
            imgR.setImageResource(rightImg);
        }
        if (TextUtils.isEmpty(title)) {
            acText.setVisibility(View.INVISIBLE);
        } else {
            acText.setVisibility(View.VISIBLE);
            acText.setText(title);
        }

    }
    public void toast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        Log.d(TAG, msg);
    }

    Toast mToast;

    public void showToast(String text) {
        if (!TextUtils.isEmpty(text)) {
            if (mToast == null) {
                mToast = Toast.makeText(getApplicationContext(), text,
                        Toast.LENGTH_SHORT);
            } else {
                mToast.setText(text);
            }
            mToast.show();
        }
    }

    public void showToast(int resId) {
        if (mToast == null) {
            mToast = Toast.makeText(getApplicationContext(), resId,
                    Toast.LENGTH_SHORT);
        } else {
            mToast.setText(resId);
        }
        mToast.show();
    }

    public static void showLog(String msg) {
        Log.i("BmobPro", msg);
    }
}

