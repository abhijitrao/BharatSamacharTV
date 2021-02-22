package com.wts.bharatsamachar.retrofit;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.gson.reflect.TypeToken;
import com.wts.bharatsamachar.AppApplication;
import com.wts.bharatsamachar.beans.CategoryResponse;
import com.wts.bharatsamachar.beans.DefaultResponse;
import com.wts.bharatsamachar.beans.entity.CategoriesWiseNewsEntity;
import com.wts.bharatsamachar.beans.entity.HomeDataEntity;
import com.wts.bharatsamachar.beans.HomeDataModel;
import com.wts.bharatsamachar.model.CategoryModel;
import com.wts.bharatsamachar.utils.AppCallback;
import com.wts.bharatsamachar.utils.AppConstant;
import com.wts.bharatsamachar.utils.AppPreferences;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetworkManager {

    public static void getCategory(Context context, AppCallback.Callback<List<CategoryModel>> callback) {
        AppApplication.getInstance().getApiInterface().getCategory().enqueue(new Callback<DefaultResponse<List<CategoryModel>>>() {
            @Override
            public void onResponse(@NonNull Call<DefaultResponse<List<CategoryModel>>> call,@NonNull Response<DefaultResponse<List<CategoryModel>>> response) {
                if(response.isSuccessful() && response.body() != null && response.body().getStatus().equalsIgnoreCase(AppConstant.SUCCESS)){
                    if (response.body().getData() != null) {
                        AppPreferences.setCategory(context, AppApplication.getInstance().getGson().toJson(response.body().getData(), new TypeToken<List<CategoryModel>>() {}.getType()));
                        callback.onSuccess(response.body().getData());
                    }else {
                        callback.onFailure(new Exception(response.body().getMessage()));
                    }
                }else {
                    callback.onFailure(new Exception(AppConstant.NO_DATA));
                }
            }

            @Override
            public void onFailure(@NonNull Call<DefaultResponse<List<CategoryModel>>> call,@NonNull Throwable t) {
                callback.onFailure(new Exception(AppConstant.NO_DATA));
            }
        });
    }

    public static void getHomeNews(AppCallback.Callback<HomeDataEntity> callback) {
        AppApplication.getInstance().getApiInterface().getHomeNews().enqueue(new Callback<DefaultResponse<HomeDataEntity>>() {
            @Override
            public void onResponse(@NonNull Call<DefaultResponse<HomeDataEntity>> call,@NonNull Response<DefaultResponse<HomeDataEntity>> response) {
                if(response.isSuccessful() && response.body() != null && response.body().getStatus().equalsIgnoreCase(AppConstant.SUCCESS)){
                    if (response.body().getData() != null) {
                        callback.onSuccess(response.body().getData());
                    }else {
                        callback.onFailure(new Exception(response.body().getMessage()));
                    }
                }else {
                    callback.onFailure(new Exception(AppConstant.NO_DATA));
                }
            }

            @Override
            public void onFailure(@NonNull Call<DefaultResponse<HomeDataEntity>> call,@NonNull Throwable t) {
                callback.onFailure(new Exception(AppConstant.NO_DATA));
            }
        });
    }

    public static void getTopNews(AppCallback.Callback<HomeDataEntity> callback) {
        AppApplication.getInstance().getApiInterface().getTopNews().enqueue(new Callback<DefaultResponse<HomeDataEntity>>() {
            @Override
            public void onResponse(@NonNull Call<DefaultResponse<HomeDataEntity>> call,@NonNull Response<DefaultResponse<HomeDataEntity>> response) {
                if(response.isSuccessful() && response.body() != null && response.body().getStatus().equalsIgnoreCase(AppConstant.SUCCESS)){
                    if (response.body().getData() != null) {
                        callback.onSuccess(response.body().getData());
                    }else {
                        callback.onFailure(new Exception(response.body().getMessage()));
                    }
                }else {
                    callback.onFailure(new Exception(AppConstant.NO_DATA));
                }
            }

            @Override
            public void onFailure(@NonNull Call<DefaultResponse<HomeDataEntity>> call,@NonNull Throwable t) {
                callback.onFailure(new Exception(AppConstant.NO_DATA));
            }
        });
    }

    public static void getNewListV2(String catId, AppCallback.Callback<List<CategoriesWiseNewsEntity>> callback) {
        AppApplication.getInstance().getApiInterface().getNewListV2(catId).enqueue(new Callback<CategoriesWiseNewsEntity>() {
            @Override
            public void onResponse(@NonNull Call<CategoriesWiseNewsEntity> call, @NonNull Response<CategoriesWiseNewsEntity> response) {
                if(response.isSuccessful() && response.body() != null && response.body().getStatus().equalsIgnoreCase(AppConstant.SUCCESS)){
                    if (response.body().getNews() != null) {
                        List<CategoriesWiseNewsEntity> mList = new ArrayList<>();
                        CategoriesWiseNewsEntity item = new CategoriesWiseNewsEntity();
                        item.setNews(response.body().getNews());
                        item.setCategory_type("3");
                        mList.add(item);
                        callback.onSuccess(mList);
                    }else {
                        callback.onFailure(new Exception(response.body().getMessage()));
                    }
                }else {
                    callback.onFailure(new Exception(AppConstant.NO_DATA));
                }
            }

            @Override
            public void onFailure(@NonNull Call<CategoriesWiseNewsEntity> call, @NonNull Throwable t) {
                callback.onFailure(new Exception(AppConstant.NO_DATA));
            }
        });
    }
}
