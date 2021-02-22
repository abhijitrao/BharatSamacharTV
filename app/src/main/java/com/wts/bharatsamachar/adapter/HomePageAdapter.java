package com.wts.bharatsamachar.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.wts.bharatsamachar.DetailPage;
import com.wts.bharatsamachar.R;
import com.wts.bharatsamachar.beans.entity.BigNewsEntity;
import com.wts.bharatsamachar.beans.entity.CategoriesWiseNewsEntity;
import com.wts.bharatsamachar.utils.AppCallback;
import com.wts.bharatsamachar.utils.SupportUtil;

import java.util.List;

import static com.wts.bharatsamachar.retrofit.RetrofitClient.IMAGE_URL;


public class HomePageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final AppCallback.OnHomeClickListener callback;
    private final Context context;
    private int mItemType;
    private List<CategoriesWiseNewsEntity> mList;

    public HomePageAdapter(Context context, List<CategoriesWiseNewsEntity> mList, AppCallback.OnHomeClickListener callback) {
        this.context = context;
        this.mList = mList;
        this.callback = callback;
    }

    public interface ItemType {
        int ITEM_TYPE_COMMON = 1;
        int ITEM_TYPE_VIDEO = 2;
        int ITEM_TYPE_WITHOUT_HEADER = 3;
    }

    @Override
    public int getItemViewType(int position) {
        return mList == null ? ItemType.ITEM_TYPE_COMMON : convertInt(mList.get(position).getCategory_type());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == HomePageAdapter.ItemType.ITEM_TYPE_VIDEO) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.slot_home_page_video, viewGroup, false);
            return new VideoViewHolder(view);
        } if (i == HomePageAdapter.ItemType.ITEM_TYPE_WITHOUT_HEADER) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.slot_home_page_without_header, viewGroup, false);
            return new CommonViewHolder(view);
        } else {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.slot_home_page, viewGroup, false);
            return new CommonViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        CategoriesWiseNewsEntity item = mList.get(position);
        if (holder instanceof CommonViewHolder) {
            CommonViewHolder viewHolder = (CommonViewHolder) holder;
            viewHolder.tvCategory.setText(item.getCategory_name());
            if(item.getNews() != null && item.getNews().getBigNews() != null) {
                BigNewsEntity bigNews = item.getNews().getBigNews();
                Picasso.get()
                        .load(IMAGE_URL + bigNews.getNews_cover_photo())
//                        .networkPolicy(NetworkPolicy.NO_CACHE, NetworkPolicy.NO_STORE)
                        .into(viewHolder.ivBigNews);
                viewHolder.topCategoryTT.setText(bigNews.getPlace_area());
                viewHolder.topHeadingTT.setText(bigNews.getNews_heading());
                viewHolder.topPostDateTime.setText(SupportUtil.convertDate(bigNews.getPost_date()));
                viewHolder.cvBigNews.setVisibility(View.VISIBLE);
            }else {
                viewHolder.cvBigNews.setVisibility(View.GONE);
            }
            if(item.getNews() != null && item.getNews().getListNews() != null && item.getNews().getListNews().size() > 0) {
                viewHolder.recyclerView.setAdapter(new HomePageSubAdapter(context, HomePageSubAdapter.ItemType.ITEM_TYPE_COMMON, item.getNews().getListNews(), callback));
                viewHolder.recyclerView.setVisibility(View.VISIBLE);
            }else {
                viewHolder.recyclerView.setVisibility(View.GONE);
            }

        }else if (holder instanceof VideoViewHolder) {
            VideoViewHolder viewHolder = (VideoViewHolder) holder;
            viewHolder.tvCategory.setText(item.getCategory_name());

            if(item.getNews() != null && item.getNews().getListNews() != null && item.getNews().getListNews().size() > 0) {
                viewHolder.recyclerView.setAdapter(new HomePageSubAdapter(context, HomePageSubAdapter.ItemType.ITEM_TYPE_VIDEO, item.getNews().getListNews(), callback));
                viewHolder.recyclerView.setVisibility(View.VISIBLE);
            }else {
                viewHolder.recyclerView.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    private int convertInt(String category_type) {
        try {
            return Integer.parseInt(category_type);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return ItemType.ITEM_TYPE_COMMON;
        }
    }


    public class CommonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final TextView tvCategory;
        private final TextView tvViewAll, topCategoryTT, topPostDateTime, topHeadingTT;
        private final RecyclerView recyclerView;
        private final ImageView ivBigNews;
        private final View cvBigNews;

        CommonViewHolder(View view) {
            super(view);
            tvCategory = view.findViewById(R.id.tv_category);
            tvViewAll = view.findViewById(R.id.tv_view_all);
            cvBigNews = view.findViewById(R.id.cv_big_news);
            ivBigNews = view.findViewById(R.id.iv_big_news);
            topCategoryTT = view.findViewById(R.id.topCategoryTT);
            topPostDateTime = view.findViewById(R.id.topPostTT);
            topHeadingTT = view.findViewById(R.id.topHeadingTT);
            recyclerView = view.findViewById(R.id.recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
            cvBigNews.setOnClickListener(this);
            tvViewAll.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mList.size() > getAdapterPosition() && getAdapterPosition() >= 0) {
                if (v.getId() == R.id.tv_view_all) {
                    callback.onViewMoreClicked(v, getAdapterPosition(), mList.get(getAdapterPosition()).getId());
                } else {
                    String id = mList.get(getAdapterPosition()).getId();
                    callback.onItemClicked(v, id, null);
                }
            }
        }
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final TextView tvCategory;
        private final TextView tvViewAll;
        private final RecyclerView recyclerView;

        VideoViewHolder(View view) {
            super(view);
            tvCategory = view.findViewById(R.id.tv_category);
            tvViewAll = view.findViewById(R.id.tv_view_all);
            recyclerView = view.findViewById(R.id.recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));
            tvViewAll.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mList.size() > getAdapterPosition() && getAdapterPosition() >= 0) {
                if (v.getId() == R.id.tv_view_all) {
                    callback.onViewMoreClicked(v, getAdapterPosition(), mList.get(getAdapterPosition()).getId());
                }
            }
        }
    }
}