package com.wts.bharatsamachar;

import android.app.Application;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.onesignal.OneSignal;
import com.wts.bharatsamachar.utils.AppConstant;


/**
 * Created by amit on 12/1/17.
 */

public class AppApplication extends Application {
    private static AppApplication appApplication;
    private static final String ONESIGNAL_APP_ID = "670ebe9a-ea45-46c5-b6f2-05a0b0c805dd";

    public static AppApplication getInstance() {
        return appApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appApplication = this;
        initAds();
        initOneSignal();
    }

    private void initOneSignal() {
        // Enable verbose OneSignal logging to debug issues if needed.
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);

        // OneSignal Initialization
        OneSignal.initWithContext(this);
        OneSignal.setAppId(ONESIGNAL_APP_ID);
    }

    public void initAds(){
        if (AppConstant.IS_ADS_ENABLED) {
            MobileAds.initialize(this, new OnInitializationCompleteListener() {
                @Override
                public void onInitializationComplete(InitializationStatus initializationStatus) {
                }
            });
        }
    }
}