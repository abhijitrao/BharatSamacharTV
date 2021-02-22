package com.wts.bharatsamachar.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wts.bharatsamachar.BuildConfig;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitClient {
    public static final String BASE_URL = "https://www.bharatsamachartv.in/webapi/";
    public static final String BASE_URL_V3 = "https://bharatsamachartv.in/api/V3/webapi/";
    public static final String IMAGE_URL = "https://www.bharatsamachartv.in/application/libraries/uploads/news_img/";
    private static RetrofitClient mInstance;
    private static Retrofit retrofitV2;
    private retrofit2.Retrofit retrofit;

    private RetrofitClient() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
//        OkHttpClient client = new OkHttpClient.Builder()
//                .connectTimeout(7000, TimeUnit.SECONDS)
//                .readTimeout(7000, TimeUnit.SECONDS).build();

        retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addConverterFactory(ScalarsConverterFactory.create())
                .client(getHttpClient(BuildConfig.DEBUG).build())
                .build();
    }

    private static OkHttpClient.Builder getHttpClient(boolean isDebug) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (isDebug) {
            builder.addInterceptor(loggingInterceptor);
        }
        return builder;
    }

    private static final HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);


    public static synchronized RetrofitClient getInstance() {
        if (mInstance == null) {
            mInstance = new RetrofitClient();
        }
        return mInstance;
    }

    public ApiInterface getApi() {
        return retrofit.create(ApiInterface.class);
    }

    public static Retrofit getClientV2() {
        if (retrofitV2==null) {
            retrofitV2 = new Retrofit.Builder()
                    .baseUrl(BASE_URL_V3)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(getHttpClient(BuildConfig.DEBUG).build())
                    .build();
        }
        return retrofitV2;
    }
}
