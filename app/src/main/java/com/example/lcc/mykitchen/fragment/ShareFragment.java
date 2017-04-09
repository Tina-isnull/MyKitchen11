package com.example.lcc.mykitchen.fragment;


import com.example.lcc.mykitchen.MyApp;
import com.example.lcc.mykitchen.adapter.SharePagerAdapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lcc.mykitchen.R;
import com.example.lcc.mykitchen.entity.Comments;
import com.example.lcc.mykitchen.entity.UserInfo;
import com.example.lcc.mykitchen.sharemultiphoto.PublishActivity;

import com.example.lcc.mykitchen.ui.SearchActivity;
import com.example.lcc.mykitchen.view.TabGroup;

import cn.bmob.v3.listener.SaveListener;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
public class ShareFragment extends BaseFragment implements ShareFragment01.sendDynamic {
    private View commnet_diaolog;
    private ViewPager vPager;
    private TabGroup tgroup;
    private SharePagerAdapter adapter;
    private EditText et_comment_big;
    private TextView bt_submit_commnet;
    private LinearLayout rl_comment;
    private LinearLayout rl_blank;
    private TextView tv_send;
    private String shareId;
    public ShareFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contentView = inflater.inflate(R.layout.fragment_share, container, false);
        initUI();
        return contentView;
    }

    @Override
    public void initUI() {
        actionBar = (LinearLayout) contentView.findViewById(R.id.llActionbarId);
        initActionbar(-1, "分享美食", -1);
        ImageView share = (ImageView) contentView.findViewById(R.id.img_share_cameraId);
        share.setOnClickListener(new shareOnClick());
        //评论部分
        commnet_diaolog = contentView.findViewById(R.id.commnet_diaolog);
        et_comment_big = (EditText) commnet_diaolog.findViewById(R.id.et_comment_big);
        bt_submit_commnet = (TextView) commnet_diaolog.findViewById(R.id.bt_submit_commnet);
        rl_comment = (LinearLayout) commnet_diaolog.findViewById(R.id.rl_comment);
        rl_blank = (LinearLayout) commnet_diaolog.findViewById(R.id.rl_blank);
        tv_send= (TextView) commnet_diaolog.findViewById(R.id.bt_submit_commnet_1);
        et_comment_big.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputManager = (InputMethodManager) et_comment_big.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(et_comment_big, 0);
            }
        });
        rl_blank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hidddenKeyWindow();
                rl_comment.setVisibility(View.GONE);
            }
        });
        tv_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text=et_comment_big.getText().toString();
                String Content=MyApp.bmobUser.getUsername()+" 回复 "+text;
                Comments comment=new Comments();
                comment.setShareId(shareId);
                comment.setContent(Content);
                comment.save(getActivity(), new SaveListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(getActivity(),"评论成功",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        Toast.makeText(getActivity(),"请稍后再试",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        // 初始化ViewPager
        initViewPager();
        // 初始化RadioButton
        initRadioButton();

    }

    @Override
    public void sendVisible(String id) {
        //得到分享列表的id值
        shareId=id;
        startEditext("请评论");
    }



    class shareOnClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            startActivity(new Intent(getActivity(), PublishActivity.class));
        }
    }

    private void initRadioButton() {
        tgroup = (TabGroup) contentView.findViewById(R.id.radioGroup1);
        tgroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //
                RadioButton rBtn = (RadioButton) group.findViewById(checkedId);
                int index = group.indexOfChild(rBtn);
                tgroup.update(index);
                if (checkedId == R.id.radio0) {
                    vPager.setCurrentItem(0);

                } else {
                    vPager.setCurrentItem(1);
                }

            }
        });
    }

    private void initViewPager() {
        vPager = (ViewPager) contentView.findViewById(R.id.viewPagerShareFID);
        FragmentManager fManager = getChildFragmentManager();
        adapter = new SharePagerAdapter(fManager);
        ShareFragment01 sfrag1 = new ShareFragment01();
        ShareFragment02 sfrag2 = new ShareFragment02();
        sfrag1.setOnSendVisiable(this);
        sfrag2.setOnSendVisiable(this);
        adapter.addFragment(sfrag1);
        adapter.addFragment(sfrag2);
        vPager.setAdapter(adapter);
        //让第一个ViewPager页面显示
        vPager.setCurrentItem(0);
        vPager.setOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageSelected(int arg0) {
                if (arg0 == 0) {
                    tgroup.check(R.id.radio0);
                } else {
                    tgroup.check(R.id.radio1);
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
    }

    public void startEditext(String comment) {
        UserInfo bmobUser = MyApp.bmobUser;
        if (bmobUser == null) {
            Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();

        } else {
            //自动呼起键盘
            et_comment_big.setHint(comment);
            et_comment_big.setFocusable(true);
            et_comment_big.setFocusableInTouchMode(true);
            et_comment_big.requestFocus();
            InputMethodManager inputManager = (InputMethodManager) et_comment_big.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.showSoftInput(et_comment_big, 0);
            //显示布局
            rl_comment.setVisibility(View.VISIBLE);

        }


    }
    //隐藏软件键盘

    public void hidddenKeyWindow() {
        /**隐藏软键盘**/
        View view =getActivity().getWindow().peekDecorView();
        //清楚编辑框内的东西
        et_comment_big.setText("");
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

    }

}
