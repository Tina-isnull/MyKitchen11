package com.example.lcc.mykitchen.entity;

import java.util.List;

/**
 * Created by lcc on 2017/4/9.
 */
public class ShareContent {
    private ShareFriends shareFriends;
    private List<Comments> commentList;

    public ShareFriends getShareFriends() {
        return shareFriends;
    }

    public void setShareFriends(ShareFriends shareFriends) {
        this.shareFriends = shareFriends;
    }

    public List<Comments> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comments> commentList) {
        this.commentList = commentList;
    }
}
