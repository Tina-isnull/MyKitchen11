package com.example.lcc.mykitchen.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.lcc.mykitchen.R;
import com.example.lcc.mykitchen.entity.Comments;
import com.example.lcc.mykitchen.entity.ShareContent;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

import com.example.lcc.mykitchen.adapter.ShareFragmentAdapter;

import cn.bmob.v3.BmobQuery;
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
    private List<String> stringM;
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
public void setOnSendVisiable(sendDynamic send){
    this.send=send;
}
    private void initialUI() {
        listView = (PullToRefreshListView) view.findViewById(R.id.listView_shareF_id);
        data = new ArrayList<>();
        mShareContent=new ArrayList<>();

        adapter = new ShareFragmentAdapter(getActivity(),send);
        listView.setAdapter(adapter);

        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
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
                        ShareContent shareContent=new ShareContent();
                        stringM=new ArrayList<>();
                        shareContent.setShareFriends(lists);
                        for (Comments comment:Clist){
                            if(comment.getShareId().equals(lists.getObjectId())){
                                stringM.add(comment.getContent());
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

    private void CommentDate() {
        BmobQuery<Comments> query=new BmobQuery<>();
        query.findObjects(getActivity(), new FindListener<Comments>() {
            @Override
            public void onSuccess(List<Comments> list) {
                initialDate(list);
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }
    public interface sendDynamic {
        public void sendVisible(String id);
    }
}
