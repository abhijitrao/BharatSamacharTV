package com.wts.bharatsamachar;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.wts.bharatsamachar.utils.ShrPrf;

public class SplashScreen extends AppCompatActivity {

    static String currentAppVersion = "";
    ImageView img;
    MediaPlayer mp;
    boolean isRang;

    LinearLayout descimage;
    Animation downtoup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash_screen);
        getVersionname(this);
        mp = MediaPlayer.create(SplashScreen.this, R.raw.notification);
        isRang = ShrPrf.getShrdPrfBoolean(SplashScreen.this,"sound_rang", false);
        if (!isRang) {
            new Thread(
                    new Runnable() {
                        public void run() {
                            try {
                                // PLAY AUDIO CODE
                                mp.start();
                                ShrPrf.getShrdPrfBoolean(SplashScreen.this,"sound_rang", true);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (ShrPrf.getShrdPrfBoolean(SplashScreen.this,"isLogin", false)) {
                    startActivity(new Intent(SplashScreen.this, MainActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(SplashScreen.this, MainActivity.class));
                    finish();
                }
            }
        }, 2000);

//        descimage = findViewById(R.id.titleimage);
//        downtoup = AnimationUtils.loadAnimation(this, R.anim.downtoup);
//
//        Thread myThread = new Thread() {
//            @Override
//            public void run() {
//                try {
//                    sleep(4000);
//                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                    startActivity(intent);
//                    finish();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        };
//        myThread.start();

    }

    public static String getVersionname(Activity con) {
        String mVersion = null;
        try {
            mVersion = con.getPackageManager().getPackageInfo(
                    "com.uttarpradeshtv", 0).versionName;
            currentAppVersion = mVersion;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mVersion;
    }
}