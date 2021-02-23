//package com.wts.bharatsamachar;
//
//import android.app.ProgressDialog;
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.widget.Toast;
//
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.google.android.youtube.player.YouTubeBaseActivity;
//import com.google.android.youtube.player.YouTubeInitializationResult;
//import com.google.android.youtube.player.YouTubePlayer;
//import com.google.android.youtube.player.YouTubePlayerView;
//import com.google.gson.JsonObject;
//import com.wts.bharatsamachar.adapter.VideoAdapter;
//import com.wts.bharatsamachar.model.NewListModel;
//import com.wts.bharatsamachar.retrofit.RetrofitClient;
//import com.wts.bharatsamachar.utils.AppConstant;
//import com.wts.bharatsamachar.utils.onClickInterface;
//
//import org.json.JSONArray;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//public class VideoPlay extends YouTubeBaseActivity {
//
//    RecyclerView recyclervideo;
//    ArrayList<NewListModel> specialVideoArray = new ArrayList();
//    onClickInterface onClickInterface;
//    String videoId;
//    YouTubePlayerView youtubePlayerView;
//    YouTubePlayer.OnInitializedListener mOnInitialized;
//    private static final int RECOVERY_REQUEST = 1;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_video_play);
//        videoId = getIntent().getStringExtra("vl");
//        recyclervideo = findViewById(R.id.recyclervideo);
//        youtubePlayerView = findViewById(R.id.youtubePlayerView);
//
//        try {
//            mOnInitialized = new YouTubePlayer.OnInitializedListener() {
//                @Override
//                public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
//                    youTubePlayer.loadVideo(videoId);
//                    YouTubePlayer.PlayerStyle style = YouTubePlayer.PlayerStyle.DEFAULT;
//                    youTubePlayer.setPlayerStyle(style);
//                }
//
//                @Override
//                public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
//
//                    if (youTubeInitializationResult.isUserRecoverableError()) {
//                        youTubeInitializationResult.getErrorDialog(VideoPlay.this, RECOVERY_REQUEST).show();
//                    } else {
//                        String error = youTubeInitializationResult.toString();
//                    }
//                }
//            };
//            youtubePlayerView.initialize(AppConstant.DEVELOPER_KEY, mOnInitialized);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        onClickInterface = vidId -> {
//            videoId = vidId;
//            Intent intent = new Intent(VideoPlay.this,VideoPlay.class);
//            intent.putExtra("vl",videoId);
//            startActivity(intent);
//        };
//
//        getVideo();
//
//    }
//
//    private void getVideo() {
//        ProgressDialog progressDialog = new ProgressDialog(VideoPlay.this);
//        progressDialog.setMessage("Loading...");
//        progressDialog.setCancelable(false);
//        progressDialog.show();
//        Call<JsonObject> call = RetrofitClient.getInstance().getApi().getVideoData();
//        call.enqueue(new Callback<JsonObject>() {
//            @Override
//            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
//                try {
//                    String jsonStr = String.valueOf(response.body());
//                    JSONObject jsobobj = new JSONObject(jsonStr);
//                    String status = jsobobj.getString("status");
//                    if (status.equalsIgnoreCase("success")) {
//                        JSONArray jsonArray = jsobobj.getJSONArray("data");
//                        for (int i = 0; i < jsonArray.length(); i++) {
//                            NewListModel newListModel = new NewListModel();
//                            JSONObject jsonObject = jsonArray.getJSONObject(i);
//                            String id = jsonObject.getString("id");
//                            String cat_id = jsonObject.getString("cat_id");
//                            String news_video_link = jsonObject.getString("news_video_link");
//                            String news_heading = jsonObject.getString("news_heading");
//                            String news_cover_photo = jsonObject.getString("news_cover_photo");
//                            String post_date = jsonObject.getString("post_date");
//                            String category_name = jsonObject.getString("category_name");
//                            newListModel.setNewId(id);
//                            newListModel.setNewId(cat_id);
//                            newListModel.setNewVideoLink(news_video_link);
//                            newListModel.setNewsHeading(news_heading);
//                            newListModel.setNewsPhoto(news_cover_photo);
//                            newListModel.setNewTime(post_date);
//                            newListModel.setCategoryName(category_name);
//                            specialVideoArray.add(newListModel);
//                        }
//                        VideoAdapter adapter = new VideoAdapter(VideoPlay.this, specialVideoArray,"activity",onClickInterface);
//                        recyclervideo.setLayoutManager(new LinearLayoutManager(VideoPlay.this, RecyclerView.VERTICAL, false));
//                        recyclervideo.setAdapter(adapter);
//                        adapter.notifyDataSetChanged();
//                        progressDialog.dismiss();
//                    } else {
//                        Toast.makeText(VideoPlay.this, "Something went wrong", Toast.LENGTH_SHORT).show();
//                        progressDialog.dismiss();
//                    }
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    progressDialog.dismiss();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<JsonObject> call, Throwable t) {
//                Toast.makeText(VideoPlay.this, "Something went wrong", Toast.LENGTH_SHORT).show();
//                Log.e("hsdshhdflhsd", t.toString());
//                progressDialog.dismiss();
//            }
//        });
//
//    }
//
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        Intent intent = new Intent(VideoPlay.this,MainActivity.class);
//        startActivity(intent);
//    }
//}