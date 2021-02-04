//package com.wts.bharatsamachar.adapter;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.Filter;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.cardview.widget.CardView;
//
//import com.squareup.picasso.Picasso;
//import com.wts.bharatsamachar.R;
//import com.wts.bharatsamachar.model.NewListModel;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//
//import static com.wts.bharatsamachar.adapter.NewListAdapter.hoursAgo;
//
//public class CustomListSearchAdapter extends ArrayAdapter {
//
//    private ArrayList<NewListModel> dataList;
//    private Context mContext;
//    private int itemLayout;
//    private ListFilter listFilter = new ListFilter();
//    private List<NewListModel> dataListAllItems;
//
//
//
//    public CustomListSearchAdapter(Context context, int resource, ArrayList<NewListModel> storeDataLst) {
//        super(context, resource, storeDataLst);
//        dataList = storeDataLst;
//        mContext = context;
//        itemLayout = resource;
//    }
//
//    @Override
//    public int getCount() {
//        return dataList.size();
//    }
//
//    @Override
//    public NewListModel getItem(int position) {
//        return dataList.get(position);
//    }
//
//    @Override
//    public View getView(int position, View view, @NonNull ViewGroup parent) {
//
//        if (view == null) {
//            view = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
//        }
//        ImageView newsImage = view.findViewById(R.id.newsImage);
//        ImageView newsShare = view.findViewById(R.id.newsShare);
//        TextView newsHeadingTT = view.findViewById(R.id.newsHeadingTT);
//        TextView postDateTT = view.findViewById(R.id.postDateTT);
//        CardView mainLayoutClick = view.findViewById(R.id.mainLayoutClick);
//        newsHeadingTT.setText(getItem(position).getCategoryName());
//        String time = getItem(position).getNewTime();
//        String ago = null;
//        try {
//            ago = hoursAgo(time);
//            if (Integer.parseInt(ago) >12){
//                long vl = Integer.parseInt(ago)*60*60;
//                int day = (int) TimeUnit.SECONDS.toDays(vl);
//                ago = String.valueOf(day);
//                postDateTT.setText(ago+" DAYS AGO");
//            }else {
//                postDateTT.setText(ago+" HOUR AGO");
//            }
//            Picasso.with(mContext).load(getItem(position).getNewsPhoto()).into(newsImage);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return view;
//    }
//
//    @NonNull
//    @Override
//    public Filter getFilter() {
//        return listFilter;
//    }
//
//    public class ListFilter extends Filter {
//        private Object lock = new Object();
//
//        @Override
//        protected FilterResults performFiltering(CharSequence prefix) {
//            FilterResults results = new FilterResults();
//            if (dataListAllItems == null) {
//                synchronized (lock) {
//                    dataListAllItems = new ArrayList<NewListModel>(dataList);
//                }
//            }
//
//            if (prefix == null || prefix.length() == 0) {
//                synchronized (lock) {
//                    results.values = dataListAllItems;
//                    results.count = dataListAllItems.size();
//                }
//            } else {
//                final String searchStrLowerCase = prefix.toString().toLowerCase();
//
//                ArrayList<NewListModel> matchValues = new ArrayList<NewListModel>();
//
//                for (NewListModel dataItem : dataListAllItems) {
//                    if (dataItem.getCategoryName().toLowerCase().startsWith(searchStrLowerCase)) {
//                        matchValues.add(dataItem);
//                    }
//                }
//
//                results.values = matchValues;
//                results.count = matchValues.size();
//            }
//
//            return results;
//        }
//
//        @Override
//        protected void publishResults(CharSequence constraint, FilterResults results) {
//            if (results.values != null) {
//                dataList = (ArrayList<NewListModel>) results.values;
//            } else {
//                dataList = null;
//            }
//            if (results.count > 0) {
//                notifyDataSetChanged();
//            } else {
//                notifyDataSetInvalidated();
//            }
//        }
//
//    }
//}