package com.wts.bharatsamachar.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.wts.bharatsamachar.R;
import com.wts.bharatsamachar.beans.entity.CategoriesWiseNewsEntity;
import com.wts.bharatsamachar.beans.entity.ListNewsEntity;
import com.wts.bharatsamachar.utils.AppCallback;
import com.wts.bharatsamachar.utils.SupportUtil;

import java.util.List;

import static com.wts.bharatsamachar.retrofit.RetrofitClient.IMAGE_URL;

public class HomePageSubAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final AppCallback.OnHomeClickListener callback;
    private final Context context;
    private final int mItemType;
    private List<ListNewsEntity> mList;

    public interface ItemType {
        int ITEM_TYPE_COMMON = 1;
        int ITEM_TYPE_VIDEO = 2;
    }

    @Override
    public int getItemViewType(int position) {
        return mItemType;
    }


    public HomePageSubAdapter(Context context, int mItemType, List<ListNewsEntity> mList, AppCallback.OnHomeClickListener callback) {
        this.context = context;
        this.mItemType = mItemType;
        this.mList = mList;
        this.callback = callback;
    }

    @Override
    @NonNull
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == HomePageAdapter.ItemType.ITEM_TYPE_VIDEO) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.slot_home_page_video_child, viewGroup, false);
            return new ViewHolder(view);
        } else {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.top_news_item, viewGroup, false);
            return new ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder myViewHolder, int position) {
        ViewHolder holder = (ViewHolder) myViewHolder;
//        holder.mainLayoutClick.setAnimation(AnimationUtils.loadAnimation(context,R.anim.fade_anim));

        Picasso.get()
                .load(IMAGE_URL + mList.get(position).getNews_cover_photo())
//                .networkPolicy(NetworkPolicy.NO_CACHE, NetworkPolicy.NO_STORE)
                .into(holder.newsImage);
        holder.newsHeadingTT.setText(mList.get(position).getNews_heading());
        holder.topCategoryTT.setText(mList.get(position).getPlace_area());
        String time = mList.get(position).getPost_date();
        holder.postDateTT.setText(SupportUtil.convertDate(time));
    }


    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    private class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CardView mainLayoutClick;
        ImageView newsImage;
        TextView newsHeadingTT,postDateTT,topCategoryTT;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            newsImage = itemView.findViewById(R.id.newsImage);
            topCategoryTT = itemView.findViewById(R.id.topCategoryTT);
            newsHeadingTT = itemView.findViewById(R.id.newsHeadingTT);
            postDateTT = itemView.findViewById(R.id.postDateTT);
            mainLayoutClick = itemView.findViewById(R.id.mainLayoutClick);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(mList.size() > getAdapterPosition() && getAdapterPosition() >= 0) {
                String id = mList.get(getAdapterPosition()).getId();
                String img = mList.get(getAdapterPosition()).getNews_cover_photo();
                callback.onItemClicked(v, id, img);
            }
        }
    }

}
