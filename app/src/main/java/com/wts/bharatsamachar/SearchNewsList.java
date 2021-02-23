package com.wts.bharatsamachar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonObject;
import com.wts.bharatsamachar.adapter.NewListAdapter;
import com.wts.bharatsamachar.model.NewListModel;
import com.wts.bharatsamachar.retrofit.RetrofitClient;
import com.wts.bharatsamachar.utils.ads.AdsAppCompactActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchNewsList extends AdsAppCompactActivity {

    RecyclerView topNewsRecycler;
    ArrayList<NewListModel> topNewsArray = new ArrayList<>();
    LinearLayout searchImgLY;
    EditText search_NewsET;

    ImageView backpress,liveTV_Img,searchImg,mainLogo_Img;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_news_list);
        topNewsRecycler = findViewById(R.id.topNewsRecycler);
        searchImgLY = findViewById(R.id.searchImgLY);
        search_NewsET = findViewById(R.id.search_NewsET);
        backpress = findViewById(R.id.backpress);
        liveTV_Img = findViewById(R.id.liveTV_Img);
        searchImg = findViewById(R.id.searchImg);

        getTopNew();

        searchImg.setOnClickListener(v -> {
            String searchtxt = search_NewsET.getText().toString();
            if (searchtxt.equalsIgnoreCase("")){
                search_NewsET.setError("Required");
            }else {
                getSearchedNews(searchtxt);
            }
        });

        backpress.setOnClickListener(v -> onBackPressed());

        liveTV_Img.setOnClickListener(v -> startActivity(new Intent(SearchNewsList.this,LiveTV.class)));

        searchImg.setOnClickListener(v -> {
            Intent intent = new Intent(SearchNewsList.this,SearchNewsList.class);
            startActivity(intent);
        });

        mainLogo_Img = findViewById(R.id.mainLogo_Img);
        mainLogo_Img.setOnClickListener(v -> {
            Intent intent = new Intent(SearchNewsList.this,MainActivity.class);
            startActivity(intent);
        });

    }

    private void getSearchedNews(String searchtxt) {
        topNewsArray.clear();
        ProgressDialog progressDialog = new ProgressDialog(SearchNewsList.this);
        progressDialog.setMessage("Please wait while searching...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Call<JsonObject> call = RetrofitClient.getInstance().getApi().getnewssearchData(searchtxt);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                try {
                    String jsonStr = String.valueOf(response.body());
                    JSONObject jsobobj = new JSONObject(jsonStr);
                    String status = jsobobj.getString("status");
                    if (status.equalsIgnoreCase("success")) {
                        JSONArray jsonArray = jsobobj.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            NewListModel newListModel = new NewListModel();
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String id = jsonObject.getString("id");
                            String news_heading = jsonObject.getString("news_heading");
                            String news_cover_photo = jsonObject.getString("news_cover_photo");
                            String post_date = jsonObject.getString("post_date");
                            String category_name = jsonObject.getString("category_name");
                            newListModel.setCategoryName(category_name);
                            newListModel.setNewId(id);
                            newListModel.setNewsHeading(news_heading);
                            newListModel.setNewTime(post_date);
                            newListModel.setNewsPhoto(news_cover_photo);
                            topNewsArray.add(newListModel);
                        }
                        NewListAdapter adapter = new NewListAdapter(SearchNewsList.this, topNewsArray);
                        topNewsRecycler.setLayoutManager(new LinearLayoutManager(SearchNewsList.this, RecyclerView.VERTICAL, false));
                        topNewsRecycler.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        progressDialog.dismiss();
                    } else {
                        Toast.makeText(SearchNewsList.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(SearchNewsList.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                Log.e("hsdshhdflhsd", t.toString());
                progressDialog.dismiss();
            }
        });

    }

    private void getTopNew() {
        topNewsArray.clear();
        ProgressDialog progressDialog = new ProgressDialog(SearchNewsList.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Call<JsonObject> call = RetrofitClient.getInstance().getApi().getTopNews1();
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                try {
                    String jsonStr = String.valueOf(response.body());
                    JSONObject jsobobj = new JSONObject(jsonStr);
                    String status = jsobobj.getString("status");
                    if (status.equalsIgnoreCase("success")) {
                        JSONArray jsonArray = jsobobj.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            NewListModel newListModel = new NewListModel();
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String id = jsonObject.getString("id");
                            String cat_id = jsonObject.getString("cat_id");
                            String news_heading = jsonObject.getString("news_heading");
                            String news_cover_photo = jsonObject.getString("news_cover_photo");
                            String post_date = jsonObject.getString("post_date");
                            String category_name = jsonObject.getString("category_name");
                            newListModel.setNewId(id);
                            newListModel.setCategoryName(cat_id);
                            newListModel.setNewsHeading(news_heading);
                            newListModel.setNewTime(post_date);
                            newListModel.setCategoryName(category_name);
                            newListModel.setNewsPhoto(news_cover_photo);
                            topNewsArray.add(newListModel);
                        }
                        NewListAdapter adapter = new NewListAdapter(SearchNewsList.this, topNewsArray);
                        topNewsRecycler.setLayoutManager(new LinearLayoutManager(SearchNewsList.this, RecyclerView.VERTICAL, false));
                        topNewsRecycler.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        progressDialog.dismiss();
                    } else {
                        Toast.makeText(SearchNewsList.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(SearchNewsList.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                Log.e("hsdshhdflhsd", t.toString());
                progressDialog.dismiss();
            }
        });

    }
}