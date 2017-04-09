package com.example.lcc.mykitchen.video;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.lcc.mykitchen.R;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import com.example.lcc.mykitchen.constant.Constant;
import com.example.lcc.mykitchen.utils.SpUtils;


/**
 * qiangyu on 1/26/16 15:33
 * csdn博客:http://blog.csdn.net/yissan
 */
public class CommonVideoView extends FrameLayout implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener, View.OnTouchListener, View.OnClickListener, Animator.AnimatorListener, SeekBar.OnSeekBarChangeListener {

    private final int UPDATE_VIDEO_SEEKBAR = 1000;
    private Context context;
    private FrameLayout viewBox;
    private MyVideoView videoView;
    private LinearLayout videoPauseBtn;
    private LinearLayout screenSwitchBtn;
    private LinearLayout touchStatusView;
    private LinearLayout videoControllerLayout;
    private ImageView touchStatusImg;
    private ImageView videoPlayImg;
    private ImageView videoPauseImg;
    private TextView touchStatusTime;
    private TextView videoCurTimeText;
    private TextView videoTotalTimeText;
    private SeekBar videoSeekBar;

    private ProgressBar progressBar;

    private int duration;
    private String formatTotalTime;

    private Timer timer = new Timer();

    private float touchLastX;
    //定义用seekBar当前的位置，触摸快进的时候显示时间
    private int position;
    private int touchStep = 1000;//快进的时间，1秒
    private int touchPosition = -1;

    private boolean videoControllerShow = true;//底部状态栏的显示状态
    private boolean animation = false;
    //获得视频的路径
    private String videoUrl;
    //偏好设置确定视频是否暂停
    private SpUtils spUtils;

