package com.wts.bharatsamachar.retrofit;

import androidx.annotation.NonNull;

import com.wts.bharatsamachar.AppApplication;
import com.wts.bharatsamachar.beans.CategoryResponse;
import com.wts.bharatsamachar.beans.entity.CategoriesWiseNewsEntity;
import com.wts.bharatsamachar.beans.entity.DataEntity;
import com.wts.bharatsamachar.beans.HomeDataModel;
import com.wts.bharatsamachar.model.CategoryModel;
import com.wts.bharatsamachar.utils.AppCallback;
import com.wts.bharatsamachar.utils.AppConstant;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetworkManager {

    public static void getCategory(AppCallback.Callback<List<CategoryModel>> callback) {
        RetrofitClient.getInstance().getApi().getCategory().enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(@NonNull Call<CategoryResponse> call, @NonNull Response<CategoryResponse> response) {
                if(response.isSuccessful() && response.body() != null && response.body().getStatus().equalsIgnoreCase(AppConstant.SUCCESS)){
                    if (response.body().getData() != null) {
                        callback.onSuccess(response.body().getData());
                    }else {
                        callback.onFailure(new Exception(AppConstant.NO_DATA));
                    }
                }else {
                    callback.onFailure(new Exception(response.body().getMessage()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<CategoryResponse> call, @NonNull Throwable t) {
                callback.onFailure(new Exception(AppConstant.NO_DATA));
            }
        });
    }

    public static void getHomeNews(AppCallback.Callback<DataEntity> callback) {
        AppApplication.getInstance().getApiInterface().getHomeNews().enqueue(new Callback<HomeDataModel>() {
            @Override
            public void onResponse(@NonNull Call<HomeDataModel> call, @NonNull Response<HomeDataModel> response) {
                if(response.isSuccessful() && response.body() != null && response.body().getStatus().equalsIgnoreCase(AppConstant.SUCCESS)){
                    if (response.body().getData() != null) {
                        callback.onSuccess(response.body().getData());
                    }else {
                        callback.onFailure(new Exception(AppConstant.NO_DATA));
                    }
                }else {
                    callback.onFailure(new Exception(response.body().getMessage()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<HomeDataModel> call, @NonNull Throwable t) {

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
                        callback.onFailure(new Exception(AppConstant.NO_DATA));
                    }
                }else {
                    callback.onFailure(new Exception(response.body().getMessage()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<CategoriesWiseNewsEntity> call, @NonNull Throwable t) {

            }
        });
    }
}
