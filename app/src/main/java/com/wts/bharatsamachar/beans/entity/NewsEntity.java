package com.wts.bharatsamachar.beans.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewsEntity {
    @Expose
    @SerializedName("listNews")
    private List<ListNewsEntity> listNews;
    @Expose
    @SerializedName("bigNews")
    private BigNewsEntity bigNews;

    public List<ListNewsEntity> getListNews() {
        return listNews;
    }

    public void setListNews(List<ListNewsEntity> listNews) {
        this.listNews = listNews;
    }

    public BigNewsEntity getBigNews() {
        return bigNews;
    }

    public void setBigNews(BigNewsEntity bigNews) {
        this.bigNews = bigNews;
    }
}
