//package com.wts.bharatsamachar;
//
//import android.app.ProgressDialog;
//import android.os.Build;
//import android.os.Bundle;
//import android.text.Html;
//import android.util.Log;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.google.gson.JsonObject;
//import com.squareup.picasso.Picasso;
//import com.wts.bharatsamachar.retrofit.RetrofitClient;
//import com.wts.bharatsamachar.utils.ads.AdsAppCompactActivity;
//
//import org.json.JSONObject;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//import static com.wts.bharatsamachar.retrofit.RetrofitClient.IMAGE_URL;
//
//public class NewDetailsOpen extends AdsAppCompactActivity {
//
//    String id, img;
//    ImageView imgIcon;
//    TextView textNews,textNewsDes;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_new_details_open);
//
//        id = getIntent().getStringExtra("id");
//        img = getIntent().getStringExtra("img");
//        imgIcon = findViewById(R.id.imgIcon);
//        textNews = findViewById(R.id.textNews);
//        textNewsDes = findViewById(R.id.textNewsDes);
//        Picasso.get()
//                .load(IMAGE_URL + img)
//                .into(imgIcon);
//        getNewsDetailOpen();
//
//    }
//
//    private void getNewsDetailOpen() {
//        ProgressDialog progressDialog = new ProgressDialog(NewDetailsOpen.this);
//        progressDialog.setMessage("Loading...");
//        progressDialog.setCancelable(false);
//        progressDialog.show();
//        Call<JsonObject> call = RetrofitClient.getInstance().getApi().getNewDetailData(id);
//        call.enqueue(new Callback<JsonObject>() {
//            @Override
//            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
//                try {
//                    String jsonStr = String.valueOf(response.body());
//                    JSONObject jsobobj = new JSONObject(jsonStr);
//                    String status = jsobobj.getString("status");
//                    if (status.equalsIgnoreCase("success")) {
//                        String obj = jsobobj.getString("data");
//                        JSONObject jsonObject = new JSONObject(obj);
//                        String text = jsonObject.getString("news_heading");
//                        String news_description = jsonObject.getString("news_description");
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                            textNews.setText(Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT));
//                            textNewsDes.setText(Html.fromHtml(news_description, Html.FROM_HTML_MODE_COMPACT));
//                        } else {
//                            textNews.setText(Html.fromHtml(text));
//                            textNewsDes.setText(Html.fromHtml(news_description));
//                        }
////                        textNews.setText(text);
////                        textNewsDes.setText(news_description);
//                    }
//                    progressDialog.dismiss();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    progressDialog.dismiss();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<JsonObject> call, Throwable t) {
//                Toast.makeText(NewDetailsOpen.this, "Something went wrong", Toast.LENGTH_SHORT).show();
//                Log.e("hsdshhdflhsd", t.toString());
//            }
//        });
//    }
//}