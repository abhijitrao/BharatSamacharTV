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

    @GET("getcategory")
    Call<CategoryResponse> getCategory1();

    @GET("getbreaking")
    Call<JsonObject> getBreaking();

    @GET("getsubcategory")
    Call<JsonObject> getSubCategory();

    @GET("getdeshsubcategory")
    Call<JsonObject> getRajyaSubCategory();

    @GET("getnewstopData")
    Call<JsonObject> getTopNews1();

    @GET("getdeshnewsData")
    Call<JsonObject> getDeshNews();

    @GET("getrajyaData")
    Call<JsonObject> getRajyaNews();

    @GET("getvideoData")
    Call<JsonObject> getVideoData();

    @FormUrlEncoded
    @POST("getnewslistData")
    Call<JsonObject> getNewList(@Field("newslist_id") String newslist_Id);

    @FormUrlEncoded
    @POST("getnewslistData")
    Call<CategoriesWiseNewsEntity> getNewListV2(@Field("newslist_id") String newslist_Id);

    @FormUrlEncoded
    @POST("getnewsdetailsData")
    Call<JsonObject> getNewDetailData(@Field("newsdetails_id") String newsDetail_Id);

    @FormUrlEncoded
    @POST("getnewslistsubcatData")
    Call<JsonObject> getSubCatNewsData(@Field("newslist_id") String newsDetail_Id);

    @FormUrlEncoded
    @POST("getnewssearchData")
    Call<JsonObject> getnewssearchData(@Field("news_heading") String newsHeading);

    @GET("getHomeNews")
    Call<HomeDataModel> getHomeNews2();

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

    @GET("get_topnews")
    Call<DefaultResponse<HomeDataEntity>> getTopNews();
}
