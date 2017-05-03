package com.example.lcc.mykitchen.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lcc.mykitchen.MyApp;
import com.example.lcc.mykitchen.R;
import com.example.lcc.mykitchen.entity.Comments;
import com.example.lcc.mykitchen.entity.ShareContent;
import com.example.lcc.mykitchen.fragment.ShareFragment01;
import com.example.lcc.mykitchen.view.MyListVIew;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import com.example.lcc.mykitchen.adapter.MyBaseAdapter;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.sharesdk.onekeyshare.OnekeyShare;

import com.example.lcc.mykitchen.constant.Constant;
import com.example.lcc.mykitchen.entity.CollectKiter;
import com.example.lcc.mykitchen.entity.Image;
import com.example.lcc.mykitchen.entity.RelatedPartner;
import com.example.lcc.mykitchen.entity.ShareFriends;
import com.example.lcc.mykitchen.entity.UserInfo;
import com.example.lcc.mykitchen.entity.Zan;

import com.example.lcc.mykitchen.utils.DBUtils;
import com.example.lcc.mykitchen.utils.ScreenTools;
import com.example.lcc.mykitchen.utils.SpUtils;
import com.example.lcc.mykitchen.view.CustomImageView;
import com.example.lcc.mykitchen.view.NineGridLayout;

/**
 * Created by lcc on 2016/12/12.
 */
public class ShareFragmentAdapter extends MyBaseAdapter<ShareContent> {
    UserInfo bmobUser = BmobUser.getCurrentUser(context, UserInfo.class);;
    SpUtils sp = new SpUtils(context, Constant.USER_INFO);
    DBUtils db = new DBUtils(context, 2);
    //控制输入面板的输入
    ShareFragment01.sendDynamic mSendDynamic;

