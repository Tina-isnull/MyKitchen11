package com.example.lcc.mykitchen.zhibo;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.Toast;

import com.example.lcc.mykitchen.R;
import com.example.lcc.mykitchen.activity.FlusMessage;
import com.tencent.rtmp.ITXLivePushListener;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXLivePushConfig;
import com.tencent.rtmp.TXLivePusher;
import com.tencent.rtmp.ui.TXCloudVideoView;

public class LivePublisherActivity extends RTMPBaseActivity implements View.OnClickListener, ITXLivePushListener, SeekBar.OnSeekBarChangeListener, FlusMessage.messageShow/*, ImageReader.OnImageAvailableListener*/ {
    private static final String TAG = LivePublisherActivity.class.getSimpleName();

    private TXLivePushConfig mLivePushConfig;
    private TXLivePusher mLivePusher;
    private TXCloudVideoView mCaptureView;

    private LinearLayout mBitrateLayout;
    private LinearLayout mFaceBeautyLayout;
    private SeekBar mBeautySeekBar;
    private SeekBar mWhiteningSeekBar;
    private ScrollView mScrollView;
    private RadioGroup mRadioGroupBitrate;
    private Button mBtnBitrate;
    private Button mBtnPlay;
    private Button mBtnFaceBeauty;
    private Button mBtnFlashLight;
    private Button mBtnTouchFocus;
    private Button mBtnHWEncode;
    private Button mBtnOrientation;
    private boolean mPortrait = true;         //手动切换，横竖屏推流

    private boolean mVideoPublish;
    private boolean mFrontCamera = true;
    private boolean mHWVideoEncode = false;
    private boolean mFlashTurnOn = false;
    private boolean mTouchFocus = true;
    private boolean mHWListConfirmDialogResult = false;
    private int mBeautyLevel = 0;
    private int mWhiteningLevel = 0;

    private Handler mHandler = new Handler();

    private Bitmap mBitmap;
    private FlusMessage flusMessage;
    // 关注系统设置项“自动旋转”的状态切换
    private RotationObserver mRotationObserver = null;

