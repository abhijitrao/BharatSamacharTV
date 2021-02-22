package com.wts.bharatsamachar.fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wts.bharatsamachar.DetailPage;
import com.wts.bharatsamachar.R;
import com.wts.bharatsamachar.adapter.HomePageAdapter;
import com.wts.bharatsamachar.beans.entity.CategoriesWiseNewsEntity;
import com.wts.bharatsamachar.retrofit.NetworkManager;
import com.wts.bharatsamachar.utils.AppCallback;
import com.wts.bharatsamachar.utils.AppConstant;
import com.wts.bharatsamachar.utils.SupportUtil;
import com.wts.bharatsamachar.utils.TimeValidator;

import java.util.ArrayList;
import java.util.List;


public class TabFragment extends Fragment {

    private HomePageAdapter adapter;
    private List<CategoriesWiseNewsEntity> mList = new ArrayList<>();
    private Activity activity;
    private View viewNoData;
    private String categoryId;
    private String type;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tabs, container, false);
        activity = getActivity();
        initView(view);
        getIntentData();
        return view;
    }

    private void getIntentData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            categoryId = bundle.getString(AppConstant.CAT_ID, "");
            type = bundle.getString(AppConstant.TYPE, "");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mList.size() == 0 || TimeValidator.newInstance().isValidTimeDiff()) {
            loadData();
        }
    }

    private void loadData() {
        NetworkManager.getNewListV2(categoryId, new AppCallback.Callback<List<CategoriesWiseNewsEntity>>() {
            @Override
            public void onSuccess(List<CategoriesWiseNewsEntity> response) {
                loadList(response);
            }

            @Override
            public void onFailure(Exception e) {
                SupportUtil.showNoData(viewNoData, View.VISIBLE);
            }
        });
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

            @Override
            public void onViewMoreClicked(View view, int position, String catId) {

            }
        });
        rvList.setAdapter(adapter);
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