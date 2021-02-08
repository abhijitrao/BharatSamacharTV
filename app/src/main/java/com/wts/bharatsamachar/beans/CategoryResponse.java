package com.wts.bharatsamachar.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.wts.bharatsamachar.model.CategoryModel;

import java.util.ArrayList;

public class CategoryResponse {

    @Expose
    @SerializedName("data")
    private ArrayList<CategoryModel> data;
    @Expose
    @SerializedName("message")
    private String message;
    @Expose
    @SerializedName("status")
    private String status;

    public ArrayList<CategoryModel> getData() {
        return data;
    }

    public void setData(ArrayList<CategoryModel> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
