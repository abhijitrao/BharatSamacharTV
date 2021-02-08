package com.wts.bharatsamachar.beans.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataEntity {

    @Expose
    @SerializedName("categorieswisenews")
    private List<CategoriesWiseNewsEntity> categoriesWiseNewsEntity;
    @Expose
    @SerializedName("breakingNews")
    private List<BreakingNewsEntity> breakingNewsEntity;
    @Expose
    @SerializedName("topNews")
    private NewsEntity topNews;

    public List<CategoriesWiseNewsEntity> getCategoriesWiseNewsEntity() {
        return categoriesWiseNewsEntity;
    }

    public void setCategoriesWiseNewsEntity(List<CategoriesWiseNewsEntity> categoriesWiseNewsEntity) {
        this.categoriesWiseNewsEntity = categoriesWiseNewsEntity;
    }

    public List<BreakingNewsEntity> getBreakingNewsEntity() {
        return breakingNewsEntity;
    }

    public void setBreakingNewsEntity(List<BreakingNewsEntity> breakingNewsEntity) {
        this.breakingNewsEntity = breakingNewsEntity;
    }

    public NewsEntity getTopNews() {
        return topNews;
    }

    public void setTopNews(NewsEntity topNews) {
        this.topNews = topNews;
    }
}
