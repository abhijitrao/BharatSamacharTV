package com.wts.bharatsamachar;

import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.VideoView;

import com.wts.bharatsamachar.utils.ads.AdsAppCompactActivity;

public class LiveTV extends AdsAppCompactActivity {

    VideoView videoView;
    MediaController mediaController;
    ProgressDialog progressDialog;
    Button closeBtn;
    LinearLayout mainLayout;
    ImageView backpress;
    private View fullScreenBtn;
    private boolean isFullScreen = false;
    private View ivClose;

    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putBoolean("isFullScreen", isFullScreen);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_tv);
        if (savedInstanceState != null) {
            isFullScreen = savedInstanceState.getBoolean("isFullScreen");
        }
        videoView = findViewById(R.id.videoView);
        fullScreenBtn = findViewById(R.id.full_screen);
        closeBtn = findViewById(R.id.closeBtn);
        mainLayout = findViewById(R.id.mainLayout);
        backpress = findViewById(R.id.backpress);
        ivClose = findViewById(R.id.ic_close);
        String LINK = "https://streamidvo.multitvsolution.in/idvo/bharatsamachar.m3u8";
        playvideo(LINK);

        fullScreenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFullScreen = true;
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            }
        });

        backpress.setOnClickListener(v -> onBackPressed());
        ivClose.setOnClickListener(v -> showFullScreenMode(false));

//        closeBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//                mainLayout.setVisibility(View.VISIBLE);
//            }
//        });

    }


    @Override
    protected void onResume() {
        super.onResume();
        showFullScreenMode(isFullScreen);
    }

    private boolean isFreeHandler = true;

    private void showFullScreenMode(boolean isFullScreen) {
        this.isFullScreen = isFullScreen;
        if (getWindow() != null) {
            if (isFullScreen) {
                mainLayout.setVisibility(View.GONE);
                addVideoViewTouchListener(true);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            } else {
                mainLayout.setVisibility(View.VISIBLE);
                addVideoViewTouchListener(false);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
        }
    }
    private boolean isCloseFlag = true;

    private void addVideoViewTouchListener(boolean isAdd) {
        if(isAdd){
            videoView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    ivClose.setVisibility(isCloseFlag ? View.VISIBLE : View.GONE);
                    isCloseFlag = !isCloseFlag;
                    if(isFreeHandler && ivClose.getVisibility() == View.VISIBLE) {
                        isFreeHandler = false;
                        getHandler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                isFreeHandler = true;
                                ivClose.setVisibility(View.GONE);
                                isCloseFlag = true;
                            }
                        }, 3000);
                    }
                    return false;
                }
            });
        }else {
            videoView.setOnTouchListener(null);
        }
    }

    private Handler handler;

    public Handler getHandler() {
        if(handler == null){
            handler = new Handler();
        }
        return handler;
    }

    public void playvideo(String videopath) {
        try {
            getWindow().setFormat(PixelFormat.TRANSLUCENT);
            mediaController = new MediaController(LiveTV.this);
            Uri video = Uri.parse(videopath);
            videoView.setMediaController(mediaController);
            videoView.setVideoURI(video);
            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                public void onPrepared(MediaPlayer mp) {
                    videoView.start();
                }
            });

        }
        catch(Exception e)
        {
            progressDialog.dismiss();
            System.out.println("Video Play Error :"+e.toString());
            finish();
        }

    }

    @Override
    public void onBackPressed() {
        if(isFullScreen){
            showFullScreenMode(false);
        }else {
            super.onBackPressed();
        }
    }
}