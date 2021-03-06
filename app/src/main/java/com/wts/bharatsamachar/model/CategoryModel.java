package com.wts.bharatsamachar.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CategoryModel {

    @Expose
    @SerializedName("id")
    String id;

    @Expose
    @SerializedName("subcategory_name")
    String subCatName;

    @Expose
    @SerializedName("category_name")
    String catName;

    @Expose
    @SerializedName(value="cat_id", alternate={"category_type"})
    String cat_id;

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getSubCatName() {
        return subCatName;
    }

    public void setSubCatName(String subCatName) {
        this.subCatName = subCatName;
    }
}
