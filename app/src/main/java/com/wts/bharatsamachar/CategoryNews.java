package com.wts.bharatsamachar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.JsonObject;
import com.wts.bharatsamachar.adapter.TabAdapter;
import com.wts.bharatsamachar.adapter.ViewPagerAdapter;
import com.wts.bharatsamachar.fragment.HomeFragment;
import com.wts.bharatsamachar.fragment.TabFragment;
import com.wts.bharatsamachar.model.CategoryModel;
import com.wts.bharatsamachar.retrofit.NetworkManager;
import com.wts.bharatsamachar.retrofit.RetrofitClient;
import com.wts.bharatsamachar.utils.AppCallback;
import com.wts.bharatsamachar.utils.AppConstant;
import com.wts.bharatsamachar.utils.ads.AdsAppCompactActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryNews extends AdsAppCompactActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    String type;

    ImageView backpress,liveTV_Img,searchImg,mainLogo_Img;
    private String catId;
    private ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_news);
        tabLayout = findViewById(R.id.tabs_layout_up);
        viewPager = findViewById(R.id.viewpager_up);
        backpress = findViewById(R.id.backpress);
        liveTV_Img = findViewById(R.id.liveTV_Img);
        searchImg = findViewById(R.id.searchImg);
        mainLogo_Img = findViewById(R.id.mainLogo_Img);

        catId = getIntent().getStringExtra(AppConstant.CAT_ID);
        type = getIntent().getStringExtra(AppConstant.TYPE);
//        if (type.equalsIgnoreCase("up")){
        loadCategoryFromServer();
//        }else {
//            getRajyaCategory();
//        }

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


    private void loadCategoryFromServer() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        NetworkManager.getSubCategory(this, catId, new AppCallback.Callback<List<CategoryModel>>() {
            @Override
            public void onSuccess(List<CategoryModel> response) {
                setupViewPager(response, progressDialog);
            }

            @Override
            public void onFailure(Exception e) {
                Toast.makeText(CategoryNews.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    private void setupViewPager(List<CategoryModel> mList, ProgressDialog progressDialog) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), mList.size());
        Bundle bundle;
        for (CategoryModel child : mList) {
            Fragment fragment;
            fragment = new TabFragment();
            bundle = new Bundle();
            bundle.putString(AppConstant.CAT_ID, child.getId());
            bundle.putString(AppConstant.TYPE, AppConstant.SUB_CATEGORY);
            bundle.putString(AppConstant.TITLE, child.getSubCatName());
            fragment.setArguments(bundle);
            adapter.addFrag(fragment,child.getId(), child.getSubCatName());
            tabLayout.addTab(tabLayout.newTab().setText(child.getSubCatName()));
        }
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(mList.size());
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.setCurrentItem(0);
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }
}