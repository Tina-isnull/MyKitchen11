package com.example.lcc.mykitchen.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.lcc.mykitchen.R;
import com.example.lcc.mykitchen.entity.VideoWeb;
import com.example.lcc.mykitchen.zhibo.LivePlayerActivity;
import com.example.lcc.mykitchen.zhibo.RTMPBaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class VideoFragment2 extends Fragment {

    private ListView listView;
    private MyAdapter adapter;
    private List<VideoWeb> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video_fragment2, container, false);
        list = new ArrayList<>();

        listView = (ListView) view.findViewById(R.id.listView_video_id);
        adapter = new MyAdapter();
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getActivity(), LivePlayerActivity.class);
                VideoWeb videoWeb= (VideoWeb) adapter.getItem(position);
                String url=videoWeb.getUrl();
                intent.putExtra("playUrl",url);
                startActivity(intent);
            }
        });
        return view;
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder vHolder;
            if(convertView==null){
                vHolder=new ViewHolder();
                convertView=getActivity().getLayoutInflater().inflate(R.layout.item_video_web,parent,false);
                vHolder.img= (ImageView) convertView.findViewById(R.id.img_video_img);
                vHolder.url= (TextView) convertView.findViewById(R.id.tv_video_url);
                convertView.setTag(vHolder);
            }else{
                vHolder= (ViewHolder) convertView.getTag();
            }
            VideoWeb mVideoWeb= (VideoWeb) getItem(position);
            vHolder.url.setText(mVideoWeb.getUrl());
            vHolder.img.setImageResource(mVideoWeb.getImg());
            ImageView img = new ImageView(getActivity());
            img.setImageResource(list.get(position).getImg());

            return convertView;

        }

        class ViewHolder {
            private ImageView img;
            private TextView url;

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        VideoWeb videoWeb=new VideoWeb();
        videoWeb.setImg(R.drawable.video_food1);
        videoWeb.setUrl("rtmp://6505.liveplay.myqcloud.com/live/6505_5115940c0e");
        list.add(videoWeb);
        adapter.notifyDataSetChanged();
    }
}
