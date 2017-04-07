package cit.fyp.dk.betting_app.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import cit.fyp.dk.betting_app.R;

/**
 * Created by davyk on 01/04/2017.
 */

public class HorseDetailActivity extends AppCompatActivity {

    private String urlQueryPrefix = "https://duckduckgo.com/?q=!ducky+";
    private String urlQuerySuffix = "+site%3Aracingpost.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horse_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        WebView webView = (WebView) findViewById(R.id.webView);
        String horseName = getIntent().getStringExtra("horse");
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(urlQueryPrefix);
        horseName = horseName.replace(" ", "-");
        urlBuilder.append(horseName);
        urlBuilder.append(urlQuerySuffix);
        Log.d("DETAIL", urlBuilder.toString());
        webView.loadUrl(urlBuilder.toString());
        webView.getSettings().setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });
    }

}
