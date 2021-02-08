package com.wts.bharatsamachar.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.wts.bharatsamachar.beans.entity.DataEntity;

public class HomeDataModel {

    @Expose
    @SerializedName("data")
    private DataEntity data;
    @Expose
    @SerializedName("message")
    private String message;
    @Expose
    @SerializedName("status")
    private String status;

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
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
