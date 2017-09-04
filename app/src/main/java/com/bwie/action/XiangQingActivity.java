package com.bwie.action;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

public class XiangQingActivity extends AppCompatActivity {

    private WebView wv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xiang_qing);
        initView();
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        final String url = intent.getStringExtra("url");
        wv.getSettings().setJavaScriptEnabled(true);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                wv.loadUrl(url);
            }
        });



    }

    private void initView() {

        wv = (WebView) findViewById(R.id.wv);


    }
}
