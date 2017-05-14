package com.example.lcc.mykitchen.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lcc.mykitchen.MyApp;
import com.example.lcc.mykitchen.R;
import com.example.lcc.mykitchen.entity.Comments;
import com.example.lcc.mykitchen.entity.RelatedPartner;
import com.example.lcc.mykitchen.entity.ShareContent;
import com.example.lcc.mykitchen.entity.UserInfo;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

import com.example.lcc.mykitchen.adapter.ShareFragmentAdapter;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;

import com.example.lcc.mykitchen.entity.ShareFriends;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShareFragment01 extends Fragment {


    private View view;
    private ShareFragmentAdapter adapter;
    private List<ShareFriends> data;
    private PullToRefreshListView listView;
    private sendDynamic send;
    private List<ShareContent> mShareContent;
    private List<Comments> stringM;
    private UserInfo bmobUser;

    public ShareFragment01() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_share_fragment01, container, false);
        initialUI();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        CommentDate();
    }

    public void setOnSendVisiable(sendDynamic send) {
        this.send = send;
    }

    private void initialUI() {
        bmobUser = BmobUser.getCurrentUser(getActivity(), UserInfo.class);
        listView = (PullToRefreshListView) view.findViewById(R.id.listView_shareF_id);
        data = new ArrayList<>();
        mShareContent = new ArrayList<>();

        adapter = new ShareFragmentAdapter(getActivity(), send);
        listView.setAdapter(adapter);

        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                getRelatedPatener();
                CommentDate();
            }
        });

    }


    private void initialDate(final List<Comments> Clist) {

        final BmobQuery<ShareFriends> query = new BmobQuery<>();
        query.include("userInfo");
        query.findObjects(getActivity(), new FindListener<ShareFriends>() {
            @Override
            public void onSuccess(List<ShareFriends> list) {
                listView.onRefreshComplete();
                mShareContent.clear();
                if (list != null && list.size() > 0) {
                    for (ShareFriends lists : list) {
                        ShareContent shareContent = new ShareContent();
                        stringM = new ArrayList<>();
                        shareContent.setShareFriends(lists);
                        for (Comments comment : Clist) {
                            if (comment.getShareId().equals(lists.getObjectId())) {
                                stringM.add(comment);
                            }
                        }
                        shareContent.setCommentList(stringM);
                        mShareContent.add(shareContent);
                    }
                    adapter.addDate(mShareContent, true);

                }
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }
    private void initialDate2() {

        final BmobQuery<ShareFriends> query = new BmobQuery<>();
        query.include("userInfo");
        query.findObjects(getActivity(), new FindListener<ShareFriends>() {
            @Override
            public void onSuccess(List<ShareFriends> list) {
                listView.onRefreshComplete();
                mShareContent.clear();
                if (list != null && list.size() > 0) {
                    for (ShareFriends lists : list) {
                        ShareContent shareContent = new ShareContent();
                        shareContent.setShareFriends(lists);
                        mShareContent.add(shareContent);
                    }
                    adapter.addDate(mShareContent, true);

                }
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }
    private void CommentDate() {
        BmobQuery<Comments> query = new BmobQuery<>();
        query.findObjects(getActivity(), new FindListener<Comments>() {
            @Override
            public void onSuccess(List<Comments> list) {
                initialDate(list);
            }

            @Override
            public void onError(int i, String s) {
                if(i==101){
                    initialDate2();
                }
            }
        });
    }

    public interface sendDynamic {
        public void sendVisible(String id, String name);
    }

    public void getRelatedPatener() {
        MyApp.relatedName.clear();
        //获得关注的人的信息
        if (bmobUser != null) {
            BmobQuery<RelatedPartner> query = new BmobQuery<>();
            query.include("relatedName");
            query.addWhereEqualTo("userName", bmobUser.getObjectId());
            query.findObjects(getActivity(), new FindListener<RelatedPartner>() {
                @Override
                public void onSuccess(List<RelatedPartner> list) {
                    if(list==null&&list.size()==0){
                        Toast.makeText(getActivity(), "您还没有关注人哦", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    for (RelatedPartner data : list) {
                        MyApp.relatedName.add(data.getRelatedName().getObjectId());
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

}
