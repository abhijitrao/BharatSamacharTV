package com.wts.bharatsamachar.utils.ads;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.wts.bharatsamachar.BuildConfig;
import com.wts.bharatsamachar.utils.AppConstant;

public class AdsAppCompactActivity extends AppCompatActivity {
    private AdsInterstitial adsInterstitial;

    public AdsAppCompactActivity() {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adsInterstitial = new AdsInterstitial(this, BuildConfig.DEBUG ? AppConstant.AdsTestIds.TEST_ID_INTERSTITIAL : AppConstant.ADS_UNIT_INTERSTITIAL);
    }

    public void onBackPressed() {
        super.onBackPressed();
        if (adsInterstitial != null) {
            adsInterstitial.showInterstitial(this);
        }
    }
}
