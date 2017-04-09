package com.example.lcc.mykitchen.video;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.media.MediaRecorder;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lcc.mykitchen.R;

import java.io.File;
import java.io.IOException;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;
import com.example.lcc.mykitchen.entity.FoodVideo;
import com.example.lcc.mykitchen.entity.UserInfo;

public class VideoActivity2 extends AppCompatActivity implements SurfaceHolder.Callback {
    private Button start;// 开始录制按钮
    private Button stop;// 停止录制按钮
    private MediaRecorder mediarecorder;// 录制视频的类
    private SurfaceView surfaceview;// 显示视频的控件
    // 用来显示视频的一个接口，我靠不用还不行，也就是说用mediarecorder录制视频还得给个界面看
    // 想偷偷录视频的同学可以考虑别的办法。。嗯需要实现这个接口的Callback接口
    private SurfaceHolder surfaceHolder;
    private String localPath;
    private EditText videoName;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏
        // 设置横屏显示
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        // 选择支持半透明模式,在有surfaceview的activity中使用。
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        setContentView(R.layout.activity_video2);
        init();
    }

    private void init() {
        videoName = (EditText) this.findViewById(R.id.et_video_name);
        start = (Button) this.findViewById(R.id.start);
        stop = (Button) this.findViewById(R.id.stop);
        start.setOnClickListener(new TestVideoListener());
        stop.setOnClickListener(new TestVideoListener());
        surfaceview = (SurfaceView) this.findViewById(R.id.surfaceview);
        SurfaceHolder holder = surfaceview.getHolder();// 取得holder
        holder.addCallback(this); // holder加入回调接口
        // setType必须设置，要不出错.
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    class TestVideoListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (v == start) {
                mediarecorder = new MediaRecorder();// 创建mediarecorder对象
                // 设置录制视频源为Camera(相机)
                mediarecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
                // 设置录制完成后视频的封装格式THREE_GPP为3gp.MPEG_4为mp4
                mediarecorder
                        .setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                // 设置录制的视频编码h263 h264
                mediarecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
                // 设置视频录制的分辨率。必须放在设置编码和格式的后面，否则报错
                mediarecorder.setVideoSize(176, 144);
                // 设置录制的视频帧率。必须放在设置编码和格式的后面，否则报错
                mediarecorder.setVideoFrameRate(20);
                mediarecorder.setPreviewDisplay(surfaceHolder.getSurface());
                // 设置视频文件输出的路径
                localPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES).getPath() + System.currentTimeMillis() + ".mp4";
                mediarecorder.setOutputFile(localPath);
                try {
                    // 准备录制
                    mediarecorder.prepare();
                    // 开始录制
                    mediarecorder.start();
                } catch (IllegalStateException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if (v == stop) {
                if (mediarecorder != null) {
                    // 停止录制
                    mediarecorder.stop();
                    // 释放资源
                    mediarecorder.release();
                    mediarecorder = null;
                }
                new AlertDialog.Builder(VideoActivity2.this)
                        .setMessage("是否要保存")
                        .setPositiveButton("保存",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        dialog.dismiss();
                                        sendVideo(null);

                                    }
                                })
                        .setNegativeButton("取消",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        if (localPath != null) {
                                            File file = new File(localPath);
                                            if (file.exists())
                                                file.delete();
                                        }
                                        finish();

                                    }
                                }).setCancelable(false).show();
            }

        }

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        // 将holder，这个holder为开始在oncreat里面取得的holder，将它赋给surfaceHolder
        surfaceHolder = holder;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // 将holder，这个holder为开始在oncreat里面取得的holder，将它赋给surfaceHolder
        surfaceHolder = holder;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // surfaceDestroyed的时候同时对象设置为null
        surfaceview = null;
        surfaceHolder = null;
        mediarecorder = null;
    }

    MediaScannerConnection msc = null;
    ProgressDialog progressDialog = null;

    public void sendVideo(View view) {
        if (TextUtils.isEmpty(localPath)) {
            Log.i("Recorder", "recorder fail please try again!");
            return;
        }
        if (msc == null)
            msc = new MediaScannerConnection(this,
                    new MediaScannerConnection.MediaScannerConnectionClient() {

                        @Override
                        public void onScanCompleted(String path, Uri uri) {
                            Log.i("TAG", "scanner completed");
                            msc.disconnect();
                            progressDialog.dismiss();
                            setResult(RESULT_OK, getIntent().putExtra("uri", uri));

                            //将视频上传到bmob文件进行存储
                            uploadVideoBmob();
                            finish();
                        }

                        @Override
                        public void onMediaScannerConnected() {
                            msc.scanFile(localPath, "video/*");
                        }
                    });


        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("加载中...");
            progressDialog.setCancelable(false);
        }
        progressDialog.show();
        msc.connect();

    }

    private void uploadVideoBmob() {
        final String vName = videoName.getText().toString();
        final BmobFile bmobFile=new BmobFile(new File(localPath));
        bmobFile.uploadblock(this, new UploadFileListener() {
            @Override
            public void onSuccess() {
                //上传后的图片在服务器上的位置（网址）
               String videoUrl = bmobFile.getFileUrl(VideoActivity2.this);
                if(TextUtils.isEmpty(vName)){
                    Toast.makeText(VideoActivity2.this, "视频名称不可为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                FoodVideo foodVideo=new FoodVideo();
                foodVideo.setUserInfo(BmobUser.getCurrentUser(VideoActivity2.this, UserInfo.class));
                foodVideo.setVideoName(vName);
                foodVideo.setVideoUrl(videoUrl);
                foodVideo.save(VideoActivity2.this, new SaveListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(VideoActivity2.this, "上传成功", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        Toast.makeText(VideoActivity2.this, "上传失败，请重试", Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onFailure(int i, String s) {

            }
        });
    }

}

