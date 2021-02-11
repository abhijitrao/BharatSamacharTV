package com.wts.bharatsamachar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.JsonObject;
import com.wts.bharatsamachar.adapter.TabAdapter;
import com.wts.bharatsamachar.model.CategoryModel;
import com.wts.bharatsamachar.retrofit.RetrofitClient;
import com.wts.bharatsamachar.utils.AppConstant;
import com.wts.bharatsamachar.utils.ads.AdsAppCompactActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryNews extends AdsAppCompactActivity {

    TabLayout tabs_layout_up;
    ViewPager viewpager_up;
    ArrayList<CategoryModel> rajya_subCategoryArray = new ArrayList<>();
    String type;

    ImageView backpress,liveTV_Img,searchImg,mainLogo_Img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_news);
        tabs_layout_up = findViewById(R.id.tabs_layout_up);
        viewpager_up = findViewById(R.id.viewpager_up);
        backpress = findViewById(R.id.backpress);
        liveTV_Img = findViewById(R.id.liveTV_Img);
        searchImg = findViewById(R.id.searchImg);
        mainLogo_Img = findViewById(R.id.mainLogo_Img);

        type = getIntent().getStringExtra(AppConstant.TYPE);
        if (type.equalsIgnoreCase("up")){
            getSubCategory();
        }else {
            getRajyaCategory();
        }

        backpress.setOnClickListener(v -> onBackPressed());

        liveTV_Img.setOnClickListener(v -> startActivity(new Intent(CategoryNews.this,LiveTV.class)));

        searchImg.setOnClickListener(v -> {
            Intent intent = new Intent(CategoryNews.this,SearchNewsList.class);
            startActivity(intent);
        });

        mainLogo_Img.setOnClickListener(v -> {
            Intent intent = new Intent(CategoryNews.this,MainActivity.class);
            startActivity(intent);
        });
    }

    private void getRajyaCategory() {
        ProgressDialog progressDialog = new ProgressDialog(CategoryNews.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Call<JsonObject> call = RetrofitClient.getInstance().getApi().getRajyaSubCategory();
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                try {
                    String jsonStr = String.valueOf(response.body());
                    JSONObject jsobobj = new JSONObject(jsonStr);
                    String status = jsobobj.getString("status");
                    if (status.equalsIgnoreCase("success")){
                        JSONArray jsonArray = jsobobj.getJSONArray("data");
                        for (int i = 0; i <jsonArray.length(); i++) {
                            CategoryModel categoryModel = new CategoryModel();
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String id = jsonObject.getString("id");
                            String category_name = jsonObject.getString("subcategory_name");
                            categoryModel.setId(id);
                            categoryModel.setCatName(category_name);
                            rajya_subCategoryArray.add(categoryModel);
                            viewpager_up.setCurrentItem(0);//
                            tabs_layout_up.addTab(tabs_layout_up.newTab().setText(category_name));//
                        }
                        TabAdapter adapter = new TabAdapter
                                (getSupportFragmentManager(), tabs_layout_up.getTabCount(),rajya_subCategoryArray,"sub");
                        viewpager_up.setAdapter(adapter);
                        viewpager_up.setOffscreenPageLimit(1);
                        viewpager_up.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs_layout_up));
                        progressDialog.dismiss();
                    } else {
                        tabs_layout_up.setVisibility(View.GONE);
                        viewpager_up.setVisibility(View.GONE);
                        Toast.makeText(CategoryNews.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }

                }catch (Exception e){
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(CategoryNews.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                Log.e("hsdshhdflhsd",t.toString());
                progressDialog.dismiss();
            }
        });
    }

    private void getSubCategory() {
        ProgressDialog progressDialog = new ProgressDialog(CategoryNews.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Call<JsonObject> call = RetrofitClient.getInstance().getApi().getSubCategory();
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                try {
                    String jsonStr = String.valueOf(response.body());
                    JSONObject jsobobj = new JSONObject(jsonStr);
                    String status = jsobobj.getString("status");
                    if (status.equalsIgnoreCase("success")){
                        JSONArray jsonArray = jsobobj.getJSONArray("data");
                        for (int i = 0; i <jsonArray.length(); i++) {
                            CategoryModel categoryModel = new CategoryModel();
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String id = jsonObject.getString("id");
                            String cat_id = jsonObject.getString("cat_id");
                            String category_name = jsonObject.getString("subcategory_name");
                            categoryModel.setId(id);
                            categoryModel.setCatName(category_name);
                            rajya_subCategoryArray.add(categoryModel);
                            viewpager_up.setCurrentItem(0);
                            tabs_layout_up.addTab(tabs_layout_up.newTab().setText(category_name));
                        }
                        TabAdapter adapters = new TabAdapter
                                (getSupportFragmentManager(), tabs_layout_up.getTabCount(),rajya_subCategoryArray,"sub");
                        viewpager_up.setAdapter(adapters);
                        viewpager_up.setOffscreenPageLimit(1);
                        viewpager_up.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs_layout_up));
                        progressDialog.dismiss();
                    } else {
                        Toast.makeText(CategoryNews.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }

                }catch (Exception e){
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(CategoryNews.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                Log.e("hsdshhdflhsd",t.toString());
                progressDialog.dismiss();
            }
        });
    }
}