    public ShareFragmentAdapter(Context context, ShareFragment01.sendDynamic mSendDynamic) {
        super(context);
        this.mSendDynamic = mSendDynamic;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.inflater_sharef_listview_item, null);
            viewHolder = new ViewHolder();
            viewHolder.ivMore = (NineGridLayout) convertView.findViewById(R.id.rl_sharef_imgsId);
            viewHolder.ivOne = (CustomImageView) convertView.findViewById(R.id.rl_sharef_oneImgId);
            viewHolder.headerImg = (ImageView) convertView.findViewById(R.id.img_sharef_headerid);
            viewHolder.name = (TextView) convertView.findViewById(R.id.tv_sharef_nameid);
            viewHolder.time = (TextView) convertView.findViewById(R.id.tv_sharef_timeid);
            viewHolder.context = (TextView) convertView.findViewById(R.id.tv_sharef_contendId);
            viewHolder.tvShare = (TextView) convertView.findViewById(R.id.tv_item_blog_share);
            viewHolder.tvLove = (TextView) convertView.findViewById(R.id.tv_item_blog_love);
            viewHolder.tvComment = (TextView) convertView.findViewById(R.id.tv_item_blog_comment);
            viewHolder.focuse = (ImageView) convertView.findViewById(R.id.btn_sharef_focuid);
            viewHolder.commentList = (MyListVIew) convertView.findViewById(R.id.list_comment);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final ShareContent shareItem = getItem(position);
        List<String> imgUrls = shareItem.getShareFriends().getImgs();
        List<Image> imgs = new ArrayList<Image>();
        for (int i = 0; i < imgUrls.size(); i++) {
            Image img = new Image(imgUrls.get(i), 250, 250);
            imgs.add(img);
        }
        if (imgs.isEmpty()) {
            viewHolder.ivMore.setVisibility(View.GONE);
            viewHolder.ivOne.setVisibility(View.GONE);
        } else if (imgs.size() == 1) {
            viewHolder.ivMore.setVisibility(View.GONE);
            viewHolder.ivOne.setVisibility(View.VISIBLE);
            handlerOneImage(viewHolder, imgs.get(0));
        } else {
            viewHolder.ivMore.setVisibility(View.VISIBLE);
            viewHolder.ivOne.setVisibility(View.GONE);
            viewHolder.ivMore.setImagesData(imgs);
        }
        if (!shareItem.getShareFriends().getContent().equals(null)) {
            viewHolder.context.setText(shareItem.getShareFriends().getContent());
        }
        viewHolder.time.setText(shareItem.getShareFriends().getCreatedAt());
        viewHolder.name.setText(shareItem.getShareFriends().getUserInfo().getUsername());
        ImageLoader.getInstance().displayImage(shareItem.getShareFriends().getUserInfo().getHeaderUrl(), viewHolder.headerImg);
        //如果用户没有登录，则不会有用户信息，则全部显示信息
        if (bmobUser != null) {
            if (shareItem.getShareFriends().getUserInfo().getObjectId().equals(bmobUser.getObjectId())) {
                viewHolder.focuse.setVisibility(View.GONE);
            }
            //关注的人直接显示已经关注
            List<String> focus = MyApp.relatedName;
            if (focus.size() > 0 && focus != null) {
                for (String list : focus) {
                    if (list.equals(shareItem.getShareFriends().getUserInfo().getObjectId()))
                        viewHolder.focuse.setImageResource(R.drawable.rec_followed);
                    viewHolder.focuse.setEnabled(false);
                }
            }
        }
        viewHolder.tvLove.setText(shareItem.getShareFriends().getCountLove().toString());
        viewHolder.focuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bmobUser == null) {
                    Toast.makeText(context, context.getString(R.string.unLogin_toast), Toast.LENGTH_LONG).show();
                    return;
                }
                viewHolder.focuse.setImageResource(R.drawable.rec_followed);
               /* CollectKiter kiter = new CollectKiter();
                kiter.setId(shareItem.getUserInfo().getObjectId());
                kiter.setImgHeader(shareItem.getUserInfo().getHeaderUrl());
                kiter.setName(shareItem.getUserInfo().getUsername());
                kiter.setIntroduce(shareItem.getUserInfo().getIntro());
                db.addKiter(kiter);*/
                viewHolder.focuse.setEnabled(false);
                RelatedPartner partner = new RelatedPartner();
                partner.setUserName(bmobUser.getObjectId());
                partner.setRelatedName(shareItem.getShareFriends().getUserInfo());
                partner.save(context, new SaveListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(context, "关注成功", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        Log.i("TAG", "服务器繁忙，稍后重试" + i + "," + s);
                        Toast.makeText(context, "服务器繁忙，稍后重试" + i + "," + s, Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
        viewHolder.tvLove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bmobUser == null) {
                    Toast.makeText(context, context.getString(R.string.unLogin_toast), Toast.LENGTH_LONG).show();
                    return;
                }
                BmobQuery<Zan> query = new BmobQuery<Zan>();
                query.addWhereEqualTo("userId", bmobUser.getObjectId());
                query.findObjects(context, new FindListener<Zan>() {
                    @Override
                    public void onSuccess(List<Zan> list) {
                        if (list != null && list.size() > 0) {
                            Toast.makeText(context, "已经点过赞了", Toast.LENGTH_SHORT).show();
                        } else {
                            saveZan(shareItem.getShareFriends(), bmobUser.getObjectId());
                        }


                    }

                    @Override
                    public void onError(int i, String s) {
                        switch (i) {
                            case 101:
                                saveZan(shareItem.getShareFriends(), bmobUser.getObjectId());
                                break;

                            default:
                                Log.d("TAG", "查询失败，错误代码:" + i + "," + s);
                                break;
                        }
                    }
                });

            }

            private void saveZan(final ShareFriends shareFriends, String userId) {
                Zan zan = new Zan();
                zan.setUserId(userId);
                zan.setShareId(shareFriends);
                zan.save(context, new SaveListener() {
                    @Override
                    public void onSuccess() {
                        //TODO 判断有没更新
                        shareFriends.setCountLove(shareFriends.getCountLove() + 1);
                        //更新数据源中赞的数量
                        notifyDataSetChanged();
                        shareFriends.update(context, new UpdateListener() {
                            @Override
                            public void onSuccess() {
                                Toast.makeText(context, "点赞成功", Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onFailure(int i, String s) {
                                Toast.makeText(context, "点赞失败" + i + "," + s, Toast.LENGTH_SHORT).show();

                            }
                        });
                    }

                    @Override
                    public void onFailure(int i, String s) {

                    }
                });


            }
        });
        // ArrayAdapter<String> adapter=new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,shareItem.getCommentList());
        final ArrayCommentAdapter cAdapter = new ArrayCommentAdapter(context);
        viewHolder.commentList.setAdapter(cAdapter);
        viewHolder.commentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                if (bmobUser == null) {
                    Toast.makeText(context, context.getString(R.string.unLogin_toast), Toast.LENGTH_LONG).show();
                    return;
                }
                String item = cAdapter.getItem(position).getContent();
                int index = item.indexOf("回") - 1;
                String name = item.substring(0, index);
                if (bmobUser.getUsername().equals(name)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("要删除评论吗")
                            .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    cAdapter.removeData(shareItem.getCommentList().get(position));
                                    notifyDataSetChanged();
                                    Comments comment = new Comments();
                                    comment.setObjectId(shareItem.getCommentList().get(position).getObjectId());
                                    comment.delete(context, new DeleteListener() {


                                        @Override
                                        public void onSuccess() {
                                            Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show();
                                        }

                                        @Override
                                        public void onFailure(int i, String s) {

                                        }
                                    });
                                }
                            })
                            .setNegativeButton("否", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            }).show();
                } else {
                    mSendDynamic.sendVisible(shareItem.getShareFriends().getObjectId(), name);
                }


            }
        });

        cAdapter.addDate(shareItem.getCommentList(), true);
        cAdapter.notifyDataSetChanged();
        viewHolder.tvComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSendDynamic.sendVisible(shareItem.getShareFriends().getObjectId(), shareItem.getShareFriends().getUserInfo().getUsername());

            }
        });
        viewHolder.tvShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showShare(position, shareItem.getShareFriends().getContent(), shareItem.getShareFriends().getImgs().get(1));
            }
        });

        return convertView;
    }


    protected void showShare(int pos, String text, String imgurl) {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网、QQ和QQ空间使用
        oks.setTitle("来自私厨的分享");
        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText(text);
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl(imgurl);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("ShareSDK");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

        // 启动分享GUI
        oks.show(context);
    }

    //请求得到关注人的信息
    private List<String> queryRelatedPartner() {
        final List<String> focusUsers = new ArrayList<>();
       /* List<CollectKiter> kiter = db.queryKiter();
        if (kiter != null && kiter.size() > 0) {
            for (CollectKiter data : kiter) {
                focusUsers.add(data.getId());
            }
            return focusUsers;
        }*/
        BmobQuery<RelatedPartner> query = new BmobQuery<>();
        query.include("relatedName");
        query.addWhereEqualTo("userName", bmobUser.getObjectId());
        query.findObjects(context, new FindListener<RelatedPartner>() {
            @Override
            public void onSuccess(List<RelatedPartner> list) {
                for (RelatedPartner data : list) {
                    focusUsers.add(data.getRelatedName().getObjectId());
                    Log.i("TAG", data.getRelatedName().getObjectId());
                }
            }

            @Override
            public void onError(int i, String s) {
                //TODO
                Log.i("TAG", "错误代码" + i + "," + s);
            }
        });
        return focusUsers;
    }

    private void handlerOneImage(ViewHolder viewHolder, Image image) {
        int totalWidth;
        int imageWidth;
        int imageHeight;
        ScreenTools screentools = ScreenTools.instance(context);
        totalWidth = screentools.getScreenWidth() - screentools.dip2px(80);
        imageWidth = screentools.dip2px(image.getWidth());
        imageHeight = screentools.dip2px(image.getHeight());
        if (image.getWidth() <= image.getHeight()) {
            if (imageHeight > totalWidth) {
                imageHeight = totalWidth;
                imageWidth = (imageHeight * image.getWidth()) / image.getHeight();
            }
        } else {
            if (imageWidth > totalWidth) {
                imageWidth = totalWidth;
                imageHeight = (imageWidth * image.getHeight()) / image.getWidth();
            }
        }
        ViewGroup.LayoutParams layoutparams = viewHolder.ivOne.getLayoutParams();
        layoutparams.height = imageHeight;
        layoutparams.width = imageWidth;
        viewHolder.ivOne.setLayoutParams(layoutparams);
        viewHolder.ivOne.setClickable(true);
        viewHolder.ivOne.setScaleType(android.widget.ImageView.ScaleType.FIT_XY);
        //viewHolder.ivOne.setImageResource(image.getImg());
        // HttpRequestManager.displayImage(image.getImgUrl(), viewHolder.ivOne);
        ImageLoader.getInstance().displayImage(image.getImgUrl(), viewHolder.ivOne);

    }


    class ViewHolder {
        public NineGridLayout ivMore;
        public CustomImageView ivOne;
        public ImageView headerImg;
        public TextView name;
        public TextView time;
        public TextView context;
        public ImageView focuse;
        public TextView tvShare;
        public TextView tvLove;
        public TextView tvComment;
        public MyListVIew commentList;
    }


}

