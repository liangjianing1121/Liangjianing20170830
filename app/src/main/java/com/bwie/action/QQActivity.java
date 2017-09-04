package com.bwie.action;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.xutils.x;

public class QQActivity extends AppCompatActivity {

    private TextView tv_name;
    private ImageView iv_head;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qq);
        initView();
        initData();
    }

    private void initData() {


        Intent intent = getIntent();
        String img = intent.getStringExtra("img");
        String name = intent.getStringExtra("name");

        tv_name.setText(name);
        x.image().bind(iv_head,img);


    }

    private void initView() {
        tv_name = (TextView) findViewById(R.id.tv_name);
        iv_head = (ImageView) findViewById(R.id.iv_head);

    }
}
