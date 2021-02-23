//package com.wts.bharatsamachar.adapter;
//
//import android.content.Context;
//import android.content.Intent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.animation.AnimationUtils;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.cardview.widget.CardView;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.squareup.picasso.Picasso;
//import com.wts.bharatsamachar.R;
//import com.wts.bharatsamachar.VideoPlay;
//import com.wts.bharatsamachar.model.NewListModel;
//import com.wts.bharatsamachar.utils.SupportUtil;
//import com.wts.bharatsamachar.utils.onClickInterface;
//
//import java.util.ArrayList;
//
//import static com.wts.bharatsamachar.retrofit.RetrofitClient.IMAGE_URL;
//
//public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {
//
//    Context context;
//    ArrayList<NewListModel> arrayList;
//    String type;
//    onClickInterface onClickInterface;
//
//    public VideoAdapter(Context context, ArrayList<NewListModel> arrayList,String type,onClickInterface onClickInterface) {
//        this.context = context;
//        this.arrayList = arrayList;
//        this.type = type;
//        this.onClickInterface = onClickInterface;
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_item_layout,parent,false);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//
//        String time = arrayList.get(position).getNewTime();
////        String ago = null;
////        try {
////            ago = hoursAgo(time);
////            if (Integer.parseInt(ago) > 12) {
////                long vl = Integer.parseInt(ago) * 60 * 60;
////                int day = (int) TimeUnit.SECONDS.toDays(vl);
////                ago = String.valueOf(day);
////                holder.timePosted.setText(ago + " DAYS AGO");
////            } else {
////                holder.timePosted.setText(ago + " HOUR AGO");
////            }
////        }catch (Exception e){
////            e.printStackTrace();
////        }
//        holder.timePosted.setText(SupportUtil.convertDate(time));
//
//        if (type.equalsIgnoreCase("fragment")){
//            holder.cardView.setAnimation(AnimationUtils.loadAnimation(context,R.anim.fade_anim));
//            holder.newsHeadingTT.setText(arrayList.get(position).getNewsHeading());
//            Picasso.get()
//                    .load(IMAGE_URL + arrayList.get(position).getNewsPhoto())
//                    .into(holder.newsImage);
//            holder.cardView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    String videoLink = arrayList.get(position).getNewVideoLink();
//                    String[] vl_link = videoLink.split("embed");
//                    String videoId = vl_link[1];
//                    videoId = videoId.replace("/","");
//                    Intent intent = new Intent(context, VideoPlay.class);
//                    intent.putExtra("vl",videoId);
//                    context.startActivity(intent);
//                }
//            });
//        }else {
//            holder.cardView.setAnimation(AnimationUtils.loadAnimation(context,R.anim.fade_anim));
//            holder.newsHeadingTT.setText(arrayList.get(position).getNewsHeading());
//            Picasso.get()
//                    .load(IMAGE_URL+arrayList.get(position).getNewsPhoto())
//                    .into(holder.newsImage);
//            holder.newsImage.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    String videoLink = arrayList.get(position).getNewVideoLink();
//                    String[] vl_link = videoLink.split("embed");
//                    String videoId = vl_link[1];
//                    videoId = videoId.replace("/","");
//                    onClickInterface.setClick(videoId);
//                }
//            });
//
//        }
//
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return arrayList.size();
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//        CardView cardView;
//        ImageView newsImage;
//        TextView newsHeadingTT,timePosted;
//
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            cardView = itemView.findViewById(R.id.cardView);
//            newsImage = itemView.findViewById(R.id.newsImage);
//            newsHeadingTT = itemView.findViewById(R.id.newsHeadingTT);
//            timePosted = itemView.findViewById(R.id.timePosted);
//        }
//    }
//}
