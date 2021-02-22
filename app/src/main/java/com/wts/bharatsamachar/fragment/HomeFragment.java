package com.wts.bharatsamachar.fragment;


import android.app.Activity;
import android.content.Context;
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
import com.wts.bharatsamachar.beans.entity.HomeDataEntity;
import com.wts.bharatsamachar.beans.entity.NewsEntity;
import com.wts.bharatsamachar.retrofit.NetworkManager;
import com.wts.bharatsamachar.utils.AppCallback;
import com.wts.bharatsamachar.utils.AppConstant;
import com.wts.bharatsamachar.utils.SupportUtil;
import com.wts.bharatsamachar.utils.TimeValidator;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    private HomePageAdapter adapter;
    private List<CategoriesWiseNewsEntity> mList = new ArrayList<>();
    private Activity activity;
    private View viewNoData;
    private String categoryId, toCheck;
    private TextView flashNewsTT;
    private AppCallback.OnViewMoreListener mListener;

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
            categoryId = bundle.getString(AppConstant.CAT_ID, "");
            toCheck = bundle.getString("tocheck", "");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mList.size() == 0 || TimeValidator.newInstance().isValidTimeDiff()) {
            loadTopNews();
        }
    }

    private void loadTopNews() {
        NetworkManager.getTopNews(new AppCallback.Callback<HomeDataEntity>() {
            @Override
            public void onSuccess(HomeDataEntity response) {
                setBreakingNews(response.getBreakingNewsEntity());
                loadList(response.getTopNews());
                loadCategoryWiseNews();
            }

            @Override
            public void onFailure(Exception e) {
                SupportUtil.showNoData(viewNoData, View.VISIBLE);
            }
        });
    }

    private void loadCategoryWiseNews() {
        NetworkManager.getHomeNews(new AppCallback.Callback<HomeDataEntity>() {
            @Override
            public void onSuccess(HomeDataEntity response) {
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

            @Override
            public void onViewMoreClicked(View view, int position, String catId) {
                if (mListener != null) {
                    mListener.onViewMoreClicked(view, position, catId);
                }
            }
        });
        rvList.setAdapter(adapter);
        flashNewsTT = view.findViewById(R.id.flashNewsTT);

        flashNewsTT.setSelected(true);
    }

    private void loadList(NewsEntity item) {
        mList.clear();
        CategoriesWiseNewsEntity mItem = new CategoriesWiseNewsEntity();
        mItem.setCategory_type("1");
        mItem.setCategory_name("Top News");
        mItem.setId("0");
        mItem.setNews(item);
        mList.add(mItem);
        adapter.notifyDataSetChanged();
    }

    private void loadList(List<CategoriesWiseNewsEntity> list) {
        if (list != null && list.size() > 0) {
            mList.addAll(list);
        }
        adapter.notifyDataSetChanged();
        if(mList.size() <= 0){
            SupportUtil.showNoData(viewNoData, View.VISIBLE);
        }else {
            SupportUtil.showNoData(viewNoData, View.GONE);
        }
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof AppCallback.OnViewMoreListener) {
            mListener = (AppCallback.OnViewMoreListener) context;
        }
    }
}