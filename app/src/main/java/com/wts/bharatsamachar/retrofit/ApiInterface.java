package com.wts.bharatsamachar.retrofit;

import com.google.gson.JsonObject;
import com.wts.bharatsamachar.beans.CategoryResponse;
import com.wts.bharatsamachar.beans.DefaultResponse;
import com.wts.bharatsamachar.beans.HomeDataModel;
import com.wts.bharatsamachar.beans.entity.CategoriesWiseNewsEntity;
import com.wts.bharatsamachar.beans.entity.HomeDataEntity;
import com.wts.bharatsamachar.beans.entity.NewsEntity;
import com.wts.bharatsamachar.model.CategoryModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {

    @GET("getnewstopData")
    Call<JsonObject> getTopNews1();

    @FormUrlEncoded
    @POST("getnewssearchData")
    Call<JsonObject> getnewssearchData(@Field("news_heading") String newsHeading);

    //version 3

    @FormUrlEncoded
    @POST("get_news_list_cat_data")
    Call<DefaultResponse<NewsEntity>> getNewsCategory(@Field("newslist_id") String id);

    @FormUrlEncoded
    @POST("get_news_list_subcat_data")
    Call<DefaultResponse<NewsEntity>> getNewsSubCategory(@Field("newslist_id") String id);

    @GET("get_home_category_news")
    Call<DefaultResponse<HomeDataEntity>> getHomeNews();

    @FormUrlEncoded
    @POST("get_news_searchdata")
    Call<DefaultResponse<NewsEntity>> getNewsSearchData(@Field("newslist_id") String searchKeyword);

    @GET("get_category")
    Call<DefaultResponse<List<CategoryModel>>> getCategory();

    @FormUrlEncoded
    @POST("get_sub_category")
    Call<DefaultResponse<List<CategoryModel>>> getSubCategory(@Field("newslist_id") String id);

    @GET("get_topnews")
    Call<DefaultResponse<HomeDataEntity>> getTopNews();
}
