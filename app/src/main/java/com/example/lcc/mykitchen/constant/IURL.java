package com.example.lcc.mykitchen.constant;

/**
 * Created by lcc on 2016/12/20.
 */
public interface IURL {
    //http://172.60.50.197:8080/MusicServer/
    public static final String ROOT = "http://172.60.15.208:8080/MusicServer/";
    public static final String MUSIC_URL = ROOT + "loadMusics.jsp";
    //音乐播放界面向服务发送广播
    public static final String MUSIC_PLAY_ACTION = "com.lcc.play";
    public static final String MUSIC_PAUSE_ACTION = "com.lcc.pause";
    public static final String MUSIC_PROCESS_ACTION = "com.lcc.process";
    //服务向音乐播放器发送广播
    public static final String MUSIC_PROCESS_UPDATE_ACTION = "com.lcc.process.update";

}
