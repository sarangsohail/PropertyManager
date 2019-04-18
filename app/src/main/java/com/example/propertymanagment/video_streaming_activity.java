package com.example.propertymanagment;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class video_streaming_activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_streaming_activity);

        try {
            if (isNetworkAvailable()){

                WebView streamView = (WebView) findViewById(R.id.webview);
                WebSettings webSettings = streamView.getSettings();
                webSettings.setJavaScriptEnabled(true);
                streamView.setWebViewClient(new WebViewClient());
                String url = "http://google.com";
                //   myWebView.loadUrl("http://192.168.1.7:8160/index.html");
                Toast.makeText(this, "Loading Stream", Toast.LENGTH_SHORT).show();
                streamView.loadUrl(url);

            }else{
                Toast.makeText(this, "Problem loading your stream, try connecting to the internet", Toast.LENGTH_SHORT).show();
                finish();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

//        myWebView.setWebViewClient(new WebViewClient());
//        myWebView.loadUrl("http://192.168.1.7:8160/index.html");
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();    }
}

