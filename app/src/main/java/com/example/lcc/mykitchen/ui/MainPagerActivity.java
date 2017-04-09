package com.example.lcc.mykitchen.ui;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.example.lcc.mykitchen.R;
import com.example.lcc.mykitchen.entity.UserInfo;
import com.example.lcc.mykitchen.notebook.NoteMainActivity;

import com.example.lcc.mykitchen.fragment.FragmentController;

import cn.bmob.v3.BmobUser;

public class MainPagerActivity extends FragmentActivity {
    private FragmentController fc;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_pager);
        //让第一个界面显示在窗口
        fc = FragmentController.getInstance(R.id.fragmentContainerId, this);
        fc.showFragment(0);
        initialUI();
    }

    //中间按钮的附加功能点击事件
    public void addition_click(View view) {
        if(BmobUser.getCurrentUser(this, UserInfo.class)==null){
            Toast.makeText(MainPagerActivity.this,"您还没有登录",Toast.LENGTH_LONG).show();
            return;
        }
        startActivity(new Intent(this,UploadMenuActivity.class));
       // AddDialog();

    }

    //显示弹框
    private void AddDialog() {
        dialog = new Dialog(this, R.style.dialog);
        View dialogView = View.inflate(this, R.layout.inflate_dialog, null);
        dialog.setContentView(dialogView);
        //加载动画
        Animation lefeAnim = AnimationUtils.loadAnimation(this, R.anim.dialog_btn_left_anim);
        Animation rightAnim = AnimationUtils.loadAnimation(this, R.anim.dialog_btn_right_anim);
        ImageButton musicBtn = (ImageButton) dialogView.findViewById(R.id.btn_dialog_musicId);
        ImageButton noteBtn = (ImageButton) dialogView.findViewById(R.id.btn_dialog_noteId);
        musicBtn.setAnimation(lefeAnim);
        noteBtn.setAnimation(rightAnim);
        musicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainPagerActivity.this, "音乐播放器", Toast.LENGTH_SHORT).show();
                //ArrayList<Music_local> songs= MusicsFromSd.getAllSongs(MainPagerActivity.this);
               /* Log.i("TAG","music");
                Log.i("TAG",songs.toString());*/
                Intent intent = new Intent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setAction("android.intent.action.MUSIC_PLAYER");
                startActivity(intent);
              //  startActivity(new Intent(MainPagerActivity.this, MusicListActivity.class));

            }
        });
        noteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainPagerActivity.this, "记事本", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainPagerActivity.this, NoteMainActivity.class));
            }
        });
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.BOTTOM);
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = 300;
        lp.alpha = 0.7f; // 透明度;
        lp.y = 82;
        dialogWindow.setAttributes(lp);
        dialog.show();
    }

    private void initialUI() {
        RadioGroup rGroup = (RadioGroup) findViewById(R.id.bottemRbtnId);
        rGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio0:
                            fc.showFragment(0);
                        break;
                    case R.id.radio1:
                            fc.showFragment(1);
                        break;
                    case R.id.radio2:
                            fc.showFragment(2);
                        break;
                    case R.id.radio3:
                            fc.showFragment(3);
                        break;
                    default:
                        break;
                }

            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //dialog.dismiss();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(false);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}