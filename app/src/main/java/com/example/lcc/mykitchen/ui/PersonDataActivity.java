package com.example.lcc.mykitchen.ui;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lcc.mykitchen.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import com.example.lcc.mykitchen.constant.Constant;
import com.example.lcc.mykitchen.entity.UserInfo;
import com.example.lcc.mykitchen.utils.SpUtils;

public class PersonDataActivity extends MyBaseActivity {
    @Bind(R.id.img_info_ivAvatar)
    ImageView mivAvatar;
    private String path;
    private String avatarUrl;
    @Bind(R.id.et_personalData_nichengId)
    TextView mNickname;
    @Bind(R.id.rg_info_id)
    RadioGroup mRg;
    @Bind(R.id.et_personalData_introId)
    EditText mContent;
    private String filePath;
    private SpUtils spUtils;
    private UserInfo bmobUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_data);
        spUtils = new SpUtils(this, Constant.USER_INFO);
        ButterKnife.bind(this);
        initialUI();
    }

    @Override
    public void initialUI() {
        //设置ActionBar
        actionBar = (LinearLayout) findViewById(R.id.llActionbarId);
        initActionbar(R.drawable.go_back_normal, "个人主页", R.drawable.update);
        bmobUser = BmobUser.getCurrentUser(this, UserInfo.class);
        //请求个人信息，显示在页面中
        showUserInfo();
    }

    private void showUserInfo() {
        if (bmobUser==null){
            return;
        }
        String name = bmobUser.getUsername();
        Log.i("TAG","name="+name);
        mNickname.setText(name);
        if(bmobUser.getHeaderUrl()==null||bmobUser.getIntro()==null){
            return;
        }
        ImageLoader.getInstance().displayImage(bmobUser.getHeaderUrl(), mivAvatar);
        mContent.setText(bmobUser.getIntro());
        if (bmobUser.getGender()) {
            mRg.check(R.id.rBtn_info_girl);
        } else {
            mRg.check(R.id.rBtn_info_boy);
        }
    }

    //保存更新的逻辑
    @OnClick(R.id.imgRightActionbarId)
    public void saveUpdate(View view) {
        String intro = mContent.getText().toString();
        if (TextUtils.isEmpty(intro)) {
            Toast.makeText(PersonDataActivity.this, "请填入昵称并介绍下自己吧", Toast.LENGTH_SHORT).show();
            return;
        }
        Boolean gender = false;
        if (mRg.getCheckedRadioButtonId() == R.id.rBtn_info_girl) {
            gender = true;
        }
        UserInfo newUser = new UserInfo();
        newUser.setIntro(intro);
        newUser.setHeaderUrl(avatarUrl);
        newUser.setGender(gender);
        BmobUser bmobUser = BmobUser.getCurrentUser(this);
        newUser.update(this, bmobUser.getObjectId(), new UpdateListener() {
            @Override
            public void onSuccess() {
                // TODO Auto-generated method stub
                Toast.makeText(PersonDataActivity.this, "更新用户信息成功", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(int code, String msg) {
                // TODO Auto-generated method stub
                Toast.makeText(PersonDataActivity.this, "更新用户信息失败" + msg, Toast.LENGTH_SHORT).show();
            }
        });



       /* String nickname = mNickname.getText().toString();
        String intro = mContent.getText().toString();
        //获得账号在bmob中的账号id
        String objectId=spUtils.getBmobUserId();
        if (TextUtils.isEmpty(nickname) || TextUtils.isEmpty(intro)) {
            Toast.makeText(PersonDataActivity.this, "请填入昵称并介绍下自己吧", Toast.LENGTH_SHORT).show();
            return;
        }
        Boolean gender = false;
        UserInfo info = new UserInfo();
        if (mRg.getCheckedRadioButtonId() == R.id.rBtn_info_girl) {
            gender = true;
        }
        //保存数据在偏好设置中
        spUtils.setNickName(nickname);
        spUtils.setHeaderUrl(avatarUrl);
        spUtils.setIntro(intro);
        spUtils.setGender(gender);

        info.setObjectId(objectId);
        info.setNickName(nickname);
        info.setGender(gender);
        //TODO
        info.setHeaderUrl(avatarUrl);
        info.setIntro(intro);
        //更新数据
        info.update(PersonDataActivity.this, new UpdateListener() {
            @Override
            public void onSuccess() {
                //更新成功
                finish();
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(PersonDataActivity.this, "出错啦"+i+" "+s, Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    @OnClick(R.id.tv_personalData2_id)
    public void makeIvAvatar(View view) {
        //打开系统图库程序的隐式intent
        Intent intent1 = new Intent(Intent.ACTION_PICK);
        //MIME格式 image/* 表示所有图片
        intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");

        //打开拍照的隐式意图
        Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), System.currentTimeMillis() + ".jpg");
        path = file.getAbsolutePath();
        Uri value = Uri.fromFile(file);
        intent2.putExtra(MediaStore.EXTRA_OUTPUT, value);
        Intent chooser = Intent.createChooser(intent1, "选择头像...");
        chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{intent2});
        startActivityForResult(chooser, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 100) {

            if (data != null) {
                //图片是从系统图库中挑选回来
                Uri uri = data.getData();
                //根据uri找到它在sd卡的位置
                Cursor cursor = getContentResolver().query(uri, new String[]{MediaStore.Images.Media.DATA},
                        null, null, null);
                cursor.moveToNext();
                filePath = cursor.getString(0);
                cursor.close();
            } else {
                //图片是从系统拍照返回的
                filePath = path;
            }
            //将图片上传Bmob服务器
            final BmobFile bf = new BmobFile(new File(filePath));
            bf.uploadblock(this, new UploadFileListener() {

                @Override
                public void onSuccess() {
                    //上传后的图片在服务器上的位置（网址）
                    avatarUrl = bf.getFileUrl(PersonDataActivity.this);

                    //将用户选择的头像放到页面中
                    Bitmap bm = BitmapFactory.decodeFile(filePath);
                    mivAvatar.setImageBitmap(bm);
                }

                @Override
                public void onFailure(int arg0, String arg1) {
                    Toast.makeText(PersonDataActivity.this, "头像图片上传失败，错误代码：" + arg0 + "，" + arg1, Toast.LENGTH_SHORT).show();
                }
            });

        }
    }


}
