package com.example.lcc.mykitchen.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.lcc.mykitchen.R;
import com.example.lcc.mykitchen.entity.UserInfo;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.RequestSMSCodeListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.VerifySMSCodeListener;

public class RegisterActivity extends MyBaseActivity {
    @Bind(R.id.et_register_phoneId)
    EditText mPhone;
    @Bind(R.id.et_register_password)
    EditText mPassword;
    @Bind(R.id.et_register_nameId)
    EditText mName;
    @Bind(R.id.et_register_sure_password)
    EditText sure_password;
    @Bind(R.id.et_code_id)
    EditText mCode;
    @Bind(R.id.btn_getCodeId)
    Button btnCode;
    MyCountTimer timer;
    private String name;
    private String password;
    private String phone;
    private String sure_password_m;
    private String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        //初始化控件
        initialUI();
    }

    @Override
    public void initialUI() {
        //设置actionBar
        actionBar = (LinearLayout) findViewById(R.id.include_register_id);
        initActionbar(R.drawable.go_back_normal, "注册", -1);
        btnCode= (Button) findViewById(R.id.btn_getCodeId);
    }

    @OnClick(R.id.btn_getCodeId)
    public void getCode(View view) {
        requestSMSCode();
    }


    @OnClick(R.id.btn_register_Id)
    public void register(View view) {
        name = mName.getText().toString();
        phone = mPhone.getText().toString();
        password = mPassword.getText().toString();
        sure_password_m = sure_password.getText().toString();
        code = mCode.getText().toString();
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(password)||TextUtils.isEmpty(sure_password_m)) {
            Toast.makeText(this, "请您填写完整", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(sure_password_m)) {
            Toast.makeText(this, "密码不一致", Toast.LENGTH_SHORT).show();
            return;
        }
        verifyOrBind();
    }

    class MyCountTimer extends CountDownTimer {

        public MyCountTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            btnCode.setText((millisUntilFinished / 1000) + "秒后重发");
        }

        @Override
        public void onFinish() {
            btnCode.setText("重新发送验证码");
        }
    }

    private void requestSMSCode() {
        phone = mPhone.getText().toString();
        if (!TextUtils.isEmpty(phone)) {
            timer = new MyCountTimer(60000, 1000);
            timer.start();
            BmobSMS.requestSMSCode(this, phone, "手机号码登陆模板", new RequestSMSCodeListener() {
                @Override
                public void done(Integer smsId, BmobException ex) {
                    // TODO Auto-generated method stub
                    if (ex == null) {// 验证码发送成功
                        toast("验证码发送成功");// 用于查询本次短信发送详情
                    } else {//如果验证码发送错误，可停止计时
                        toast(ex.toString());
                        timer.cancel();
                    }
                }
            });
        } else {
            toast("请输入手机号码");
        }
    }

    private void verifyOrBind() {
        if (TextUtils.isEmpty(phone)) {
            showToast("手机号码不能为空");
            return;
        }
        if (TextUtils.isEmpty(code)) {
            showToast("验证码不能为空");
            return;
        }
        final ProgressDialog progress = new ProgressDialog(this);
        progress.setMessage("正在验证短信验证码...");
        progress.setCanceledOnTouchOutside(false);
        progress.show();
        // V3.3.9提供的一键注册或登录方式，可传手机号码和验证码
        BmobSMS.verifySmsCode(this, phone, code, new VerifySMSCodeListener() {
            @Override
            public void done(BmobException ex) {
                // TODO Auto-generated method stub
                progress.dismiss();
                if (ex == null) {
                    bindMobilePhone(phone);
                } else {
                    if (ex.getErrorCode() == 207) {
                        Toast.makeText(RegisterActivity.this, "验证码错误", Toast.LENGTH_SHORT).show();
                    } else {
                        toast("验证失败：code=" + ex.getErrorCode() + "，错误描述：" + ex.getLocalizedMessage());
                    }

                }
            }
        });
    }

    private void bindMobilePhone(String phone) {
        //开发者在给用户绑定手机号码的时候需要提交两个字段的值：mobilePhoneNumber、mobilePhoneNumberVerified

            UserInfo bu = new UserInfo();
            String md5 = new String(Hex.encodeHex(DigestUtils.sha(password)));
            bu.setUsername(name);
            bu.setPassword(md5);
            bu.setMobilePhoneNumber(phone);
            bu.setMobilePhoneNumberVerified(true);
            bu.signUp(this, new SaveListener() {
                @Override
                public void onSuccess() {
                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    finish();
                }

                @Override
                public void onFailure(int code, String msg) {
                    if (code == 202) {
                        Toast.makeText(RegisterActivity.this, "用户名已存在", Toast.LENGTH_SHORT).show();
                    } else if (code == 207) {
                        Toast.makeText(RegisterActivity.this, "验证码错误", Toast.LENGTH_SHORT).show();
                    } else if (code == 209) {
                        Toast.makeText(RegisterActivity.this, "手机号已存在", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                    }


                }
            });

        }

}