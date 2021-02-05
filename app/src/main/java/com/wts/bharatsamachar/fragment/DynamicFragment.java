package com.wts.bharatsamachar.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;
import com.wts.bharatsamachar.DetailPage;
import com.wts.bharatsamachar.R;
import com.wts.bharatsamachar.adapter.HomeAdapter;
import com.wts.bharatsamachar.adapter.NewListAdapter;
import com.wts.bharatsamachar.adapter.TopNewsAdapter;
import com.wts.bharatsamachar.adapter.VideoAdapter;
import com.wts.bharatsamachar.model.CategoryModel;
import com.wts.bharatsamachar.model.NewListModel;
import com.wts.bharatsamachar.retrofit.RetrofitClient;
import com.wts.bharatsamachar.utils.AppCallback;
import com.wts.bharatsamachar.utils.SupportUtil;
import com.wts.bharatsamachar.utils.onClickInterface;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.wts.bharatsamachar.retrofit.RetrofitClient.IMAGE_URL;


public class DynamicFragment extends Fragment {
    View view;
    LinearLayout home;
    String categoryId;
    TextView flashNewsTT,videoTitle;
    RecyclerView recyclerView;
    NestedScrollView nestedScroll;
    ArrayList<NewListModel> arrayList = new ArrayList<>();
    ArrayList<NewListModel> topNewsArray = new ArrayList<>();
    ArrayList<NewListModel> deshNewsArray = new ArrayList<>();
    ArrayList<NewListModel> rajyaNewsArray = new ArrayList<>();
    ArrayList<NewListModel> specialVideoArray = new ArrayList<>();
    ArrayList<CategoryModel> subCategoryArray = new ArrayList<>();
    ArrayList<CategoryModel> rajya_subCategoryArray = new ArrayList<>();
    String toCheck;
    RecyclerView topNewsRecycler,deshRecycler,rajyaRecycler,specialVideoRecycler;
    private TabLayout tabLayout;
    TabLayout tabs_layout_up;
    ViewPager viewpager_up;
    private ViewPager viewPager;
    onClickInterface onClickInterface;

    ImageView topnewImg,bigNewImg;
    TextView topCategoryTT,topPostTT,topHeadingTT;
    String id0,id1;
    LinearLayout topHeaderNewsLY,bigNewLY;
    NestedScrollView layout;

