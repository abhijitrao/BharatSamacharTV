package com.wts.bharatsamachar.utils.ads;

import android.content.Context;
import android.text.TextUtils;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.wts.bharatsamachar.BuildConfig;
import com.wts.bharatsamachar.utils.AppConstant;

/**
 * AdsManager.initAds((RelativeLayout)findViewById(R.id.ll_ad));
 */
public class AdsManager {

    public static void initAds(RelativeLayout view) {
        if (AppConstant.IS_ADS_ENABLED) {
            initAds(view, BuildConfig.DEBUG ? AppConstant.AdsTestIds.TEST_ID_BANNER : AppConstant.ADS_UNIT_BANNER, AdSize.BANNER);
        }
    }

    static void initAds(final RelativeLayout view, String id, AdSize adSize) {
        if (!TextUtils.isEmpty(id) && view != null && view.getContext() != null ) {
            final AdView adView = new AdView(view.getContext());
            adView.setAdUnitId(id);
            adView.setAdSize(adSize);
            adView.loadAd(getAdRequest());
            adView.setAdListener(new AdListener() {
                public void onAdFailedToLoad(LoadAdError i) {
                    super.onAdFailedToLoad(i);
                }

                public void onAdLoaded() {
                    super.onAdLoaded();
                    if (view != null) {
                        view.removeAllViews();
                        view.addView(adView);
                    }

                }
            });
            if (view != null) {
                view.removeAllViews();
                view.addView(adView);
            }
        }

    }

    public static AdRequest getAdRequest() {
        AdRequest.Builder builder = new AdRequest.Builder();
        builder.addTestDevice("5E254AC1CF02E640413645E46C8A1A64");
        builder.addTestDevice("2C374DC7F25FC3474B235578C446D36F");
        builder.addTestDevice("A3B72905D6C2F896FBDB8A01F186A77D");
        builder.addTestDevice("B3EEABB8EE11C2BE770B684D95219ECB");
        return builder.build();
    }
}
