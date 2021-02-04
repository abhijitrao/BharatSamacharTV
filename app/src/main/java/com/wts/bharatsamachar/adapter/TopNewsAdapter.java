package com.wts.bharatsamachar.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.wts.bharatsamachar.DetailPage;
import com.wts.bharatsamachar.R;
import com.wts.bharatsamachar.model.NewListModel;
import com.wts.bharatsamachar.utils.SupportUtil;

import java.util.ArrayList;

import static com.wts.bharatsamachar.retrofit.RetrofitClient.IMAGE_URL;

public class TopNewsAdapter extends RecyclerView.Adapter<TopNewsAdapter.ViewHolder>{

    Context context;
    ArrayList<NewListModel> arrayList;

    public TopNewsAdapter(Context context, ArrayList<NewListModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.top_news_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.mainLayoutClick.setAnimation(AnimationUtils.loadAnimation(context,R.anim.fade_anim));

        Picasso.get()
                .load(IMAGE_URL + arrayList.get(position).getNewsPhoto())
                .into(holder.newsImage);
        holder.newsHeadingTT.setText(arrayList.get(position).getNewsHeading());
        holder.topCategoryTT.setText(arrayList.get(position).getCategoryName());
        String time = arrayList.get(position).getNewTime();
//        String ago = null;
        try {
//            ago = hoursAgo(time);
//            if (Integer.parseInt(ago) >12){
//                long vl = Integer.parseInt(ago)*60*60;
//                int day = (int) TimeUnit.SECONDS.toDays(vl);
//                ago = String.valueOf(day);
//                holder.postDateTT.setText(ago+" DAYS AGO");
//            }else {
//                holder.postDateTT.setText(ago+" HOUR AGO");
//            }
            holder.postDateTT.setText(SupportUtil.convertDate(time));

            holder.mainLayoutClick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String id = arrayList.get(position).getNewId();
                    String img = arrayList.get(position).getNewsPhoto();
                    Intent intent = new Intent(context, DetailPage.class);
                    intent.putExtra("id",id);
                    intent.putExtra("img",img);
                    context.startActivity(intent);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

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
        }
    }
}
