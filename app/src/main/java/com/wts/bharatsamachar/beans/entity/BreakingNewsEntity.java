package com.wts.bharatsamachar.beans.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BreakingNewsEntity {
    @Expose
    @SerializedName("breaking_news")
    private String breaking_news;

    public String getBreaking_news() {
        return breaking_news;
    }

    public void setBreaking_news(String breaking_news) {
        this.breaking_news = breaking_news;
    }
}
