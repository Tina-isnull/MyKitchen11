package com.example.lcc.mykitchen.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.lcc.mykitchen.ui.CollectActivity;
import com.example.lcc.mykitchen.ui.FocusActivity;
import com.example.lcc.mykitchen.ui.FootprintActivity;
import com.example.lcc.mykitchen.ui.LogoRegistActivity;
import com.example.lcc.mykitchen.ui.MakeActivity;
import com.example.lcc.mykitchen.ui.PersonDataActivity;
import com.example.lcc.mykitchen.R;
import com.example.lcc.mykitchen.notebook.NoteMainActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

import cn.bmob.v3.BmobUser;
import com.example.lcc.mykitchen.constant.Constant;
import com.example.lcc.mykitchen.entity.UserInfo;
import com.example.lcc.mykitchen.utils.SpUtils;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
public class MyFragment extends BaseFragment implements OnClickListener {
    private UserInfo bmobUser;
    private ImageView header;
    private SpUtils spUtils;
    private TextView name;

    public MyFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contentView = inflater.inflate(R.layout.fragment_my, container, false);
        initUI();
        return contentView;
    }

    @Override
    public void initUI() {

        name = (TextView) contentView.findViewById(R.id.tv_myf_nameId);
        spUtils = new SpUtils(getActivity(), Constant.USER_INFO);
        //设置Actionbar
        actionBar = (LinearLayout) contentView.findViewById(R.id.llActionbarId);
        initActionbar(-1, "我的主页", -1);
        //获得控件添加监听事件
        RadioButton money = (RadioButton) contentView.findViewById(R.id.rBtnMoneyId);
        RadioButton focus = (RadioButton) contentView.findViewById(R.id.rBtnFocusId);
        RadioButton collect = (RadioButton) contentView.findViewById(R.id.rBtnCollectId);
        RadioButton seting = (RadioButton) contentView.findViewById(R.id.rBtnMakeId);
        RadioButton foot = (RadioButton) contentView.findViewById(R.id.rBtnFootId);
        RadioButton remember = (RadioButton) contentView.findViewById(R.id.rBtn_remenber_Id);
        header = (ImageView) contentView.findViewById(R.id.img_myf_headerId);
        money.setOnClickListener(this);
        focus.setOnClickListener(this);
        collect.setOnClickListener(this);
        seting.setOnClickListener(this);
        foot.setOnClickListener(this);
        remember.setOnClickListener(this);
        header.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        bmobUser = BmobUser.getCurrentUser(getActivity(),UserInfo.class);
        //更新头像
        if (bmobUser == null) {
            header.setImageResource(R.drawable.header0);
            name.setText("用户名");
            return;
        }
        if (bmobUser.getHeaderUrl() == null) {
            header.setImageResource(R.drawable.header0);
        } else {
            ImageLoader.getInstance().displayImage(bmobUser.getHeaderUrl(), header);
        }
        name.setText(bmobUser.getUsername());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rBtnMoneyId:

                break;
            case R.id.rBtnFocusId:
                startActivity(new Intent(getActivity(), FocusActivity.class));
                break;
            case R.id.rBtnCollectId:
                startActivity(new Intent(getActivity(), CollectActivity.class));
                break;
            case R.id.rBtnMakeId:
                startActivity(new Intent(getActivity(), MakeActivity.class));
                break;
            case R.id.rBtnFootId:
                startActivity(new Intent(getActivity(), FootprintActivity.class));
                break;
            case R.id.rBtn_remenber_Id:
                startActivity(new Intent(getActivity(), NoteMainActivity.class));
                break;
            case R.id.img_myf_headerId:

                boolean loginState = spUtils.getLoginState();
                if (loginState) {
                    Intent intent = new Intent(getActivity(), PersonDataActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent1 = new Intent(getActivity(), LogoRegistActivity.class);
                    startActivity(intent1);
                }
                break;

            default:
                break;
        }
    }

}
