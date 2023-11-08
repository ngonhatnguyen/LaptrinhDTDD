package com.example.readrss;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class NewsActivity extends AppCompatActivity {
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        webView = findViewById(R.id.webViewNews);

        Intent intent = getIntent();
        String link = intent.getStringExtra("linkTinTuc");
        webView.loadUrl(link);
        webView.setWebViewClient(new WebViewClient());
    }
}