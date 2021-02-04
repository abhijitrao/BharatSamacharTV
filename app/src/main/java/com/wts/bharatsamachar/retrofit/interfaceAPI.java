package com.wts.bharatsamachar.retrofit;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface interfaceAPI {

    @GET("getcategory")
    Call<JsonObject> getCategory();

    @GET("getbreaking")
    Call<JsonObject> getBreaking();

    @GET("getsubcategory")
    Call<JsonObject> getSubCategory();

    @GET("getdeshsubcategory")
    Call<JsonObject> getRajyaSubCategory();

    @GET("getnewstopData")
    Call<JsonObject> getTopNews();

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
    @POST("getnewsdetailsData")
    Call<JsonObject> getNewDetailData(@Field("newsdetails_id") String newsDetail_Id);

    @FormUrlEncoded
    @POST("getnewslistsubcatData")
    Call<JsonObject> getSubCatNewsData(@Field("newslist_id") String newsDetail_Id);

    @FormUrlEncoded
    @POST("getnewssearchData")
    Call<JsonObject> getnewssearchData(@Field("news_heading") String newsHeading);
}
