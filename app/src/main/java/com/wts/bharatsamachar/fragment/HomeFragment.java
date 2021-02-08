package com.wts.bharatsamachar.fragment;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.wts.bharatsamachar.DetailPage;
import com.wts.bharatsamachar.R;
import com.wts.bharatsamachar.adapter.HomePageAdapter;
import com.wts.bharatsamachar.beans.entity.BreakingNewsEntity;
import com.wts.bharatsamachar.beans.entity.CategoriesWiseNewsEntity;
import com.wts.bharatsamachar.beans.entity.DataEntity;
import com.wts.bharatsamachar.retrofit.NetworkManager;
import com.wts.bharatsamachar.utils.AppCallback;
import com.wts.bharatsamachar.utils.SupportUtil;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    private HomePageAdapter adapter;
    private List<CategoriesWiseNewsEntity> mList = new ArrayList<>();
    private Activity activity;
    private View viewNoData;
    private String categoryId, toCheck;
    private TextView flashNewsTT;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        activity = getActivity();
        initView(view);
        getIntentData();
        return view;
    }

    private void getIntentData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            categoryId = bundle.getString("someInt", "");
            toCheck = bundle.getString("tocheck", "");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        NetworkManager.getHomeNews(new AppCallback.Callback<DataEntity>() {
            @Override
            public void onSuccess(DataEntity response) {
                setBreakingNews(response.getBreakingNewsEntity());
                loadList(response.getCategoriesWiseNewsEntity());
            }

            @Override
            public void onFailure(Exception e) {
                SupportUtil.showNoData(viewNoData, View.VISIBLE);
            }
        });
    }

    private void setBreakingNews(List<BreakingNewsEntity> breakingNewsEntity) {
        if(breakingNewsEntity != null && breakingNewsEntity.size() > 0) {
            flashNewsTT.setText(breakingNewsEntity.get(0).getBreaking_news());
        }
    }

    private void initView(View view) {
        viewNoData = view.findViewById(R.id.ll_no_data);
        RecyclerView rvList = view.findViewById(R.id.recycler_view);
        rvList.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        adapter = new HomePageAdapter(activity, mList, new AppCallback.OnHomeClickListener() {
            @Override
            public void onItemClicked(View view, String id, String image) {
                Intent intent = new Intent(getActivity(), DetailPage.class);
                intent.putExtra("id", id);
                intent.putExtra("img", image);
                startActivity(intent);
            }
        });
        rvList.setAdapter(adapter);
        flashNewsTT = view.findViewById(R.id.flashNewsTT);

        flashNewsTT.setSelected(true);
    }

    private void loadList(List<CategoriesWiseNewsEntity> list) {
        SupportUtil.showNoData(viewNoData, View.GONE);
        mList.clear();
        if (list != null && list.size() > 0) {
            mList.addAll(list);
        }
        adapter.notifyDataSetChanged();
        if(mList.size() <= 0){
            SupportUtil.showNoData(viewNoData, View.VISIBLE);
        }
    }

}