    TextView bigNewCategoryTT,bigNewPostTT,bigNewsHeadingTT;
    private AppCallback.OnViewMoreListener mListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_dynamic, container, false);
        categoryId = getArguments().getString("someInt", "");
        toCheck = getArguments().getString("tocheck", "");
        flashNewsTT = view.findViewById(R.id.flashNewsTT);
        recyclerView = view.findViewById(R.id.recyclerView);
        nestedScroll = view.findViewById(R.id.nestedScroll);
        home = view.findViewById(R.id.home);
        topNewsRecycler = view.findViewById(R.id.topNewsRecycler);
        specialVideoRecycler = view.findViewById(R.id.specialVideoRecycler);
        deshRecycler = view.findViewById(R.id.deshRecycler);
        rajyaRecycler = view.findViewById(R.id.rajyaRecycler);
        tabLayout = view.findViewById(R.id.tabs_layout);
        viewPager = view.findViewById(R.id.viewpager_);
        tabs_layout_up = view.findViewById(R.id.tabs_layout_up);
        viewpager_up = view.findViewById(R.id.viewpager_up);
        videoTitle = view.findViewById(R.id.videoTitle);
        topHeaderNewsLY = view.findViewById(R.id.topHeaderNewsLY);

        topnewImg = view.findViewById(R.id.topnewImg);
        topCategoryTT = view.findViewById(R.id.topCategoryTT);
        topPostTT = view.findViewById(R.id.topPostTT);
        topHeadingTT = view.findViewById(R.id.topHeadingTT);

        bigNewImg = view.findViewById(R.id.bigNewImg);
        bigNewCategoryTT = view.findViewById(R.id.bigNewCategoryTT);
        bigNewPostTT = view.findViewById(R.id.bigNewPostTT);
        bigNewsHeadingTT = view.findViewById(R.id.bigNewsHeadingTT);
        bigNewLY = view.findViewById(R.id.bigNewLY);
        layout = view.findViewById(R.id.layout);

        (view.findViewById(R.id.layout)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onViewMoreClicked(v, 0);
                }
            }
        });

        (view.findViewById(R.id.tv_view_more_1)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onViewMoreClicked(v, 1);
                }
            }
        });

        (view.findViewById(R.id.tv_view_more_2)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onViewMoreClicked(v, 2);
                }
            }
        });

        tabLayout.setupWithViewPager(viewPager);

        if (categoryId.equalsIgnoreCase("-1")) {
            home.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            layout.setVisibility(View.GONE);
            flashNewsTT.setSelected(true);
            tabLayout.setupWithViewPager(viewPager);
            getHomeCode();

        }
        else {
            home.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            layout.setVisibility(View.VISIBLE);
            if (toCheck.equalsIgnoreCase("sub")){
                getSubNewsData();
            }else {
                getNewListData();
            }

            layout.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), DetailPage.class);
                intent.putExtra("id",id1);
                startActivity(intent);
            });
        }
        return view;
    }


    private void getHomeCode() {
        getTopNew();
        getDeshNews();
        getRajyaNews();
        getVideo();
        getBreakingNews();
        topHeaderNewsLY.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), DetailPage.class);
            intent.putExtra("id",id0);
            startActivity(intent);
        });

    }

    private void getBreakingNews() {
        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Call<JsonObject> call = RetrofitClient.getInstance().getApi().getBreaking();
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                try {
                    String jsonStr = String.valueOf(response.body());
                    JSONObject jsobobj = new JSONObject(jsonStr);
                    String status = jsobobj.getString("status");
                    if (status.equalsIgnoreCase("success")) {
                        JSONArray jsonArray = jsobobj.getJSONArray("data");
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        String breaking = jsonObject.getString("breaking_news");
                        flashNewsTT.setText(breaking);
                        progressDialog.dismiss();
                    } else {
                        Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                Log.e("hsdshhdflhsd", t.toString());
                progressDialog.dismiss();
            }
        });
    }

    private void getRajyaNews() {
        rajyaNewsArray.clear();
        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Call<JsonObject> call = RetrofitClient.getInstance().getApi().getRajyaNews();
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                try {
                    String jsonStr = String.valueOf(response.body());
                    JSONObject jsobobj = new JSONObject(jsonStr);
                    String status = jsobobj.getString("status");
                    if (status.equalsIgnoreCase("success")) {
                        JSONArray jsonArray = jsobobj.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            NewListModel newListModel = new NewListModel();
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String id = jsonObject.getString("id");
                            String news_heading = jsonObject.getString("news_heading");
                            String news_cover_photo = jsonObject.getString("news_cover_photo");
                            String post_date = jsonObject.getString("post_date");
                            String category_name = jsonObject.getString("category_name");
                            newListModel.setNewId(id);
                            newListModel.setNewsHeading(news_heading);
                            newListModel.setNewTime(post_date);
                            newListModel.setNewsPhoto(news_cover_photo);
                            newListModel.setCategoryName(category_name);
                            rajyaNewsArray.add(newListModel);
                        }
                        NewListAdapter adapter = new NewListAdapter(getActivity(), rajyaNewsArray);
                        rajyaRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
                        rajyaRecycler.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        progressDialog.dismiss();
                    } else {
                        Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                Log.e("hsdshhdflhsd", t.toString());
                progressDialog.dismiss();
            }
        });
    }

    private void getDeshNews() {
        deshNewsArray.clear();
        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Call<JsonObject> call = RetrofitClient.getInstance().getApi().getDeshNews();
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                try {
                    String jsonStr = String.valueOf(response.body());
                    JSONObject jsobobj = new JSONObject(jsonStr);
                    String status = jsobobj.getString("status");
                    if (status.equalsIgnoreCase("success")) {
                        JSONArray jsonArray = jsobobj.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            NewListModel newListModel = new NewListModel();
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String id = jsonObject.getString("id");
                            String cat_id = jsonObject.getString("cat_id");
                            String news_heading = jsonObject.getString("news_heading");
                            String news_cover_photo = jsonObject.getString("news_cover_photo");
                            String post_date = jsonObject.getString("post_date");
                            newListModel.setNewId(id);
                            newListModel.setCatId(cat_id);
                            newListModel.setNewsHeading(news_heading);
                            newListModel.setNewTime(post_date);
                            newListModel.setNewsPhoto(news_cover_photo);
                            deshNewsArray.add(newListModel);
                        }
                        HomeAdapter adapter = new HomeAdapter(getActivity(), deshNewsArray);
                        deshRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
                        deshRecycler.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        progressDialog.dismiss();
                    } else {
                        Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                Log.e("hsdshhdflhsd", t.toString());
                progressDialog.dismiss();
            }
        });
    }

    private void getTopNew() {
        topNewsArray.clear();
        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Call<JsonObject> call = RetrofitClient.getInstance().getApi().getTopNews();
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                try {
                    String jsonStr = String.valueOf(response.body());
                    JSONObject jsobobj = new JSONObject(jsonStr);
                    String status = jsobobj.getString("status");
                    if (status.equalsIgnoreCase("success")) {
                        String data = jsobobj.getString("data");
                        JSONObject jsonObjectss = new JSONObject(data);
                        String bigNews = jsonObjectss.getString("bigNews");
                        JSONObject jsonObjects = new JSONObject(bigNews);
                        id0 = jsonObjects.getString("id");
                        String cat_id0 = jsonObjects.getString("cat_id");
                        String subcat_id0 = jsonObjects.getString("subcat_id");
                        String news_video_link0 = jsonObjects.getString("id");
                        String news_heading0 = jsonObjects.getString("news_heading");
                        String news_cover_photo0 = jsonObjects.getString("news_cover_photo");
                        String category_name0 = jsonObjects.getString("category_name");
                        String post_date0 = jsonObjects.getString("post_date");
                        try {
                            topPostTT.setText(SupportUtil.convertDate(post_date0));
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        Picasso.get().load(IMAGE_URL +news_cover_photo0).into(topnewImg);
                        topHeadingTT.setText(news_heading0);
                        topCategoryTT.setText(category_name0);

                        JSONArray jsonArray = jsonObjectss.getJSONArray("listNews");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            NewListModel newListModel = new NewListModel();
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String id = jsonObject.getString("id");
                            String cat_id = jsonObject.getString("cat_id");
                            String news_heading = jsonObject.getString("news_heading");
                            String news_cover_photo = jsonObject.getString("news_cover_photo");
                            String post_date = jsonObject.getString("post_date");
                            String category_name = jsonObject.getString("category_name");
                            newListModel.setNewId(id);
                            newListModel.setCategoryName(cat_id);
                            newListModel.setNewsHeading(news_heading);
                            newListModel.setNewTime(post_date);
                            newListModel.setCategoryName(category_name);
                            newListModel.setNewsPhoto(news_cover_photo);
                            topNewsArray.add(newListModel);
                        }
                        TopNewsAdapter adapter = new TopNewsAdapter(getActivity(), topNewsArray);
                        topNewsRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
                        topNewsRecycler.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        progressDialog.dismiss();
                    } else {
                        Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                Log.e("hsdshhdflhsd", t.toString());
                progressDialog.dismiss();
            }
        });

    }

    private void getNewListData() {
        arrayList.clear();
        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Call<JsonObject> call = RetrofitClient.getInstance().getApi().getNewList(categoryId);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                try {
                    String jsonStr = String.valueOf(response.body());
                    JSONObject jsobobj = new JSONObject(jsonStr);
                    String status = jsobobj.getString("status");
                    if (status.equalsIgnoreCase("success")) {
                        String data = jsobobj.getString("data");
                        JSONObject jsonObjectss = new JSONObject(data);
                        String bigNews = jsonObjectss.getString("bigNews");
                        JSONObject jsonObjects = new JSONObject(bigNews);
                        id1 = jsonObjects.getString("id");
                        String news_heading0 = jsonObjects.getString("news_heading");
                        String news_cover_photo0 = jsonObjects.getString("news_cover_photo");
                        String category_name0 = jsonObjects.getString("category_name");
                        String post_date0 = jsonObjects.getString("post_date");
                        try {
                            bigNewPostTT.setText(SupportUtil.convertDate(post_date0));
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        Picasso.get().load(IMAGE_URL +news_cover_photo0).into(bigNewImg);
                        bigNewsHeadingTT.setText(news_heading0);
                        bigNewCategoryTT.setText(category_name0);
                        JSONArray jsonArray = jsonObjectss.getJSONArray("listNews");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            //yaha bhi add hoga big news
                            NewListModel newListModel = new NewListModel();
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String id = jsonObject.getString("id");
                            String news_heading = jsonObject.getString("news_heading");
                            String news_cover_photo = jsonObject.getString("news_cover_photo");
                            String post_date = jsonObject.getString("post_date");
                            String category_name = jsonObject.getString("category_name");
                            newListModel.setCategoryName(category_name);
                            newListModel.setNewId(id);
                            newListModel.setNewsHeading(news_heading);
                            newListModel.setNewsPhoto(news_cover_photo);
                            newListModel.setNewTime(post_date);
                            arrayList.add(newListModel);
                        }
                        NewListAdapter adapter = new NewListAdapter(getActivity(), arrayList);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        progressDialog.dismiss();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("tyuioiuyfyui", t.toString());
            }
        });
    }

    private void getVideo() {
        specialVideoArray.clear();
        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Call<JsonObject> call = RetrofitClient.getInstance().getApi().getVideoData();
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                try {
                    String jsonStr = String.valueOf(response.body());
                    JSONObject jsobobj = new JSONObject(jsonStr);
                    String status = jsobobj.getString("status");
                    if (status.equalsIgnoreCase("success")) {
                        JSONArray jsonArray = jsobobj.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            NewListModel newListModel = new NewListModel();
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String cat_id = jsonObject.getString("cat_id");
                            String news_video_link = jsonObject.getString("news_video_link");
                            String news_heading = jsonObject.getString("news_heading");
                            String news_cover_photo = jsonObject.getString("news_cover_photo");
                            String post_date = jsonObject.getString("post_date");
                            String category_name = jsonObject.getString("category_name");
                            newListModel.setNewId(cat_id);
                            newListModel.setNewsHeading(news_heading);
                            newListModel.setNewTime(post_date);
                            newListModel.setNewsPhoto(news_cover_photo);
                            newListModel.setNewVideoLink(news_video_link);
                            newListModel.setCategoryName(category_name);
                            specialVideoArray.add(newListModel);
                        }
                        String title = specialVideoArray.get(0).getCategoryName();
                        videoTitle.setText(title);
                        VideoAdapter adapter = new VideoAdapter(getActivity(), specialVideoArray,"fragment",onClickInterface);
                        specialVideoRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
                        specialVideoRecycler.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        progressDialog.dismiss();
                    } else {
                        Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                Log.e("hsdshhdflhsd", t.toString());
                progressDialog.dismiss();
            }
        });

    }

    private void getSubNewsData() {
        arrayList.clear();
        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Call<JsonObject> call = RetrofitClient.getInstance().getApi().getSubCatNewsData(categoryId);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                try {
                    String jsonStr = String.valueOf(response.body());
                    JSONObject jsobobj = new JSONObject(jsonStr);
                    String status = jsobobj.getString("status");
                    if (status.equalsIgnoreCase("success")) {
                        String data = jsobobj.getString("data");
                        JSONObject jsonObjectss = new JSONObject(data);
                        String bigNews = jsonObjectss.getString("bigNews");
                        JSONObject jsonObjects = new JSONObject(bigNews);
                        id1 = jsonObjects.getString("id");
                        String news_heading0 = jsonObjects.getString("news_heading");
                        String news_cover_photo0 = jsonObjects.getString("news_cover_photo");
                        String category_name0 = jsonObjects.getString("category_name");
                        String post_date0 = jsonObjects.getString("post_date");
                        try {
                            bigNewPostTT.setText(SupportUtil.convertDate(post_date0));
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        Picasso.get().load(IMAGE_URL +news_cover_photo0).into(bigNewImg);
                        bigNewsHeadingTT.setText(news_heading0);
                        bigNewCategoryTT.setText(category_name0);
                        JSONArray jsonArray = jsonObjectss.getJSONArray("listNews");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            //yaha bhi add hoga big news
                            NewListModel newListModel = new NewListModel();
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String id = jsonObject.getString("id");
                            String news_heading = jsonObject.getString("news_heading");
                            String news_cover_photo = jsonObject.getString("news_cover_photo");
                            String post_date = jsonObject.getString("post_date");
                            String category_name = jsonObject.getString("subcategory_name");
                            newListModel.setCategoryName(category_name);
                            newListModel.setNewId(id);
                            newListModel.setNewsHeading(news_heading);
                            newListModel.setNewsPhoto(news_cover_photo);
                            newListModel.setNewTime(post_date);
                            arrayList.add(newListModel);
                        }
                        NewListAdapter adapter = new NewListAdapter(getActivity(), arrayList);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        progressDialog.dismiss();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("tyuioiuyfyui", t.toString());
            }
        });
    }

    public static DynamicFragment addfrag(String val,String tochek) {
        DynamicFragment fragment = new DynamicFragment();
        Bundle args = new Bundle();
        args.putString("someInt", val);
        args.putString("tocheck", tochek);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AppCallback.OnViewMoreListener) {
            mListener = (AppCallback.OnViewMoreListener) context;
        }
    }
}