    private TimerTask timerTask;
    class MyTimerTask extends TimerTask{
        @Override
        public void run() {
            videoHandler.sendEmptyMessage(UPDATE_VIDEO_SEEKBAR);
        }}
    private Handler videoHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_VIDEO_SEEKBAR:
                    if (videoView.isPlaying()) {
                        videoSeekBar.setProgress(videoView.getCurrentPosition());
                    }
                    break;
            }
        }
    };
    private ImageView videoThumbnail;

    public CommonVideoView(Context context) {
        this(context, null);
    }

    public CommonVideoView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommonVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    public void getURL(String url) {
       this.videoUrl = url;
    }

    public void start(String url) {
        videoPauseBtn.setEnabled(false);
        videoSeekBar.setEnabled(false);
        videoView.setVideoURI(Uri.parse(url));
        videoView.start();
    }

    public void setFullScreen() {
        touchStatusImg.setImageResource(R.drawable.iconfont_exit);
        this.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        videoView.requestLayout();
    }

    public void setNormalScreen() {
        touchStatusImg.setImageResource(R.drawable.iconfont_enter_32);
        this.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 400));
        videoView.requestLayout();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initView();
    }

    private void initView() {
        spUtils=new SpUtils(context, Constant.VIDEO_ISPAUSE);
        View view = LayoutInflater.from(context).inflate(R.layout.common_video_view, null);
        viewBox = (FrameLayout) view.findViewById(R.id.viewBox);
        videoView = (MyVideoView) view.findViewById(R.id.videoView);
        videoPauseBtn = (LinearLayout) view.findViewById(R.id.videoPauseBtn);
        screenSwitchBtn = (LinearLayout) view.findViewById(R.id.screen_status_btn);
        videoControllerLayout = (LinearLayout) view.findViewById(R.id.videoControllerLayout);
        touchStatusView = (LinearLayout) view.findViewById(R.id.touch_view);
        touchStatusImg = (ImageView) view.findViewById(R.id.touchStatusImg);
        touchStatusTime = (TextView) view.findViewById(R.id.touch_time);
        videoCurTimeText = (TextView) view.findViewById(R.id.videoCurTime);
        videoTotalTimeText = (TextView) view.findViewById(R.id.videoTotalTime);
        videoSeekBar = (SeekBar) view.findViewById(R.id.videoSeekBar);
        videoPlayImg = (ImageView) view.findViewById(R.id.videoPlayImg);
        //videoPlayImg.setVisibility(GONE);
        videoPauseImg = (ImageView) view.findViewById(R.id.videoPauseImg);
        videoPauseImg.setImageResource(R.drawable.icon_video_play);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        videoThumbnail = (ImageView) view.findViewById(R.id.img_VideoThumbnail);

        videoPauseBtn.setOnClickListener(this);
        videoSeekBar.setOnSeekBarChangeListener(this);
        videoPauseBtn.setOnClickListener(this);
        videoView.setOnPreparedListener(this);
        videoView.setOnCompletionListener(this);
        screenSwitchBtn.setOnClickListener(this);
        videoPlayImg.setOnClickListener(this);
        //注册在设置或播放过程中发生错误时调用的回调函数。如果未指定回调函数，或回调函数返回false，VideoView 会通知用户发生了错误。
        videoView.setOnErrorListener(this);
        viewBox.setOnTouchListener(this);
        viewBox.setOnClickListener(this);
        addView(view);
    }

    public void showTthumbnail(String path) {
        Bitmap b = createVideoThumbnail(path, 60, 60);
        videoThumbnail.setImageBitmap(b);
    }

    private Bitmap createVideoThumbnail(String url, int width, int height) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        int kind = MediaStore.Video.Thumbnails.MINI_KIND;
        try {
            if (Build.VERSION.SDK_INT >= 14) {
                retriever.setDataSource(url, new HashMap<String, String>());
            } else {
                retriever.setDataSource(url);
            }
            bitmap = retriever.getFrameAtTime();
        } catch (IllegalArgumentException ex) {
            // Assume this is a corrupt video file
        } catch (RuntimeException ex) {
            // Assume this is a corrupt video file.
        } finally {
            try {
                retriever.release();
            } catch (RuntimeException ex) {
                // Ignore failures while cleaning up.
            }
        }
        if (kind == MediaStore.Images.Thumbnails.MICRO_KIND && bitmap != null) {
            bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
                    ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        }
        return bitmap;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        duration = videoView.getDuration();
        int[] time = getMinuteAndSecond(duration);
        videoTotalTimeText.setText(String.format("%02d:%02d", time[0], time[1]));
        formatTotalTime = String.format("%02d:%02d", time[0], time[1]);
        videoSeekBar.setMax(duration);
        progressBar.setVisibility(View.GONE);
        timerTask =new MyTimerTask();
        mp.start();
        videoPauseBtn.setEnabled(true);
        videoSeekBar.setEnabled(true);
        videoPauseImg.setImageResource(R.drawable.icon_video_pause);
        timer.schedule(timerTask, 0, 1000);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        videoView.seekTo(0);
        videoSeekBar.setProgress(0);
        videoPauseImg.setImageResource(R.drawable.icon_video_play);
        videoPlayImg.setVisibility(View.VISIBLE);
        videoThumbnail.setVisibility(View.VISIBLE);
        spUtils.setPause(false);
        timerTask.cancel();
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        Log.i("通知", "播放中出现错误");
        switch (what) {
            case -1004:
                Log.d("Streaming Media", "MEDIA_ERROR_IO");
                break;
            case -1007:
                Log.d("Streaming Media", "MEDIA_ERROR_MALFORMED");
                break;
            case 200:
                Log.d("Streaming Media", "MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK");
                break;
            case 100:
                Log.d("Streaming Media", "MEDIA_ERROR_SERVER_DIED");
                break;
            case -110:
                Log.d("Streaming Media", "MEDIA_ERROR_TIMED_OUT");
                break;
            case 1:
                Log.d("Streaming Media", "MEDIA_ERROR_UNKNOWN");
                break;
            case -1010:
                Log.d("Streaming Media", "MEDIA_ERROR_UNSUPPORTED");
                break;
        }
        switch (extra) {
            case 800:
                Log.d("Streaming Media", "MEDIA_INFO_BAD_INTERLEAVING");
                break;
            case 702:
                Log.d("Streaming Media", "MEDIA_INFO_BUFFERING_END");
                break;
            case 701:
                Log.d("Streaming Media", "MEDIA_INFO_METADATA_UPDATE");
                break;
            case 802:
                Log.d("Streaming Media", "MEDIA_INFO_METADATA_UPDATE");
                break;
            case 801:
                Log.d("Streaming Media", "MEDIA_INFO_NOT_SEEKABLE");
                break;
            case 1:
                Log.d("Streaming Media", "MEDIA_INFO_UNKNOWN");
                break;
            case 3:
                Log.d("Streaming Media", "MEDIA_INFO_VIDEO_RENDERING_START");
                break;
            case 700:
                Log.d("Streaming Media", "MEDIA_INFO_VIDEO_TRACK_LAGGING");
                break;
        }
        return false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!videoView.isPlaying()) {
                    return false;
                }
                float downX = event.getRawX();
                touchLastX = downX;
                Log.d("FilmDetailActivity", "downX" + downX);
                this.position = videoView.getCurrentPosition();
                break;
            case MotionEvent.ACTION_MOVE:
                if (!videoView.isPlaying()) {
                    return false;
                }
                float currentX = event.getRawX();
                float deltaX = currentX - touchLastX;
                float deltaXAbs = Math.abs(deltaX);
                if (deltaXAbs > 1) {
                    if (touchStatusView.getVisibility() != View.VISIBLE) {
                        touchStatusView.setVisibility(View.VISIBLE);
                    }
                    touchLastX = currentX;
                    Log.d("FilmDetailActivity", "deltaX" + deltaX);
                    if (deltaX > 1) {
                        position += touchStep;
                        if (position > duration) {
                            position = duration;
                        }
                        touchPosition = position;
                        touchStatusImg.setImageResource(R.drawable.ic_fast_forward_white_24dp);
                        int[] time = getMinuteAndSecond(position);
                        touchStatusTime.setText(String.format("%02d:%02d/%s", time[0], time[1], formatTotalTime));
                    } else if (deltaX < -1) {
                        position -= touchStep;
                        if (position < 0) {
                            position = 0;
                        }
                        touchPosition = position;
                        touchStatusImg.setImageResource(R.drawable.ic_fast_rewind_white_24dp);
                        int[] time = getMinuteAndSecond(position);
                        touchStatusTime.setText(String.format("%02d:%02d/%s", time[0], time[1], formatTotalTime));
                        //mVideoView.seekTo(position);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (touchPosition != -1) {
                    videoView.seekTo(touchPosition);
                    touchStatusView.setVisibility(View.GONE);
                    touchPosition = -1;
                    if (videoControllerShow) {
                        return true;
                    }
                }
                break;
        }
        return false;
    }

    private int[] getMinuteAndSecond(int mils) {
        mils /= 1000;
        int[] time = new int[2];
        time[0] = mils / 60;
        time[1] = mils % 60;
        return time;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.videoPlayImg:
                videoThumbnail.setVisibility(View.INVISIBLE);
                if(spUtils.isPause()){
                    videoView.start();
                }else{
                    start(videoUrl);
                    spUtils.setPause(true);
                }
                videoPlayImg.setVisibility(View.INVISIBLE);
                videoPauseImg.setImageResource(R.drawable.icon_video_pause);
                break;
            case R.id.videoPauseBtn:
                if (videoView.isPlaying()) {
                    videoView.pause();
                    videoPauseImg.setImageResource(R.drawable.icon_video_play);
                    videoPlayImg.setVisibility(View.VISIBLE);
                } else {
                    videoView.start();
                    videoPauseImg.setImageResource(R.drawable.icon_video_pause);
                    videoPlayImg.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.viewBox:
                float curY = videoControllerLayout.getY();
                if (!animation && videoControllerShow) {
                    animation = true;
                    ObjectAnimator animator = ObjectAnimator.ofFloat(videoControllerLayout, "y",
                            curY, curY + videoControllerLayout.getHeight());
                    animator.setDuration(200);
                    animator.start();
                    animator.addListener(this);
                } else if (!animation) {
                    animation = true;
                    ObjectAnimator animator = ObjectAnimator.ofFloat(videoControllerLayout, "y",
                            curY, curY - videoControllerLayout.getHeight());
                    animator.setDuration(200);
                    animator.start();
                    animator.addListener(this);
                }
                break;
            case R.id.screen_status_btn:
                int i = getResources().getConfiguration().orientation;
                if (i == Configuration.ORIENTATION_PORTRAIT) {
                    ((Activity) context).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                } else if (i == Configuration.ORIENTATION_LANDSCAPE) {
                    ((Activity) context).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                }
                break;

        }
    }

    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {
        this.animation = false;
        this.videoControllerShow = !this.videoControllerShow;
    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        int[] time = getMinuteAndSecond(progress);
        videoCurTimeText.setText(String.format("%02d:%02d", time[0], time[1]));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        videoView.pause();
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        videoView.seekTo(videoSeekBar.getProgress());
        videoView.start();
        videoPlayImg.setVisibility(View.INVISIBLE);
        videoPauseImg.setImageResource(R.drawable.icon_video_pause);
    }
}
