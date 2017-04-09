package com.example.lcc.mykitchen.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lcc.mykitchen.R;
import com.example.lcc.mykitchen.video.VideoActivity2;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.List;

import com.example.lcc.mykitchen.adapter.MethFragmentAdapter;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;
import com.example.lcc.mykitchen.entity.FoodVideo;
import com.example.lcc.mykitchen.entity.UserInfo;

/**
 * A simple {@link Fragment} subclass.
 */
public class VideoFragment1 extends Fragment {
    private View contentView;
    private PullToRefreshListView listview;
    private MethFragmentAdapter videoAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        contentView = inflater.inflate(R.layout.fragment_video_fragment1, container, false);
        initUI();
        return contentView;
    }

    private void initUI() {
        ImageView video = (ImageView) contentView.findViewById(R.id.img_video_cameraId);
        listview = (PullToRefreshListView) contentView.findViewById(R.id.ptrv_video);
        videoAdapter = new MethFragmentAdapter(getActivity());
        listview.setAdapter(videoAdapter);
        if (BmobUser.getCurrentUser(getActivity(), UserInfo.class) != null) {
            video.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getActivity(), VideoActivity2.class));
                }
            });
        } else {
            Toast.makeText(getActivity(), "请您先登录", Toast.LENGTH_SHORT).show();
        }
        listview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                addListViewData();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        addListViewData();
    }

    private void addListViewData() {
        BmobQuery<FoodVideo> query = new BmobQuery<>();
        query.include("userInfo");
        query.findObjects(getActivity(), new FindListener<FoodVideo>() {
            @Override
            public void onSuccess(List list) {
                listview.onRefreshComplete();
                if (list.size() > 0 && list != null) {
                    videoAdapter.addDate(list, true);
                }
            }

            @Override
            public void onError(int i, String s) {
                Toast.makeText(getActivity(), "查询出错" + "i=" + i + "s=" + s, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
