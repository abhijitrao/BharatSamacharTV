package com.wts.bharatsamachar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.wts.bharatsamachar.utils.SocialUtil;
import com.wts.bharatsamachar.utils.SupportUtil;
import com.wts.bharatsamachar.utils.ads.AdsAppCompactActivity;

public class DetailPage extends AdsAppCompactActivity {

    WebView browser;
    ImageView backpress,liveTV_Img,searchImg,mainLogo_Img;
    private View viewNoData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_page);
        viewNoData = findViewById(R.id.ll_no_data);
        backpress = findViewById(R.id.backpress);
        liveTV_Img = findViewById(R.id.liveTV_Img);
        searchImg = findViewById(R.id.searchImg);

        try {
            final String url=getIntent().getExtras().getString("id");
            String loadUrl = "https://www.bharatsamachartv.in/web/webnewsdetails/"+url;
            browser = findViewById(R.id.webview);
            browser.getSettings().setLoadsImagesAutomatically(true);
            browser.getSettings().setJavaScriptEnabled(true);
            browser.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
            browser.getSettings().setDomStorageEnabled(true);
            browser.setWebViewClient(new DetailPage.MyWebViewClient(this));
            browser.loadUrl(loadUrl);
        }catch (Exception e){
            e.printStackTrace();
        }

        backpress.setOnClickListener(v -> onBackPressed());

        liveTV_Img.setOnClickListener(v -> startActivity(new Intent(DetailPage.this,LiveTV.class)));

        searchImg.setOnClickListener(v -> {
            Intent intent = new Intent(DetailPage.this,SearchNewsList.class);
            startActivity(intent);
        });

        mainLogo_Img = findViewById(R.id.mainLogo_Img);
        mainLogo_Img.setOnClickListener(v -> {
            Intent intent = new Intent(DetailPage.this,MainActivity.class);
            startActivity(intent);
        });
    }

    class MyWebViewClient extends WebViewClient {
        private Context context;

        public MyWebViewClient(Context context) {
            this.context = context;
        }
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            super.shouldOverrideUrlLoading(view, request);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                String requestUrl = request.getUrl().toString();
                return shouldOverrideUrl(view, requestUrl);
            }
            return false;
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            super.shouldOverrideUrlLoading(view, url);
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                return shouldOverrideUrl(view, url);
            }
            return false;
        }


        @Override
        public void onPageCommitVisible(WebView view, String url) {
            super.onPageCommitVisible(view, url);
            SupportUtil.showNoData(viewNoData, View.GONE);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            SupportUtil.showNoData(viewNoData, View.GONE);
//            webView.setVisibility(View.VISIBLE);
//            if (!isUrlPdfType(url))
//                view.loadUrl("javascript:console.log('" + TAG + "'+document.getElementsByTagName('html')[0].innerHTML);");
        }
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);

        }
    }

    private boolean shouldOverrideUrl(WebView view, String requestUrl) {
        SupportUtil.showNoData(viewNoData, View.GONE);
        if (requestUrl.endsWith("viewer.action=download")) {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(requestUrl));
            startActivity(i);
            return false;
        }
        if (isUrlIntentType(requestUrl) || isUrlWhatsAppType(requestUrl) || isUrlTelegramType(requestUrl) || isUrlFbMessengerType(requestUrl)) {
            SocialUtil.openIntentUrl(DetailPage.this, requestUrl);
            view.stopLoading();
            view.goBack();
            return false;
        }
        if (isUrlFacebookType(requestUrl) || isUrlTwitterType(requestUrl)) {
            SocialUtil.openUrlExternal(DetailPage.this, requestUrl);
            view.stopLoading();
            view.goBack();
            return false;
        }
        view.loadUrl(requestUrl);
        return true;
    }

    //https://www.facebook.com/sharer.php?t=%E0%A4%95%E0%A5%87%E0%A4%B5%E0%A4%A1%E0%A4%BF%E0%A4%AF%E0%A4%BE%20%E0%A4%95%E0%A5%8B%20%E0%A4%B6%E0%A5%87%E0%A4%B7%20%E0%A4%AD%E0%A4%BE%E0%A4%B0%E0%A4%A4%20%E0%A4%B8%E0%A5%87%20%E0%A4%9C%E0%A5%8B%E0%A4%A1%E0%A4%BC%E0%A4%A8%E0%A5%87%20%E0%A4%B5%E0%A4%BE%E0%A4%B2%E0%A5%80%208%20%E0%A4%9F%E0%A5%8D%E0%A4%B0%E0%A5%87%E0%A4%A8%E0%A5%8B%E0%A4%82%20%E0%A4%95%E0%A5%8B%20%E0%A4%AA%E0%A5%80%E0%A4%8F%E0%A4%AE%20%E0%A4%A8%E0%A5%87%20%E0%A4%A6%E0%A4%BF%E0%A4%96%E0%A4%BE%E0%A4%88%20%E0%A4%B9%E0%A4%B0%E0%A5%80%20%E0%A4%9D%E0%A4%82%E0%A4%A1%E0%A5%80%E0%A5%A4&u=https%3A%2F%2Fwww.bharatsamachartv.in%2Fweb%2Fwebnewsdetails%2F828
    private boolean isUrlFacebookType(String url) {
        return url.toLowerCase().startsWith("https://www.facebook.com");
    }

     //whatsapp://send?text=https%3A%2F%2Fwww.bharatsamachartv.in%2Fweb%2Fwebnewsdetails%2F828
    private boolean isUrlWhatsAppType(String url) {
        return url.toLowerCase().startsWith("whatsapp://");
    }

    //tg:msg_url?url=https%3A%2F%2Fwww.bharatsamachartv.in%2Fweb%2Fwebnewsdetails%2F828&text=%E0%A4%95%E0%A5%87%E0%A4%B5%E0%A4%A1%E0%A4%BF%E0%A4%AF%E0%A4%BE%20%E0%A4%95%E0%A5%8B%20%E0%A4%B6%E0%A5%87%E0%A4%B7%20%E0%A4%AD%E0%A4%BE%E0%A4%B0%E0%A4%A4%20%E0%A4%B8%E0%A5%87%20%E0%A4%9C%E0%A5%8B%E0%A4%A1%E0%A4%BC%E0%A4%A8%E0%A5%87%20%E0%A4%B5%E0%A4%BE%E0%A4%B2%E0%A5%80%208%20%E0%A4%9F%E0%A5%8D%E0%A4%B0%E0%A5%87%E0%A4%A8%E0%A5%8B%E0%A4%82%20%E0%A4%95%E0%A5%8B%20%E0%A4%AA%E0%A5%80%E0%A4%8F%E0%A4%AE%20%E0%A4%A8%E0%A5%87%20%E0%A4%A6%E0%A4%BF%E0%A4%96%E0%A4%BE%E0%A4%88%20%E0%A4%B9%E0%A4%B0%E0%A5%80%20%E0%A4%9D%E0%A4%82%E0%A4%A1%E0%A5%80%E0%A5%A4
    private boolean isUrlTelegramType(String url) {
        return url.toLowerCase().startsWith("tg:msg_url");
    }

    //https://twitter.com/intent/tweet?text=%E0%A4%95%E0%A5%87%E0%A4%B5%E0%A4%A1%E0%A4%BF%E0%A4%AF%E0%A4%BE%20%E0%A4%95%E0%A5%8B%20%E0%A4%B6%E0%A5%87%E0%A4%B7%20%E0%A4%AD%E0%A4%BE%E0%A4%B0%E0%A4%A4%20%E0%A4%B8%E0%A5%87%20%E0%A4%9C%E0%A5%8B%E0%A4%A1%E0%A4%BC%E0%A4%A8%E0%A5%87%20%E0%A4%B5%E0%A4%BE%E0%A4%B2%E0%A5%80%208%20%E0%A4%9F%E0%A5%8D%E0%A4%B0%E0%A5%87%E0%A4%A8%E0%A5%8B%E0%A4%82%20%E0%A4%95%E0%A5%8B%20%E0%A4%AA%E0%A5%80%E0%A4%8F%E0%A4%AE%20%E0%A4%A8%E0%A5%87%20%E0%A4%A6%E0%A4%BF%E0%A4%96%E0%A4%BE%E0%A4%88%20%E0%A4%B9%E0%A4%B0%E0%A5%80%20%E0%A4%9D%E0%A4%82%E0%A4%A1%E0%A5%80%E0%A5%A4&url=https%3A%2F%2Fwww.bharatsamachartv.in%2Fweb%2Fwebnewsdetails%2F828
    private boolean isUrlTwitterType(String url) {
        return url.toLowerCase().startsWith("https://twitter.com");
    }

    //fb-messenger://share/?link=https%3A%2F%2Fwww.bharatsamachartv.in%2Fweb%2Fwebnewsdetails%2F828&app_id=521270401588372
    private boolean isUrlFbMessengerType(String url) {
        return url.toLowerCase().startsWith("fb-messenger://");
    }

    private boolean isUrlIntentType(String url) {
        return url.toLowerCase().startsWith("intent://");
    }


    @Override
    public void onBackPressed() {
        if (browser.canGoBack()) {
            browser.goBack();
        } else {
            super.onBackPressed();
        }
    }
}