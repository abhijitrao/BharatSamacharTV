package com.wts.bharatsamachar.utils.ads;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.LoadAdError;
import com.wts.bharatsamachar.utils.AppConstant;

/**
 * Created by Amit on 3/9/2018.
 */

public class AdsInterstitial {

    private Context context;
    private String adId;
    private Activity activity ;
    private int failCount = 0 ;
    public static final int FAILED_MAX_COUNT = 5 ;

    public AdsInterstitial(Context context, String adId) {
        this.context = context;
        this.adId = adId;
        if ( !TextUtils.isEmpty(adId) ) {
            initFullAds();
        }
    }

    private InterstitialAd mInterstitialAd;

    private void initFullAds() {
        if (AppConstant.IS_ADS_ENABLED) {
            mInterstitialAd = new InterstitialAd(context);
            mInterstitialAd.setAdUnitId(adId);
            mInterstitialAd.loadAd(AdsManager.getAdRequest());
            mInterstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdFailedToLoad(LoadAdError loadAdError) {
                    super.onAdFailedToLoad(loadAdError);
                    if ( failCount < FAILED_MAX_COUNT ) {
                        failCount++;
                        mInterstitialAd.loadAd(AdsManager.getAdRequest());
                    }
                }

                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();
                    failCount = 0 ;
                }

                @Override
                public void onAdClosed() {
                        super.onAdClosed();
                    if ( activity != null ) {
                        activity.finish();
                    }
                    initFullAds();
                }
            });
        }
    }

    public void showInterstitial(Activity activity) {
        this.activity = activity ;
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else if ( activity != null )
            activity.finish();
    }
}
