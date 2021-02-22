package com.wts.bharatsamachar;

import android.app.Application;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.onesignal.OneSignal;
import com.wts.bharatsamachar.retrofit.RetrofitClient;
import com.wts.bharatsamachar.retrofit.ApiInterface;
import com.wts.bharatsamachar.utils.AppConstant;
import com.wts.bharatsamachar.utils.TrackingApp;


/**
 * Created by amit on 12/1/17.
 */

public class AppApplication extends TrackingApp {
    private static AppApplication appApplication;
    private static final String ONESIGNAL_APP_ID = "670ebe9a-ea45-46c5-b6f2-05a0b0c805dd";
    private ApiInterface apiInterface;

    private Gson gson;

    public Gson getGson() {
        if(gson == null){
            gson = new GsonBuilder()
                    .excludeFieldsWithoutExposeAnnotation()
                    .serializeNulls()
                    .create();
        }
        return gson;
    }

    public static AppApplication getInstance() {
        return appApplication;
    }

    @Override
    public boolean isDebugMode() {
        return BuildConfig.DEBUG;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appApplication = this;
        initAds();
        initOneSignal();
        apiInterface = getApiInterface();
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


    public ApiInterface getApiInterface() {
        if(apiInterface == null){
            apiInterface = RetrofitClient.getClientV2().create(ApiInterface.class);
        }
        return apiInterface;
    }
}