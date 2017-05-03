package com.example.lcc.mykitchen.activity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lcc.mykitchen.MyApp;
import com.example.lcc.mykitchen.R;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

import com.example.lcc.mykitchen.entity.FoodFromWeb;
import com.example.lcc.mykitchen.entity.MenuMaterial;
import com.example.lcc.mykitchen.entity.UploadMenuBean;
import com.example.lcc.mykitchen.entity.UserInfo;

public class UploadMenuActivity extends AppCompatActivity {
    List<MenuMaterial> menuMaterialList;
    @Bind(R.id.tv_addMenu_material)
    TextView menu_material;
    @Bind(R.id.tv_addMenu_name)
    EditText menuName;
    @Bind(R.id.tv_addMenu_info)
    TextView MenInfo;
    @Bind(R.id.img_upload_imgTitle)
    ImageView menuHeader;
    @Bind(R.id.ll_upload_addStep)
    LinearLayout menuSteps;
    private UploadMenuBean bean;
    private String path;
    private String filePath;
    private String avatarUrl;
    //创建集合管理动态布局
    HashMap<Integer, LinearLayout> conditions = new HashMap<>();
    int itemId = 0;
    //默认加入的位置
    int position = 0;
    private ImageView imgStep;
    private List<FoodFromWeb.Steps> lists;
    private TextView imgUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_menu);
        ButterKnife.bind(this);
        bean = new UploadMenuBean();
        lists = new ArrayList<>();

    }

    @OnClick(R.id.ll_upload_material)
    public void addMaterial(View view) {
        Intent intent = new Intent(this, AddMaterialActivity.class);
        startActivityForResult(intent, 200);
    }

    @OnClick(R.id.ll_upload_menuInfo)
    public void MenuInfo(View view) {
        Intent intent = new Intent(this, MenuAddContentActivity.class);
        String info = MenInfo.getText().toString();
        if (info != null) {
            intent.putExtra("next", info);
        }
        startActivityForResult(intent, 201);
    }

    @OnClick(R.id.img_upload_imgTitle)
    public void makeIvAvatar(View view) {
        selectPicture(100);

    }

    @OnClick(R.id.btn_upload_add)
    public void uploadAdd(View view) {
        createConditon();
    }

    @OnClick(R.id.btn_upload_menu)
    public void upload(View view) {
        finishMaterial();
        String mName = menuName.getText().toString();
        bean.setUser(BmobUser.getCurrentUser(this, UserInfo.class));
        bean.setMenuName(mName);
        bean.save(this, new SaveListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(UploadMenuActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
                //获得是10个厨币
                UserInfo userInfo=new UserInfo();
                String money=MyApp.bmobUser.getMoney();
                if(TextUtils.isEmpty(money)){
                    money=10+"";
                }else{
                    money=(Integer.parseInt(money)+10)+"";
                }

                userInfo.setMoney(money);
                userInfo.update(UploadMenuActivity.this, new UpdateListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(UploadMenuActivity.this, "恭喜您获得了10个厨币哦", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(int i, String s) {

                    }
                });

                finish();
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(UploadMenuActivity.this, "上传失败 "+s
                        , Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 动态添加菜谱步骤
     */
    private void createConditon() {
        //每次创建一个view
        LinearLayout ll_limit = (LinearLayout) View.inflate(this, R.layout.item_add_menu_step, null);
        //删除按钮
        ImageView iv_del_item = (ImageView) ll_limit.findViewById(R.id.img_menu_delete);
        imgUrl = (TextView) ll_limit.findViewById(R.id.tv_addMenu_url);
        imgStep = (ImageView) ll_limit.findViewById(R.id.img_upload_step);
        TextView index = (TextView) ll_limit.findViewById(R.id.tv_upload_index);
        index.setText(String.valueOf(itemId + 1));
        //给每个删除条目绑定一个id
        iv_del_item.setTag(itemId);
        iv_del_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取绑定的id
                int itemId = (int) v.getTag();
                //布局中删除指定位置的view
                menuSteps.removeView(conditions.get(itemId));
                //集合中删除指定的item对象
                conditions.remove(itemId);
                //记录position最新位置
                position--;
            }
        });
        imgStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPicture(101);

            }
        });
        //把当前对象保存到集合中
        conditions.put(itemId, ll_limit);
        menuSteps.addView(ll_limit, position);
        //每次添加以后，位置会变动
        position++;
        //每调用一次id++，防止重复
        itemId++;

    }

    /**
     * 获得动态添加控件中的数据
     */

    public void finishMaterial() {
        Set<Map.Entry<Integer, LinearLayout>> items = conditions.entrySet();
        //转换为迭代器
        Iterator iter = items.iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            LinearLayout val = (LinearLayout) entry.getValue();
            EditText step = (EditText) val.findViewById(R.id.et_add_menu_content_step);
            TextView imgUrl = (TextView) val.findViewById(R.id.tv_addMenu_url);
            String stringStep = step.getText().toString();
            String stringUrl = imgUrl.getText().toString();
            if (TextUtils.isEmpty(stringStep)) {
                Toast.makeText(this, "限制条件填写不完整", Toast.LENGTH_SHORT).show();
                return;
            }
           FoodFromWeb.Steps menuStep = new FoodFromWeb().new Steps();
            menuStep.setImg(stringUrl);
            menuStep.setStep(stringStep);
            Log.i("TAG", "menuStep=" + menuStep.toString());
            lists.add(menuStep);
        }
        bean.setMenuStepList(lists);
    }

    /**
     * 选择相机还是相册
     *
     * @param requestCode
     */

    public void selectPicture(int requestCode) {
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
        startActivityForResult(chooser, requestCode);
    }

    /**
     * 得到图片的url上传服务器，为控件赋值
     *
     * @param img
     * @param data
     */
    public void getPicture(final ImageView img, Intent data, final int select) {
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
                avatarUrl = bf.getFileUrl(UploadMenuActivity.this);

                //将用户选择的头像放到页面中
                Bitmap bm = BitmapFactory.decodeFile(filePath);
                img.setImageBitmap(bm);
                if (select == 0) {
                    bean.setMenuHeader(avatarUrl);
                } else {
                    imgUrl.setText(avatarUrl);
                }


            }

            @Override
            public void onFailure(int arg0, String arg1) {
                Toast.makeText(UploadMenuActivity.this, "头像图片上传失败，错误代码：" + arg0 + "，" + arg1, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 200:
                    menuMaterialList = (List<MenuMaterial>) data.getSerializableExtra("materials");
                    StringBuilder builder = new StringBuilder();
                    for (MenuMaterial datas : menuMaterialList) {
                        builder.append(datas.getMaterial()).append(",").append(datas.getCount()).append(",");
                    }
                    builder.deleteCharAt(builder.length() - 1);
                    bean.setMenuMaterial(builder.toString());
                    menu_material.setText(builder);
                    break;
                case 201:
                    String info = data.getStringExtra("info");
                    bean.setMenuIntro(info);
                    MenInfo.setText(info);
                    break;
                case 100:
                    getPicture(menuHeader, data, 0);
                    break;
                case 101:
                    getPicture(imgStep, data, 1);
                    break;
                default:
                    break;
            }
        }
    }

}
