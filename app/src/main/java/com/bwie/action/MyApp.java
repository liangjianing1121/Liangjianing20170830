package com.bwie.action;

import android.app.Application;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.xutils.x;

/**
 * Created by DELL on 2017/8/30.
 */

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initXutils();
        initImageLoader();
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
