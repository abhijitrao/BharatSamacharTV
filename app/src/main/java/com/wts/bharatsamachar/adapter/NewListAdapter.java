package com.wts.bharatsamachar.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.format.DateUtils;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static com.wts.bharatsamachar.retrofit.RetrofitClient.IMAGE_URL;

public class NewListAdapter extends RecyclerView.Adapter<NewListAdapter.ViewHolder> {

    Context context;
    ArrayList<NewListModel> arrayList;

    public NewListAdapter(Context context, ArrayList<NewListModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.first_item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.mainLayoutClick.setAnimation(AnimationUtils.loadAnimation(context,R.anim.fade_anim));

        Picasso.get()
                .load(IMAGE_URL + arrayList.get(position).getNewsPhoto())
                .into(holder.newsImage);
        holder.newsHeadingTT.setText(arrayList.get(position).getNewsHeading());
        holder.cateNameTT.setText(arrayList.get(position).getCategoryName());
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
        TextView cateNameTT;
        TextView newsHeadingTT,postDateTT;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            newsImage = itemView.findViewById(R.id.newsImage);
            cateNameTT = itemView.findViewById(R.id.cateNameTT);
            newsHeadingTT = itemView.findViewById(R.id.newsHeadingTT);
            postDateTT = itemView.findViewById(R.id.postDateTT);
            mainLayoutClick = itemView.findViewById(R.id.mainLayoutClick);
        }
    }

//    public static String hoursAgo(String datetime) throws ParseException {
//        Calendar date = Calendar.getInstance();
//        date.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).parse(datetime)); // Parse into Date object
//        Calendar now = Calendar.getInstance(); // Get time now
//        long differenceInMillis = now.getTimeInMillis() - date.getTimeInMillis();
//        long differenceInHours = (differenceInMillis) / 1000L / 60L / 60L; // Divide by millis/sec, secs/min, mins/hr
//        return String.valueOf(differenceInHours);
//    }
}
