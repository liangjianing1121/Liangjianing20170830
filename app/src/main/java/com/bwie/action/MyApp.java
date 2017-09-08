package com.bwie.action;

import android.app.Application;
import android.content.Context;

import com.mob.MobSDK;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import org.xutils.view.annotation.ContentView;
import org.xutils.x;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by DELL on 2017/8/30.
 */

public class MyApp extends Application {

    {
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
    }
   private Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        UMShareAPI.get(this);
        initXutils();
        initImageLoader();
        context=this;
        MobSDK.init(this, "20a51956e7bce", "5bec7fcb5852a9935e909022f68efced");

        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }

    /**
     * 初始化imageloader
     */
    private void initImageLoader() {

        DisplayImageOptions options=new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();

        ImageLoaderConfiguration configuration=new ImageLoaderConfiguration.Builder(this)
                .writeDebugLogs()
                .defaultDisplayImageOptions(options)
                .build();
        ImageLoader.getInstance().init(configuration);

    }

    /**
     * 初始化xutils
     */
    private void initXutils() {

        x.Ext.init(this);

    }
}
