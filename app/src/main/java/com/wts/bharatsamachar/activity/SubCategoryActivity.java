package com.wts.bharatsamachar.activity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.wts.bharatsamachar.LiveTV;
import com.wts.bharatsamachar.MainActivity;
import com.wts.bharatsamachar.R;
import com.wts.bharatsamachar.SearchNewsList;
import com.wts.bharatsamachar.fragment.TabFragment;


public class SubCategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frag_holder);
        initUi();
        initFragment();
    }

    private void initUi() {
        (findViewById(R.id.backpress)).setOnClickListener(v -> onBackPressed());

        (findViewById(R.id.liveTV_Img)).setOnClickListener(v -> startActivity(new Intent(SubCategoryActivity.this, LiveTV.class)));

        (findViewById(R.id.searchImg)).setOnClickListener(v -> {
            Intent intent = new Intent(SubCategoryActivity.this, SearchNewsList.class);
            startActivity(intent);
        });
        (findViewById(R.id.mainLogo_Img)).setOnClickListener(v -> {
            Intent intent = new Intent(SubCategoryActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }

    private void initFragment() {
        final Bundle bundle = getIntent().getExtras();
        if (bundle != null) {

            Runnable runnable = new Runnable() {
                public void run() {
                    Fragment fragment = new TabFragment();
                    fragment.setArguments(bundle);
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.add(R.id.content, fragment);
                    transaction.commitAllowingStateLoss();
                }
            };
            new Handler().post(runnable);
//            setUpToolBar();
        } else {
            finish();
        }
    }

//    private void setUpToolBar() {
//        ActionBar actionBar = getSupportActionBar();
//        if (actionBar != null) {
//            actionBar.setDisplayHomeAsUpEnabled(true);
//            if (!TextUtils.isEmpty(title)) {
//                actionBar.setTitle(title);
//            }
//        }
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if( id == android.R.id.home ){
//            onBackPressed();
//            return true ;
//        }
//        return super.onOptionsItemSelected(item);
//    }
}
