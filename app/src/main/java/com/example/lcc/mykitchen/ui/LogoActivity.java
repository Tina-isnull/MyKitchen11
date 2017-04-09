package com.example.lcc.mykitchen.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.lcc.mykitchen.R;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;
import com.example.lcc.mykitchen.constant.Constant;
import com.example.lcc.mykitchen.utils.SpUtils;

public class LogoActivity extends MyBaseActivity {
    @Bind(R.id.et_logo_numberId)
    EditText userName;
    @Bind(R.id.et_logo_passWorldId)
    EditText mPassword;
    private SpUtils sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);
        ButterKnife.bind(this);
        //初始化界面
        initialUI();
    }

    @Override
    public void initialUI() {
        sp=new SpUtils(this,Constant.USER_INFO);
        //初始化ActionBar
        actionBar = (LinearLayout) findViewById(R.id.llActionbarId);
        initActionbar(R.drawable.go_back_normal, "登录", -1);
    }

    @OnClick(R.id.btn_logo_Id)
    public void login(View view) {
        String username = userName.getText().toString();
        String password = mPassword.getText().toString();
        String md5 = new String(Hex.encodeHex(DigestUtils.sha(password)));
        BmobUser bu2 = new BmobUser();
        bu2.setUsername(username);
        bu2.setPassword(md5);
        bu2.login(this , new SaveListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(LogoActivity.this,"登录成功",Toast.LENGTH_LONG).show();
                sp.setLoginState(true);
                startActivity(new Intent(LogoActivity.this, MainPagerActivity.class));
            }
            @Override
            public void onFailure(int code, String msg) {
                Toast.makeText(LogoActivity.this,"登录失败",Toast.LENGTH_LONG).show();
            }
        });





       /* BmobQuery<UserInfo> query = new BmobQuery<>();
        String username = userName.getText().toString();
        query.addWhereEqualTo("identification", username);
        query.findObjects(this, new FindListener<UserInfo>() {
            @Override
            public void onSuccess(List<UserInfo> list) {
                if (list.size() > 0) {
                    UserInfo user = list.get(0);
                    String password = mPassword.getText().toString();
                    String md5 = new String(Hex.encodeHex(DigestUtils.sha(password)));
                    if (user.getPassword().equals(md5)) {
                        SpUtils spUtils = new SpUtils(LogoActivity.this, Constant.USER_INFO);
                        spUtils.setLoginState(true);
                        spUtils.setBmobUserId(user.getObjectId());
                        spUtils.setIdentification(user.getIdentification());
                        String avatarUrl = user.getHeaderUrl();
                        if (avatarUrl==null) {
                            startActivity(new Intent(LogoActivity.this, MainPagerActivity.class));
                            return;
                        }
                        String nickname = user.getNickName();
                        String intro = user.getIntro();
                        boolean gender = user.getGender();
                        spUtils.setNickName(nickname);
                        spUtils.setHeaderUrl(avatarUrl);
                        spUtils.setIntro(intro);
                        spUtils.setGender(gender);
                        startActivity(new Intent(LogoActivity.this, MainPagerActivity.class));
                    } else {
                        Toast.makeText(LogoActivity.this, "您的密码错误", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LogoActivity.this, "您的用户名或者密码错误", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onError(int i, String s) {
                Toast.makeText(LogoActivity.this, "登录操作失败，请稍后重试", Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    @OnClick(R.id.tv_register_id)
    public void register(View view) {
        startActivity(new Intent(LogoActivity.this, RegisterActivity.class));
        finish();
    }
}