    private Bitmap decodeResource(Resources resources, int id) {
        TypedValue value = new TypedValue();
        resources.openRawResource(id, value);
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inTargetDensity = value.density;
        return BitmapFactory.decodeResource(resources, id, opts);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        setActivityType(RTMPBaseActivity.ACTIVITY_TYPE_PUBLISH);
        mLivePusher = new TXLivePusher(this);
        mLivePushConfig = new TXLivePushConfig();
        mBitmap = decodeResource(getResources(), R.drawable.watermark);
        mRotationObserver = new RotationObserver(new Handler());
        mRotationObserver.startObserver();
        initView();
        mCaptureView = (TXCloudVideoView) findViewById(R.id.video_view);
        mVideoPublish = false;
        mLogViewStatus.setVisibility(View.GONE);
        mLogViewStatus.setMovementMethod(new ScrollingMovementMethod());
        mLogViewEvent.setMovementMethod(new ScrollingMovementMethod());
        mScrollView = (ScrollView) findViewById(R.id.scrollview);
        mScrollView.setVisibility(View.GONE);

        //mRtmpUrlView.setHint(" 请扫码输入推流地址...");
        //mRtmpUrlView.setText("");
        //刷新消息部分
        flusMessage=new FlusMessage(this,this);
        flusMessage.start();

        //美颜部分
        mFaceBeautyLayout = (LinearLayout) findViewById(R.id.layoutFaceBeauty);
        mBeautySeekBar = (SeekBar) findViewById(R.id.beauty_seekbar);
        mBeautySeekBar.setOnSeekBarChangeListener(this);

        mWhiteningSeekBar = (SeekBar) findViewById(R.id.whitening_seekbar);
        mWhiteningSeekBar.setOnSeekBarChangeListener(this);

        mBtnFaceBeauty = (Button) findViewById(R.id.btnFaceBeauty);
        mBtnFaceBeauty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFaceBeautyLayout.setVisibility(mFaceBeautyLayout.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
            }
        });

        //播放部分
        mBtnPlay = (Button) findViewById(R.id.btnPlay);
        mBtnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mVideoPublish) {
                    stopPublishRtmp();
                    mVideoPublish = false;
                } else {
                    FixOrAdjustBitrate();  //根据设置确定是“固定”还是“自动”码率
                    mVideoPublish = startPublishRtmp();
//                    StartScreenCapture();
                }
            }
        });


        //log部分
        final Button btnLog = (Button) findViewById(R.id.btnLog);
        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLogViewStatus.getVisibility() == View.GONE) {
                    mLogViewStatus.setVisibility(View.VISIBLE);
                    mScrollView.setVisibility(View.VISIBLE);
                    mLogViewEvent.setText(mLogMsg);
                    scroll2Bottom(mScrollView, mLogViewEvent);
                    btnLog.setBackgroundResource(R.drawable.log_hidden);
                } else {
                    mLogViewStatus.setVisibility(View.GONE);
                    mScrollView.setVisibility(View.GONE);
                    btnLog.setBackgroundResource(R.drawable.log_show);
                }
            }
        });

        //切换前置后置摄像头
        final Button btnChangeCam = (Button) findViewById(R.id.btnCameraChange);
        btnChangeCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFrontCamera = !mFrontCamera;

                if (mLivePusher.isPushing()) {
                    mLivePusher.switchCamera();
                } else {
                    mLivePushConfig.setFrontCamera(mFrontCamera);
                }
                btnChangeCam.setBackgroundResource(mFrontCamera ? R.drawable.camera_change : R.drawable.camera_change2);
            }
        });

        //开启硬件加速
        mBtnHWEncode = (Button) findViewById(R.id.btnHWEncode);
        mBtnHWEncode.getBackground().setAlpha(mHWVideoEncode ? 255 : 100);
        mBtnHWEncode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean HWVideoEncode = mHWVideoEncode;
                mHWVideoEncode = !mHWVideoEncode;
                mBtnHWEncode.getBackground().setAlpha(mHWVideoEncode ? 255 : 100);

                if (mHWVideoEncode) {
                    if (mLivePushConfig != null) {
                        if (Build.VERSION.SDK_INT < 18) {
                            Toast.makeText(LivePublisherActivity.this, "硬件加速失败，当前手机API级别过低（最低16）", Toast.LENGTH_SHORT).show();
                            mHWVideoEncode = false;
                        }
                    }
                }
                if (HWVideoEncode != mHWVideoEncode) {
                    mLivePushConfig.setHardwareAcceleration(mHWVideoEncode);
                    if (mHWVideoEncode == false) {
                        Toast.makeText(LivePublisherActivity.this, "取消硬件加速", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LivePublisherActivity.this, "开启硬件加速", Toast.LENGTH_SHORT).show();
                    }
                }
                if (mLivePusher != null) {
                    mLivePusher.setConfig(mLivePushConfig);
                }
            }
        });

        //码率自适应部分
        mBtnBitrate = (Button) findViewById(R.id.btnBitrate);
        mBitrateLayout = (LinearLayout) findViewById(R.id.layoutBitrate);
        mBtnBitrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBitrateLayout.setVisibility(mBitrateLayout.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
            }
        });

        mRadioGroupBitrate = (RadioGroup) findViewById(R.id.resolutionRadioGroup);
        mRadioGroupBitrate.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                FixOrAdjustBitrate();
                mBitrateLayout.setVisibility(View.GONE);
            }
        });

        //闪光灯
        mBtnFlashLight = (Button) findViewById(R.id.btnFlash);
        mBtnFlashLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLivePusher == null) {
                    return;
                }

                mFlashTurnOn = !mFlashTurnOn;
                if (!mLivePusher.turnOnFlashLight(mFlashTurnOn)) {
                    Toast.makeText(LivePublisherActivity.this,
                            "打开闪光灯失败（1）大部分前置摄像头并不支持闪光灯（2）该接口需要在启动预览之后调用", Toast.LENGTH_SHORT).show();
                }

                mBtnFlashLight.setBackgroundResource(mFlashTurnOn ? R.drawable.flash_off : R.drawable.flash_on);
            }
        });

        //手动对焦/自动对焦
        mBtnTouchFocus = (Button) findViewById(R.id.btnTouchFoucs);
        mBtnTouchFocus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFrontCamera) {
                    return;
                }

                mTouchFocus = !mTouchFocus;
                mLivePushConfig.setTouchFocus(mTouchFocus);
                v.setBackgroundResource(mTouchFocus ? R.drawable.automatic : R.drawable.manual);

                if (mLivePusher.isPushing()) {
                    mLivePusher.stopCameraPreview(false);
                    mLivePusher.startCameraPreview(mCaptureView);
                }

                Toast.makeText(LivePublisherActivity.this, mTouchFocus ? "已开启手动对焦" : "已开启自动对焦", Toast.LENGTH_SHORT).show();
            }
        });

        //锁定Activity不旋转的情况下，才能进行横屏|竖屏推流切换
        mBtnOrientation = (Button) findViewById(R.id.btnPushOrientation);
        if (isActivityCanRotation()) {
            mBtnOrientation.setVisibility(View.GONE);
        }
        mBtnOrientation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPortrait = !mPortrait;
                int renderRotation = 0;
                if (mPortrait) {
                    mLivePushConfig.setHomeOrientation(TXLiveConstants.VIDEO_ANGLE_HOME_DOWN);
                    mBtnOrientation.setBackgroundResource(R.drawable.landscape);
                    renderRotation = 0;
                } else {
                    mLivePushConfig.setHomeOrientation(TXLiveConstants.VIDEO_ANGLE_HOME_RIGHT);
                    mBtnOrientation.setBackgroundResource(R.drawable.portrait);
                    renderRotation = 270;
                }
                mLivePusher.setRenderRotation(renderRotation);
                mLivePusher.setConfig(mLivePushConfig);
            }
        });

        // setOnClickListener(this);
        mLogViewStatus.setText("Log File Path:" + Environment.getExternalStorageDirectory().getAbsolutePath() + "/txRtmpLog");
    }

    protected void HWListConfirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LivePublisherActivity.this);
        builder.setMessage("警告：当前机型不在白名单中,是否继续尝试硬编码？");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                mHWListConfirmDialogResult = true;
                throw new RuntimeException();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                mHWListConfirmDialogResult = false;
                throw new RuntimeException();
            }
        });
        builder.setCancelable(false);
        builder.create().show();
        try {
            Looper.loop();
        } catch (Exception e) {
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                mFaceBeautyLayout.setVisibility(View.GONE);
                mBitrateLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mCaptureView != null) {
            mCaptureView.onResume();
        }

        if (mVideoPublish && mLivePusher != null) {
            mLivePusher.resumePusher();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mCaptureView != null) {
            mCaptureView.onPause();
        }

        if (mVideoPublish && mLivePusher != null) {
            mLivePusher.pausePusher();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopPublishRtmp();
        if (mCaptureView != null) {
            mCaptureView.onDestroy();
        }

        mRotationObserver.stopObserver();
         FlusMessage.isStop=true;
    }

    private boolean startPublishRtmp() {
        String rtmpUrl = "rtmp://6505.livepush.myqcloud.com/live/6505_12265c2e4a?bizid=6505&txSecret=395dda21e57281d170f1a89f2fd584e5&txTime=58FCCF7F";
        if (TextUtils.isEmpty(rtmpUrl) || (!rtmpUrl.trim().toLowerCase().startsWith("rtmp://"))) {
            mVideoPublish = false;
            Toast.makeText(LivePublisherActivity.this, "推流地址不合法，目前支持rtmp推流!", Toast.LENGTH_SHORT).show();
            return false;
        }

        mCaptureView.setVisibility(View.VISIBLE);
        mLivePushConfig.setWatermark(mBitmap, 10, 10);

        int customModeType = 0;

        mLivePushConfig.setVideoFPS(25);

        //【示例代码1】设置自定义视频采集逻辑 （自定义视频采集逻辑不要调用startPreview）
//        customModeType |= TXLiveConstants.CUSTOM_MODE_VIDEO_CAPTURE;
//        mLivePushConfig.setVideoResolution(TXLiveConstants.VIDEO_RESOLUTION_TYPE_640_360);
//        mLivePushConfig.setAutoAdjustBitrate(false);
//        mLivePushConfig.setVideoBitrate(1300);
//        mLivePushConfig.setVideoFPS(25);
//        mLivePushConfig.setVideoEncodeGop(3);
//        new Thread() {  //视频采集线程
//            @Override
//            public void run() {
//                while (true) {
//                    try {
//                        FileInputStream in = new FileInputStream("/sdcard/dump_1280_720.yuv");
//                        int len = 1280 * 720 * 3 / 2;
//                        byte buffer[] = new byte[len];
//                        int count;
//                        while ((count = in.read(buffer)) != -1) {
//                            if (len == count) {
//                                mLivePusher.sendCustomVideoData(buffer, TXLivePusher.YUV_420SP);
//                            } else {
//                                break;
//                            }
//                            sleep(50, 0);
//                        }
//                        in.close();
//                    } catch (Exception e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }.start();

        //【示例代码2】设置自定义音频采集逻辑（音频采样位宽必须是16）
//        mLivePushConfig.setAudioSampleRate(44100);
//        mLivePushConfig.setAudioChannels(1);
//        customModeType |= TXLiveConstants.CUSTOM_MODE_AUDIO_CAPTURE;
//        new Thread() {  //音频采集线程
//            @Override
//            public void run() {
//                while (true) {
//                    try {
//                        FileInputStream in = new FileInputStream("/sdcard/dump.pcm");
//                        int len = 2048;
//                        byte buffer[] = new byte[len];
//                        int count;
//                        while ((count = in.read(buffer)) != -1) {
//                            if (len == count) {
//                                mLivePusher.sendCustomPCMData(buffer);
//                            } else {
//                                break;
//                            }
//                            sleep(10, 0);
//                        }
//                        in.close();
//                    } catch (Exception e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }.start();

        //【示例代码3】设置自定义视频预处理逻辑
//        customModeType |= TXLiveConstants.CUSTOM_MODE_VIDEO_PREPROCESS;
//        String path = this.getActivity().getApplicationInfo().dataDir + "/lib";
//        mLivePushConfig.setCustomVideoPreProcessLibrary(path +"/libvideo.so", "tx_video_process");

        //【示例代码4】设置自定义音频预处理逻辑
//        customModeType |= TXLiveConstants.CUSTOM_MODE_AUDIO_PREPROCESS;
//        String path = this.getActivity().getApplicationInfo().dataDir + "/lib";
//        mLivePushConfig.setCustomAudioPreProcessLibrary(path +"/libvideo.so", "tx_audio_process");

//        mLivePushConfig.setAutoAdjustBitrate(true);
//        mLivePushConfig.setMaxVideoBitrate(1200);
//        mLivePushConfig.setMinVideoBitrate(500);
//        mLivePushConfig.setVideoBitrate(600);

        mLivePushConfig.setCustomModeType(customModeType);

        mLivePushConfig.setPauseImg(300, 10);
        Bitmap bitmap = decodeResource(getResources(), R.drawable.pause_publish);
        mLivePushConfig.setPauseImg(bitmap);
        mLivePushConfig.setPauseFlag(TXLiveConstants.PAUSE_FLAG_PAUSE_VIDEO | TXLiveConstants.PAUSE_FLAG_PAUSE_AUDIO);
        mLivePushConfig.setBeautyFilter(mBeautyLevel, mWhiteningLevel);
        mLivePusher.setConfig(mLivePushConfig);
        mLivePusher.setPushListener(this);
        mLivePusher.startCameraPreview(mCaptureView);
//        mLivePusher.startScreenCapture();
        mLivePusher.startPusher(rtmpUrl.trim());

        //enableQRCodeBtn(false);
        clearLog();
        int[] ver = TXLivePusher.getSDKVersion();
        if (ver != null && ver.length >= 3) {
            mLogMsg.append(String.format("rtmp sdk version:%d.%d.%d ", ver[0], ver[1], ver[2]));
            mLogViewEvent.setText(mLogMsg);
        }

        mBtnPlay.setBackgroundResource(R.drawable.play_pause);

        appendEventLog(0, "点击推流按钮！");

        return true;
    }

    private void stopPublishRtmp() {

//        StopScreenCapture();

        mLivePusher.stopCameraPreview(true);
        mLivePusher.stopScreenCapture();
        mLivePusher.setPushListener(null);
        mLivePusher.stopPusher();
        mCaptureView.setVisibility(View.GONE);

        if (mBtnHWEncode != null) {
            mLivePushConfig.setHardwareAcceleration(true);
            mBtnHWEncode.setBackgroundResource(R.drawable.quick);
        }

        //enableQRCodeBtn(true);
        mBtnPlay.setBackgroundResource(R.drawable.play_start);

        if (mLivePushConfig != null) {
            mLivePushConfig.setPauseImg(null);
        }
    }


    public void FixOrAdjustBitrate() {
        if (mRadioGroupBitrate == null || mLivePushConfig == null || mLivePusher == null) {
            return;
        }

        RadioButton rb = (RadioButton) findViewById(mRadioGroupBitrate.getCheckedRadioButtonId());
        int mode = Integer.parseInt((String) rb.getTag());

        switch (mode) {
            case 4: /*720p*/
                if (mLivePusher != null) {
                    mLivePushConfig.setVideoResolution(TXLiveConstants.VIDEO_RESOLUTION_TYPE_720_1280);
                    mLivePushConfig.setAutoAdjustBitrate(false);
                    mLivePushConfig.setVideoBitrate(1500);
                    mLivePusher.setConfig(mLivePushConfig);
                }
                mBtnBitrate.setBackgroundResource(R.drawable.fix_bitrate);
                break;
            case 3: /*540p*/
                if (mLivePusher != null) {
                    mLivePushConfig.setVideoResolution(TXLiveConstants.VIDEO_RESOLUTION_TYPE_540_960);
                    mLivePushConfig.setAutoAdjustBitrate(false);
                    mLivePushConfig.setVideoBitrate(1000);
                    mLivePusher.setConfig(mLivePushConfig);
                }
                mBtnBitrate.setBackgroundResource(R.drawable.fix_bitrate);
                break;
            case 2: /*360p*/
                if (mLivePusher != null) {
                    mLivePushConfig.setVideoResolution(TXLiveConstants.VIDEO_RESOLUTION_TYPE_360_640);
                    mLivePushConfig.setAutoAdjustBitrate(false);
                    mLivePushConfig.setVideoBitrate(700);
                    mLivePusher.setConfig(mLivePushConfig);
                }
                mBtnBitrate.setBackgroundResource(R.drawable.fix_bitrate);
                break;

            case 1: /*自动*/
                if (mLivePusher != null) {
                    mLivePushConfig.setVideoResolution(TXLiveConstants.VIDEO_RESOLUTION_TYPE_360_640);
                    mLivePushConfig.setAutoAdjustBitrate(true);
                    mLivePushConfig.setAutoAdjustStrategy(TXLiveConstants.AUTO_ADJUST_BITRATE_STRATEGY_1);
                    mLivePushConfig.setMaxVideoBitrate(1000);
                    mLivePushConfig.setMinVideoBitrate(500);
                    mLivePushConfig.setVideoBitrate(700);
                    mLivePusher.setConfig(mLivePushConfig);
                }
                mBtnBitrate.setBackgroundResource(R.drawable.auto_bitrate);
                break;
            default:
                break;
        }
    }

    @Override
    public void onPushEvent(int event, Bundle param) {
        String msg = param.getString(TXLiveConstants.EVT_DESCRIPTION);
        appendEventLog(event, msg);
        if (mScrollView.getVisibility() == View.VISIBLE) {
            mLogViewEvent.setText(mLogMsg);
            scroll2Bottom(mScrollView, mLogViewEvent);
        }
//        if (mLivePusher != null) {
//            mLivePusher.onLogRecord("[event:" + event + "]" + msg + "\n");
//        }
        //错误还是要明确的报一下
        if (event < 0) {
            Toast.makeText(LivePublisherActivity.this, param.getString(TXLiveConstants.EVT_DESCRIPTION), Toast.LENGTH_SHORT).show();
        }

        if (event == TXLiveConstants.PUSH_ERR_NET_DISCONNECT) {
            stopPublishRtmp();
            mVideoPublish = false;
        } else if (event == TXLiveConstants.PUSH_WARNING_HW_ACCELERATION_FAIL) {
            Toast.makeText(LivePublisherActivity.this, param.getString(TXLiveConstants.EVT_DESCRIPTION), Toast.LENGTH_SHORT).show();
            mLivePushConfig.setHardwareAcceleration(false);
            mBtnHWEncode.setBackgroundResource(R.drawable.quick2);
            mLivePusher.setConfig(mLivePushConfig);
            mHWVideoEncode = false;
        } else if (event == TXLiveConstants.PUSH_ERR_SCREEN_CAPTURE_UNSURPORT) {
            stopPublishRtmp();
        } else if (event == TXLiveConstants.PUSH_ERR_SCREEN_CAPTURE_START_FAILED) {
            stopPublishRtmp();
        } else if (event == TXLiveConstants.PUSH_EVT_CHANGE_RESOLUTION) {
            Log.d(TAG, "change resolution to " + param.getInt(TXLiveConstants.EVT_PARAM2) + ", bitrate to" + param.getInt(TXLiveConstants.EVT_PARAM1));
        } else if (event == TXLiveConstants.PUSH_EVT_CHANGE_BITRATE) {
            Log.d(TAG, "change bitrate to" + param.getInt(TXLiveConstants.EVT_PARAM1));
        }
    }

    @Override
    public void onNetStatus(Bundle status) {
        String str = getNetStatusString(status);
        mLogViewStatus.setText(str);
        Log.d(TAG, "Current status, CPU:" + status.getString(TXLiveConstants.NET_STATUS_CPU_USAGE) +
                ", RES:" + status.getInt(TXLiveConstants.NET_STATUS_VIDEO_WIDTH) + "*" + status.getInt(TXLiveConstants.NET_STATUS_VIDEO_HEIGHT) +
                ", SPD:" + status.getInt(TXLiveConstants.NET_STATUS_NET_SPEED) + "Kbps" +
                ", FPS:" + status.getInt(TXLiveConstants.NET_STATUS_VIDEO_FPS) +
                ", ARA:" + status.getInt(TXLiveConstants.NET_STATUS_AUDIO_BITRATE) + "Kbps" +
                ", VRA:" + status.getInt(TXLiveConstants.NET_STATUS_VIDEO_BITRATE) + "Kbps");
//        if (mLivePusher != null){
//            mLivePusher.onLogRecord("[net state]:\n"+str+"\n");
//        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (seekBar.getId() == R.id.beauty_seekbar) {
            mBeautyLevel = progress;
        } else if (seekBar.getId() == R.id.whitening_seekbar) {
            mWhiteningLevel = progress;
        }

        if (mLivePusher != null) {
            if (!mLivePusher.setBeautyFilter(mBeautyLevel, mWhiteningLevel)) {
                Toast.makeText(LivePublisherActivity.this, "当前机型的性能无法支持美颜功能", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        onActivityRotation();
    }

    protected void onActivityRotation() {
        // 自动旋转打开，Activity随手机方向旋转之后，需要改变推流方向
        int mobileRotation = this.getWindowManager().getDefaultDisplay().getRotation();
        int pushRotation = TXLiveConstants.VIDEO_ANGLE_HOME_DOWN;
        switch (mobileRotation) {
            case Surface.ROTATION_0:
                pushRotation = TXLiveConstants.VIDEO_ANGLE_HOME_DOWN;
                break;
            case Surface.ROTATION_90:
                pushRotation = TXLiveConstants.VIDEO_ANGLE_HOME_RIGHT;
                break;
            case Surface.ROTATION_270:
                pushRotation = TXLiveConstants.VIDEO_ANGLE_HOME_LEFT;
                break;
            default:
                break;
        }
        mLivePusher.setRenderRotation(0); //因为activity也旋转了，本地渲染相对正方向的角度为0。
        mLivePushConfig.setHomeOrientation(pushRotation);
        mLivePusher.setConfig(mLivePushConfig);
    }

    /**
     * 判断Activity是否可旋转。只有在满足以下条件的时候，Activity才是可根据重力感应自动旋转的。
     * 系统“自动旋转”设置项打开；
     *
     * @return false---Activity可根据重力感应自动旋转
     */
    protected boolean isActivityCanRotation() {
        // 判断自动旋转是否打开
        int flag = Settings.System.getInt(this.getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, 0);
        if (flag == 0) {
            return false;
        }
        return true;
    }

    /**
     * 显示观看者发过来的消息
     *
     * @param message
     */
    @Override
    public void setInfos(String message) {
        Log.d("TAG","push="+message);
        mLogViewEvent.setText(message);
    }

    //观察屏幕旋转设置变化，类似于注册动态广播监听变化机制
    private class RotationObserver extends ContentObserver {
        ContentResolver mResolver;

        public RotationObserver(Handler handler) {
            super(handler);
            mResolver = LivePublisherActivity.this.getContentResolver();
        }

        //屏幕旋转设置改变时调用
        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            //更新按钮状态
            if (isActivityCanRotation()) {
                mBtnOrientation.setVisibility(View.GONE);
                onActivityRotation();
            } else {
                mBtnOrientation.setVisibility(View.VISIBLE);
                mPortrait = true;
                mLivePushConfig.setHomeOrientation(TXLiveConstants.VIDEO_ANGLE_HOME_DOWN);
                mBtnOrientation.setBackgroundResource(R.drawable.landscape);
                mLivePusher.setRenderRotation(0);
                mLivePusher.setConfig(mLivePushConfig);
            }

        }

        public void startObserver() {
            mResolver.registerContentObserver(Settings.System.getUriFor(Settings.System.ACCELEROMETER_ROTATION), false, this);
        }

        public void stopObserver() {
            mResolver.unregisterContentObserver(this);
        }
    }
}