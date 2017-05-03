package com.example.lcc.mykitchen.sharemultiphoto;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.lcc.mykitchen.MyApp;
import com.example.lcc.mykitchen.activity.MainPagerActivity;
import com.example.lcc.mykitchen.R;

import com.example.lcc.mykitchen.adapter.sharemultiphoto.ImagePublishAdapter;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import com.example.lcc.mykitchen.entity.ImageItem;
import com.example.lcc.mykitchen.entity.ShareFriends;
import com.example.lcc.mykitchen.entity.UserInfo;
import com.example.lcc.mykitchen.utils.sharemultiphoto.CustomConstants;
import com.example.lcc.mykitchen.utils.sharemultiphoto.IntentConstants;


public class PublishActivity extends Activity {
    private GridView mGridView;
    private ImagePublishAdapter mAdapter;
    private TextView sendTv;
    public static List<ImageItem> mDataList = new ArrayList<ImageItem>();
    private List<String> imgUrls = new ArrayList<String>();
    private EditText editText;
    private ProgressDialog progressDialog;
    int i=0;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_publish);
        progressDialog=new ProgressDialog(this);
        initData();
        initView();
    }

    protected void onPause() {
        super.onPause();
        saveTempToPref();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        saveTempToPref();
    }

    private void saveTempToPref() {
        SharedPreferences sp = getSharedPreferences(
                CustomConstants.APPLICATION_NAME, MODE_PRIVATE);
        String prefStr = JSON.toJSONString(mDataList);
        sp.edit().putString(CustomConstants.PREF_TEMP_IMAGES, prefStr).commit();

    }

    private void getTempFromPref() {
        SharedPreferences sp = getSharedPreferences(
                CustomConstants.APPLICATION_NAME, MODE_PRIVATE);
        String prefStr = sp.getString(CustomConstants.PREF_TEMP_IMAGES, null);
        if (!TextUtils.isEmpty(prefStr)) {
            List<ImageItem> tempImages = JSON.parseArray(prefStr,
                    ImageItem.class);
            mDataList = tempImages;
        }
    }

    private void removeTempFromPref() {
        SharedPreferences sp = getSharedPreferences(
                CustomConstants.APPLICATION_NAME, MODE_PRIVATE);
        sp.edit().remove(CustomConstants.PREF_TEMP_IMAGES).commit();
    }

    @SuppressWarnings("unchecked")
    private void initData() {
        getTempFromPref();
        List<ImageItem> incomingDataList = (List<ImageItem>) getIntent()
                .getSerializableExtra(IntentConstants.EXTRA_IMAGE_LIST);
        if (incomingDataList != null) {
            mDataList.addAll(incomingDataList);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        notifyDataChanged(); //当在ImageZoomActivity中删除图片时，返回这里需要刷新
    }

    public void initView() {
        TextView titleTv = (TextView) findViewById(R.id.title);
        titleTv.setText("");
        editText= (EditText) findViewById(R.id.et_public_content);
        mGridView = (GridView) findViewById(R.id.gridview);
        mGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        mAdapter = new ImagePublishAdapter(this, mDataList);
        mGridView.setAdapter(mAdapter);
        mGridView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (position == getDataSize()) {
                    new PopupWindows(PublishActivity.this, mGridView);
                } else {
                    Intent intent = new Intent(PublishActivity.this,
                            ImageZoomActivity.class);
                    intent.putExtra(IntentConstants.EXTRA_IMAGE_LIST,
                            (Serializable) mDataList);
                    intent.putExtra(IntentConstants.EXTRA_CURRENT_IMG_POSITION, position);
                    startActivity(intent);
                }
            }
        });
        sendTv = (TextView) findViewById(R.id.action);
        sendTv.setText("发送");
        sendTv.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                //获得是10个厨币
                UserInfo userInfo=new UserInfo();
                String money=BmobUser.getCurrentUser(PublishActivity.this, UserInfo.class).getMoney();
                if(TextUtils.isEmpty(money)){
                    money=10+"";
                }else{

                    money=(Integer.parseInt(money)+10)+"";
                }
                userInfo.setMoney(money+"");
                userInfo.update(PublishActivity.this, new UpdateListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(PublishActivity.this, "恭喜您获得了10个厨币哦", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(int i, String s) {

                    }
                });


                removeTempFromPref();
                progressDialog.show(PublishActivity.this,"提示","正在上传图片");
                //System.exit(0);
                //TODO 这边以mDataList为来源做上传的动作
                //将图片上传Bmob服务器

                for (ImageItem img : mDataList) {

                    final BmobFile bf = new BmobFile(new File(img.sourcePath));
                    bf.uploadblock(PublishActivity.this, new UploadFileListener() {
                        @Override
                        public void onSuccess() {
                            Log.i("TAG", "success2");
                            i=i+1;
                            //上传后的图片在服务器上的位置（网址）
                            String avatarUrl = bf.getFileUrl(PublishActivity.this);
                            imgUrls.add(avatarUrl);
                            if(i==mDataList.size()){
                                //将获得的图片位置和文本内容保存在bmob云端
                                saveShareLive(imgUrls);
                            }
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            Log.i("TAG", "error" + i + " " + s);
                        }
                    });
                }

            }
        });
    }

    public void saveShareLive(List<String> lists) {
        UserInfo userInfo= BmobUser.getCurrentUser(this,UserInfo.class);
        ShareFriends sf=new ShareFriends();
        String content=editText.getText().toString();
        sf.setImgs(lists);
        sf.setCountLove(0);
        sf.setContent(content);
        sf.setUserInfo(userInfo);
        sf.save(PublishActivity.this, new SaveListener() {
            @Override
            public void onSuccess() {
                Log.i("TAG", "success2");
                progressDialog.dismiss();
                startActivity(new Intent(PublishActivity.this, MainPagerActivity.class));
            }

            @Override
            public void onFailure(int i, String s) {
                Log.i("TAG", "error" + i + " " + s);
            }
        });
    }


    private int getDataSize() {
        return mDataList == null ? 0 : mDataList.size();
    }

    private int getAvailableSize() {
        int availSize = CustomConstants.MAX_IMAGE_SIZE - mDataList.size();
        if (availSize >= 0) {
            return availSize;
        }
        return 0;
    }

    public String getString(String s) {
        String path = null;
        if (s == null) return "";
        for (int i = s.length() - 1; i > 0; i++) {
            s.charAt(i);
        }
        return path;
    }

    public class PopupWindows extends PopupWindow {

        public PopupWindows(Context mContext, View parent) {

            View view = View.inflate(mContext, R.layout.item_popupwindow, null);
            view.startAnimation(AnimationUtils.loadAnimation(mContext,
                    R.anim.fade_ins));
            LinearLayout ll_popup = (LinearLayout) view
                    .findViewById(R.id.ll_popup);
            ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext,
                    R.anim.push_bottom_in_2));

            setWidth(LayoutParams.MATCH_PARENT);
            setHeight(LayoutParams.MATCH_PARENT);
            setFocusable(true);
            setOutsideTouchable(true);
            setContentView(view);
            showAtLocation(parent, Gravity.BOTTOM, 0, 0);
            update();

            Button bt1 = (Button) view
                    .findViewById(R.id.item_popupwindows_camera);
            Button bt2 = (Button) view
                    .findViewById(R.id.item_popupwindows_Photo);
            Button bt3 = (Button) view
                    .findViewById(R.id.item_popupwindows_cancel);
            bt1.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    takePhoto();
                    dismiss();
                }
            });
            bt2.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(PublishActivity.this,
                            ImageBucketChooseActivity.class);
                    intent.putExtra(IntentConstants.EXTRA_CAN_ADD_IMAGE_SIZE,
                            getAvailableSize());
                    startActivity(intent);
                    dismiss();
                }
            });
            bt3.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    dismiss();
                }
            });

        }
    }

    private static final int TAKE_PICTURE = 0x000000;
    private String path = "";

    public void takePhoto() {
        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        File vFile = new File(Environment.getExternalStorageDirectory()
                + "/myimage/", String.valueOf(System.currentTimeMillis())
                + ".jpg");
        if (!vFile.exists()) {
            File vDirPath = vFile.getParentFile();
            vDirPath.mkdirs();
        } else {
            if (vFile.exists()) {
                vFile.delete();
            }
        }
        path = vFile.getPath();
        Uri cameraUri = Uri.fromFile(vFile);
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, cameraUri);
        startActivityForResult(openCameraIntent, TAKE_PICTURE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PICTURE:
                if (mDataList.size() < CustomConstants.MAX_IMAGE_SIZE
                        && resultCode == -1 && !TextUtils.isEmpty(path)) {
                    ImageItem item = new ImageItem();
                    item.sourcePath = path;
                    mDataList.add(item);
                }
                break;
        }
    }

    private void notifyDataChanged() {
        mAdapter.notifyDataSetChanged();
    }

}