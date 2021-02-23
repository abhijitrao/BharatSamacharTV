//package com.wts.bharatsamachar;
//
//import android.app.ProgressDialog;
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.google.gson.JsonObject;
//import com.squareup.picasso.Picasso;
//import com.wts.bharatsamachar.adapter.NewListAdapter;
//import com.wts.bharatsamachar.model.NewListModel;
//import com.wts.bharatsamachar.retrofit.RetrofitClient;
//import com.wts.bharatsamachar.utils.AppConstant;
//import com.wts.bharatsamachar.utils.SupportUtil;
//import com.wts.bharatsamachar.utils.ads.AdsAppCompactActivity;
//
//import org.json.JSONArray;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//import static com.wts.bharatsamachar.retrofit.RetrofitClient.IMAGE_URL;
//
//public class CategoryShow extends AdsAppCompactActivity {
//
//    RecyclerView recyclerView;
//    ArrayList<NewListModel> arrayList = new ArrayList<>();
//    String categoryId,typeStr;
//
//    ImageView topnewImg;
//    TextView topCategoryTT,topPostTT,topHeadingTT;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_category_show);
//        recyclerView = findViewById(R.id.recyclerView);
//
//        topnewImg = findViewById(R.id.topnewImg);
//        topCategoryTT = findViewById(R.id.topCategoryTT);
//        topPostTT = findViewById(R.id.topPostTT);
//        topHeadingTT = findViewById(R.id.topHeadingTT);
//
//        categoryId = getIntent().getStringExtra(AppConstant.CAT_ID);
//        typeStr = getIntent().getStringExtra(AppConstant.TYPE);
//
//        if (typeStr.equalsIgnoreCase("category")){
//            getNewListData();
//        }else {
//            getSubNewsData();
//        }
//
//
//        (findViewById(R.id.backpress)).setOnClickListener(v -> onBackPressed());
//
//        (findViewById(R.id.liveTV_Img)).setOnClickListener(v -> startActivity(new Intent(CategoryShow.this,LiveTV.class)));
//
//        (findViewById(R.id.searchImg)).setOnClickListener(v -> {
//            Intent intent = new Intent(CategoryShow.this,SearchNewsList.class);
//            startActivity(intent);
//        });
//        (findViewById(R.id.mainLogo_Img)).setOnClickListener(v -> {
//            Intent intent = new Intent(CategoryShow.this,MainActivity.class);
//            startActivity(intent);
//        });
//
//    }
//
//    private void getNewListData() {
//        arrayList.clear();
//        ProgressDialog progressDialog = new ProgressDialog(CategoryShow.this);
//        progressDialog.setMessage("Loading...");
//        progressDialog.setCancelable(false);
//        progressDialog.show();
//        Call<JsonObject> call = RetrofitClient.getInstance().getApi().getNewList(categoryId);
//        call.enqueue(new Callback<JsonObject>() {
//            @Override
//            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
//                try {
//                    String jsonStr = String.valueOf(response.body());
//                    JSONObject jsobobj = new JSONObject(jsonStr);
//                    String status = jsobobj.getString("status");
//                    if (status.equalsIgnoreCase("success")) {
//                        String data = jsobobj.getString("data");
//                        JSONObject jsonObjectss = new JSONObject(data);
//                        String bigNews = jsonObjectss.getString("bigNews");
//                        JSONObject jsonObjects = new JSONObject(bigNews);
//                        String news_heading0 = jsonObjects.getString("news_heading");
//                        String news_cover_photo0 = jsonObjects.getString("news_cover_photo");
//                        String category_name0 = jsonObjects.getString("category_name");
//                        String post_date0 = jsonObjects.getString("post_date");
//                        try {
//                            topPostTT.setText(SupportUtil.convertDate(post_date0));
//                        }catch (Exception e){
//                            e.printStackTrace();
//                        }
//                        Picasso.get().load(IMAGE_URL +news_cover_photo0).into(topnewImg);
//                        topHeadingTT.setText(news_heading0);
//                        topCategoryTT.setText(category_name0);
//                        JSONArray jsonArray = jsonObjectss.getJSONArray("listNews");
//                        for (int i = 0; i < jsonArray.length(); i++) {
//                            //yaha bhi big news lagnga
//                            NewListModel newListModel = new NewListModel();
//                            JSONObject jsonObject = jsonArray.getJSONObject(i);
//                            String id = jsonObject.getString("id");
//                            String news_heading = jsonObject.getString("news_heading");
//                            String news_cover_photo = jsonObject.getString("news_cover_photo");
//                            String post_date = jsonObject.getString("post_date");
//                            String category_name = jsonObject.getString("category_name");
//                            newListModel.setCategoryName(category_name);
//                            newListModel.setNewId(id);
//                            newListModel.setNewsHeading(news_heading);
//                            newListModel.setNewsPhoto(news_cover_photo);
//                            newListModel.setNewTime(post_date);
//                            arrayList.add(newListModel);
//                        }
//                        NewListAdapter adapter = new NewListAdapter(CategoryShow.this, arrayList);
//                        recyclerView.setLayoutManager(new LinearLayoutManager(CategoryShow.this, RecyclerView.VERTICAL, false));
//                        recyclerView.setAdapter(adapter);
//                        adapter.notifyDataSetChanged();
//                        progressDialog.dismiss();
//                    } else {
//                        progressDialog.dismiss();
//                        Toast.makeText(CategoryShow.this, "Something went wrong", Toast.LENGTH_SHORT).show();
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    progressDialog.dismiss();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<JsonObject> call, Throwable t) {
//                progressDialog.dismiss();
//                Log.e("tyuioiuyfyui", t.toString());
//            }
//        });
//    }
//
//    private void getSubNewsData() {
//        arrayList.clear();
//        ProgressDialog progressDialog = new ProgressDialog(CategoryShow.this);
//        progressDialog.setMessage("Loading...");
//        progressDialog.setCancelable(false);
//        progressDialog.show();
//        Call<JsonObject> call = RetrofitClient.getInstance().getApi().getSubCatNewsData(categoryId);
//        call.enqueue(new Callback<JsonObject>() {
//            @Override
//            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
//                try {
//                    String jsonStr = String.valueOf(response.body());
//                    JSONObject jsobobj = new JSONObject(jsonStr);
//                    String status = jsobobj.getString("status");
//                    if (status.equalsIgnoreCase("success")) {
//                        String data = jsobobj.getString("data");
//                        JSONObject jsonObjectss = new JSONObject(data);
//                        String bigNews = jsonObjectss.getString("bigNews");
//                        JSONObject jsonObjects = new JSONObject(bigNews);
//                        String news_heading0 = jsonObjects.getString("news_heading");
//                        String news_cover_photo0 = jsonObjects.getString("news_cover_photo");
//                        String category_name0 = jsonObjects.getString("category_name");
//                        String post_date0 = jsonObjects.getString("post_date");
//                        try {
//                            topPostTT.setText(SupportUtil.convertDate(post_date0));
//                        }catch (Exception e){
//                            e.printStackTrace();
//                        }
//                        Picasso.get().load(IMAGE_URL +news_cover_photo0).into(topnewImg);
//                        topHeadingTT.setText(news_heading0);
//                        topCategoryTT.setText(category_name0);
//                        JSONArray jsonArray = jsonObjectss.getJSONArray("listNews");
//                        for (int i = 0; i < jsonArray.length(); i++) {
//                            NewListModel newListModel = new NewListModel();
//                            JSONObject jsonObject = jsonArray.getJSONObject(i);
//                            String id = jsonObject.getString("id");
//                            String news_heading = jsonObject.getString("news_heading");
//                            String news_cover_photo = jsonObject.getString("news_cover_photo");
//                            String post_date = jsonObject.getString("post_date");
//                            String category_name = jsonObject.getString("subcategory_name");
//                            newListModel.setCategoryName(category_name);
//                            newListModel.setNewId(id);
//                            newListModel.setNewsHeading(news_heading);
//                            newListModel.setNewsPhoto(news_cover_photo);
//                            newListModel.setNewTime(post_date);
//                            arrayList.add(newListModel);
//                        }
//                        NewListAdapter adapter = new NewListAdapter(CategoryShow.this, arrayList);
//                        recyclerView.setLayoutManager(new LinearLayoutManager(CategoryShow.this, RecyclerView.VERTICAL, false));
//                        recyclerView.setAdapter(adapter);
//                        adapter.notifyDataSetChanged();
//                        progressDialog.dismiss();
//                    } else {
//                        progressDialog.dismiss();
//                        Toast.makeText(CategoryShow.this, "Something went wrong", Toast.LENGTH_SHORT).show();
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    progressDialog.dismiss();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<JsonObject> call, Throwable t) {
//                progressDialog.dismiss();
//                Log.e("tyuioiuyfyui", t.toString());
//            }
//        });
//    }
//}