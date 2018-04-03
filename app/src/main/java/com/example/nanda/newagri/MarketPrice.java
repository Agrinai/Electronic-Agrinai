package com.example.nanda.newagri;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MarketPrice extends AppCompatActivity {
    private WebView webview1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_price);

        webview1=(WebView)findViewById(R.id.webview1);
        WebSettings web=webview1.getSettings();
        web.setJavaScriptEnabled(true);
        webview1.loadUrl("http://www.livechennai.com/Vegetable_price_chennai.asp");
        webview1.setWebViewClient(new WebViewClient());

    }
}
