package com.bwie.action;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.xutils.x;

import java.util.Map;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {

    private EventHandler eventHandler;
    private EditText et_phone;
    private EditText et_code;
    private TextView tv_getcode;
    private Button bt_login;
    private int TIME=5;
    private int SECOND=1000;

    private Handler timeHandler=new Handler();

    Runnable timerRunnable=new Runnable() {
        @Override
        public void run() {
            TIME--;
            if(TIME==0)
            {
                timeHandler.removeCallbacks(this);
                TIME=5;
                tv_getcode.setEnabled(true);
                tv_getcode.setText("再次获取");
            }
            else
            {
                tv_getcode.setEnabled(false);
                tv_getcode.setText(TIME+"S");
                tv_getcode.setTextColor(Color.RED);
                timeHandler.postDelayed(this,SECOND);
            }
        }
    };
    private ImageView qq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initView();
        registerMSM();
    }

    private void initView() {
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_code = (EditText) findViewById(R.id.et_code);
        tv_getcode = (TextView) findViewById(R.id.tv_getcode);
        bt_login = (Button) findViewById(R.id.bt_login);

        tv_getcode.setOnClickListener(this);
        bt_login.setOnClickListener(this); 

        qq = (ImageView) findViewById(R.id.qq);
    }



    public void qqclick(View v){

        UMShareAPI.get(this).getPlatformInfo(Main2Activity.this, SHARE_MEDIA.QQ, umAuthListener);

    }
    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //授权开始的回调
        }
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            Toast.makeText(getApplicationContext(), "Authorize succeed", Toast.LENGTH_SHORT).show();

            String iconurl = data.get("iconurl");
            String name = data.get("name");
            Intent intent =new Intent(Main2Activity.this,QQActivity.class);
            intent.putExtra("name",name);
            intent.putExtra("img",iconurl);
            startActivity(intent);


        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText( getApplicationContext(), "Authorize fail", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText( getApplicationContext(), "Authorize cancel", Toast.LENGTH_SHORT).show();
        }
    };




    public void exit(View v){
        finish();
    }

    /**
     * 监听事件
     * @param view
     */
    @Override
    public void onClick(View view) {


        switch (view.getId())
        {

            case R.id.tv_getcode:

                if(TextUtils.isEmpty(et_phone.getText().toString()))
                {
                    Toast.makeText(Main2Activity.this,"请填写手机号",Toast.LENGTH_SHORT).show();
                    return;
                }

                SMSSDK.getVerificationCode("86",et_phone.getText().toString());
                timeHandler.postDelayed(timerRunnable,SECOND);


                break;
            case R.id.bt_login:

                if(TextUtils.isEmpty(et_phone.getText().toString()))
                {
                    Toast.makeText(Main2Activity.this,"请填写手机号",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(TextUtils.isEmpty(et_code.getText().toString()))
                {
                    Toast.makeText(Main2Activity.this,"请填验证码",Toast.LENGTH_SHORT).show();
                    return;
                }
                SMSSDK.submitVerificationCode("86",et_phone.getText().toString(),et_code.getText().toString());

                Intent intent=new Intent(this,LoginActivity.class);
                startActivity(intent);

                break;
        }
    }



    private void registerMSM() {

        // 创建EventHandler对象
        eventHandler = new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (data instanceof Throwable) {
                    Throwable throwable = (Throwable)data;
                    final String msg = throwable.getMessage();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Main2Activity.this, msg, Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {
                    if (result == SMSSDK.RESULT_COMPLETE) {
                        //回调完成
                        if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(Main2Activity.this,"获取验证码成功",Toast.LENGTH_SHORT).show();
                                }
                            });
                            //提交验证码成功
                        }else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                            //获取验证码成功
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(Main2Activity.this,"验证码已发送",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }else if (event ==SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){
                            //返回支持发送验证码的国家列表
                        }
                    }
                }
            }
        };
        // 注册监听器
        SMSSDK.registerEventHandler(eventHandler);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

}
