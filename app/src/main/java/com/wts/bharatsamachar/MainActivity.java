package com.wts.bharatsamachar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.reflect.TypeToken;
import com.wts.bharatsamachar.activity.SubCategoryActivity;
import com.wts.bharatsamachar.adapter.ViewPagerAdapter;
import com.wts.bharatsamachar.fragment.HomeFragment;
import com.wts.bharatsamachar.fragment.TabFragment;
import com.wts.bharatsamachar.model.CategoryModel;
import com.wts.bharatsamachar.retrofit.NetworkManager;
import com.wts.bharatsamachar.utils.AppCallback;
import com.wts.bharatsamachar.utils.AppConstant;
import com.wts.bharatsamachar.utils.AppPreferences;
import com.wts.bharatsamachar.utils.ads.AdsAppCompactActivity;
import com.wts.bharatsamachar.utils.ads.AdsManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AdsAppCompactActivity implements NavigationView.OnNavigationItemSelectedListener, AppCallback.OnViewMoreListener {

    public DrawerLayout drawer;
    NavigationView navigationView;
    ImageView imgDrawer, liveTV_Img, searchImg;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    ArrayList<CategoryModel> categoryarrayList = new ArrayList<>();

    //Side Bar Data
    LinearLayout subRajyaLayout, subUttarpradeshLy;
    LinearLayout nav_home, nav_Desh, nav_Rajya, nav_debateWith, nav_up, nav_duniya, nav_manoranjan,
            nav_khel, nav_corona, nav_video, nav_helth;
    TextView versionTT;
    TextView orPadhe, biharTT, delhiTT, gujratTT;
    TextView orPadhe2, lkoTT, meerathTT, allahbadTT;
    private ViewPagerAdapter adapter;
    //Side Bar Data

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawer = findViewById(R.id.drawer_layout);
        imgDrawer = findViewById(R.id.imgDrawer);
        liveTV_Img = findViewById(R.id.liveTV_Img);
        searchImg = findViewById(R.id.searchImg);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        tabLayout = findViewById(R.id.tabs_layout);
        viewPager = findViewById(R.id.viewpager_);
        tabLayout.setupWithViewPager(viewPager);
        getCategory();
        imgDrawer.setOnClickListener(v -> drawer.openDrawer(GravityCompat.START));

        liveTV_Img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LiveTV.class));
            }

        });


        //Test

        View headerView = navigationView.getHeaderView(0);
        versionTT = headerView.findViewById(R.id.versionTT);
        nav_home = headerView.findViewById(R.id.nav_home);
        nav_Desh = headerView.findViewById(R.id.nav_Desh);
        nav_Rajya = headerView.findViewById(R.id.nav_Rajya);
        nav_debateWith = headerView.findViewById(R.id.nav_debateWith);
        nav_up = headerView.findViewById(R.id.nav_up);
        nav_duniya = headerView.findViewById(R.id.nav_duniya);
        nav_manoranjan = headerView.findViewById(R.id.nav_manoranjan);
        nav_khel = headerView.findViewById(R.id.nav_khel);
        nav_corona = headerView.findViewById(R.id.nav_corona);
        nav_video = headerView.findViewById(R.id.nav_video);
        nav_helth = headerView.findViewById(R.id.nav_helth);

        subRajyaLayout = headerView.findViewById(R.id.subRajyaLayout);
        subUttarpradeshLy = headerView.findViewById(R.id.subUttarpradeshLy);

        orPadhe = headerView.findViewById(R.id.orPadhe);
        biharTT = headerView.findViewById(R.id.biharTT);
        delhiTT = headerView.findViewById(R.id.delhiTT);
        gujratTT = headerView.findViewById(R.id.gujratTT);

        orPadhe2 = headerView.findViewById(R.id.orPadhe2);
        lkoTT = headerView.findViewById(R.id.lkoTT);
        meerathTT = headerView.findViewById(R.id.meerathTT);
        allahbadTT = headerView.findViewById(R.id.allahbadTT);


        getAppVersion();

        searchImg.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SearchNewsList.class);
            startActivity(intent);
        });

        nav_home.setOnClickListener(v -> drawer.closeDrawer(GravityCompat.START));

        orPadhe.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CategoryNews.class);
            intent.putExtra(AppConstant.CAT_ID, "22");
            startActivity(intent);
            drawer.closeDrawer(GravityCompat.START);
        });
        orPadhe2.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CategoryNews.class);
            intent.putExtra(AppConstant.CAT_ID, "25");
            startActivity(intent);
            drawer.closeDrawer(GravityCompat.START);
        });


        nav_Desh.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SubCategoryActivity.class);
            intent.putExtra(AppConstant.CAT_ID, "21");
            intent.putExtra(AppConstant.TYPE, AppConstant.CATEGORY);
            startActivity(intent);
            drawer.closeDrawer(GravityCompat.START);
        });

        nav_debateWith.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SubCategoryActivity.class);
            intent.putExtra(AppConstant.CAT_ID, "23");
            intent.putExtra(AppConstant.TYPE, AppConstant.CATEGORY);
            startActivity(intent);
            drawer.closeDrawer(GravityCompat.START);
        });

        nav_duniya.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SubCategoryActivity.class);
            intent.putExtra(AppConstant.CAT_ID, "26");
            intent.putExtra(AppConstant.TYPE, AppConstant.CATEGORY);
            startActivity(intent);
            drawer.closeDrawer(GravityCompat.START);
        });
        nav_manoranjan.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SubCategoryActivity.class);
            intent.putExtra(AppConstant.CAT_ID, "27");
            intent.putExtra(AppConstant.TYPE, AppConstant.CATEGORY);
            startActivity(intent);
            drawer.closeDrawer(GravityCompat.START);
        });
        nav_khel.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SubCategoryActivity.class);
            intent.putExtra(AppConstant.CAT_ID, "28");
            intent.putExtra(AppConstant.TYPE, AppConstant.CATEGORY);
            startActivity(intent);
            drawer.closeDrawer(GravityCompat.START);
        });
        nav_corona.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SubCategoryActivity.class);
            intent.putExtra(AppConstant.CAT_ID, "33");
            intent.putExtra(AppConstant.TYPE, AppConstant.CATEGORY);
            startActivity(intent);
            drawer.closeDrawer(GravityCompat.START);
        });
        nav_video.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SubCategoryActivity.class);
            intent.putExtra(AppConstant.CAT_ID, "34");
            intent.putExtra(AppConstant.TYPE, AppConstant.CATEGORY);
            startActivity(intent);
            drawer.closeDrawer(GravityCompat.START);
        });
        nav_helth.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SubCategoryActivity.class);
            intent.putExtra(AppConstant.CAT_ID, "37");
            intent.putExtra(AppConstant.TYPE, AppConstant.CATEGORY);
            startActivity(intent);
            drawer.closeDrawer(GravityCompat.START);
        });


        biharTT.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SubCategoryActivity.class);
            intent.putExtra(AppConstant.CAT_ID, "68");
            intent.putExtra(AppConstant.TYPE, AppConstant.SUB_CATEGORY);
            startActivity(intent);
            drawer.closeDrawer(GravityCompat.START);
        });
        delhiTT.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SubCategoryActivity.class);
            intent.putExtra(AppConstant.CAT_ID, "53");
            intent.putExtra(AppConstant.TYPE, AppConstant.SUB_CATEGORY);
            startActivity(intent);
            drawer.closeDrawer(GravityCompat.START);
        });
        gujratTT.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SubCategoryActivity.class);
            intent.putExtra(AppConstant.CAT_ID, "57");
            intent.putExtra(AppConstant.TYPE, AppConstant.SUB_CATEGORY);
            startActivity(intent);
            drawer.closeDrawer(GravityCompat.START);
        });

        lkoTT.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SubCategoryActivity.class);
            intent.putExtra(AppConstant.CAT_ID, "60");
            intent.putExtra(AppConstant.TYPE, AppConstant.SUB_CATEGORY);
            startActivity(intent);
            drawer.closeDrawer(GravityCompat.START);
        });
        meerathTT.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SubCategoryActivity.class);
            intent.putExtra(AppConstant.CAT_ID, "61");
            intent.putExtra(AppConstant.TYPE, AppConstant.SUB_CATEGORY);
            startActivity(intent);
            drawer.closeDrawer(GravityCompat.START);
        });
        allahbadTT.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SubCategoryActivity.class);
            intent.putExtra(AppConstant.CAT_ID, "62");
            intent.putExtra(AppConstant.TYPE, AppConstant.SUB_CATEGORY);
            startActivity(intent);
            drawer.closeDrawer(GravityCompat.START);
        });

        nav_Rajya.setOnClickListener(v -> {
            if (subRajyaLayout.getVisibility() == View.VISIBLE) {
                subRajyaLayout.setVisibility(View.GONE);
            } else {
                subRajyaLayout.setVisibility(View.VISIBLE);
            }
        });

        nav_up.setOnClickListener(v -> {
            if (subUttarpradeshLy.getVisibility() == View.VISIBLE) {
                subUttarpradeshLy.setVisibility(View.GONE);
            } else {
                subUttarpradeshLy.setVisibility(View.VISIBLE);
            }
        });

        loadAds();
    }

    private void loadAds() {
        AdsManager.initAds(findViewById(R.id.ll_ad));
    }


    private void getCategory() {
        String categoryJson = AppPreferences.getCategory(this);
        if(!TextUtils.isEmpty(categoryJson)){
            List<CategoryModel> response = AppApplication.getInstance().getGson()
                    .fromJson(categoryJson, new TypeToken<List<CategoryModel>>() {
                    }.getType());
            if(response != null) {
                setupViewPager(response, null);
            }else {
                loadCategoryFromServer();
            }
        }else {
            loadCategoryFromServer();
        }
    }

    private void loadCategoryFromServer() {
        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        NetworkManager.getCategory(this, new AppCallback.Callback<List<CategoryModel>>() {
            @Override
            public void onSuccess(List<CategoryModel> response) {
                setupViewPager(response, progressDialog);
            }

            @Override
            public void onFailure(Exception e) {
                Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    private void setupViewPager(List<CategoryModel> mList, ProgressDialog progressDialog) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), mList.size());
        Bundle bundle;
        CategoryModel categoryModel = new CategoryModel();
        categoryModel.setId("-1");
        categoryModel.setCat_id("-1");
        categoryModel.setCatName("होम");
        mList.add(0, categoryModel);

        for (CategoryModel child : mList) {
            Fragment fragment;
            if (child.getCat_id().equalsIgnoreCase("-1")) {
                fragment = new HomeFragment();
            } else {
                fragment = new TabFragment();
            }
            bundle = new Bundle();
            bundle.putString(AppConstant.CAT_ID, child.getId());
            bundle.putString(AppConstant.TITLE, child.getCatName());
            fragment.setArguments(bundle);
            adapter.addFrag(fragment,child.getId(), child.getCatName());
            tabLayout.addTab(tabLayout.newTab().setText(child.getCatName()));
        }
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(mList.size());
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.setCurrentItem(0);
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        displaySelectedScreen(item.getItemId());
        return true;
    }

    public void getAppVersion() {
        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = pInfo.versionName;
            versionTT.setText("Version : " + version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onViewMoreClicked(View view, int pos, String catId) {
        if(adapter != null) {
            int index = adapter.getCatPosition(catId);
            if (index > 0 && index < adapter.getCount()) {
                viewPager.setCurrentItem(index);
            }
        }
    }
}