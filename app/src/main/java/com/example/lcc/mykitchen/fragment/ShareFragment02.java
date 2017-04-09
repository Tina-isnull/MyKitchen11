package com.example.lcc.mykitchen.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lcc.mykitchen.MyApp;
import com.example.lcc.mykitchen.R;
import com.example.lcc.mykitchen.entity.Comments;
import com.example.lcc.mykitchen.entity.ShareContent;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

import com.example.lcc.mykitchen.adapter.ShareFragmentAdapter;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;
import com.example.lcc.mykitchen.entity.CollectKiter;
import com.example.lcc.mykitchen.entity.ShareFriends;
import com.example.lcc.mykitchen.entity.UserInfo;
import com.example.lcc.mykitchen.utils.DBUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShareFragment02 extends Fragment {

    private View view;
    private ShareFragmentAdapter adapter;
    private UserInfo bmobUser;
    private List<String> focusUsers = MyApp.relatedName;
    private DBUtils db;
    private PullToRefreshListView listView;
    private ShareFragment01.sendDynamic send;
    private List<ShareContent> mShareContent;
    private List<Comments> stringM;
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
        commentDate();
    }
    public void setOnSendVisiable(ShareFragment01.sendDynamic send){
        this.send=send;
    }
    private void initialUI() {
        db=new DBUtils(getActivity(),2);
        mShareContent=new ArrayList<>();

        bmobUser = BmobUser.getCurrentUser(getActivity(), UserInfo.class);
        listView = (PullToRefreshListView) view.findViewById(R.id.listView_shareF_id);
        adapter = new ShareFragmentAdapter(getActivity(),send);
        listView.setAdapter(adapter);
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
               commentDate();
            }
        });
    }

    private void queryRelatedPartner() {
        List<CollectKiter> kiter= db.queryKiter();
        if(kiter!=null&&kiter.size()>0){
            for (CollectKiter data:kiter){
                focusUsers.add(data.getId());
            }
            return;
        }
     /*   BmobQuery<RelatedPartner> query = new BmobQuery<>();
        query.include("relatedName");
        query.addWhereEqualTo("userName", bmobUser.getObjectId());
        query.findObjects(getActivity(), new FindListener<RelatedPartner>() {
            @Override
            public void onSuccess(List<RelatedPartner> list) {
                for (RelatedPartner data : list) {
                    focusUsers.add(data.getRelatedName().getObjectId());
                    //卸载后，软件重新安装后从网上请求关注关系数据表
                    DBUtils utils = new DBUtils(getActivity(), 2);
                    CollectKiter kiter = new CollectKiter();
                    kiter.setId(data.getRelatedName().getObjectId());
                    kiter.setImgHeader(data.getRelatedName().getHeaderUrl());
                    kiter.setName(data.getRelatedName().getUsername());
                    kiter.setIntroduce(data.getRelatedName().getIntro());
                    utils.addKiter(kiter);
                }
            }

            @Override
            public void onError(int i, String s) {
                Log.i("TAG","错误代码"+i+","+s);
            }
        });*/
    }

    private void initialDate(final List<Comments> Clist) {
        final BmobQuery<ShareFriends> query = new BmobQuery<>();
        query.include("userInfo");
        query.findObjects(getActivity(), new FindListener<ShareFriends>() {
            @Override
            public void onSuccess(List<ShareFriends> list) {
                listView.onRefreshComplete();
                mShareContent.clear();

                if(list!=null&&list.size()>0){
                for (ShareFriends data : list) {
                    for (String focus : focusUsers) {
                        if (data.getUserInfo().getObjectId().equals(focus)||data.getUserInfo().getObjectId().equals(bmobUser.getObjectId())) {
                            ShareContent shareContent=new ShareContent();
                            stringM=new ArrayList<>();
                            shareContent.setShareFriends(data);
                            for (Comments comment:Clist){
                                if(comment.getShareId().equals(data.getObjectId())){
                                    stringM.add(comment);
                                }
                            }
                            shareContent.setCommentList(stringM);
                            mShareContent.add(shareContent);

                            }
                        break;
                        }
                    }
                    adapter.addDate(mShareContent, true);

                }
            }

            @Override
            public void onError(int i, String s) {

            }
        });


    }
    private void commentDate() {
        if (focusUsers.size() == 0 || focusUsers == null) {
            Toast.makeText(getActivity(), "您还没有关注人哦", Toast.LENGTH_SHORT).show();
            return;
        }
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