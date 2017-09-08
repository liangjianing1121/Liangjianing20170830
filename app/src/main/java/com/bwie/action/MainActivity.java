package com.bwie.action;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.DefaultItemAnimator;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;

import com.andy.library.ChannelActivity;
import com.andy.library.ChannelBean;
import com.google.gson.Gson;
import com.kson.slidingmenu.SlidingMenu;
import com.kson.slidingmenu.app.SlidingFragmentActivity;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import adapter.MyAdapter;
import bean.News;
import bean.News2;
import dao.TitleDao;
import fragment.Fragment1;
import fragment.Fragment10;
import fragment.Fragment2;
import fragment.Fragment3;
import fragment.Fragment4;
import fragment.Fragment5;
import fragment.Fragment6;
import fragment.Fragment7;
import fragment.Fragment8;
import fragment.Fragment9;
import fragment.LeftFragment;
import fragment.RightFragment;
import view.HorizontalScrollViewMenu;

@ContentView(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    private String url="http://v.juhe.cn/toutiao/index";

    private MyAdapter adapter;
    private List<String> list;
    private List<Fragment> fragments;
    private HorizontalScrollViewMenu tabhost;
    private SlidingMenu menu;
    private ImageView jiahao;
    private TitleDao dao;
    private List<ChannelBean> list1;
    private List<Fragment> newfragments;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        x.view().inject(this);
        initView();
        initData();
        initMenu();
    }
    private void initView() {

        jiahao = (ImageView) findViewById(R.id.jiahao);
        jiahao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list1 = new ArrayList<>();
                ChannelBean channelBean;

                SharedPreferences sp = getSharedPreferences("congif", MODE_PRIVATE);
                String json = sp.getString("json", null);


                if(json!=null){
                   // ChannelActivity.startChannelActivity(MainActivity.this,json);

                    try {
                        JSONArray result=new JSONArray(json);
                        for (int i = 0; i < result.length(); i++) {
                            JSONObject resulBean = (JSONObject) result.get(i);
                            String name = resulBean.getString("name");
                            boolean isSelect = resulBean.getBoolean("isSelect");

                            ChannelBean channel=new ChannelBean(name,isSelect);
                            list1.add(channel);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else
                {
                    channelBean=new ChannelBean("头条",true);
                    ChannelBean channelBean2=new ChannelBean("社会",true);
                    ChannelBean channelBean3=new ChannelBean("国内",true);
                    ChannelBean channelBean4=new ChannelBean("国际",true);
                    ChannelBean channelBean5=new ChannelBean("娱乐",true);
                    ChannelBean channelBean6=new ChannelBean("体育",true);
                    ChannelBean channelBean7=new ChannelBean("军事",true);
                    ChannelBean channelBean8=new ChannelBean("科技",true);
                    ChannelBean channelBean9=new ChannelBean("财经",true);
                    ChannelBean channelBean10=new ChannelBean("时尚",true);

                    list1.add(channelBean);
                    list1.add(channelBean2);
                    list1.add(channelBean3);
                    list1.add(channelBean4);
                    list1.add(channelBean5);
                    list1.add(channelBean6);
                    list1.add(channelBean7);
                    list1.add(channelBean8);
                    list1.add(channelBean9);
                    list1.add(channelBean10);



                }

                ChannelActivity.startChannelActivity(MainActivity.this,list1);
            }
        });

    }

    private void initData() {
        list = new ArrayList<>();
        fragments = new ArrayList<>();
        tabhost = (HorizontalScrollViewMenu) findViewById(R.id.tabhost);

        list.add("头条");
        list.add("社会");
        list.add("国内");
        list.add("国际");
        list.add("娱乐");
        list.add("体育");
        list.add("军事");
        list.add("科技");
        list.add("财经");
        list.add("时尚");

        fragments.add(new Fragment1());
        fragments.add(new Fragment2());
        fragments.add(new Fragment3());
        fragments.add(new Fragment4());
        fragments.add(new Fragment5());
        fragments.add(new Fragment6());
        fragments.add(new Fragment7());
        fragments.add(new Fragment8());
        fragments.add(new Fragment9());
        fragments.add(new Fragment10());

        tabhost.display(list,fragments);


        dao = new TitleDao(this);
    }

    private void initMenu() {
        //添加左菜单
        menu = new SlidingMenu(this);
        menu.setMenu(R.layout.fragment1);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment1,new LeftFragment()).commit();



        menu.setMode(SlidingMenu.LEFT_RIGHT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        menu.setBehindOffsetRes(R.dimen.BehindOffsetRes);

        //设置右菜单
        menu.setSecondaryMenu(R.layout.fragment2);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment2,new RightFragment()).commit();

        menu.attachToActivity(this,SlidingMenu.SLIDING_CONTENT);
    }

    public void left(View v){
        menu.showMenu();
    }

    public void right(View v){
        menu.showSecondaryMenu();
    }
    public void login(View v){

        Intent intent=new Intent(this,Main2Activity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==101) {
            String json = data.getExtras().getString("json");
            System.out.println(json);
            SharedPreferences sp = getSharedPreferences("congif", MODE_PRIVATE);
            SharedPreferences.Editor edit = sp.edit();
            edit.putString("json",json);
            edit.commit();
            parseData(json);
        }
    }

    private void parseData(String json) {

        list.clear();
        newfragments = new ArrayList<>();
        try {
            JSONArray result=new JSONArray(json);
            for (int i = 0; i < result.length(); i++) {

                JSONObject resulBean = (JSONObject) result.get(i);
                String name = resulBean.getString("name");
                boolean isSelect = resulBean.getBoolean("isSelect");
                if(isSelect==true)
                {

                    list.add(name);


                    newfragments.add(fragments.get(i));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        tabhost.update();
        tabhost.display(list,newfragments);

    }
}
