package com.example.lcc.mykitchen.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.example.lcc.mykitchen.MyApp;
import com.example.lcc.mykitchen.R;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.OtherLoginListener;
import cn.bmob.v3.listener.SaveListener;
import com.example.lcc.mykitchen.constant.Constant;
import com.example.lcc.mykitchen.entity.RelatedPartner;
import com.example.lcc.mykitchen.entity.UserInfo;
import com.example.lcc.mykitchen.utils.SpUtils;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import java.util.List;

public class LoginActivity extends MyBaseActivity {
    @Bind(R.id.et_logo_numberId)
    EditText userName;
    @Bind(R.id.et_logo_passWorldId)
    EditText mPassword;
    private SpUtils sp;
    public static Tencent mTencent;

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
                Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_LONG).show();
                getRelatedPatener();
                sp.setLoginState(true);
                startActivity(new Intent(LoginActivity.this, MainPagerActivity.class));
            }
            @Override
            public void onFailure(int code, String msg) {
                if(code==109){
                    Toast.makeText(LoginActivity.this,"用户名和密码不可为空",Toast.LENGTH_LONG).show();
                }else if(code==101) {
                    Toast.makeText(LoginActivity.this,"用户名或密码不正确",Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    @OnClick(R.id.tv_register_id)
    public void register(View view) {
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        finish();
    }
    //qq第三方登录
    @OnClick(R.id.ll_qq_logoId)
    public void qqLogin(){
        qqAuthorize();
    }

    private void qqAuthorize(){
        if(mTencent==null){
            mTencent = Tencent.createInstance("101402508", getApplicationContext());
        }
        mTencent.logout(this);
        mTencent.login(this, "all", new IUiListener() {
            @Override
            public void onComplete(Object arg0) {
                // TODO Auto-generated method stub
                if(arg0!=null){
                    JSONObject jsonObject = (JSONObject) arg0;
                    try {
                        String token = jsonObject.getString(com.tencent.connect.common.Constants.PARAM_ACCESS_TOKEN);
                        String expires = jsonObject.getString(com.tencent.connect.common.Constants.PARAM_EXPIRES_IN);
                        String openId = jsonObject.getString(com.tencent.connect.common.Constants.PARAM_OPEN_ID);
                        BmobUser.BmobThirdUserAuth authInfo = new BmobUser.BmobThirdUserAuth(BmobUser.BmobThirdUserAuth.SNS_TYPE_QQ,token, expires,openId);
                        loginWithAuth(authInfo);
                    } catch (JSONException e) {
                    }
                }
            }

            @Override
            public void onError(UiError arg0) {
                // TODO Auto-generated method stub
                toast("QQ授权出错："+arg0.errorCode+"--"+arg0.errorDetail);
            }

            @Override
            public void onCancel() {
                // TODO Auto-generated method stub
                toast("取消qq授权");
            }
        });
    }
    public void loginWithAuth(final BmobUser.BmobThirdUserAuth authInfo){
        BmobUser.loginWithAuthData(LoginActivity.this, authInfo, new OtherLoginListener() {

            @Override
            public void onSuccess(org.json.JSONObject jsonObject) {
                // TODO Auto-generated method stub
                Log.i("smile",authInfo.getSnsType()+"登陆成功返回:"+jsonObject);
                getRelatedPatener();
                Intent intent = new Intent(LoginActivity.this, MainPagerActivity.class);
                intent.putExtra("json", jsonObject.toString());
                intent.putExtra("from", authInfo.getSnsType());
                startActivity(intent);
            }

            @Override
            public void onFailure(int code, String msg) {
                // TODO Auto-generated method stub
                toast("第三方登陆失败："+msg);
            }

        });
    }
    public void getRelatedPatener() {
        MyApp.relatedName.clear();
        //获得关注的人的信息
            BmobQuery<RelatedPartner> query = new BmobQuery<>();
            query.include("relatedName");
            query.addWhereEqualTo("userName", BmobUser.getCurrentUser(this, UserInfo.class).getObjectId());
            query.findObjects(this, new FindListener<RelatedPartner>() {
                @Override
                public void onSuccess(List<RelatedPartner> list) {
                    if(list==null&&list.size()==0){
                        Toast.makeText(LoginActivity.this, "您还没有关注人哦", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    for (RelatedPartner data : list) {
                        MyApp.relatedName.add(data.getRelatedName().getObjectId());
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