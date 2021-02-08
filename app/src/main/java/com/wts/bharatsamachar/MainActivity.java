package com.wts.bharatsamachar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
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
import com.google.gson.JsonObject;
import com.wts.bharatsamachar.adapter.TabAdapter;
import com.wts.bharatsamachar.adapter.ViewPagerAdapter;
import com.wts.bharatsamachar.fragment.DynamicFragment;
import com.wts.bharatsamachar.fragment.HomeFragment;
import com.wts.bharatsamachar.fragment.TabFragment;
import com.wts.bharatsamachar.model.CategoryModel;
import com.wts.bharatsamachar.retrofit.NetworkManager;
import com.wts.bharatsamachar.retrofit.RetrofitClient;
import com.wts.bharatsamachar.utils.AppCallback;
import com.wts.bharatsamachar.utils.ads.AdsAppCompactActivity;
import com.wts.bharatsamachar.utils.ads.AdsManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AdsAppCompactActivity implements NavigationView.OnNavigationItemSelectedListener,
        AdapterView.OnItemSelectedListener, AppCallback.OnViewMoreListener {

    public DrawerLayout drawer;
    NavigationView navigationView;
    ImageView imgDrawer,liveTV_Img,searchImg;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    ArrayList<CategoryModel> categoryarrayList = new ArrayList<>();

    //Side Bar Data
    LinearLayout subRajyaLayout,subUttarpradeshLy;
    LinearLayout nav_home,nav_Desh,nav_Rajya,nav_debateWith,nav_up,nav_duniya,nav_manoranjan,
            nav_khel,nav_corona,nav_video,nav_helth;
    TextView versionTT;
    TextView orPadhe,biharTT,delhiTT,gujratTT;
    TextView orPadhe2,lkoTT,meerathTT,allahbadTT;
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
                startActivity(new Intent(MainActivity.this,LiveTV.class));
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
            Intent intent = new Intent(MainActivity.this,SearchNewsList.class);
            startActivity(intent);
        });

        nav_home.setOnClickListener(v -> drawer.closeDrawer(GravityCompat.START));

        orPadhe.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this,CategoryNews.class);
            intent.putExtra("type","rajya");
            startActivity(intent);
            drawer.closeDrawer(GravityCompat.START);
        });
        orPadhe2.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this,CategoryNews.class);
            intent.putExtra("type","up");
            startActivity(intent);
            drawer.closeDrawer(GravityCompat.START);
        });


        nav_Desh.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this,CategoryShow.class);
            intent.putExtra("catId","21");
            intent.putExtra("type","category");
            startActivity(intent);
            drawer.closeDrawer(GravityCompat.START);
        });

        nav_debateWith.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this,CategoryShow.class);
            intent.putExtra("catId","23");
            intent.putExtra("type","category");
            startActivity(intent);
            drawer.closeDrawer(GravityCompat.START);
        });

        nav_duniya.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this,CategoryShow.class);
            intent.putExtra("catId","26");
            intent.putExtra("type","category");
            startActivity(intent);
            drawer.closeDrawer(GravityCompat.START);
        });
        nav_manoranjan.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this,CategoryShow.class);
            intent.putExtra("catId","27");
            intent.putExtra("type","category");
            startActivity(intent);
            drawer.closeDrawer(GravityCompat.START);
        });
        nav_khel.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this,CategoryShow.class);
            intent.putExtra("catId","28");
            intent.putExtra("type","category");
            startActivity(intent);
            drawer.closeDrawer(GravityCompat.START);
        });
        nav_corona.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this,CategoryShow.class);
            intent.putExtra("catId","33");
            intent.putExtra("type","category");
            startActivity(intent);
            drawer.closeDrawer(GravityCompat.START);
        });
        nav_video.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this,CategoryShow.class);
            intent.putExtra("catId","34");
            intent.putExtra("type","category");
            startActivity(intent);
            drawer.closeDrawer(GravityCompat.START);
        });
        nav_helth.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this,CategoryShow.class);
            intent.putExtra("catId","37");
            intent.putExtra("type","category");
            startActivity(intent);
            drawer.closeDrawer(GravityCompat.START);
        });


        biharTT.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this,CategoryShow.class);
            intent.putExtra("catId","68");
            intent.putExtra("type","subcategory");
            startActivity(intent);
            drawer.closeDrawer(GravityCompat.START);
        });
        delhiTT.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this,CategoryShow.class);
            intent.putExtra("catId","53");
            intent.putExtra("type","subcategory");
            startActivity(intent);
            drawer.closeDrawer(GravityCompat.START);
        });
        gujratTT.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this,CategoryShow.class);
            intent.putExtra("catId","57");
            intent.putExtra("type","subcategory");
            startActivity(intent);
            drawer.closeDrawer(GravityCompat.START);
        });

        lkoTT.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this,CategoryShow.class);
            intent.putExtra("catId","60");
            intent.putExtra("type","subcategory");
            startActivity(intent);
            drawer.closeDrawer(GravityCompat.START);
        });
        meerathTT.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this,CategoryShow.class);
            intent.putExtra("catId","61");
            intent.putExtra("type","subcategory");
            startActivity(intent);
            drawer.closeDrawer(GravityCompat.START);
        });
        allahbadTT.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this,CategoryShow.class);
            intent.putExtra("catId","62");
            intent.putExtra("type","subcategory");
            startActivity(intent);
            drawer.closeDrawer(GravityCompat.START);
        });

        nav_Rajya.setOnClickListener(v -> {
            if (subRajyaLayout.getVisibility() == View.VISIBLE){
                subRajyaLayout.setVisibility(View.GONE);
            }else {
                subRajyaLayout.setVisibility(View.VISIBLE);
            }
        });

        nav_up.setOnClickListener(v -> {
            if (subUttarpradeshLy.getVisibility() == View.VISIBLE){
                subUttarpradeshLy.setVisibility(View.GONE);
            }else {
                subUttarpradeshLy.setVisibility(View.VISIBLE);
            }
        });

        loadAds();
    }

    private void loadAds() {
        AdsManager.initAds(findViewById(R.id.ll_ad));
    }


    private void getCategory() {
        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        NetworkManager.getCategory(new AppCallback.Callback<List<CategoryModel>>() {
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
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager() , mList.size());
        Bundle bundle ;
        CategoryModel categoryModel = new CategoryModel();
        categoryModel.setId("-1");
        categoryModel.setCat_id("-1");
        categoryModel.setCatName("होम");
        mList.add(0, categoryModel);

        for (CategoryModel child : mList ) {
            Fragment fragment;
            if(child.getCat_id().equalsIgnoreCase("-1")) {
                fragment = new HomeFragment();
            }else {
                fragment = new TabFragment();
            }
            bundle = new Bundle();
            bundle.putString("someInt", child.getId());
            bundle.putString("tocheck", "main");
            fragment.setArguments(bundle);
            adapter.addFrag(fragment , child.getCatName());
            tabLayout.addTab(tabLayout.newTab().setText(child.getCatName()));
        }
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(1);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.setCurrentItem(0);
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

//        for (int i = 0; i <mList.size(); i++) {
//            CategoryModel categoryModel = new CategoryModel();
//            JSONObject jsonObject = jsonArray.getJSONObject(i);
//            String id = jsonObject.getString("id");
//            String category_name = jsonObject.getString("category_name");
//            categoryModel.setId(id);
//            categoryModel.setCatName(category_name);
//            categoryarrayList.add(categoryModel);
//            if (i == jsonArray.length() -1){
//                id = "-1";
//                category_name = "होम";
//                categoryModel.setId(id);
//                categoryModel.setCatName(category_name);
//                categoryarrayList.add(0,categoryModel);
//            }
//            tabLayout.addTab(tabLayout.newTab().setText(category_name));
//        }
//        TabAdapter adapter = new TabAdapter
//                (getSupportFragmentManager(), tabLayout.getTabCount(), categoryarrayList,"main");
//        viewPager.setAdapter(adapter);
//        viewPager.setOffscreenPageLimit(1);
//        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        displaySelectedScreen(item.getItemId());
        return true;
    }

    private void displaySelectedScreen(int itemId) {
        switch (itemId) {

            case R.id.nav_home:
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + itemId);
        }
        drawer.closeDrawer(GravityCompat.START);
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
    public void onViewMoreClicked(View view, int pos) {
        viewPager.setCurrentItem(pos);
    }
}