package com.example.lcc.mykitchen.video;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import com.example.lcc.mykitchen.R;

public class VideoActivity extends AppCompatActivity {
    private SurfaceView surfaceview;
    private Button start;
    private Button stop;
    private MyVideoRecorder video;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        video = new MyVideoRecorder();
        initUI();
    }

    private void initUI() {
        surfaceview = (SurfaceView) findViewById(R.id.Surface_video);
        start = (Button) findViewById(R.id.video_start);
        stop = (Button) findViewById(R.id.video_stop);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                video.startRecording(surfaceview);
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                video.stopRecording();

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        video.release();
    }
}
