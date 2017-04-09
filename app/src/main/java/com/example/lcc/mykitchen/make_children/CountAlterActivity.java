package com.example.lcc.mykitchen.make_children;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.lcc.mykitchen.constant.Constant;
import com.example.lcc.mykitchen.entity.UserInfo;
import com.example.lcc.mykitchen.ui.MyBaseActivity;
import com.example.lcc.mykitchen.R;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QuerySMSStateListener;
import cn.bmob.v3.listener.RequestSMSCodeListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.VerifySMSCodeListener;

public class CountAlterActivity extends MyBaseActivity implements View.OnClickListener {

    private String number;
    private EditText countAlter;
    private Button code;
    MyCountTimer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_alter);
        initialUI();
    }

    @Override
    public void initialUI() {
        //设置ActionBar
        actionBar = (LinearLayout)findViewById(R.id.llActionbarId);
        initActionbar(R.drawable.go_back_normal, "账号设置", -1);
        //获得控件，添加监听事件
        code= (Button) findViewById(R.id.btn_countAlter_getCodeId);
        countAlter = (EditText) findViewById(R.id.et_countAlter_id);
        Button next = (Button) findViewById(R.id.btn_countAlter_id);

        code.setOnClickListener(this);
        next.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_countAlter_getCodeId:
                requestSMSCode();

                break;
            case R.id.btn_countAlter_id:
                verifyOrBind();
                break;
            default:
                break;
        }
    }
    class MyCountTimer extends CountDownTimer {

        public MyCountTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }
        @Override
        public void onTick(long millisUntilFinished) {
            code.setText((millisUntilFinished / 1000) +"秒后重发");
        }
        @Override
        public void onFinish() {
            code.setText("重新发送验证码");
        }
    }
    private void requestSMSCode() {
        String number = countAlter.getText().toString();
        if (!TextUtils.isEmpty(number)) {
            timer = new MyCountTimer(60000, 1000);
            timer.start();
            BmobSMS.requestSMSCode(this, number, "手机号码登陆模板",new RequestSMSCodeListener() {

                @Override
                public void done(Integer smsId, BmobException ex) {
                    // TODO Auto-generated method stub


                    if (ex == null) {// 验证码发送成功
                        toast("验证码发送成功");// 用于查询本次短信发送详情
                    }else{//如果验证码发送错误，可停止计时
                        timer.cancel();
                    }

                }
            });
        } else {
            toast("请输入手机号码");
        }
    }

    private void verifyOrBind(){
        final String phone = countAlter.getText().toString();
        String code = countAlter.getText().toString();
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
        BmobSMS.verifySmsCode(this,phone, code, new VerifySMSCodeListener() {

            @Override
            public void done(BmobException ex) {
                // TODO Auto-generated method stub
                progress.dismiss();
                if(ex==null){
                    toast("手机号码已验证");
                    bindMobilePhone(phone);
                }else{
                    toast("验证失败：code="+ex.getErrorCode()+"，错误描述："+ex.getLocalizedMessage());
                }
            }
        });
    }
    private void bindMobilePhone(String phone){
        //开发者在给用户绑定手机号码的时候需要提交两个字段的值：mobilePhoneNumber、mobilePhoneNumberVerified
        UserInfo user =new UserInfo();
        user.setMobilePhoneNumber(phone);
        user.setMobilePhoneNumberVerified(true);
        UserInfo cur = BmobUser.getCurrentUser(CountAlterActivity.this,UserInfo.class);
        user.update(CountAlterActivity.this, cur.getObjectId(),new UpdateListener() {

            @Override
            public void onSuccess() {
                // TODO Auto-generated method stub
                toast("手机号码绑定成功");
                finish();
            }

            @Override
            public void onFailure(int arg0, String arg1) {
                // TODO Auto-generated method stub
                toast("手机号码绑定失败："+arg0+"-"+arg1);
            }
        });
    }



}
